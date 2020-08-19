// import javax.swing.*;

class Application {
  public static void main(String[] args) {
    Patient[] patients = new Patient[20];
    for (int i = 0; i < 20; i++) {
      patients[i] = new Patient(List.names[i], List.ages[i], List.towers[i], List.dates[i]);
    }
  }
}

class Date {
  int d, m, y;

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
