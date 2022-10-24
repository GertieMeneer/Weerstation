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
        String temp = "Temperature     1/4";
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
        String airp = "Air pressure    2/4";
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
        String solar = "Solar radiation 3/4";
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
        String dewpoint = "Dewpoint        4/4";
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
}
