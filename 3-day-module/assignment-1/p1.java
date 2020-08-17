import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Solution {
  public static void main(String[] args) throws IOException {
    Reader in = new Reader(System.in);
    int t = in.nextInt();
    while(t-- > 0) {
      int n = in.nextInt();
      int arr[] = new int[n];
      for (int i=0;i<n;i++) arr[i] = in.nextInt();
      int x = in.nextInt();
      if (x < 1 || x > n) {
        System.out.println("False");
        System.out.println(0);
        for (int k : arr) System.out.print(k + " ");
      } else {
        System.out.println("True");
        System.out.println(n - x + 1);
        for (int j = 0; j<n;j++) {
          if (j == x) continue;
          System.out.print(arr[j] + " ");
        }
      }
      System.out.println();
    }
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
