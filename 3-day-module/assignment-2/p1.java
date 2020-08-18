import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Solution {
  public static void main(String[] args) throws IOException {
    Reader in = new Reader(System.in);
    Student[] students = new Student[10];
    for (int i = 0; i < 5; i++) {
      students[i] = new Student();
    }
    students[5] = new Student("Ananya Lohani", 19, Student.latestRollNo + 1, "CSE");
    students[6] = new Student("Mihir Chaturvedi", 20,  Student.latestRollNo + 1, "CSE");
    students[7] = new Student("Ansh Arora", 19,  Student.latestRollNo + 1, "ECE");
    students[8] = new Student("Rishit Gupta", 20,  Student.latestRollNo + 1, "CSD");
    students[9] = new Student("Jishnu Parashar", 19,  Student.latestRollNo + 1, "CSAM");

    for (Student s : students) s.display();
  }
}

class Student {
  private String name;
  private int age;
  private int rollNo;
  private String branch;

  public static int latestRollNo = 2018999;

  public void display () {
    System.out.println("Name: " + this.name);
    System.out.println("Age: " + this.age);
    System.out.println("Roll no: " + this.rollNo);
    System.out.println("Branch: " + this.branch);
    System.out.println();
  }

  Student () {
    this.name = "Raghav Sharma";
    this.age = 20;
    this.rollNo = ++this.latestRollNo;
    this.branch = "CSD";
  }

  Student (String name, int age, int rollNo, String branch) {
    this.name = name;
    this.age = age;
    this.rollNo = this.latestRollNo = rollNo;
    this.branch = branch;
  }
}

class Reader {
  static BufferedReader reader;
  static StringTokenizer tokenizer;

  Reader(InputStream input) {
    reader = new BufferedReader(new InputStreamReader(input));
    tokenizer = new StringTokenizer("");
  }

  static String next() throws IOException {
    while (!tokenizer.hasMoreTokens()) {
      tokenizer = new StringTokenizer(reader.readLine());
    }
    return tokenizer.nextToken();
  }

  static int nextInt() throws IOException {
    return Integer.parseInt(next());
  }

  static double nextDouble() throws IOException {
    return Double.parseDouble(next());
  }

  static long nextLong() throws IOException {
    return Long.parseLong(next());
  }
}
