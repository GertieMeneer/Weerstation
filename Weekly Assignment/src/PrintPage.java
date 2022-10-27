import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class PrintPage {
    public static void homePage() {
        GuiBoardUtilities.clrDMDisplay();
        GuiBoardUtilities.clrSevenSegment();
        for (int i = 1; i > 0; i++) {
            clock();
            Period now = new Period();
            ArrayList<Measurement> measurements = now.getMeasurements();
            SelectOptions.displayTemps(now, measurements);
            String windSpeed = "";
            String windDirection = "";
            windSpeed += "Wind speed: " + Utilities.rounder(now.getMeasurements().get(measurements.size() - 1).getWindSpeed()) + " Km/h";
            windDirection += "Wind direction: " + now.getMeasurements().get(measurements.size() - 1).getWindDirection();
            GuiBoardUtilities.clrDMDisplay();
            for (int j = 0; j < windSpeed.length(); j++) {
                IO.writeShort(0x40, windSpeed.charAt(j));
            }
            IO.writeShort(0x40, '\n');
            for (int y = 0; y < windDirection.length(); y++) {
                IO.writeShort(0x40, windDirection.charAt(y));
            }
            if (IO.readShort(0x0090) == 1) {
                i = -1;
                firstPage();
                PageSelectors.returnToFirstPage();
            }
        }
    }

    public static void clock() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        LocalDateTime now = LocalDateTime.now();
        IO.writeShort(0x18, dtf.format(now).charAt(0));
        IO.writeShort(0x16, dtf.format(now).charAt(1));
        IO.writeShort(0x12, dtf.format(now).charAt(3));
        IO.writeShort(0x10, dtf.format(now).charAt(4));
    }

    public static void firstPage() {
        GuiBoardUtilities.clrDMDisplay();
        GuiBoardUtilities.clrSevenSegment();
        String temp = "Temperature     1/5";
        String hum = "Humidity";
        String wind = "Wind";
        IO.writeShort(0x40, ' ');
        IO.writeShort(0x40, ' ');
        for (int i = 0; i < temp.length(); i++) {
            IO.writeShort(0x40, temp.charAt(i));
        }
        IO.writeShort(0x40, '\n');
        IO.writeShort(0x40, ' ');
        IO.writeShort(0x40, ' ');
        for (int i = 0; i < hum.length(); i++) {
            IO.writeShort(0x40, hum.charAt(i));
        }
        IO.writeShort(0x40, '\n');
        IO.writeShort(0x40, ' ');
        IO.writeShort(0x40, ' ');
        for (int i = 0; i < wind.length(); i++) {
            IO.writeShort(0x40, wind.charAt(i));
        }
        IO.writeShort(0x42, 1);
    }

    public static void secondPage() {
        GuiBoardUtilities.clrDMDisplay();
        GuiBoardUtilities.clrSevenSegment();
        String airp = "Air pressure    2/5";
        String rainrate = "Rain rate";
        String uv = "UV-index";
        IO.writeShort(0x40, ' ');
        IO.writeShort(0x40, ' ');
        for (int i = 0; i < airp.length(); i++) {
            IO.writeShort(0x40, airp.charAt(i));
        }
        IO.writeShort(0x40, '\n');
        IO.writeShort(0x40, ' ');
        IO.writeShort(0x40, ' ');
        for (int i = 0; i < rainrate.length(); i++) {
            IO.writeShort(0x40, rainrate.charAt(i));
        }
        IO.writeShort(0x40, '\n');
        IO.writeShort(0x40, ' ');
        IO.writeShort(0x40, ' ');
        for (int i = 0; i < uv.length(); i++) {
            IO.writeShort(0x40, uv.charAt(i));
        }
        IO.writeShort(0x42, 1);
    }

    public static void thirdPage() {
        GuiBoardUtilities.clrDMDisplay();
        GuiBoardUtilities.clrSevenSegment();
        String solar = "Solar radiation 3/5";
        String sun = "Sun";
        String batt = "Batteries";
        IO.writeShort(0x40, ' ');
        IO.writeShort(0x40, ' ');
        for (int i = 0; i < solar.length(); i++) {
            IO.writeShort(0x40, solar.charAt(i));
        }
        IO.writeShort(0x40, '\n');
        IO.writeShort(0x40, ' ');
        IO.writeShort(0x40, ' ');
        for (int i = 0; i < sun.length(); i++) {
            IO.writeShort(0x40, sun.charAt(i));
        }
        IO.writeShort(0x40, '\n');
        IO.writeShort(0x40, ' ');
        IO.writeShort(0x40, ' ');
        for (int i = 0; i < batt.length(); i++) {
            IO.writeShort(0x40, batt.charAt(i));
        }
        IO.writeShort(0x42, 1);
    }

    public static void fourthPage() {
        GuiBoardUtilities.clrDMDisplay();
        GuiBoardUtilities.clrSevenSegment();
        String dewpoint = "Dewpoint        4/5";
        String windchill = "Windchill";
        String heatindex = "Heatindex";
        IO.writeShort(0x40, ' ');
        IO.writeShort(0x40, ' ');
        for (int i = 0; i < dewpoint.length(); i++) {
            IO.writeShort(0x40, dewpoint.charAt(i));
        }
        IO.writeShort(0x40, '\n');
        IO.writeShort(0x40, ' ');
        IO.writeShort(0x40, ' ');
        for (int i = 0; i < windchill.length(); i++) {
            IO.writeShort(0x40, windchill.charAt(i));
        }
        IO.writeShort(0x40, '\n');
        IO.writeShort(0x40, ' ');
        IO.writeShort(0x40, ' ');
        for (int i = 0; i < heatindex.length(); i++) {
            IO.writeShort(0x40, heatindex.charAt(i));
        }
        IO.writeShort(0x42, 1);
    }

    public static void fifthPage() {
        GuiBoardUtilities.clrDMDisplay();
        GuiBoardUtilities.clrSevenSegment();
        String other = "Other      5/5";
        String gototop = "Return home";
        IO.writeShort(0x40, ' ');
        IO.writeShort(0x40, ' ');
        for (int i = 0; i < other.length() ; i++) {
            IO.writeShort(0x40, other.charAt(i));
        }
        IO.writeShort(0x40, '\n');
        IO.writeShort(0x40, ' ');
        IO.writeShort(0x40, ' ');
        for (int i = 0; i < gototop.length(); i++) {
            IO.writeShort(0x40, gototop.charAt(i));
        }
        IO.writeShort(0x42, 1);
    }

    public static void selectOtherPage1() {
        GuiBoardUtilities.clrDMDisplay();
        GuiBoardUtilities.clrSevenSegment();
        String degreedays = "Degree days";
        String outsidewarmeramount = "Outside warmer amount";
        String biggestdifference = "Biggest temp difference";
        IO.writeShort(0x40, ' ');
        IO.writeShort(0x40, ' ');
        for (int i = 0; i < degreedays.length(); i++) {
            IO.writeShort(0x40, degreedays.charAt(i));
        }
        IO.writeShort(0x40, '\n');
        IO.writeShort(0x40, ' ');
        IO.writeShort(0x40, ' ');
        for (int i = 0; i < outsidewarmeramount.length(); i++) {
            IO.writeShort(0x40, outsidewarmeramount.charAt(i));
        }
        IO.writeShort(0x40, '\n');
        IO.writeShort(0x40, ' ');
        IO.writeShort(0x40, ' ');
        for (int i = 0; i < biggestdifference.length(); i++) {
            IO.writeShort(0x40, biggestdifference.charAt(i));
        }
        IO.writeShort(0x42, 1);
    }

    public static void selectOtherPage2() {
        GuiBoardUtilities.clrDMDisplay();
        GuiBoardUtilities.clrSevenSegment();
        String mostrain = "Most rain in year";
        String niek = "Difference windchill temp";
        IO.writeShort(0x40, ' ');
        IO.writeShort(0x40, ' ');
        for (int i = 0; i < mostrain.length(); i++) {
            IO.writeShort(0x40, mostrain.charAt(i));
        }
        IO.writeShort(0x40, '\n');
        IO.writeShort(0x40, ' ');
        IO.writeShort(0x40, ' ');
        for (int i = 0; i < niek.length(); i++) {
            IO.writeShort(0x40, niek.charAt(i));
        }
        IO.writeShort(0x42, 1);
    }

    public static void selectCurrentOrCustomPage() {
        GuiBoardUtilities.clrDMDisplay();
        GuiBoardUtilities.clrSevenSegment();
        String currenttemp = "Current value";
        String customtemp = "Custom info";
        IO.writeShort(0x40, ' ');
        IO.writeShort(0x40, ' ');
        for (int i = 0; i < currenttemp.length(); i++) {
            IO.writeShort(0x40, currenttemp.charAt(i));
        }
        IO.writeShort(0x40, '\n');
        IO.writeShort(0x40, ' ');
        IO.writeShort(0x40, ' ');
        for (int i = 0; i < customtemp.length(); i++) {
            IO.writeShort(0x40, customtemp.charAt(i));
        }
    }

    public static void selectCustomInfoPage1() {
        GuiBoardUtilities.clrDMDisplay();
        GuiBoardUtilities.clrSevenSegment();
        String average = "Average";
        String lowest = "Lowest";
        String highest = "Highest";
        IO.writeShort(0x40, ' ');
        IO.writeShort(0x40, ' ');
        for (int i = 0; i < average.length(); i++) {
            IO.writeShort(0x40, average.charAt(i));
        }
        IO.writeShort(0x40, '\n');
        IO.writeShort(0x40, ' ');
        IO.writeShort(0x40, ' ');
        for (int i = 0; i < lowest.length(); i++) {
            IO.writeShort(0x40, lowest.charAt(i));
        }
        IO.writeShort(0x40, '\n');
        IO.writeShort(0x40, ' ');
        IO.writeShort(0x40, ' ');
        for (int i = 0; i < highest.length(); i++) {
            IO.writeShort(0x40, highest.charAt(i));
        }
    }

    public static void selectCustomInfoPage2() {
        GuiBoardUtilities.clrDMDisplay();
        GuiBoardUtilities.clrSevenSegment();
        String median = "Median";
        String mode = "Mode";
        String deviation = "Deviation";
        IO.writeShort(0x40, ' ');
        IO.writeShort(0x40, ' ');
        for (int i = 0; i < median.length(); i++) {
            IO.writeShort(0x40, median.charAt(i));
        }
        IO.writeShort(0x40, '\n');
        IO.writeShort(0x40, ' ');
        IO.writeShort(0x40, ' ');
        for (int i = 0; i < mode.length(); i++) {
            IO.writeShort(0x40, mode.charAt(i));
        }
        IO.writeShort(0x40, '\n');
        IO.writeShort(0x40, ' ');
        IO.writeShort(0x40, ' ');
        for (int i = 0; i < deviation.length(); i++) {
            IO.writeShort(0x40, deviation.charAt(i));
        }
    }

    public static void selectCustomPeriod(String value, String type) {
        GuiBoardUtilities.clrDMDisplay();
        GuiBoardUtilities.clrSevenSegment();
        String selectcustom = "Follow instructions \n in console";
        for (int i = 0; i < selectcustom.length(); i++) {
            IO.writeShort(0x40, selectcustom.charAt(i));
        }
        SelectOptions.selectCustomPeriod(value, type);
    }

    public static void followInstructionsInConsole() {
        GuiBoardUtilities.clrDMDisplay();
        GuiBoardUtilities.clrSevenSegment();
        String selectcustom = "Follow instructions \n in console";
        for (int i = 0; i < selectcustom.length(); i++) {
            IO.writeShort(0x40, selectcustom.charAt(i));
        }
    }


}
