public class A {
  public static void main(String args[]) {
    A a = new A();
    a.s();
  }
  void s() {
    Boolean b1 = true;
    Boolean b2 = c(b1);
    System.out.println(b1 + " " + b2);
  }
  Boolean c(Boolean k) {
    k=false;
    return k;
  }
}
