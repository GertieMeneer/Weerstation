
import java.time.LocalDate;
import java.util.Scanner;

public class Test {
  public static void main(String[] args) {
      Scanner reader = new Scanner(System.in);
      System.out.print("Enter a year: ");
      String year = reader.nextLine();
      Period yearToCheck = new Period(year);
      System.out.println(yearToCheck.mostRainInGivenYear());


  }
}
