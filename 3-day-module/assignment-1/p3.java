import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.math.BigInteger; 

public class Solution {
  static void prime(BigInteger n) {
    Boolean check = false;

    BigInteger two = new BigInteger("2");
    BigInteger three = new BigInteger("3");
    BigInteger root = n.sqrt().add(BigInteger.ONE);

    while (n.mod(two).compareTo(BigInteger.ZERO) == 0) {
      if (check) {
        System.out.print("* ");
      }
      n = n.divide(two);
      System.out.print(2 + " ");
      check = true;
    }

    for (BigInteger i = three; i.compareTo(root) == -1; i = i.add(two)) {
      while (n.mod(i).compareTo(BigInteger.ZERO) == 0) {
        if (check) {
          System.out.print("* ");
        }
        n = n.divide(i);
        System.out.print(i + " ");
        check = true;
      }
    }

    if (n.compareTo(two) == 1) {
      if (check) {
        System.out.print("* ");
      }
      System.out.println(n);
    }
  }

  public static void main(String[] args) throws IOException {
    Reader in = new Reader(System.in);
    String s = args[0];
    BigInteger n = new BigInteger(Long.toString(s.length()));
    prime(n);
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
