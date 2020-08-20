import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

class Application extends JFrame {
  static private Patient[] patients;
  static private JFrame frame;
  static private JPanel mainPanel;
  static private JPanel optionsPanel;
  static private JLabel dateInputLabel;
  static private JTextField dateTextField;
  static private JLabel towerCheckboxLabel;
  static private JCheckBox chA;
  static private JCheckBox chB;
  static private JCheckBox chC;
  static private JCheckBox chD;
  static private JButton checkStatusBtn;
  static private JTable mainTable;
  static private JTable infoTable;
  static private DefaultTableModel mainDtm;
  static private DefaultTableModel infoDtm;
  static private JScrollPane mainScrollPane;
  static private JScrollPane infoScrollPane;
  static private GridBagConstraints mainC;

  public static void main(String[] args) {
    patients = new Patient[20];
    for (int i = 0; i < 20; i++) {
      patients[i] = new Patient(List.names[i], List.ages[i], List.towers[i], List.dates[i]);
    }

    frame = new JFrame("COVID-19 Status");
    mainC = new GridBagConstraints();
    mainC.insets = new Insets(20, 20, 0, 0);

    mainPanel = new JPanel(new GridBagLayout());
    optionsPanel = new JPanel();

    dateInputLabel = new JLabel("Enter a date (dd/mm/yyyy)");
    dateTextField = new JTextField(10);
    towerCheckboxLabel = new JLabel("Choose towers");
    chA = new JCheckBox("A");
    chB = new JCheckBox("B");
    chC = new JCheckBox("C");
    chD = new JCheckBox("D");
    checkStatusBtn = new JButton("Check status");

    String[] mainTableHeader = {"Name", "Age", "Tower", "Status", "Date of Reporting", "Date of Recovery"};
    mainTable = new JTable();
    mainDtm = new DefaultTableModel(0, 0);
    mainDtm.setColumnIdentifiers(mainTableHeader);
    mainTable.setFocusable(false);
    mainTable.setRowSelectionAllowed(false);
    mainTable.setModel(mainDtm);
    mainScrollPane = new JScrollPane(mainTable);
    mainScrollPane.setPreferredSize(new Dimension(700, 300));
    resizeColumnWidth(mainTable);

    String[] infoTableHeader = {"Tower", "Active", "Recovered"};
    infoTable = new JTable();
    infoDtm = new DefaultTableModel(0, 0);
    infoDtm.setColumnIdentifiers(infoTableHeader);
    infoTable.setFocusable(false);
    infoTable.setModel(infoDtm);
    infoTable.setRowSelectionAllowed(false);
    infoScrollPane = new JScrollPane(infoTable);
    infoScrollPane.setPreferredSize(new Dimension(250, 90));
    resizeColumnWidth(infoTable);

    checkStatusBtn.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e) {
        mainDtm.setRowCount(0);
        infoDtm.setRowCount(0);

        Date inputDate = Date.dateFromString(dateTextField.getText());
        Boolean[] selectedTowers = { chA.isSelected(), chB.isSelected(), chC.isSelected(), chD.isSelected() };
        int[][] towerCount = { { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } };

        for (Patient p : patients) {
          int towerIndex = p.tower - 65;
          if (p.dateOfReporting.compare(inputDate) == 1 || !selectedTowers[towerIndex]) continue;
          Date plus21 = p.dateOfReporting.add21();
          String status;
          String recoveryDate = plus21.toString();

          if (plus21.compare(inputDate) <= 0) {
            status = "RECOVERED";
            towerCount[towerIndex][1]++;
          }
          else {
            status = "ACTIVE";
            towerCount[towerIndex][0]++;
          }

          mainDtm.addRow(new Object[] { p.name, p.age, p.tower, status, p.dateOfReporting.toString(), recoveryDate });
        }

        for (int i = 0; i < 4; i++) {
          if (!selectedTowers[i]) continue;
          infoDtm.addRow(new Object[] { (char)(i + 65), towerCount[i][0], towerCount[i][1] });
        }

        resizeColumnWidth(mainTable);
        resizeColumnWidth(infoTable);
      }
    });

    mainC.gridx = mainC.gridy = 0;
    optionsPanel.add(dateInputLabel);
    optionsPanel.add(dateTextField);
    optionsPanel.add(towerCheckboxLabel);
    optionsPanel.add(chA);
    optionsPanel.add(chB);
    optionsPanel.add(chC);
    optionsPanel.add(chD);
    optionsPanel.add(checkStatusBtn);
    optionsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    mainPanel.add(optionsPanel, mainC);

    mainC.gridy = 1;
    mainPanel.add(mainScrollPane, mainC);

    mainC.gridy = 2;
    mainPanel.add(infoScrollPane, mainC);

    frame.add(mainPanel);

    frame.setSize(800, 600);
    frame.setVisible(true);
  }

  public static void resizeColumnWidth(JTable table) {
    final TableColumnModel columnModel = table.getColumnModel();
    for (int column = 0; column < table.getColumnCount(); column++) {
      int width = 15; // Min width
      for (int row = 0; row < table.getRowCount(); row++) {
        TableCellRenderer renderer = table.getCellRenderer(row, column);
        Component comp = table.prepareRenderer(renderer, row, column);
        width = Math.max(comp.getPreferredSize().width + 1, width);
      }
      if (width > 300)
        width = 300;
      columnModel.getColumn(column).setPreferredWidth(width);
    }
  }
}

