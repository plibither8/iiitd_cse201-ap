import java.util.*;

public class Application {
  private static ArrayList<Patient> patients;
  private static ArrayList<String> instituteNames;
  private static HashMap<String, Institute> institutes;
  private static int numberOfPatients;
  private static Scanner input = new Scanner(System.in);

  private static class Operations {
    static void addInstitute() {
      String name;
      float maxTemp;
      float minO2;
      int vacancy;

      System.out.print("Institute name: ");
      name = input.next();

      System.out.print("Capacity: ");
      vacancy = input.nextInt();

      System.out.print("Criterion 1: Min O2 levels: ");
      minO2 = input.nextFloat();

      System.out.print("Criterion 2: Max body temperature: ");
      maxTemp = input.nextFloat();

      Institute institute = new Institute(name, vacancy, maxTemp, minO2);
      institutes.put(name, institute);
      instituteNames.add(name);

      institute.displayDetails();

      Admission.admitAgainstO2(institute, minO2);
      Admission.admitAgainstTemp(institute, maxTemp);
    };

    static void removeAdmittedPatients() {
      boolean deleted = false;
      for (Patient patient : patients) {
        if (patient != null && patient.isAdmitted()) {
          System.out.println("Patient " + patient.getId() + " deleted.");
          deleted = true;
          patient = null;
        }
      }
      if (!deleted) {
        System.out.println("No accounts deleted.");
      }
    };

    static void removeClosedInstitutes() {
      boolean deleted = false;
      for (String name : instituteNames) {
        Institute institute = institutes.get(name);
        if (institute != null && !institute.isOpen()) {
          System.out.println(institute.getName() + " deleted.");
          deleted = true;
          institute = null;
        }
      }
      if (!deleted) {
        System.out.println("No accounts deleted.");
      }
    };

    static void displayUnadmittedPatientsCount() {
      int count = getUnadmittedPatientsCount();
      System.out.println(count + " patients.");
    };

    static void displayOpenInstitutesCount() {
      int count = 0;
      for (String name : instituteNames) {
        Institute institute = institutes.get(name);
        if (institute != null && institute.isOpen()) {
          count++;
        }
      }
      System.out.println(count + " institutes are admitting patients currently.");
    };

    static void displayInstituteDetails() {
      String instituteName = input.next();
      Institute institute = institutes.get(instituteName);
      institute.displayDetails();
    };

    static void displayPatientDetails() {
      int id = input.nextInt();
      Patient patient = patients.get(id - 1);
      patient.displayDetails();
    };

    static void displayAllPatients() {
      for (Patient patient : patients) {
        patient.displayIdAndName();
      }
    };

    static void displayInstitutePatients() {
      String instituteName = input.next();
      Institute institute = institutes.get(instituteName);
      institute.displayPatientDetails();
    };

    static int getUnadmittedPatientsCount() {
      int count = 0;
      for (Patient patient : patients) {
        if (!patient.isAdmitted()) {
          count++;
        }
      }
      return count;
    }
  }

  private static class Admission {
    public static void admitAgainstO2(Institute institute, float minO2) {
      for (Patient patient : patients) {
        if (!institute.isOpen()) break;
        if (patient.isEligibleAgainstO2(minO2)) {
          System.out.print("Recovery days for patient " + patient.getId() + ": ");
          int recoveryDays = input.nextInt();
          patient.assignInstitute(institute.getName(), recoveryDays);
          institute.addPatient(patient);
        }
      }
    }

    public static void admitAgainstTemp(Institute institute, float maxTemp) {
      for (Patient patient : patients) {
        if (!institute.isOpen()) break;
        if (patient.isEligibleAgainstTemp(maxTemp)) {
          System.out.print("Recovery days for patient " + patient.getId() + ": ");
          int recoveryDays = input.nextInt();
          patient.assignInstitute(institute.getName(), recoveryDays);
          institute.addPatient(patient);
        }
      }
    }
  }

