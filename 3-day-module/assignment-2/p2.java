import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Solution {
  public static void main(String[] args) throws IOException {
    Reader in = new Reader(System.in);
    LinkedList L = new LinkedList();
  }
}

class Node {
  int data;
  Node next;

  Node (int data) {
    this.data = data;
  }
}

class LinkedList {
  private static int count = 0;
  private static Node header;
  private static Node footer;

  LinkedList (int data) {
    this.count++;
    Node n = new Node(data);
    if (this.count == 1) {
      this.header = n;
    }

    this.footer.next = n;
    this.footer = n;
  }

  LinkedList (Node node) {
    this.count++;
    if (this.count == 1) {
      this.header = node;
    }

    this.footer.next = node;
    this.footer = node;
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