class Date {
  private int[] daysInMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

  int d, m, y;

  public static Date dateFromString (String dateString) {
    int dd = Integer.parseInt(dateString.substring(0, 2));
    int mm = Integer.parseInt(dateString.substring(3, 5));
    int yy = Integer.parseInt(dateString.substring(6, 10));
    return new Date(dd, mm, yy);
  }

  public String toString () {
    String date = "";
    date += (this.d < 10 ? "0" : "") + this.d + "/";
    date += (this.m < 10 ? "0" : "") + this.m + "/";
    date += this.y;
    return date;
  }

  int compare (Date date) {
    if (this.y == date.y && this.m == date.m && this.d == date.d) return 0;
    if (this.y < date.y
      || this.y == date.y && this.m < date.m
      || this.y == date.y && this.m == date.m && this.d < date.d
    ) return -1;
    return 1;
  }

  Date add21 () {
    int newDate = this.d + 21;
    int newMonth = this.m;
    if (newDate > daysInMonth[newMonth - 1]) {
      newDate -= daysInMonth[newMonth - 1];
      newMonth++;
    }
    return new Date(newDate, newMonth, 2020);
  }

  Date (int d, int m, int y) {
    this.d = d;
    this.m = m;
    this.y = y;
  }
}

class Patient {
  String name;
  int age;
  char tower;
  Date dateOfReporting;

  Patient (String name, int age, char tower, int[] date) {
    this.name = name;
    this.age = age;
    this.tower = tower;
    this.dateOfReporting = new Date(date[0], date[1], date[2]);
  }
}

class List {
  public static String[] names = {
    "Flora",
    "Denys",
    "Jim",
    "Hazel",
    "Caery",
    "David",
    "Kevim",
    "Tom",
    "Bob",
    "Rachel",
    "Thomas",
    "Mary",
    "Smith",
    "Pearson",
    "Anderson",
    "Johnson",
    "Robertz",
    "Julie",
    "Edith",
    "John"
  };
  public static int[] ages = { 6, 24, 42, 87, 72, 7, 37, 67, 74, 48, 21, 17, 89, 47, 62, 10, 50, 86, 42, 95 };
  public static char[] towers = { 'A', 'B', 'C', 'D', 'A', 'B', 'D', 'D', 'A', 'C', 'C', 'D', 'A', 'B', 'B', 'D', 'A', 'B', 'D', 'D' };
  public static int[][] dates = {
    {1, 4, 2020},
    {1, 4, 2020},
    {18, 5, 2020},
    {23, 6, 2020},
    {1, 6, 2020},
    {14, 6, 2020},
    {5, 6, 2020},
    {20, 6, 2020},
    {4, 7, 2020},
    {24, 7, 2020},
    {11, 6, 2020},
    {21, 6, 2020},
    {7, 8, 2020},
    {4, 6, 2020},
    {27, 7, 2020},
    {1, 8, 2020},
    {9, 8, 2020},
    {2, 5, 2020},
    {7, 6, 2020},
    {1, 6, 2020}
  };
}