  private static void inputPrompt() {
    while (Operations.getUnadmittedPatientsCount() > 0) {
      System.out.print("\nEnter input choice (1-9): ");
      int choice = input.nextInt();
      switch (choice) {
        case 1: {
          Operations.addInstitute();
          break;
        }
        case 2: {
          Operations.removeAdmittedPatients();
          break;
        }
        case 3: {
          Operations.removeClosedInstitutes();
          break;
        }
        case 4: {
          Operations.displayUnadmittedPatientsCount();
          break;
        }
        case 5: {
          Operations.displayOpenInstitutesCount();
          break;
        }
        case 6: {
          Operations.displayInstituteDetails();
          break;
        }
        case 7: {
          Operations.displayPatientDetails();
          break;
        }
        case 8: {
          Operations.displayAllPatients();
          break;
        }
        case 9: {
          Operations.displayInstitutePatients();
          break;
        }
      }
    }
  }

  private static void getPatients() {
    System.out.print("Number of patients: ");
    numberOfPatients = input.nextInt();

    for (int i = 0; i < numberOfPatients; i++) {
      String name;
      int age;
      float temp;
      float o2;

      System.out.println("\nPatient " + (i + 1));
      System.out.println("=========");

      System.out.print("Name: ");
      name = input.next();

      System.out.print("Age: ");
      age = input.nextInt();

      System.out.print("Body temperature (F): ");
      temp = input.nextFloat();

      System.out.print("Oxygen level (%): ");
      o2 = input.nextFloat();

      Patient patient = new Patient(name, age, temp, o2);
      patients.add(patient);
    }
  }

  public static void main(String[] args) {
    patients = new ArrayList<Patient>();
    instituteNames = new ArrayList<String>();
    institutes = new HashMap<String, Institute>();

    getPatients();
    inputPrompt();

    input.close();
  }
}

class Institute {
  private String name;
  private String status;
  private boolean isOpen;
  private ArrayList<Patient> patients;
  private int vacancy;
  private float maxTemp;
  private float minO2;

  public void displayDetails() {
    System.out.println("Name: " + this.name);
    System.out.println("Temperature should be <= " + this.maxTemp);
    System.out.println("Oxygen levels should be >= " + this.minO2);
    System.out.println("Number of available beds: " + this.vacancy);
    System.out.println("Admission status: " + this.status);
  }

  public void addPatient(Patient patient) {
    this.patients.add(patient);
    this.vacancy--;
    if (this.vacancy == 0) {
      this.status = "CLOSED";
      this.isOpen = false;
    }
  }

  public void displayPatientDetails() {
    for (Patient patient : this.patients) {
      patient.displayRecoveryDate();
    }
  }

  public boolean isOpen() {
    return this.isOpen;
  }

  public String getName() {
    return this.name;
  }

  Institute(String name, int vacancy, float maxTemp, float minO2) {
    this.name = name;
    this.vacancy = vacancy;
    this.maxTemp = maxTemp;
    this.minO2 = minO2;
    this.patients = new ArrayList<Patient>();
    this.isOpen = true;
    this.status = "OPEN";
  }
}

class Patient {
  private int id;
  private String name;
  private int age;
  private float temp;
  private float o2;
  private String instituteName;
  private int recoveryDays;
  private boolean isAdmitted;

  static private int lastPatientId = 0;

  public void displayDetails() {
    System.out.println("Name: " + this.name);
    System.out.println("Age: " + this.age);
    System.out.println("Body temperature: " + this.temp);
    System.out.println("Oxygen level: " + this.o2);
    System.out.println("Admission status: " + (this.isAdmitted ? "ADMITTED" : "NOT ADMITTED"));
    if (this.isAdmitted) {
      System.out.println("Admitting institute: " + this.instituteName);
    }
  }

  public void displayIdAndName() {
    System.out.println(this.id + ": " + this.name);
  }

  public void displayRecoveryDate() {
    System.out.println(this.name + ": recovery in " + this.recoveryDays + " days");
  }

  public boolean isAdmitted() {
    return this.isAdmitted;
  }

  public boolean isEligibleAgainstO2(float minO2) {
    return !this.isAdmitted && this.o2 >= minO2;
  }

  public boolean isEligibleAgainstTemp(float maxTemp) {
    return !this.isAdmitted && this.temp <= maxTemp;
  }

  public void assignInstitute(String instituteName, int recoveryDays) {
    this.isAdmitted = true;
    this.instituteName = instituteName;
    this.recoveryDays = recoveryDays;
  }

  public int getId() {
    return this.id;
  }

  Patient(String name, int age, float temp, float o2) {
    this.name = name;
    this.age = age;
    this.temp = temp;
    this.o2 = o2;
    this.id = ++lastPatientId;
  }
}
