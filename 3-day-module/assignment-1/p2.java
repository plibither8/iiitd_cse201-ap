import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Solution {
  public static void main(String[] args) throws IOException {
    Reader in = new Reader(System.in);
    String s = in.next();

    int prevChar = -1,
        currChar = -1;
    int prevCharCount = 1;
    String newS = "";

    for (int i = 0; i < s.length(); i++) {
      currChar = s.charAt(i);

      if (i == 0) {
        prevChar = currChar;
        continue;
      }

      if (prevChar == currChar) {
        prevCharCount++;
      } else {
        newS += "" + (char)prevChar + prevCharCount;
        prevCharCount = 1;
      }

      prevChar = currChar;
    }

    newS += "" + (char)prevChar + prevCharCount;
    System.out.println(newS);
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
