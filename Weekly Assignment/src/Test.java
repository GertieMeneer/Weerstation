import java.time.LocalDate;
import java.util.Scanner;

public class Test {
  public static void main(String[] args) {
    Scanner reader = new Scanner(System.in);
//    System.out.println("Geef een beginwaarde (yyyy-mm-dd): ");
//    String startdate = reader.nextLine();
//    System.out.println("Geef een eindwaarde (yyyy-mm-dd): ");
//    String enddate = reader.nextLine();
//    LocalDate startdatelocaldate = LocalDate.parse(startdate);
//    LocalDate enddatelocaldate = LocalDate.parse(enddate);
//      Period test = new Period(startdatelocaldate, enddatelocaldate);
//      System.out.println(test.getMedianOutsideTemperature(startdatelocaldate, enddatelocaldate));

      System.out.println("Geef een aantal dagen op:");
      int inputperiode = Integer.parseInt(reader.nextLine());
      Period period = new Period(inputperiode);

      System.out.print("Het aantal graaddagen: ");
      System.out.print(Utilities.rounder(period.aantalgraaddagen()));
      
    }


    public void testValueConverter() {
        short airpressurevalue = 29834;
        double air = ValueConverter.airPressure(airpressurevalue);
        System.out.println("Luchtdruk: " + air + " hPa");

        short outsidetemperaturevalue = 616;
        double outsidetemperature = ValueConverter.Temperature(outsidetemperaturevalue);
        System.out.println("Temperatuur buiten: " + outsidetemperature + " graden Celcius");

        short insidetemperaturevalue = 700;
        double insidetemperature = ValueConverter.Temperature(insidetemperaturevalue);
        System.out.println("Temperatuur binnen: " + insidetemperature + " graden Celcius");

        short outsidehumvalue = 75;
        System.out.println("Luchtvochtigheid buiten: " + outsidehumvalue);

        short insidehumvalue = 53;
        System.out.println("Luchtvochtigheid binnen: " + insidehumvalue);

        short windspeedvalue = 8;
        double windspeed = ValueConverter.windSpeed(windspeedvalue);
        System.out.println("Windsnelheid: " + windspeed + " KM/H");

        short winddirvalue = 198;
        String winddir = ValueConverter.windDirection(winddirvalue);
        System.out.println("Windrichting: " + winddir);

        short rainmetervalue = 12;
        double rainrate = ValueConverter.rainMeter(rainmetervalue);
        System.out.println("Hoeveelheid neerslag: " + rainrate + " mm");

        short uvvalue = 31;
        double uvIndex = ValueConverter.uvIndex(uvvalue);
        System.out.println("UV Index: " + (int) uvIndex);

        short sunrisevalue = 545;
        String sunrise = ValueConverter.Time(sunrisevalue);
        System.out.println("Zonsopgang: " + sunrise);

        short sunsetvalue = 2202;
        String sunset = ValueConverter.Time(sunsetvalue);
        System.out.println("Zonsondergang: " + sunset);

        double windchill = ValueConverter.windChill(windspeedvalue, outsidetemperaturevalue);
        System.out.println("Windchill: " + windchill + " graden Celcius");

        double dewpoint = ValueConverter.dewPoint(outsidetemperature, outsidehumvalue);
        System.out.println("Dauwpunt: " + dewpoint + " graden Celcius");

        double heatindex = ValueConverter.heatIndex(outsidetemperature, outsidehumvalue);
        System.out.println("Heat Index: " + heatindex + " graden Celcius");


    }
}
