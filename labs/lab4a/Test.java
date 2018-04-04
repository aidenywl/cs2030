import java.util.*;
import java.util.stream.Stream;

class Test {
  public static void main(String[] args) {
    Scanner reader = new Scanner(System.in);
    reader.nextInt();
    Stream<String> str = reader.tokens();
    System.out.println(str);
  }
}
