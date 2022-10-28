import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class SelectOptions {

    private static int negativeSign = 0x40;

    public static int secondDigit(String secondDigit) {
        return switch (secondDigit) {
            case "0" -> 0b10111111;
            case "1" -> 0b10000110;
            case "2" -> 0b11011011;
            case "3" -> 0b11001111;
            case "4" -> 0b11100110;
            case "5" -> 0b11101101;
            case "6" -> 0b11111101;
            case "7" -> 0b10000111;
            case "8" -> 0b11111111;
            case "9" -> 0b11101111;
            default -> throw new IllegalStateException("Unexpected value");
        };
    }
    public static void selectCurrentTemp() {
        GuiBoardUtilities.clrDMDisplay();
        Period now = new Period();
        ArrayList<Measurement> measurements = now.getMeasurements();
        displayTemps(now, measurements);
        String insideTempDMD = "Left: outside temp";
        String outsideTempDMD = "Right: inside temp";
        String differenceTemptDMD = "Difference = " + Utilities.rounder((Utilities.rounder(now.getMeasurements().get(measurements.size() - 1).getInsideTemperature()) - Utilities.rounder(now.getMeasurements().get(measurements.size() - 1).getOutsideTemperature())));
        for (int i = 0; i < insideTempDMD.length(); i++) {
            IO.writeShort(0x40, insideTempDMD.charAt(i));
        }
        IO.writeShort(0x40, '\n');
        for (int i = 0; i < outsideTempDMD.length(); i++) {
            IO.writeShort(0x40, outsideTempDMD.charAt(i));
        }
        IO.writeShort(0x40, '\n');
        for (int i = 0; i < differenceTemptDMD.length(); i++) {
            IO.writeShort(0x40, differenceTemptDMD.charAt(i));
        }
        PageSelectors.returnToFirstPage();
    }


    public static void displayTemps(Period now, ArrayList<Measurement> measurements) {
        String outsideTemp = "" + Utilities.rounder(now.getMeasurements().get(measurements.size() - 1).getOutsideTemperature());
        if (Double.parseDouble(outsideTemp) >= 0 && Double.parseDouble(outsideTemp) < 10) {
            IO.writeShort(0x24, 0x100 | secondDigit("" + outsideTemp.charAt(0)));
            IO.writeShort(0x22, outsideTemp.charAt(2));
        } else if (Double.parseDouble(outsideTemp) >= 10 && Double.parseDouble(outsideTemp) < 100) {
            IO.writeShort(0x24, outsideTemp.charAt(0));
            IO.writeShort(0x22, 0x100 | secondDigit("" + outsideTemp.charAt(1)));
            IO.writeShort(0x20, outsideTemp.charAt(3));
        } else if (Double.parseDouble(outsideTemp) < 0 && Double.parseDouble(outsideTemp) > -10) {
            IO.writeShort(0x24, 0x100 | negativeSign);
            IO.writeShort(0x22, 0x100 | secondDigit("" + outsideTemp.charAt(1)));
            IO.writeShort(0x20, outsideTemp.charAt(3));
        } else if (Double.parseDouble(outsideTemp) < -10) {
            IO.writeShort(0x24, 0x100 | negativeSign);
            IO.writeShort(0x22, outsideTemp.charAt(1));
            IO.writeShort(0x22, outsideTemp.charAt(2));
        }
        String insideTemp = "" + Utilities.rounder(now.getMeasurements().get(measurements.size() - 1).getInsideTemperature());
        IO.writeShort(0x34, insideTemp.charAt(0));
        IO.writeShort(0x32, 0x100 | secondDigit("" + insideTemp.charAt(1)));
        IO.writeShort(0x30, insideTemp.charAt(3));
    }

    public static void selectHum() {
        GuiBoardUtilities.clrDMDisplay();
        Period now = new Period();
        ArrayList<Measurement> measurements = now.getMeasurements();
        String insideHum = "" + Utilities.rounder(now.getMeasurements().get(measurements.size() - 1).getInsideHum());
        IO.writeShort(0x24, insideHum.charAt(0));
        IO.writeShort(0x22, insideHum.charAt(1));
        String outsideHum = "" + Utilities.rounder(now.getMeasurements().get(measurements.size() - 1).getOutsideHum());
        IO.writeShort(0x34, outsideHum.charAt(0));
        IO.writeShort(0x32, outsideHum.charAt(1));
        String insidehumDMD = "Left: inside hum (%)";
        String outsidehumDMD = "Right: outside hum";
        String differencehumDMD = "Difference = " + (now.getMeasurements().get(measurements.size() - 1).getInsideHum() - now.getMeasurements().get(measurements.size() - 1).getOutsideHum());
        if (differencehumDMD.contains("-")) {
            differencehumDMD = differencehumDMD.substring(0, 12) + " " + differencehumDMD.substring(14);
        }
        for (int i = 0; i < insidehumDMD.length(); i++) {
            IO.writeShort(0x40, insidehumDMD.charAt(i));
        }
        IO.writeShort(0x40, '\n');
        for (int i = 0; i < outsidehumDMD.length(); i++) {
            IO.writeShort(0x40, outsidehumDMD.charAt(i));
        }
        IO.writeShort(0x40, '\n');
        for (int i = 0; i < differencehumDMD.length(); i++) {
            IO.writeShort(0x40, differencehumDMD.charAt(i));
        }
        PageSelectors.returnToFirstPage();
    }

    public static void selectWind() {
        GuiBoardUtilities.clrDMDisplay();
        Period now = new Period();
        ArrayList<Measurement> measurements = now.getMeasurements();
        String windSpeed = "" + Utilities.rounder(now.getMeasurements().get(measurements.size() - 1).getWindSpeed());
        String windDirection = "Wind direction: " + now.getMeasurements().get(measurements.size() - 1).getWindDirection();
        String avgwindspeed = "" + Utilities.rounder(now.getMeasurements().get(measurements.size() - 1).getAvgWindSpeed());

        if (Double.parseDouble(avgwindspeed) < 10 && Double.parseDouble(avgwindspeed) >= 0) {
            IO.writeShort(0x34, 0x100 | secondDigit("" + avgwindspeed.charAt(0)));
            IO.writeShort(0x32, avgwindspeed.charAt(2));
        } else if (Double.parseDouble(avgwindspeed) >= 10 && Double.parseDouble(avgwindspeed) < 100) {
            IO.writeShort(0x34, avgwindspeed.charAt(0));
            IO.writeShort(0x32, 0x100 | secondDigit("" + avgwindspeed.charAt(1)));
            IO.writeShort(0x30, avgwindspeed.charAt(3));
        } else {
            IO.writeShort(0x34, avgwindspeed.charAt(0));
            IO.writeShort(0x32, avgwindspeed.charAt(1));
            IO.writeShort(0x30, avgwindspeed.charAt(2));
        }

        if (Double.parseDouble(windSpeed) < 10 && Double.parseDouble(windSpeed) >= 0) {
            IO.writeShort(0x24, 0x100 | secondDigit("" + windSpeed.charAt(0)));
            IO.writeShort(0x22, windSpeed.charAt(2));
        } else if (Double.parseDouble(windSpeed) >= 10 && Double.parseDouble(windSpeed) < 100) {
            IO.writeShort(0x24, windSpeed.charAt(0));
            IO.writeShort(0x22, 0x100 | secondDigit("" + windSpeed.charAt(1)));
            IO.writeShort(0x20, windSpeed.charAt(3));
        } else {
            IO.writeShort(0x24, windSpeed.charAt(0));
            IO.writeShort(0x22, windSpeed.charAt(1));
            IO.writeShort(0x20, windSpeed.charAt(2));
        }

        String windspeedDMD = "Left: wind in km/h";
        String avgwindspeedDMD = "Right: avg wind";
        for (int i = 0; i < windspeedDMD.length(); i++) {
            IO.writeShort(0x40, windspeedDMD.charAt(i));
        }
        IO.writeShort(0x40, '\n');
        for (int i = 0; i < avgwindspeedDMD.length(); i++) {
            IO.writeShort(0x40, avgwindspeedDMD.charAt(i));
        }
        IO.writeShort(0x40, '\n');
        for (int i = 0; i < windDirection.length(); i++) {
            IO.writeShort(0x40, windDirection.charAt(i));
        }
        PageSelectors.returnToFirstPage();
    }

    public static void selectAirpressure() {
        GuiBoardUtilities.clrDMDisplay();
        GuiBoardUtilities.clrSevenSegment();
        Period now = new Period();
        ArrayList<Measurement> measurements = now.getMeasurements();
        String airPressure = "" + Utilities.rounder(now.getMeasurements().get(measurements.size() - 1).getAirPressure());
        if (Double.parseDouble(airPressure) >= 1000) {
            IO.writeShort(0x18, airPressure.charAt(0));
            IO.writeShort(0x16, airPressure.charAt(1));
            IO.writeShort(0x14, airPressure.charAt(2));
            IO.writeShort(0x12, 0x100 | secondDigit("" + airPressure.charAt(3)));
            IO.writeShort(0x10, airPressure.charAt(5));
        } else if (Double.parseDouble(airPressure) < 1000) {
            IO.writeShort(0x18, airPressure.charAt(0));
            IO.writeShort(0x16, airPressure.charAt(1));
            IO.writeShort(0x14, 0x100 | secondDigit("" + airPressure.charAt(2)));
            IO.writeShort(0x12, airPressure.charAt(4));
        }

        String airPressureDMD = "Showing current air \n pressure in hPa.";
        for (int i = 0; i < airPressureDMD.length(); i++) {
            IO.writeShort(0x40, airPressureDMD.charAt(i));
        }
        PageSelectors.returnToFirstPage();
    }

    public static void selectRainrate() {
        GuiBoardUtilities.clrDMDisplay();
        GuiBoardUtilities.clrSevenSegment();
        Period now = new Period();
        ArrayList<Measurement> measurements = now.getMeasurements();
        String rainrate = "" + Utilities.rounder(now.getMeasurements().get(measurements.size() - 1).getRainRate());
        if (Double.parseDouble(rainrate) < 10 && Double.parseDouble(rainrate) >= 0) {
            IO.writeShort(0x18, 0x100 | secondDigit("" + rainrate.charAt(0)));
            IO.writeShort(0x16, rainrate.charAt(2));
        } else if (Double.parseDouble(rainrate) >= 10 && Double.parseDouble(rainrate) < 100) {
            IO.writeShort(0x18, rainrate.charAt(0));
            IO.writeShort(0x16, rainrate.charAt(1));
            IO.writeShort(0x14, 0x100 | secondDigit("" + rainrate.charAt(2)));
            IO.writeShort(0x12, rainrate.charAt(4));
        } else {
            IO.writeShort(0x18, rainrate.charAt(0));
            IO.writeShort(0x16, rainrate.charAt(1));
            IO.writeShort(0x14, rainrate.charAt(2));
            IO.writeShort(0x12, 0x100 | secondDigit("" + rainrate.charAt(3)));
            IO.writeShort(0x10, rainrate.charAt(5));
        }
        String rainRateDMD = "Showing current rain \n rate in mm";
        for (int i = 0; i < rainRateDMD.length(); i++) {
            IO.writeShort(0x40, rainRateDMD.charAt(i));

        }
        PageSelectors.returnToFirstPage();
    }

    public static void selectUV() {
        GuiBoardUtilities.clrDMDisplay();
        GuiBoardUtilities.clrSevenSegment();
        Period now = new Period();
        ArrayList<Measurement> measurements = now.getMeasurements();
        String UVIndex = "" + Utilities.rounder(now.getMeasurements().get(measurements.size() - 1).getUVLevel());
        if (Double.parseDouble(UVIndex) < 10 && Double.parseDouble(UVIndex) >= 0) {
            IO.writeShort(0x24, 0x100 | secondDigit("" + UVIndex.charAt(0)));
            IO.writeShort(0x22, UVIndex.charAt(2));
        } else {
            IO.writeShort(0x24, UVIndex.charAt(0));
            IO.writeShort(0x22, 0x100 | secondDigit("" + UVIndex.charAt(1)));
            IO.writeShort(0x20, UVIndex.charAt(3));
        }
        String UVindexDMD = "Showing current UV \n index in the Global \n Solar UV Index";
        for (int i = 0; i < UVindexDMD.length(); i++) {
            IO.writeShort(0x40, UVindexDMD.charAt(i));

        }
        PageSelectors.returnToFirstPage();
    }

    public static void selectSolarrad() {
        GuiBoardUtilities.clrDMDisplay();
        GuiBoardUtilities.clrSevenSegment();
        Period now = new Period();
        ArrayList<Measurement> measurements = now.getMeasurements();
        String solarrad = "" + Utilities.rounder(now.getMeasurements().get(measurements.size() - 1).getSolarRad());
        if (Double.parseDouble(solarrad) < 10 && Double.parseDouble(solarrad) >= 0) {
            IO.writeShort(0x18, 0x100 | secondDigit("" + solarrad.charAt(0)));
            IO.writeShort(0x16, solarrad.charAt(2));
        } else if (Double.parseDouble(solarrad) >= 10 && Double.parseDouble(solarrad) < 100) {
            IO.writeShort(0x18, solarrad.charAt(0));
            IO.writeShort(0x16, 0x100 | secondDigit("" + solarrad.charAt(1)));
            IO.writeShort(0x14, solarrad.charAt(3));
        } else if (Double.parseDouble(solarrad) >= 100 && Double.parseDouble(solarrad) < 1000) {
            IO.writeShort(0x18, solarrad.charAt(0));
            IO.writeShort(0x16, solarrad.charAt(1));
            IO.writeShort(0x14, 0x100 | secondDigit("" + solarrad.charAt(2)));
            IO.writeShort(0x12, solarrad.charAt(4));
        } else {
            IO.writeShort(0x18, solarrad.charAt(0));
            IO.writeShort(0x16, solarrad.charAt(1));
            IO.writeShort(0x14, solarrad.charAt(2));
            IO.writeShort(0x12, 0x100 | secondDigit("" + solarrad.charAt(3)));
            IO.writeShort(0x10, solarrad.charAt(5));
        }
        String SolarradDMD = "Showing current solar \n radiation in W/M2";
        for (int i = 0; i < SolarradDMD.length(); i++) {
            IO.writeShort(0x40, SolarradDMD.charAt(i));

        }
        PageSelectors.returnToFirstPage();
    }

    public static void selectSun() {
        GuiBoardUtilities.clrDMDisplay();
        GuiBoardUtilities.clrSevenSegment();
        Period now = new Period();
        ArrayList<Measurement> measurements = now.getMeasurements();
        String sunrise = "Sunrise: " + now.getMeasurements().get(measurements.size() - 1).getSunrise();
        String sunset = "Sunset: " + now.getMeasurements().get(measurements.size() - 1).getSunset();
        String suninfo = "Sun information";
        for (int i = 0; i < suninfo.length(); i++) {
            IO.writeShort(0x40, suninfo.charAt(i));
        }
        IO.writeShort(0x40, '\n');

        for (int i = 0; i < sunrise.length(); i++) {
            IO.writeShort(0x40, sunrise.charAt(i));

        }
        IO.writeShort(0x40, '\n');
        for (int i = 0; i < sunset.length(); i++) {
            IO.writeShort(0x40, sunset.charAt(i));
        }
        PageSelectors.returnToFirstPage();
    }


    public static void selectBatteries() {
        GuiBoardUtilities.clrDMDisplay();
        GuiBoardUtilities.clrSevenSegment();
        Period now = new Period();
        ArrayList<Measurement> measurements = now.getMeasurements();
        String xmitbatt = "" + now.getMeasurements().get(measurements.size() - 1).getXmitBatt();
        String otherbatt = "" + now.getMeasurements().get(measurements.size() - 1).getBattLevel();
        if (Double.parseDouble(xmitbatt) >= 00 && Double.parseDouble(xmitbatt) < 10) {
            IO.writeShort(0x24, xmitbatt.charAt(0));
        } else if (Double.parseDouble(xmitbatt) >= 10 && Double.parseDouble(xmitbatt) < 100) {
            IO.writeShort(0x24, xmitbatt.charAt(0));
            IO.writeShort(0x22, xmitbatt.charAt(1));
        } else {
            IO.writeShort(0x24, xmitbatt.charAt(0));
            IO.writeShort(0x22, xmitbatt.charAt(1));
            IO.writeShort(0x20, xmitbatt.charAt(2));
        }

        if (Double.parseDouble(otherbatt) >= 00 && Double.parseDouble(otherbatt) < 10) {
            IO.writeShort(0x34, otherbatt.charAt(0));
        } else if (Double.parseDouble(otherbatt) >= 10 && Double.parseDouble(otherbatt) < 100) {
            IO.writeShort(0x34, otherbatt.charAt(0));
            IO.writeShort(0x32, otherbatt.charAt(1));
        } else {
            IO.writeShort(0x34, otherbatt.charAt(0));
            IO.writeShort(0x32, otherbatt.charAt(1));
            IO.writeShort(0x30, otherbatt.charAt(2));
        }
        String battsDMD = "Current battery stats \n Left: xmit bat (%) \n Right: other bat";
        for (int i = 0; i < battsDMD.length(); i++) {
            IO.writeShort(0x40, battsDMD.charAt(i));
        }
        PageSelectors.returnToFirstPage();
    }

    public static void selectDewpoint() {
        GuiBoardUtilities.clrDMDisplay();
        GuiBoardUtilities.clrSevenSegment();
        Period now = new Period();
        ArrayList<Measurement> measurements = now.getMeasurements();
        String dewpoint = "" + now.getMeasurements().get(measurements.size() - 1).getDewPoint();
        if (Double.parseDouble(dewpoint) < 10) {
            IO.writeShort(0x24, 0x100 | secondDigit("" + dewpoint.charAt(0)));
            IO.writeShort(0x22, dewpoint.charAt(2));
        } else {
            IO.writeShort(0x24, dewpoint.charAt(0));
            IO.writeShort(0x22, 0x100 | secondDigit("" + dewpoint.charAt(1)));
            IO.writeShort(0x20, dewpoint.charAt(3));
        }
        String dewpointDMD = "Showing current \n dewpoint in \n degrees Celcius";
        for (int i = 0; i < dewpointDMD.length(); i++) {
            IO.writeShort(0x40, dewpointDMD.charAt(i));
        }
        PageSelectors.returnToFirstPage();
    }

    public static void selectWindchill() {
        GuiBoardUtilities.clrDMDisplay();
        GuiBoardUtilities.clrSevenSegment();
        Period now = new Period();
        ArrayList<Measurement> measurements = now.getMeasurements();
        String windchill = "" + now.getMeasurements().get(measurements.size() - 1).getWindChill();
        if (Double.parseDouble(windchill) < 10) {
            IO.writeShort(0x24, 0x100 | secondDigit("" + windchill.charAt(0)));
            IO.writeShort(0x22, windchill.charAt(2));
        } else {
            IO.writeShort(0x24, windchill.charAt(0));
            IO.writeShort(0x22, 0x100 | secondDigit("" + windchill.charAt(1)));
            IO.writeShort(0x20, windchill.charAt(3));
        }
        String windchillDMD = "Showing current \n windchill in \n degrees Celcius";
        for (int i = 0; i < windchillDMD.length(); i++) {
            IO.writeShort(0x40, windchillDMD.charAt(i));
        }
        PageSelectors.returnToFirstPage();
    }

    public static void selectHeatindex() {
        GuiBoardUtilities.clrDMDisplay();
        GuiBoardUtilities.clrSevenSegment();
        Period now = new Period();
        ArrayList<Measurement> measurements = now.getMeasurements();
        String heatindex = "" + now.getMeasurements().get(measurements.size() - 1).getHeatIndex();
        if (Double.parseDouble(heatindex) < 10) {
            IO.writeShort(0x24, 0x100 | secondDigit("" + heatindex.charAt(0)));
            IO.writeShort(0x22, heatindex.charAt(2));
        } else {
            IO.writeShort(0x24, heatindex.charAt(0));
            IO.writeShort(0x22, 0x100 | secondDigit("" + heatindex.charAt(1)));
            IO.writeShort(0x20, heatindex.charAt(3));
        }
        String heatindexDMD = "Showing current \n heat index in \n degrees Celcius";
        for (int i = 0; i < heatindexDMD.length(); i++) {
            IO.writeShort(0x40, heatindexDMD.charAt(i));
        }
        PageSelectors.returnToFirstPage();
    }

    public static void selectOtherDegreeDays() {
        Scanner reader = new Scanner(System.in);
        PrintPage.followInstructionsInConsole();
        System.out.println("Input start date (yyyy-mm-dd): ");
        String startdate = reader.nextLine();
        System.out.println("Input end date (yyyy-mm-dd): ");
        String enddate = reader.nextLine();
        LocalDate startdatelocaldate = LocalDate.parse(startdate);
        LocalDate enddatelocaldate = LocalDate.parse(enddate);
        Period test = new Period(startdatelocaldate, enddatelocaldate);
        GuiBoardUtilities.clrSevenSegment();
        GuiBoardUtilities.clrDMDisplay();
        String graaddagen = "Left: \n Degree days amount";
        String amount = "" + Utilities.rounder(test.aantalGraaddagen());
        for (int i = 0; i < graaddagen.length(); i++) {
            IO.writeShort(0x40, graaddagen.charAt(i));
        }
        if(Double.parseDouble(amount) < 10) {
            IO.writeShort(0x24, 0x100 | secondDigit("" + amount.charAt(0)));
            IO.writeShort(0x22, amount.charAt(2));
        } else {
            IO.writeShort(0x24, amount.charAt(0));
            IO.writeShort(0x22, 0x100 | secondDigit("" + amount.charAt(1)));
            IO.writeShort(0x20, amount.charAt(3));
        }
        IO.writeShort(0x42, 1);
        PageSelectors.returnToFirstPage();
    }

    public static void selectOtherOutsideWarmerAmount() {
        Scanner reader = new Scanner(System.in);
        PrintPage.followInstructionsInConsole();
        System.out.println("Input start date (yyyy-mm-dd): ");
        String startdate = reader.nextLine();
        System.out.println("Input end date (yyyy-mm-dd): ");
        String enddate = reader.nextLine();
        LocalDate startdatelocaldate = LocalDate.parse(startdate);
        LocalDate enddatelocaldate = LocalDate.parse(enddate);
        Period test = new Period(startdatelocaldate, enddatelocaldate);
        GuiBoardUtilities.clrSevenSegment();
        GuiBoardUtilities.clrDMDisplay();


    }

    public static void selectOtherBiggestDifference() {
        GuiBoardUtilities.clrDMDisplay();
        PrintPage.followInstructionsInConsole();
        Scanner reader = new Scanner(System.in);
        System.out.println("Input start date (yyyy-mm-dd): ");
        String startdate = reader.nextLine();
        System.out.println("Input end date (yyyy-mm-dd): ");
        String enddate = reader.nextLine();
        LocalDate startdatelocaldate = LocalDate.parse(startdate);
        LocalDate enddatelocaldate = LocalDate.parse(enddate);
        Period test = new Period(startdatelocaldate, enddatelocaldate);
        GuiBoardUtilities.clrDMDisplay();
        String biggestDiff = test.biggestDifference();
        for (int i = 0; i < biggestDiff.length(); i++) {
            IO.writeShort(0x40, test.biggestDifference().charAt(i));
        }
    }

    public static void selectOtherMostRain() {
        GuiBoardUtilities.clrDMDisplay();
        PrintPage.followInstructionsInConsole();
        Scanner reader = new Scanner(System.in);
        System.out.print("Enter a year: ");
        String year = reader.nextLine();
        Period yearToCheck = new Period(year);
        String monthDMD = "Month with \n most rain: \n " + yearToCheck.mostRainInGivenYear();
        GuiBoardUtilities.clrDMDisplay();
        for (int i = 0; i < monthDMD.length(); i++) {
            IO.writeShort(0x40, monthDMD.charAt(i));
        }
        PageSelectors.returnToFirstPage();
    }

    public static void selectOtherDifferenceWindchillTemp() {
        PrintPage.followInstructionsInConsole();
        Scanner reader = new Scanner(System.in);
        System.out.println("Input start date (yyyy-mm-dd): ");
        String startdate = reader.nextLine();
        System.out.println("Input end date (yyyy-mm-dd): ");
        String enddate = reader.nextLine();
        LocalDate startdatelocaldate = LocalDate.parse(startdate);
        LocalDate enddatelocaldate = LocalDate.parse(enddate);
        Period test = new Period(startdatelocaldate, enddatelocaldate);
        GuiBoardUtilities.clrDMDisplay();
        GuiBoardUtilities.clrSevenSegment();
        String text = "Biggest difference \n windchill & temp is \n" + test.windChillAndOutsideTemperatureDifference();
        for (int i = 0; i < text.length(); i++) {
            IO.writeShort(0x40, text.charAt(i));
        }
        PageSelectors.returnToFirstPage();
    }



    public static void selectReturnHome() {
        GuiBoardUtilities.clrDMDisplay();
        GuiBoardUtilities.clrSevenSegment();
        PrintPage.homePage();
    }

    public static void selectCustomPeriod(String value, String type) {
        Scanner reader = new Scanner(System.in);
        System.out.println("Input start date (yyyy-mm-dd): ");
        String startdate = reader.nextLine();
        System.out.println("Input end date (yyyy-mm-dd): ");
        String enddate = reader.nextLine();
        LocalDate startdatelocaldate = LocalDate.parse(startdate);
        LocalDate enddatelocaldate = LocalDate.parse(enddate);
        Period test = new Period(startdatelocaldate, enddatelocaldate);
        switch (value) {
            case "temp":
                switch (type) {
                    case "avg" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String avgoutside = "" + Utilities.rounder(test.getAverageOutsideTemperature());
                        String avginside = "" + Utilities.rounder(test.getAverageInsideTemperature());
                        String avgoutsideDMD = "Left: avg outside";
                        String avginsideDMD = "Right: avg inside";
                        String val = "Temperature (C)";
                        if (Double.parseDouble(avgoutside) >= 0 && Double.parseDouble(avgoutside) < 10) {
                            IO.writeShort(0x24, 0x100 | secondDigit("" + avgoutside.charAt(0)));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        } else if (Double.parseDouble(avgoutside) >= 10 && Double.parseDouble(avgoutside) < 100) {
                            IO.writeShort(0x24, avgoutside.charAt(0));
                            IO.writeShort(0x22, 0x100 | secondDigit("" + avgoutside.charAt(1)));
                            IO.writeShort(0x20, avgoutside.charAt(3));
                        } else if (Double.parseDouble(avgoutside) < 0 && Double.parseDouble(avgoutside) > -10) {
                            IO.writeShort(0x24, 0x100 | negativeSign);
                            IO.writeShort(0x22, 0x100 | secondDigit("" + avgoutside.charAt(1)));
                            IO.writeShort(0x20, avgoutside.charAt(3));
                        } else if (Double.parseDouble(avgoutside) < -10) {
                            IO.writeShort(0x24, 0x100 | negativeSign);
                            IO.writeShort(0x22, avgoutside.charAt(1));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        }
                        IO.writeShort(0x34, avginside.charAt(0));
                        IO.writeShort(0x32, 0x100 | secondDigit("" + avginside.charAt(1)));
                        IO.writeShort(0x30, avginside.charAt(3));
                        for (int i = 0; i < avginsideDMD.length(); i++) {
                            IO.writeShort(0x40, avginsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < avgoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, avgoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                    }
                    case "low" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String lowoutside = "" + Utilities.rounder(test.getLowestOutsideTemp());
                        String lowinside = "" + Utilities.rounder(test.getLowestInsideTemp());
                        String lowoutsideDMD = "Left: low outside";
                        String lowinsideDMD = "Right: low inside";
                        String val = "Temperature (C)";
                        if (Double.parseDouble(lowoutside) >= 0 && Double.parseDouble(lowoutside) < 10) {
                            IO.writeShort(0x24, 0x100 | secondDigit("" + lowoutside.charAt(0)));
                            IO.writeShort(0x22, lowoutside.charAt(2));
                        } else if (Double.parseDouble(lowoutside) >= 10 && Double.parseDouble(lowoutside) < 100) {
                            IO.writeShort(0x24, lowoutside.charAt(0));
                            IO.writeShort(0x22, 0x100 | secondDigit("" + lowoutside.charAt(1)));
                            IO.writeShort(0x20, lowoutside.charAt(3));
                        } else if (Double.parseDouble(lowoutside) < 0 && Double.parseDouble(lowoutside) > -10) {
                            IO.writeShort(0x24, 0x100 | negativeSign);
                            IO.writeShort(0x22, 0x100 | secondDigit("" + lowoutside.charAt(1)));
                            IO.writeShort(0x20, lowoutside.charAt(3));
                        } else if (Double.parseDouble(lowoutside) < -10) {
                            IO.writeShort(0x24, 0x100 | negativeSign);
                            IO.writeShort(0x22, lowoutside.charAt(1));
                            IO.writeShort(0x22, lowoutside.charAt(2));
                        }
                        IO.writeShort(0x34, lowinside.charAt(0));
                        IO.writeShort(0x32, 0x100 | secondDigit("" + lowinside.charAt(1)));
                        IO.writeShort(0x30, lowinside.charAt(3));
                        for (int i = 0; i < lowinsideDMD.length(); i++) {
                            IO.writeShort(0x40, lowinsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < lowoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, lowoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();

                    }
                    case "high" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String highoutside = "" + Utilities.rounder(test.getHighestOutsideTemp());
                        String highinside = "" + Utilities.rounder(test.getHighestInsideTemp());
                        String highoutsideDMD = "Left: high outside";
                        String highinsideDMD = "Right: high inside";
                        String val = "Temperature (C)";
                        if (Double.parseDouble(highoutside) >= 0 && Double.parseDouble(highoutside) < 10) {
                            IO.writeShort(0x24, 0x100 | secondDigit("" + highoutside.charAt(0)));
                            IO.writeShort(0x22, highoutside.charAt(2));
                        } else if (Double.parseDouble(highoutside) >= 10 && Double.parseDouble(highoutside) < 100) {
                            IO.writeShort(0x24, highoutside.charAt(0));
                            IO.writeShort(0x22, 0x100 | secondDigit("" + highoutside.charAt(1)));
                            IO.writeShort(0x20, highoutside.charAt(3));
                        } else if (Double.parseDouble(highoutside) < 0 && Double.parseDouble(highoutside) > -10) {
                            IO.writeShort(0x24, 0x100 | negativeSign);
                            IO.writeShort(0x22, 0x100 | secondDigit("" + highoutside.charAt(1)));
                            IO.writeShort(0x20, highoutside.charAt(3));
                        } else if (Double.parseDouble(highoutside) < -10) {
                            IO.writeShort(0x24, 0x100 | negativeSign);
                            IO.writeShort(0x22, highoutside.charAt(1));
                            IO.writeShort(0x22, highoutside.charAt(2));
                        }
                        IO.writeShort(0x34, highinside.charAt(0));
                        IO.writeShort(0x32, 0x100 | secondDigit("" + highinside.charAt(1)));
                        IO.writeShort(0x30, highinside.charAt(3));
                        for (int i = 0; i < highinsideDMD.length(); i++) {
                            IO.writeShort(0x40, highinsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < highoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, highoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                    }
                    case "median" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String medoutside = "" + Utilities.rounder(test.getMedianOutsideTemperature());
                        String medinside = "" + Utilities.rounder(test.getMedianInsideTemperature());
                        String medoutsideDMD = "Left: med outside";
                        String medinsideDMD = "Right: med inside";
                        String val = "Temperature (C)";
                        if (Double.parseDouble(medoutside) >= 0 && Double.parseDouble(medoutside) < 10) {
                            IO.writeShort(0x24, 0x100 | secondDigit("" + medoutside.charAt(0)));
                            IO.writeShort(0x22, medoutside.charAt(2));
                        } else if (Double.parseDouble(medoutside) >= 10 && Double.parseDouble(medoutside) < 100) {
                            IO.writeShort(0x24, medoutside.charAt(0));
                            IO.writeShort(0x22, 0x100 | secondDigit("" + medoutside.charAt(1)));
                            IO.writeShort(0x20, medoutside.charAt(3));
                        } else if (Double.parseDouble(medoutside) < 0 && Double.parseDouble(medoutside) > -10) {
                            IO.writeShort(0x24, 0x100 | negativeSign);
                            IO.writeShort(0x22, 0x100 | secondDigit("" + medoutside.charAt(1)));
                            IO.writeShort(0x20, medoutside.charAt(3));
                        } else if (Double.parseDouble(medoutside) < -10) {
                            IO.writeShort(0x24, 0x100 | negativeSign);
                            IO.writeShort(0x22, medoutside.charAt(1));
                            IO.writeShort(0x22, medoutside.charAt(2));
                        }
                        IO.writeShort(0x34, medinside.charAt(0));
                        IO.writeShort(0x32, 0x100 | secondDigit("" + medinside.charAt(1)));
                        IO.writeShort(0x30, medinside.charAt(3));
                        for (int i = 0; i < medinsideDMD.length(); i++) {
                            IO.writeShort(0x40, medinsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < medoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, medoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                    }
                    case "mode" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String modeoutside = "" + Utilities.rounder(test.getModeOutsideTemp());
                        String modeinside = "" + Utilities.rounder(test.getModeInsideTemp());
                        String modeoutsideDMD = "Left: mode outside";
                        String modeinsideDMD = "Right: mode inside";
                        String val = "Temperature (C)";
                        if (Double.parseDouble(modeoutside) >= 0 && Double.parseDouble(modeoutside) < 10) {
                            IO.writeShort(0x24, 0x100 | secondDigit("" + modeoutside.charAt(0)));
                            IO.writeShort(0x22, modeoutside.charAt(2));
                        } else if (Double.parseDouble(modeoutside) >= 10 && Double.parseDouble(modeoutside) < 100) {
                            IO.writeShort(0x24, modeoutside.charAt(0));
                            IO.writeShort(0x22, 0x100 | secondDigit("" + modeoutside.charAt(1)));
                            IO.writeShort(0x20, modeoutside.charAt(3));
                        } else if (Double.parseDouble(modeoutside) < 0 && Double.parseDouble(modeoutside) > -10) {
                            IO.writeShort(0x24, 0x100 | negativeSign);
                            IO.writeShort(0x22, 0x100 | secondDigit("" + modeoutside.charAt(1)));
                            IO.writeShort(0x20, modeoutside.charAt(3));
                        } else if (Double.parseDouble(modeoutside) < -10) {
                            IO.writeShort(0x24, 0x100 | negativeSign);
                            IO.writeShort(0x22, modeoutside.charAt(1));
                            IO.writeShort(0x22, modeoutside.charAt(2));
                        }
                        IO.writeShort(0x34, modeinside.charAt(0));
                        IO.writeShort(0x32, 0x100 | secondDigit("" + modeinside.charAt(1)));
                        IO.writeShort(0x30, modeinside.charAt(3));
                        for (int i = 0; i < modeinsideDMD.length(); i++) {
                            IO.writeShort(0x40, modeinsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < modeoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, modeoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                    }
                    case "deviation" -> {
                        String deviationIns = "" + test.standardDeviation("insideTemperature");
                        String deviationOut = "" + test.standardDeviation("outsideTemperature");
                        String deviationDMD = "Standard deviation \n Left = inside \n Right = outside";
                        if (Double.parseDouble(deviationIns) < 10) {
                            IO.writeShort(0x22, 0x100 | secondDigit("" + deviationIns.charAt(0)));
                            IO.writeShort(0x20, deviationIns.charAt(2));
                        } else {
                            IO.writeShort(0x24, deviationIns.charAt(0));
                            IO.writeShort(0x22, 0x100 | secondDigit("" + deviationIns.charAt(1)));
                            IO.writeShort(0x20, deviationIns.charAt(3));
                        }
                        if (Double.parseDouble(deviationOut) < 10) {
                            IO.writeShort(0x32, 0x100 | secondDigit("" + deviationOut.charAt(0)));
                            IO.writeShort(0x30, deviationIns.charAt(2));
                        } else {
                            IO.writeShort(0x34, deviationOut.charAt(0));
                            IO.writeShort(0x32, 0x100 | secondDigit("" + deviationOut.charAt(1)));
                            IO.writeShort(0x30, deviationOut.charAt(3));
                        }
                        GuiBoardUtilities.clrDMDisplay();
                        for (int i = 0; i < deviationDMD.length(); i++) {
                            IO.writeShort(0x40, deviationDMD.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                    }
                }
                break;
            case "hum":
                switch (type) {
                    case "avg" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String avgoutside = "" + Utilities.rounder(test.getAverageOutsideHumidity());
                        String avginside = "" + Utilities.rounder(test.getAverageInsideHumidity());
                        String avgoutsideDMD = "Left: avg outside";
                        String avginsideDMD = "Right: avg inside";
                        String val = "Humidity (%)";
                        IO.writeShort(0x24, avgoutside.charAt(0));
                        IO.writeShort(0x22, avgoutside.charAt(1));
                        IO.writeShort(0x34, avginside.charAt(0));
                        IO.writeShort(0x32, avginside.charAt(1));
                        for (int i = 0; i < avginsideDMD.length(); i++) {
                            IO.writeShort(0x40, avginsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < avgoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, avgoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                    }
                    case "low" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String lowoutside = "" + Utilities.rounder(test.getLowestOutsideHum());
                        String lowinside = "" + Utilities.rounder(test.getLowestOutsideHum());
                        String lowoutsideDMD = "Left: low outside";
                        String lowinsideDMD = "Right: low inside";
                        String val = "Humidity (%)";
                        IO.writeShort(0x24, lowoutside.charAt(0));
                        IO.writeShort(0x22, lowoutside.charAt(1));
                        IO.writeShort(0x34, lowinside.charAt(0));
                        IO.writeShort(0x32, lowinside.charAt(1));
                        for (int i = 0; i < lowinsideDMD.length(); i++) {
                            IO.writeShort(0x40, lowinsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < lowoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, lowoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                    }
                    case "high" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String highoutside = "" + Utilities.rounder(test.getHighestOutsideHum());
                        String highinside = "" + Utilities.rounder(test.getHighestInsideHum());
                        String highoutsideDMD = "Left: high outside";
                        String highinsideDMD = "Right: high inside";
                        String val = "Humidity (%)";
                        IO.writeShort(0x24, highoutside.charAt(0));
                        IO.writeShort(0x22, highoutside.charAt(1));
                        IO.writeShort(0x34, highinside.charAt(0));
                        IO.writeShort(0x32, highinside.charAt(1));
                        for (int i = 0; i < highinsideDMD.length(); i++) {
                            IO.writeShort(0x40, highinsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < highoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, highoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                    }
                    case "median" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String highoutside = "" + Utilities.rounder(test.getMedianOutsideHumidity());
                        String highinside = "" + Utilities.rounder(test.getMedianInsideHumidity());
                        String highoutsideDMD = "Left: med outside";
                        String highinsideDMD = "Right: med inside";
                        String val = "Humidity (%)";
                        IO.writeShort(0x24, highoutside.charAt(0));
                        IO.writeShort(0x22, highoutside.charAt(1));
                        IO.writeShort(0x34, highinside.charAt(0));
                        IO.writeShort(0x32, highinside.charAt(1));
                        for (int i = 0; i < highinsideDMD.length(); i++) {
                            IO.writeShort(0x40, highinsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < highoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, highoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                    }
                    case "mode" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String highoutside = "" + Utilities.rounder(test.getModeOutsideHum());
                        String highinside = "" + Utilities.rounder(test.getModeInsideHum());
                        String highoutsideDMD = "Left: mode outside";
                        String highinsideDMD = "Right: mode inside";
                        String val = "Humidity (%)";
                        IO.writeShort(0x24, highoutside.charAt(0));
                        IO.writeShort(0x22, highoutside.charAt(1));
                        IO.writeShort(0x34, highinside.charAt(0));
                        IO.writeShort(0x32, highinside.charAt(1));
                        for (int i = 0; i < highinsideDMD.length(); i++) {
                            IO.writeShort(0x40, highinsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < highoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, highoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                    }
                    case "deviation" -> {
                        String deviationIns = "" + test.standardDeviation("insideHumidity");
                        String deviationOut = "" + test.standardDeviation("outsideHumidity");
                        String deviationDMD = "Standard deviation \n Left = inside \n Right = outside";
                        if (Double.parseDouble(deviationIns) < 10) {
                            IO.writeShort(0x22, 0x100 | secondDigit("" + deviationIns.charAt(0)));
                            IO.writeShort(0x20, deviationIns.charAt(2));
                        } else {
                            IO.writeShort(0x24, deviationIns.charAt(0));
                            IO.writeShort(0x22, 0x100 | secondDigit("" + deviationIns.charAt(1)));
                            IO.writeShort(0x20, deviationIns.charAt(3));
                        }
                        if (Double.parseDouble(deviationOut) < 10) {
                            IO.writeShort(0x32, 0x100 | secondDigit("" + deviationOut.charAt(0)));
                            IO.writeShort(0x30, deviationIns.charAt(2));
                        } else {
                            IO.writeShort(0x34, deviationOut.charAt(0));
                            IO.writeShort(0x32, 0x100 | secondDigit("" + deviationOut.charAt(1)));
                            IO.writeShort(0x30, deviationOut.charAt(3));
                        }
                        GuiBoardUtilities.clrDMDisplay();
                        for (int i = 0; i < deviationDMD.length(); i++) {
                            IO.writeShort(0x40, deviationDMD.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                    }
                }
                break;
            case "wind":
                switch (type) {
                    case "avg" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String avgoutside = "" + Utilities.rounder(test.getAverageWindSpeed());
                        String avgoutsideDMD = "Left: avg outside";
                        String val = "Wind (KM/H)";
                        if (Double.parseDouble(avgoutside) >= 0 && Double.parseDouble(avgoutside) < 10) {
                            IO.writeShort(0x24, 0x100 | secondDigit("" + avgoutside.charAt(0)));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        } else if (Double.parseDouble(avgoutside) >= 10 && Double.parseDouble(avgoutside) < 100) {
                            IO.writeShort(0x24, avgoutside.charAt(0));
                            IO.writeShort(0x22, 0x100 | secondDigit("" + avgoutside.charAt(1)));
                            IO.writeShort(0x20, avgoutside.charAt(3));
                        } else if (Double.parseDouble(avgoutside) == 0) {
                            IO.writeShort(0x24, 0x100 | secondDigit("" + avgoutside.charAt(0)));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        } else if (Double.parseDouble(avgoutside) >= 100) {
                            IO.writeShort(0x24, avgoutside.charAt(0));
                            IO.writeShort(0x22, avgoutside.charAt(1));
                            IO.writeShort(0x20, avgoutside.charAt(2));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < avgoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, avgoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                        break;
                    }
                    case "low" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String avgoutside = "" + Utilities.rounder(test.getLowestWindSpeed());
                        String avgoutsideDMD = "Left: low outside";
                        String val = "Wind (KM/H)";
                        if (Double.parseDouble(avgoutside) >= 0 && Double.parseDouble(avgoutside) < 10) {
                            IO.writeShort(0x24, 0x100 | secondDigit("" + avgoutside.charAt(0)));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        } else if (Double.parseDouble(avgoutside) >= 10 && Double.parseDouble(avgoutside) < 100) {
                            IO.writeShort(0x24, avgoutside.charAt(0));
                            IO.writeShort(0x22, 0x100 | secondDigit("" + avgoutside.charAt(1)));
                            IO.writeShort(0x20, avgoutside.charAt(3));
                        } else if (Double.parseDouble(avgoutside) == 0) {
                            IO.writeShort(0x24, 0x100 | secondDigit("" + avgoutside.charAt(0)));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        } else if (Double.parseDouble(avgoutside) >= 100) {
                            IO.writeShort(0x24, avgoutside.charAt(0));
                            IO.writeShort(0x22, avgoutside.charAt(1));
                            IO.writeShort(0x20, avgoutside.charAt(2));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < avgoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, avgoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                        break;
                    }
                    case "high" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String avgoutside = "" + Utilities.rounder(test.getHighestWindSpeed());
                        String avgoutsideDMD = "Left: high outside";
                        String val = "Wind (KM/H)";
                        if (Double.parseDouble(avgoutside) >= 0 && Double.parseDouble(avgoutside) < 10) {
                            IO.writeShort(0x24, 0x100 | secondDigit("" + avgoutside.charAt(0)));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        } else if (Double.parseDouble(avgoutside) >= 10 && Double.parseDouble(avgoutside) < 100) {
                            System.out.println(Double.parseDouble(avgoutside));
                            IO.writeShort(0x24, avgoutside.charAt(0));
                            IO.writeShort(0x22, 0x100 | secondDigit("" + avgoutside.charAt(1)));
                            IO.writeShort(0x20, avgoutside.charAt(3));
                        } else if (Double.parseDouble(avgoutside) == 0) {
                            IO.writeShort(0x24, 0x100 | secondDigit("" + avgoutside.charAt(0)));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        } else if (Double.parseDouble(avgoutside) >= 100) {
                            IO.writeShort(0x24, avgoutside.charAt(0));
                            IO.writeShort(0x22, avgoutside.charAt(1));
                            IO.writeShort(0x20, avgoutside.charAt(2));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < avgoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, avgoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                        break;
                    }
                    case "median" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String avgoutside = "" + Utilities.rounder(test.getHighestWindSpeed());
                        String avgoutsideDMD = "Left: high outside";
                        String val = "Wind (KM/H)";
                        if (Double.parseDouble(avgoutside) >= 0 && Double.parseDouble(avgoutside) < 10) {
                            IO.writeShort(0x24, 0x100 | secondDigit("" + avgoutside.charAt(0)));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        } else if (Double.parseDouble(avgoutside) >= 10 && Double.parseDouble(avgoutside) < 100) {
                            IO.writeShort(0x24, avgoutside.charAt(0));
                            IO.writeShort(0x22, 0x100 | secondDigit("" + avgoutside.charAt(1)));
                            IO.writeShort(0x20, avgoutside.charAt(3));
                        } else if (Double.parseDouble(avgoutside) == 0) {
                            IO.writeShort(0x24, 0x100 | secondDigit("" + avgoutside.charAt(0)));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        } else if (Double.parseDouble(avgoutside) >= 100) {
                            IO.writeShort(0x24, avgoutside.charAt(0));
                            IO.writeShort(0x22, avgoutside.charAt(1));
                            IO.writeShort(0x20, avgoutside.charAt(2));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < avgoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, avgoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                        break;
                    }
                    case "mode" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String avgoutside = "" + Utilities.rounder(test.getModeWindspeed());
                        String avgoutsideDMD = "Left: mode outside";
                        String val = "Wind (KM/H)";
                        if (Double.parseDouble(avgoutside) >= 0 && Double.parseDouble(avgoutside) < 10) {
                            IO.writeShort(0x24, 0x100 | secondDigit("" + avgoutside.charAt(0)));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        } else if (Double.parseDouble(avgoutside) >= 10 && Double.parseDouble(avgoutside) < 100) {
                            IO.writeShort(0x24, avgoutside.charAt(0));
                            IO.writeShort(0x22, 0x100 | secondDigit("" + avgoutside.charAt(1)));
                            IO.writeShort(0x20, avgoutside.charAt(3));
                        } else if (Double.parseDouble(avgoutside) == 0) {
                            IO.writeShort(0x24, 0x100 | secondDigit("" + avgoutside.charAt(0)));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        } else if (Double.parseDouble(avgoutside) >= 100) {
                            IO.writeShort(0x24, avgoutside.charAt(0));
                            IO.writeShort(0x22, avgoutside.charAt(1));
                            IO.writeShort(0x20, avgoutside.charAt(2));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < avgoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, avgoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                        break;
                    }
                    case "deviation" -> {
                        String deviationIns = "" + test.standardDeviation("windSpeed");
                        String deviationDMD = "Standard deviation";
                        if (Double.parseDouble(deviationIns) < 10) {
                            IO.writeShort(0x22, 0x100 | secondDigit("" + deviationIns.charAt(0)));
                            IO.writeShort(0x20, deviationIns.charAt(2));
                        } else {
                            IO.writeShort(0x24, deviationIns.charAt(0));
                            IO.writeShort(0x22, 0x100 | secondDigit("" + deviationIns.charAt(1)));
                            IO.writeShort(0x20, deviationIns.charAt(3));
                        }
                        GuiBoardUtilities.clrDMDisplay();
                        for (int i = 0; i < deviationDMD.length(); i++) {
                            IO.writeShort(0x40, deviationDMD.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                    }
                }
                break;
            case "airpressure":
                switch (type) {
                    case "avg" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String avgoutside = "" + Utilities.rounder(test.getAverageAirPressure());
                        String avgoutsideDMD = "Top: avg outside";
                        String val = "Air pressure (hPa)";
                        if (Double.parseDouble(avgoutside) >= 1000) {
                            IO.writeShort(0x18, avgoutside.charAt(0));
                            IO.writeShort(0x16, avgoutside.charAt(1));
                            IO.writeShort(0x14, avgoutside.charAt(2));
                            IO.writeShort(0x12, 0x100 | secondDigit("" + avgoutside.charAt(3)));
                            IO.writeShort(0x10, avgoutside.charAt(5));
                        } else if (Double.parseDouble(avgoutside) < 1000) {
                            IO.writeShort(0x18, avgoutside.charAt(0));
                            IO.writeShort(0x16, avgoutside.charAt(1));
                            IO.writeShort(0x14, 0x100 | secondDigit("" + avgoutside.charAt(2)));
                            IO.writeShort(0x12, avgoutside.charAt(4));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < avgoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, avgoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                        break;
                    }
                    case "low" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String avgoutside = "" + Utilities.rounder(test.getLowestAirpressure());
                        String avgoutsideDMD = "Top: low outside";
                        String val = "Air pressure (hPa)";
                        if (Double.parseDouble(avgoutside) >= 1000) {
                            IO.writeShort(0x18, avgoutside.charAt(0));
                            IO.writeShort(0x16, avgoutside.charAt(1));
                            IO.writeShort(0x14, avgoutside.charAt(2));
                            IO.writeShort(0x12, 0x100 | secondDigit("" + avgoutside.charAt(3)));
                            IO.writeShort(0x10, avgoutside.charAt(5));
                        } else if (Double.parseDouble(avgoutside) < 1000) {
                            IO.writeShort(0x18, avgoutside.charAt(0));
                            IO.writeShort(0x16, avgoutside.charAt(1));
                            IO.writeShort(0x14, 0x100 | secondDigit("" + avgoutside.charAt(2)));
                            IO.writeShort(0x12, avgoutside.charAt(4));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < avgoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, avgoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                        break;
                    }
                    case "high" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String avgoutside = "" + Utilities.rounder(test.getHighestAirpressure());
                        String avgoutsideDMD = "Top: high outside";
                        String val = "Air pressure (hPa)";
                        if (Double.parseDouble(avgoutside) >= 1000) {
                            IO.writeShort(0x18, avgoutside.charAt(0));
                            IO.writeShort(0x16, avgoutside.charAt(1));
                            IO.writeShort(0x14, avgoutside.charAt(2));
                            IO.writeShort(0x12, 0x100 | secondDigit("" + avgoutside.charAt(3)));
                            IO.writeShort(0x10, avgoutside.charAt(5));
                        } else if (Double.parseDouble(avgoutside) < 1000) {
                            IO.writeShort(0x18, avgoutside.charAt(0));
                            IO.writeShort(0x16, avgoutside.charAt(1));
                            IO.writeShort(0x14, 0x100 | secondDigit("" + avgoutside.charAt(2)));
                            IO.writeShort(0x12, avgoutside.charAt(4));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < avgoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, avgoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                        break;
                    }
                    case "median" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String avgoutside = "" + Utilities.rounder(test.getMedianAirPressure());
                        String avgoutsideDMD = "Top: med outside";
                        String val = "Air pressure (hPa)";
                        if (Double.parseDouble(avgoutside) >= 1000) {
                            IO.writeShort(0x18, avgoutside.charAt(0));
                            IO.writeShort(0x16, avgoutside.charAt(1));
                            IO.writeShort(0x14, avgoutside.charAt(2));
                            IO.writeShort(0x12, 0x100 | secondDigit("" + avgoutside.charAt(3)));
                            IO.writeShort(0x10, avgoutside.charAt(5));
                        } else if (Double.parseDouble(avgoutside) < 1000) {
                            IO.writeShort(0x18, avgoutside.charAt(0));
                            IO.writeShort(0x16, avgoutside.charAt(1));
                            IO.writeShort(0x14, 0x100 | secondDigit("" + avgoutside.charAt(2)));
                            IO.writeShort(0x12, avgoutside.charAt(4));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < avgoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, avgoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                        break;
                    }
                    case "mode" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String avgoutside = "" + Utilities.rounder(test.getModeAirpressure()
                        );
                        String avgoutsideDMD = "Top: med outside";
                        String val = "Air pressure (hPa)";
                        if (Double.parseDouble(avgoutside) >= 1000) {
                            IO.writeShort(0x18, avgoutside.charAt(0));
                            IO.writeShort(0x16, avgoutside.charAt(1));
                            IO.writeShort(0x14, avgoutside.charAt(2));
                            IO.writeShort(0x12, 0x100 | secondDigit("" + avgoutside.charAt(3)));
                            IO.writeShort(0x10, avgoutside.charAt(5));
                        } else if (Double.parseDouble(avgoutside) < 1000) {
                            IO.writeShort(0x18, avgoutside.charAt(0));
                            IO.writeShort(0x16, avgoutside.charAt(1));
                            IO.writeShort(0x14, 0x100 | secondDigit("" + avgoutside.charAt(2)));
                            IO.writeShort(0x12, avgoutside.charAt(4));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < avgoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, avgoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                        break;
                    }
                    case "deviation" -> {
                        String deviationIns = "" + test.standardDeviation("airPressure");
                        String deviationDMD = "Standard deviation";
                        if (Double.parseDouble(deviationIns) < 10) {
                            IO.writeShort(0x22, 0x100 | secondDigit("" + deviationIns.charAt(0)));
                            IO.writeShort(0x20, deviationIns.charAt(2));
                        } else {
                            IO.writeShort(0x24, deviationIns.charAt(0));
                            IO.writeShort(0x22, 0x100 | secondDigit("" + deviationIns.charAt(1)));
                            IO.writeShort(0x20, deviationIns.charAt(3));
                        }
                        GuiBoardUtilities.clrDMDisplay();
                        for (int i = 0; i < deviationDMD.length(); i++) {
                            IO.writeShort(0x40, deviationDMD.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                    }
                }
                break;
            case "rain":
                switch (type) {
                    case "avg" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String avgoutside = "" + Utilities.rounder(test.getAverageRain());
                        String avgoutsideDMD = "Left: avg outside";
                        String val = "Rain (mm)";
                        if (Double.parseDouble(avgoutside) >= 0 && Double.parseDouble(avgoutside) < 10) {
                            IO.writeShort(0x24, 0x100 | secondDigit("" + avgoutside.charAt(0)));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        } else if (Double.parseDouble(avgoutside) >= 10 && Double.parseDouble(avgoutside) < 100) {
                            IO.writeShort(0x24, avgoutside.charAt(0));
                            IO.writeShort(0x22, 0x100 | secondDigit("" + avgoutside.charAt(1)));
                            IO.writeShort(0x20, avgoutside.charAt(3));
                        } else if (Double.parseDouble(avgoutside) >= 100) {
                            IO.writeShort(0x24, avgoutside.charAt(0));
                            IO.writeShort(0x22, avgoutside.charAt(1));
                            IO.writeShort(0x20, avgoutside.charAt(2));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < avgoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, avgoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                        break;
                    }
                    case "low" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String avgoutside = "" + Utilities.rounder(test.getLeastRain());
                        String avgoutsideDMD = "Left: least outside";
                        String val = "Rain (mm)";
                        if (Double.parseDouble(avgoutside) >= 0 && Double.parseDouble(avgoutside) < 10) {
                            IO.writeShort(0x24, 0x100 | secondDigit("" + avgoutside.charAt(0)));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        } else if (Double.parseDouble(avgoutside) >= 10 && Double.parseDouble(avgoutside) < 100) {
                            IO.writeShort(0x24, avgoutside.charAt(0));
                            IO.writeShort(0x22, 0x100 | secondDigit("" + avgoutside.charAt(1)));
                            IO.writeShort(0x20, avgoutside.charAt(3));
                        } else if (Double.parseDouble(avgoutside) >= 100) {
                            IO.writeShort(0x24, avgoutside.charAt(0));
                            IO.writeShort(0x22, avgoutside.charAt(1));
                            IO.writeShort(0x20, avgoutside.charAt(2));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < avgoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, avgoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                        break;
                    }
                    case "high" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String avgoutside = "" + Utilities.rounder(test.getMostRain());
                        String avgoutsideDMD = "Left: most outside";
                        String val = "Rain (mm)";
                        if (Double.parseDouble(avgoutside) >= 0 && Double.parseDouble(avgoutside) < 10) {
                            IO.writeShort(0x24, 0x100 | secondDigit("" + avgoutside.charAt(0)));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        } else if (Double.parseDouble(avgoutside) >= 10 && Double.parseDouble(avgoutside) < 100) {
                            IO.writeShort(0x24, avgoutside.charAt(0));
                            IO.writeShort(0x22, 0x100 | secondDigit("" + avgoutside.charAt(1)));
                            IO.writeShort(0x20, avgoutside.charAt(3));
                        } else if (Double.parseDouble(avgoutside) >= 100) {
                            IO.writeShort(0x24, avgoutside.charAt(0));
                            IO.writeShort(0x22, avgoutside.charAt(1));
                            IO.writeShort(0x20, avgoutside.charAt(2));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < avgoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, avgoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                        break;
                    }
                    case "median" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String avgoutside = "" + Utilities.rounder(test.getMedianRain());
                        String avgoutsideDMD = "Left: med outside";
                        String val = "Rain (mm)";
                        if (Double.parseDouble(avgoutside) >= 0 && Double.parseDouble(avgoutside) < 10) {
                            IO.writeShort(0x24, 0x100 | secondDigit("" + avgoutside.charAt(0)));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        } else if (Double.parseDouble(avgoutside) >= 10 && Double.parseDouble(avgoutside) < 100) {
                            IO.writeShort(0x24, avgoutside.charAt(0));
                            IO.writeShort(0x22, 0x100 | secondDigit("" + avgoutside.charAt(1)));
                            IO.writeShort(0x20, avgoutside.charAt(3));
                        } else if (Double.parseDouble(avgoutside) >= 100) {
                            IO.writeShort(0x24, avgoutside.charAt(0));
                            IO.writeShort(0x22, avgoutside.charAt(1));
                            IO.writeShort(0x20, avgoutside.charAt(2));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < avgoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, avgoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                        break;
                    }
                    case "mode" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String avgoutside = "" + Utilities.rounder(test.getModeRain());
                        String avgoutsideDMD = "Left: mod outside";
                        String val = "Rain (mm)";
                        if (Double.parseDouble(avgoutside) >= 0 && Double.parseDouble(avgoutside) < 10) {
                            IO.writeShort(0x24, 0x100 | secondDigit("" + avgoutside.charAt(0)));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        } else if (Double.parseDouble(avgoutside) >= 10 && Double.parseDouble(avgoutside) < 100) {
                            IO.writeShort(0x24, avgoutside.charAt(0));
                            IO.writeShort(0x22, 0x100 | secondDigit("" + avgoutside.charAt(1)));
                            IO.writeShort(0x20, avgoutside.charAt(3));
                        } else if (Double.parseDouble(avgoutside) >= 100) {
                            IO.writeShort(0x24, avgoutside.charAt(0));
                            IO.writeShort(0x22, avgoutside.charAt(1));
                            IO.writeShort(0x20, avgoutside.charAt(2));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < avgoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, avgoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                        break;
                    }
                    case "deviation" -> {
                        String deviationIns = "" + test.standardDeviation("rain");
                        String deviationDMD = "Standard deviation";
                        if (Double.parseDouble(deviationIns) < 10) {
                            IO.writeShort(0x22, 0x100 | secondDigit("" + deviationIns.charAt(0)));
                            IO.writeShort(0x20, deviationIns.charAt(2));
                        } else {
                            IO.writeShort(0x24, deviationIns.charAt(0));
                            IO.writeShort(0x22, 0x100 | secondDigit("" + deviationIns.charAt(1)));
                            IO.writeShort(0x20, deviationIns.charAt(3));
                        }
                        GuiBoardUtilities.clrDMDisplay();
                        for (int i = 0; i < deviationDMD.length(); i++) {
                            IO.writeShort(0x40, deviationDMD.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                    }
                }
                break;
            case "uv":
                switch (type) {
                    case "avg" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String avgoutside = "" + Utilities.rounder(test.getAverageUV());
                        String avgoutsideDMD = "Left: avg outside";
                        String val = "UV scale (intensity)";
                        if (Double.parseDouble(avgoutside) >= 0 && Double.parseDouble(avgoutside) < 10) {
                            IO.writeShort(0x24, 0x100 | secondDigit("" + avgoutside.charAt(0)));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        } else if (Double.parseDouble(avgoutside) >= 10 && Double.parseDouble(avgoutside) < 100) {
                            IO.writeShort(0x24, avgoutside.charAt(0));
                            IO.writeShort(0x22, 0x100 | secondDigit("" + avgoutside.charAt(1)));
                            IO.writeShort(0x20, avgoutside.charAt(3));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < avgoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, avgoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                        break;
                    }
                    case "low" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String avgoutside = "" + Utilities.rounder(test.getLowestUV());
                        String avgoutsideDMD = "Left: low outside";
                        String val = "UV scale (intensity)";
                        if (Double.parseDouble(avgoutside) >= 0 && Double.parseDouble(avgoutside) < 10) {
                            IO.writeShort(0x24, 0x100 | secondDigit("" + avgoutside.charAt(0)));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        } else if (Double.parseDouble(avgoutside) >= 10 && Double.parseDouble(avgoutside) < 100) {
                            IO.writeShort(0x24, avgoutside.charAt(0));
                            IO.writeShort(0x22, 0x100 | secondDigit("" + avgoutside.charAt(1)));
                            IO.writeShort(0x20, avgoutside.charAt(3));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < avgoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, avgoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                        break;
                    }
                    case "high" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String avgoutside = "" + Utilities.rounder(test.getHighestUV());
                        String avgoutsideDMD = "Left: high outside";
                        String val = "UV scale (intensity)";
                        if (Double.parseDouble(avgoutside) >= 0 && Double.parseDouble(avgoutside) < 10) {
                            IO.writeShort(0x24, 0x100 | secondDigit("" + avgoutside.charAt(0)));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        } else if (Double.parseDouble(avgoutside) >= 10 && Double.parseDouble(avgoutside) < 100) {
                            IO.writeShort(0x24, avgoutside.charAt(0));
                            IO.writeShort(0x22, 0x100 | secondDigit("" + avgoutside.charAt(1)));
                            IO.writeShort(0x20, avgoutside.charAt(3));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < avgoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, avgoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                        break;
                    }
                    case "median" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String avgoutside = "" + Utilities.rounder(test.getMedianUV());
                        String avgoutsideDMD = "Left: med outside";
                        String val = "UV scale (intensity)";
                        if (Double.parseDouble(avgoutside) >= 0 && Double.parseDouble(avgoutside) < 10) {
                            IO.writeShort(0x24, 0x100 | secondDigit("" + avgoutside.charAt(0)));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        } else if (Double.parseDouble(avgoutside) >= 10 && Double.parseDouble(avgoutside) < 100) {
                            IO.writeShort(0x24, avgoutside.charAt(0));
                            IO.writeShort(0x22, 0x100 | secondDigit("" + avgoutside.charAt(1)));
                            IO.writeShort(0x20, avgoutside.charAt(3));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < avgoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, avgoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                        break;
                    }
                    case "mode" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String avgoutside = "" + Utilities.rounder(test.getModeUV());
                        String avgoutsideDMD = "Left: mode outside";
                        String val = "UV scale (intensity)";
                        if (Double.parseDouble(avgoutside) >= 0 && Double.parseDouble(avgoutside) < 10) {
                            IO.writeShort(0x24, 0x100 | secondDigit("" + avgoutside.charAt(0)));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        } else if (Double.parseDouble(avgoutside) >= 10 && Double.parseDouble(avgoutside) < 100) {
                            IO.writeShort(0x24, avgoutside.charAt(0));
                            IO.writeShort(0x22, 0x100 | secondDigit("" + avgoutside.charAt(1)));
                            IO.writeShort(0x20, avgoutside.charAt(3));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < avgoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, avgoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                        break;
                    }
                    case "deviation" -> {
                        String deviationIns = "" + test.standardDeviation("UV");
                        String deviationDMD = "Standard deviation";
                        if (Double.parseDouble(deviationIns) < 10) {
                            IO.writeShort(0x22, 0x100 | secondDigit("" + deviationIns.charAt(0)));
                            IO.writeShort(0x20, deviationIns.charAt(2));
                        } else {
                            IO.writeShort(0x24, deviationIns.charAt(0));
                            IO.writeShort(0x22, 0x100 | secondDigit("" + deviationIns.charAt(1)));
                            IO.writeShort(0x20, deviationIns.charAt(3));
                        }
                        GuiBoardUtilities.clrDMDisplay();
                        for (int i = 0; i < deviationDMD.length(); i++) {
                            IO.writeShort(0x40, deviationDMD.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                    }
                }
                break;
            case "solarrad":
                switch (type) {
                    case "avg" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String avgoutside = "" + Utilities.rounder(test.getAverageSolarrad());
                        String avgoutsideDMD = "Left: avg outside";
                        String val = "Solar radiation (W/M2)";
                        if (Double.parseDouble(avgoutside) >= 1000) {
                            IO.writeShort(0x18, avgoutside.charAt(0));
                            IO.writeShort(0x16, avgoutside.charAt(1));
                            IO.writeShort(0x14, avgoutside.charAt(2));
                            IO.writeShort(0x12, 0x100 | secondDigit("" + avgoutside.charAt(3)));
                            IO.writeShort(0x10, avgoutside.charAt(5));
                        } else if (Double.parseDouble(avgoutside) < 1000) {
                            IO.writeShort(0x18, avgoutside.charAt(0));
                            IO.writeShort(0x16, avgoutside.charAt(1));
                            IO.writeShort(0x14, 0x100 | secondDigit("" + avgoutside.charAt(2)));
                            IO.writeShort(0x12, avgoutside.charAt(4));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < avgoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, avgoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                        break;
                    }
                    case "low" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String avgoutside = "" + Utilities.rounder(test.getLowestSolarrad());
                        String avgoutsideDMD = "Left: low outside";
                        String val = "Solar radiation (W/M2)";
                        if (Double.parseDouble(avgoutside) >= 1000) {
                            IO.writeShort(0x18, avgoutside.charAt(0));
                            IO.writeShort(0x16, avgoutside.charAt(1));
                            IO.writeShort(0x14, avgoutside.charAt(2));
                            IO.writeShort(0x12, 0x100 | secondDigit("" + avgoutside.charAt(3)));
                            IO.writeShort(0x10, avgoutside.charAt(5));
                        } else if (Double.parseDouble(avgoutside) < 1000) {
                            IO.writeShort(0x18, avgoutside.charAt(0));
                            IO.writeShort(0x16, avgoutside.charAt(1));
                            IO.writeShort(0x14, 0x100 | secondDigit("" + avgoutside.charAt(2)));
                            IO.writeShort(0x12, avgoutside.charAt(4));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < avgoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, avgoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                        break;
                    }
                    case "high" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String avgoutside = "" + Utilities.rounder(test.getHighestSolarrad());
                        String avgoutsideDMD = "Left: high outside";
                        String val = "Solar radiation (W/M2)";
                        if (Double.parseDouble(avgoutside) >= 1000) {
                            IO.writeShort(0x18, avgoutside.charAt(0));
                            IO.writeShort(0x16, avgoutside.charAt(1));
                            IO.writeShort(0x14, avgoutside.charAt(2));
                            IO.writeShort(0x12, 0x100 | secondDigit("" + avgoutside.charAt(3)));
                            IO.writeShort(0x10, avgoutside.charAt(5));
                        } else if (Double.parseDouble(avgoutside) < 1000) {
                            IO.writeShort(0x18, avgoutside.charAt(0));
                            IO.writeShort(0x16, avgoutside.charAt(1));
                            IO.writeShort(0x14, 0x100 | secondDigit("" + avgoutside.charAt(2)));
                            IO.writeShort(0x12, avgoutside.charAt(4));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < avgoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, avgoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                        break;
                    }
                    case "median" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String avgoutside = "" + Utilities.rounder(test.getMedianSolarrad());
                        String avgoutsideDMD = "Left: med outside";
                        String val = "Solar radiation (W/M2)";
                        if (Double.parseDouble(avgoutside) >= 1000) {
                            IO.writeShort(0x18, avgoutside.charAt(0));
                            IO.writeShort(0x16, avgoutside.charAt(1));
                            IO.writeShort(0x14, avgoutside.charAt(2));
                            IO.writeShort(0x12, 0x100 | secondDigit("" + avgoutside.charAt(3)));
                            IO.writeShort(0x10, avgoutside.charAt(5));
                        } else if (Double.parseDouble(avgoutside) < 1000) {
                            IO.writeShort(0x18, avgoutside.charAt(0));
                            IO.writeShort(0x16, avgoutside.charAt(1));
                            IO.writeShort(0x14, 0x100 | secondDigit("" + avgoutside.charAt(2)));
                            IO.writeShort(0x12, avgoutside.charAt(4));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < avgoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, avgoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                        break;
                    }
                    case "mode" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String avgoutside = "" + Utilities.rounder(test.getModeSolarrad());
                        String avgoutsideDMD = "Left: mode outside";
                        String val = "Solar radiation (W/M2)";
                        if (Double.parseDouble(avgoutside) >= 1000) {
                            IO.writeShort(0x18, avgoutside.charAt(0));
                            IO.writeShort(0x16, avgoutside.charAt(1));
                            IO.writeShort(0x14, avgoutside.charAt(2));
                            IO.writeShort(0x12, 0x100 | secondDigit("" + avgoutside.charAt(3)));
                            IO.writeShort(0x10, avgoutside.charAt(5));
                        } else if (Double.parseDouble(avgoutside) < 1000) {
                            IO.writeShort(0x18, avgoutside.charAt(0));
                            IO.writeShort(0x16, avgoutside.charAt(1));
                            IO.writeShort(0x14, 0x100 | secondDigit("" + avgoutside.charAt(2)));
                            IO.writeShort(0x12, avgoutside.charAt(4));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < avgoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, avgoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                        break;
                    }
                    case "deviation" -> {
                        String deviationIns = "" + test.standardDeviation("solarRad");
                        String deviationDMD = "Standard deviation";
                        if (Double.parseDouble(deviationIns) < 10) {
                            IO.writeShort(0x22, 0x100 | secondDigit("" + deviationIns.charAt(0)));
                            IO.writeShort(0x20, deviationIns.charAt(2));
                        } else {
                            IO.writeShort(0x24, deviationIns.charAt(0));
                            IO.writeShort(0x22, 0x100 | secondDigit("" + deviationIns.charAt(1)));
                            IO.writeShort(0x20, deviationIns.charAt(3));
                        }
                        GuiBoardUtilities.clrDMDisplay();
                        for (int i = 0; i < deviationDMD.length(); i++) {
                            IO.writeShort(0x40, deviationDMD.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                    }
                }
                break;
            case "dewpoint":
                switch (type) {
                    case "avg" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String avgoutside = "" + Utilities.rounder(test.getAverageDewpoint());
                        String avgoutsideDMD = "Left: avg outside";
                        String val = "Temperature (C)";
                        if (Double.parseDouble(avgoutside) >= 0 && Double.parseDouble(avgoutside) < 10) {
                            IO.writeShort(0x24, 0x100 | secondDigit("" + avgoutside.charAt(0)));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        } else if (Double.parseDouble(avgoutside) >= 10 && Double.parseDouble(avgoutside) < 100) {
                            IO.writeShort(0x24, avgoutside.charAt(0));
                            IO.writeShort(0x22, 0x100 | secondDigit("" + avgoutside.charAt(1)));
                            IO.writeShort(0x20, avgoutside.charAt(3));
                        } else if (Double.parseDouble(avgoutside) < 0 && Double.parseDouble(avgoutside) > -10) {
                            IO.writeShort(0x24, 0x100 | negativeSign);
                            IO.writeShort(0x22, 0x100 | secondDigit("" + avgoutside.charAt(1)));
                            IO.writeShort(0x20, avgoutside.charAt(3));
                        } else if (Double.parseDouble(avgoutside) < -10) {
                            IO.writeShort(0x24, 0x100 | negativeSign);
                            IO.writeShort(0x22, avgoutside.charAt(1));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < avgoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, avgoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                        break;
                    }
                    case "low" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String avgoutside = "" + Utilities.rounder(test.getLowestDewpoint());
                        String avgoutsideDMD = "Left: low outside";
                        String val = "Temperature (C)";
                        if (Double.parseDouble(avgoutside) >= 0 && Double.parseDouble(avgoutside) < 10) {
                            IO.writeShort(0x24, 0x100 | secondDigit("" + avgoutside.charAt(0)));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        } else if (Double.parseDouble(avgoutside) >= 10 && Double.parseDouble(avgoutside) < 100) {
                            IO.writeShort(0x24, avgoutside.charAt(0));
                            IO.writeShort(0x22, 0x100 | secondDigit("" + avgoutside.charAt(1)));
                            IO.writeShort(0x20, avgoutside.charAt(3));
                        } else if (Double.parseDouble(avgoutside) < 0 && Double.parseDouble(avgoutside) > -10) {
                            IO.writeShort(0x24, 0x100 | negativeSign);
                            IO.writeShort(0x22, 0x100 | secondDigit("" + avgoutside.charAt(1)));
                            IO.writeShort(0x20, avgoutside.charAt(3));
                        } else if (Double.parseDouble(avgoutside) < -10) {
                            IO.writeShort(0x24, 0x100 | negativeSign);
                            IO.writeShort(0x22, avgoutside.charAt(1));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < avgoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, avgoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                        break;
                    }
                    case "high" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String avgoutside = "" + Utilities.rounder(test.getHighestDewpoint());
                        String avgoutsideDMD = "Left: high outside";
                        String val = "Temperature (C)";
                        if (Double.parseDouble(avgoutside) >= 0 && Double.parseDouble(avgoutside) < 10) {
                            IO.writeShort(0x24, 0x100 | secondDigit("" + avgoutside.charAt(0)));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        } else if (Double.parseDouble(avgoutside) >= 10 && Double.parseDouble(avgoutside) < 100) {
                            IO.writeShort(0x24, avgoutside.charAt(0));
                            IO.writeShort(0x22, 0x100 | secondDigit("" + avgoutside.charAt(1)));
                            IO.writeShort(0x20, avgoutside.charAt(3));
                        } else if (Double.parseDouble(avgoutside) < 0 && Double.parseDouble(avgoutside) > -10) {
                            IO.writeShort(0x24, 0x100 | negativeSign);
                            IO.writeShort(0x22, 0x100 | secondDigit("" + avgoutside.charAt(1)));
                            IO.writeShort(0x20, avgoutside.charAt(3));
                        } else if (Double.parseDouble(avgoutside) < -10) {
                            IO.writeShort(0x24, 0x100 | negativeSign);
                            IO.writeShort(0x22, avgoutside.charAt(1));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < avgoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, avgoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                        break;
                    }
                    case "median" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String avgoutside = "" + Utilities.rounder(test.getMedianDewpoint());
                        String avgoutsideDMD = "Left: med outside";
                        String val = "Temperature (C)";
                        if (Double.parseDouble(avgoutside) >= 0 && Double.parseDouble(avgoutside) < 10) {
                            IO.writeShort(0x24, 0x100 | secondDigit("" + avgoutside.charAt(0)));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        } else if (Double.parseDouble(avgoutside) >= 10 && Double.parseDouble(avgoutside) < 100) {
                            IO.writeShort(0x24, avgoutside.charAt(0));
                            IO.writeShort(0x22, 0x100 | secondDigit("" + avgoutside.charAt(1)));
                            IO.writeShort(0x20, avgoutside.charAt(3));
                        } else if (Double.parseDouble(avgoutside) < 0 && Double.parseDouble(avgoutside) > -10) {
                            IO.writeShort(0x24, 0x100 | negativeSign);
                            IO.writeShort(0x22, 0x100 | secondDigit("" + avgoutside.charAt(1)));
                            IO.writeShort(0x20, avgoutside.charAt(3));
                        } else if (Double.parseDouble(avgoutside) < -10) {
                            IO.writeShort(0x24, 0x100 | negativeSign);
                            IO.writeShort(0x22, avgoutside.charAt(1));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < avgoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, avgoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                        break;
                    }
                    case "mode" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String avgoutside = "" + Utilities.rounder(test.getModeDewpoint());
                        String avgoutsideDMD = "Left: mode outside";
                        String val = "Temperature (C)";
                        if (Double.parseDouble(avgoutside) >= 0 && Double.parseDouble(avgoutside) < 10) {
                            IO.writeShort(0x24, 0x100 | secondDigit("" + avgoutside.charAt(0)));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        } else if (Double.parseDouble(avgoutside) >= 10 && Double.parseDouble(avgoutside) < 100) {
                            IO.writeShort(0x24, avgoutside.charAt(0));
                            IO.writeShort(0x22, 0x100 | secondDigit("" + avgoutside.charAt(1)));
                            IO.writeShort(0x20, avgoutside.charAt(3));
                        } else if (Double.parseDouble(avgoutside) < 0 && Double.parseDouble(avgoutside) > -10) {
                            IO.writeShort(0x24, 0x100 | negativeSign);
                            IO.writeShort(0x22, 0x100 | secondDigit("" + avgoutside.charAt(1)));
                            IO.writeShort(0x20, avgoutside.charAt(3));
                        } else if (Double.parseDouble(avgoutside) < -10) {
                            IO.writeShort(0x24, 0x100 | negativeSign);
                            IO.writeShort(0x22, avgoutside.charAt(1));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < avgoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, avgoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                        break;
                    }
                    case "deviation" -> {
                        String deviationIns = "" + test.standardDeviation("dewPoint");
                        String deviationDMD = "Standard deviation";
                        if (Double.parseDouble(deviationIns) < 10) {
                            IO.writeShort(0x22, 0x100 | secondDigit("" + deviationIns.charAt(0)));
                            IO.writeShort(0x20, deviationIns.charAt(2));
                        } else {
                            IO.writeShort(0x24, deviationIns.charAt(0));
                            IO.writeShort(0x22, 0x100 | secondDigit("" + deviationIns.charAt(1)));
                            IO.writeShort(0x20, deviationIns.charAt(3));
                        }
                        GuiBoardUtilities.clrDMDisplay();
                        for (int i = 0; i < deviationDMD.length(); i++) {
                            IO.writeShort(0x40, deviationDMD.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                    }
                }
                break;
            case "windchill":
                switch (type) {
                    case "avg" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String avgoutside = "" + Utilities.rounder(test.getAverageWindchill());
                        String avgoutsideDMD = "Left: avg outside";
                        String val = "Temperature (C)";
                        if (Double.parseDouble(avgoutside) >= 0 && Double.parseDouble(avgoutside) < 10) {
                            IO.writeShort(0x24, 0x100 | secondDigit("" + avgoutside.charAt(0)));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        } else if (Double.parseDouble(avgoutside) >= 10 && Double.parseDouble(avgoutside) < 100) {
                            IO.writeShort(0x24, avgoutside.charAt(0));
                            IO.writeShort(0x22, 0x100 | secondDigit("" + avgoutside.charAt(1)));
                            IO.writeShort(0x20, avgoutside.charAt(3));
                        } else if (Double.parseDouble(avgoutside) < 0 && Double.parseDouble(avgoutside) > -10) {
                            IO.writeShort(0x24, 0x100 | negativeSign);
                            IO.writeShort(0x22, 0x100 | secondDigit("" + avgoutside.charAt(1)));
                            IO.writeShort(0x20, avgoutside.charAt(3));
                        } else if (Double.parseDouble(avgoutside) < -10) {
                            IO.writeShort(0x24, 0x100 | negativeSign);
                            IO.writeShort(0x22, avgoutside.charAt(1));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < avgoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, avgoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                        break;
                    }
                    case "low" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String avgoutside = "" + Utilities.rounder(test.getLowestWindchill());
                        String avgoutsideDMD = "Left: low outside";
                        String val = "Temperature (C)";
                        if (Double.parseDouble(avgoutside) >= 0 && Double.parseDouble(avgoutside) < 10) {
                            IO.writeShort(0x24, 0x100 | secondDigit("" + avgoutside.charAt(0)));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        } else if (Double.parseDouble(avgoutside) >= 10 && Double.parseDouble(avgoutside) < 100) {
                            IO.writeShort(0x24, avgoutside.charAt(0));
                            IO.writeShort(0x22, 0x100 | secondDigit("" + avgoutside.charAt(1)));
                            IO.writeShort(0x20, avgoutside.charAt(3));
                        } else if (Double.parseDouble(avgoutside) < 0 && Double.parseDouble(avgoutside) > -10) {
                            IO.writeShort(0x24, 0x100 | negativeSign);
                            IO.writeShort(0x22, 0x100 | secondDigit("" + avgoutside.charAt(1)));
                            IO.writeShort(0x20, avgoutside.charAt(3));
                        } else if (Double.parseDouble(avgoutside) < -10) {
                            IO.writeShort(0x24, 0x100 | negativeSign);
                            IO.writeShort(0x22, avgoutside.charAt(1));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < avgoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, avgoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                        break;
                    }
                    case "high" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String avgoutside = "" + Utilities.rounder(test.getHighestWindchill());
                        String avgoutsideDMD = "Left: high outside";
                        String val = "Temperature (C)";
                        if (Double.parseDouble(avgoutside) >= 0 && Double.parseDouble(avgoutside) < 10) {
                            IO.writeShort(0x24, 0x100 | secondDigit("" + avgoutside.charAt(0)));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        } else if (Double.parseDouble(avgoutside) >= 10 && Double.parseDouble(avgoutside) < 100) {
                            IO.writeShort(0x24, avgoutside.charAt(0));
                            IO.writeShort(0x22, 0x100 | secondDigit("" + avgoutside.charAt(1)));
                            IO.writeShort(0x20, avgoutside.charAt(3));
                        } else if (Double.parseDouble(avgoutside) < 0 && Double.parseDouble(avgoutside) > -10) {
                            IO.writeShort(0x24, 0x100 | negativeSign);
                            IO.writeShort(0x22, 0x100 | secondDigit("" + avgoutside.charAt(1)));
                            IO.writeShort(0x20, avgoutside.charAt(3));
                        } else if (Double.parseDouble(avgoutside) < -10) {
                            IO.writeShort(0x24, 0x100 | negativeSign);
                            IO.writeShort(0x22, avgoutside.charAt(1));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < avgoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, avgoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                        break;
                    }
                    case "median" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String avgoutside = "" + Utilities.rounder(test.getMedianWindchill());
                        String avgoutsideDMD = "Left: med outside";
                        String val = "Temperature (C)";
                        if (Double.parseDouble(avgoutside) >= 0 && Double.parseDouble(avgoutside) < 10) {
                            IO.writeShort(0x24, 0x100 | secondDigit("" + avgoutside.charAt(0)));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        } else if (Double.parseDouble(avgoutside) >= 10 && Double.parseDouble(avgoutside) < 100) {
                            IO.writeShort(0x24, avgoutside.charAt(0));
                            IO.writeShort(0x22, 0x100 | secondDigit("" + avgoutside.charAt(1)));
                            IO.writeShort(0x20, avgoutside.charAt(3));
                        } else if (Double.parseDouble(avgoutside) < 0 && Double.parseDouble(avgoutside) > -10) {
                            IO.writeShort(0x24, 0x100 | negativeSign);
                            IO.writeShort(0x22, 0x100 | secondDigit("" + avgoutside.charAt(1)));
                            IO.writeShort(0x20, avgoutside.charAt(3));
                        } else if (Double.parseDouble(avgoutside) < -10) {
                            IO.writeShort(0x24, 0x100 | negativeSign);
                            IO.writeShort(0x22, avgoutside.charAt(1));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < avgoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, avgoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                        break;
                    }
                    case "mode" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String avgoutside = "" + Utilities.rounder(test.getModeWindchill());
                        String avgoutsideDMD = "Left: mod outside";
                        String val = "Temperature (C)";
                        if (Double.parseDouble(avgoutside) >= 0 && Double.parseDouble(avgoutside) < 10) {
                            IO.writeShort(0x24, 0x100 | secondDigit("" + avgoutside.charAt(0)));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        } else if (Double.parseDouble(avgoutside) >= 10 && Double.parseDouble(avgoutside) < 100) {
                            IO.writeShort(0x24, avgoutside.charAt(0));
                            IO.writeShort(0x22, 0x100 | secondDigit("" + avgoutside.charAt(1)));
                            IO.writeShort(0x20, avgoutside.charAt(3));
                        } else if (Double.parseDouble(avgoutside) < 0 && Double.parseDouble(avgoutside) > -10) {
                            IO.writeShort(0x24, 0x100 | negativeSign);
                            IO.writeShort(0x22, 0x100 | secondDigit("" + avgoutside.charAt(1)));
                            IO.writeShort(0x20, avgoutside.charAt(3));
                        } else if (Double.parseDouble(avgoutside) < -10) {
                            IO.writeShort(0x24, 0x100 | negativeSign);
                            IO.writeShort(0x22, avgoutside.charAt(1));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < avgoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, avgoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                        break;
                    }
                    case "deviation" -> {
                        String deviationIns = "" + test.standardDeviation("windChill");
                        String deviationDMD = "Standard deviation \n Left = inside \n Right = outside";
                        if (Double.parseDouble(deviationIns) < 10) {
                            IO.writeShort(0x22, 0x100 | secondDigit("" + deviationIns.charAt(0)));
                            IO.writeShort(0x20, deviationIns.charAt(2));
                        } else {
                            IO.writeShort(0x24, deviationIns.charAt(0));
                            IO.writeShort(0x22, 0x100 | secondDigit("" + deviationIns.charAt(1)));
                            IO.writeShort(0x20, deviationIns.charAt(3));
                        }
                        GuiBoardUtilities.clrDMDisplay();
                        for (int i = 0; i < deviationDMD.length(); i++) {
                            IO.writeShort(0x40, deviationDMD.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                    }
                }
                break;
            case "heatindex":
                switch (type) {
                    case "avg" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String avgoutside = "" + Utilities.rounder(test.getAverageHeatindex());
                        String avgoutsideDMD = "Left: avg outside";
                        String val = "Temperature (C)";
                        if (Double.parseDouble(avgoutside) >= 0 && Double.parseDouble(avgoutside) < 10) {
                            IO.writeShort(0x24, 0x100 | secondDigit("" + avgoutside.charAt(0)));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        } else if (Double.parseDouble(avgoutside) >= 10 && Double.parseDouble(avgoutside) < 100) {
                            IO.writeShort(0x24, avgoutside.charAt(0));
                            IO.writeShort(0x22, 0x100 | secondDigit("" + avgoutside.charAt(1)));
                            IO.writeShort(0x20, avgoutside.charAt(3));
                        } else if (Double.parseDouble(avgoutside) < 0 && Double.parseDouble(avgoutside) > -10) {
                            IO.writeShort(0x24, 0x100 | negativeSign);
                            IO.writeShort(0x22, 0x100 | secondDigit("" + avgoutside.charAt(1)));
                            IO.writeShort(0x20, avgoutside.charAt(3));
                        } else if (Double.parseDouble(avgoutside) < -10) {
                            IO.writeShort(0x24, 0x100 | negativeSign);
                            IO.writeShort(0x22, avgoutside.charAt(1));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < avgoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, avgoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                        break;
                    }
                    case "low" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String avgoutside = "" + Utilities.rounder(test.getLowestHeatindex());
                        String avgoutsideDMD = "Left: low outside";
                        String val = "Temperature (C)";
                        if (Double.parseDouble(avgoutside) >= 0 && Double.parseDouble(avgoutside) < 10) {
                            IO.writeShort(0x24, 0x100 | secondDigit("" + avgoutside.charAt(0)));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        } else if (Double.parseDouble(avgoutside) >= 10 && Double.parseDouble(avgoutside) < 100) {
                            IO.writeShort(0x24, avgoutside.charAt(0));
                            IO.writeShort(0x22, 0x100 | secondDigit("" + avgoutside.charAt(1)));
                            IO.writeShort(0x20, avgoutside.charAt(3));
                        } else if (Double.parseDouble(avgoutside) < 0 && Double.parseDouble(avgoutside) > -10) {
                            IO.writeShort(0x24, 0x100 | negativeSign);
                            IO.writeShort(0x22, 0x100 | secondDigit("" + avgoutside.charAt(1)));
                            IO.writeShort(0x20, avgoutside.charAt(3));
                        } else if (Double.parseDouble(avgoutside) < -10) {
                            IO.writeShort(0x24, 0x100 | negativeSign);
                            IO.writeShort(0x22, avgoutside.charAt(1));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < avgoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, avgoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                        break;
                    }
                    case "high" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String avgoutside = "" + Utilities.rounder(test.getHighestHeatindex());
                        String avgoutsideDMD = "Left: high outside";
                        String val = "Temperature (C)";
                        if (Double.parseDouble(avgoutside) >= 0 && Double.parseDouble(avgoutside) < 10) {
                            IO.writeShort(0x24, 0x100 | secondDigit("" + avgoutside.charAt(0)));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        } else if (Double.parseDouble(avgoutside) >= 10 && Double.parseDouble(avgoutside) < 100) {
                            IO.writeShort(0x24, avgoutside.charAt(0));
                            IO.writeShort(0x22, 0x100 | secondDigit("" + avgoutside.charAt(1)));
                            IO.writeShort(0x20, avgoutside.charAt(3));
                        } else if (Double.parseDouble(avgoutside) < 0 && Double.parseDouble(avgoutside) > -10) {
                            IO.writeShort(0x24, 0x100 | negativeSign);
                            IO.writeShort(0x22, 0x100 | secondDigit("" + avgoutside.charAt(1)));
                            IO.writeShort(0x20, avgoutside.charAt(3));
                        } else if (Double.parseDouble(avgoutside) < -10) {
                            IO.writeShort(0x24, 0x100 | negativeSign);
                            IO.writeShort(0x22, avgoutside.charAt(1));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < avgoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, avgoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                        break;
                    }
                    case "median" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String avgoutside = "" + Utilities.rounder(test.getMedianHeatindex());
                        String avgoutsideDMD = "Left: med outside";
                        String val = "Temperature (C)";
                        if (Double.parseDouble(avgoutside) >= 0 && Double.parseDouble(avgoutside) < 10) {
                            IO.writeShort(0x24, 0x100 | secondDigit("" + avgoutside.charAt(0)));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        } else if (Double.parseDouble(avgoutside) >= 10 && Double.parseDouble(avgoutside) < 100) {
                            IO.writeShort(0x24, avgoutside.charAt(0));
                            IO.writeShort(0x22, 0x100 | secondDigit("" + avgoutside.charAt(1)));
                            IO.writeShort(0x20, avgoutside.charAt(3));
                        } else if (Double.parseDouble(avgoutside) < 0 && Double.parseDouble(avgoutside) > -10) {
                            IO.writeShort(0x24, 0x100 | negativeSign);
                            IO.writeShort(0x22, 0x100 | secondDigit("" + avgoutside.charAt(1)));
                            IO.writeShort(0x20, avgoutside.charAt(3));
                        } else if (Double.parseDouble(avgoutside) < -10) {
                            IO.writeShort(0x24, 0x100 | negativeSign);
                            IO.writeShort(0x22, avgoutside.charAt(1));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < avgoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, avgoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                        break;
                    }
                    case "mode" -> {
                        GuiBoardUtilities.clrDMDisplay();
                        GuiBoardUtilities.clrSevenSegment();
                        String avgoutside = "" + Utilities.rounder(test.getModeHeatindex());
                        String avgoutsideDMD = "Left: mod outside";
                        String val = "Temperature (C)";
                        if (Double.parseDouble(avgoutside) >= 0 && Double.parseDouble(avgoutside) < 10) {
                            IO.writeShort(0x24, 0x100 | secondDigit("" + avgoutside.charAt(0)));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        } else if (Double.parseDouble(avgoutside) >= 10 && Double.parseDouble(avgoutside) < 100) {
                            IO.writeShort(0x24, avgoutside.charAt(0));
                            IO.writeShort(0x22, 0x100 | secondDigit("" + avgoutside.charAt(1)));
                            IO.writeShort(0x20, avgoutside.charAt(3));
                        } else if (Double.parseDouble(avgoutside) < 0 && Double.parseDouble(avgoutside) > -10) {
                            IO.writeShort(0x24, 0x100 | negativeSign);
                            IO.writeShort(0x22, 0x100 | secondDigit("" + avgoutside.charAt(1)));
                            IO.writeShort(0x20, avgoutside.charAt(3));
                        } else if (Double.parseDouble(avgoutside) < -10) {
                            IO.writeShort(0x24, 0x100 | negativeSign);
                            IO.writeShort(0x22, avgoutside.charAt(1));
                            IO.writeShort(0x22, avgoutside.charAt(2));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < avgoutsideDMD.length(); i++) {
                            IO.writeShort(0x40, avgoutsideDMD.charAt(i));
                        }
                        IO.writeShort(0x40, '\n');
                        for (int i = 0; i < val.length(); i++) {
                            IO.writeShort(0x40, val.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                        break;
                    }
                    case "deviation" -> {
                        String deviationIns = "" + test.standardDeviation("heatIndex");
                        String deviationDMD = "Standard deviation \n Left = inside \n Right = outside";
                        if (Double.parseDouble(deviationIns) < 10) {
                            IO.writeShort(0x22, 0x100 | secondDigit("" + deviationIns.charAt(0)));
                            IO.writeShort(0x20, deviationIns.charAt(2));
                        } else {
                            IO.writeShort(0x24, deviationIns.charAt(0));
                            IO.writeShort(0x22, 0x100 | secondDigit("" + deviationIns.charAt(1)));
                            IO.writeShort(0x20, deviationIns.charAt(3));
                        }
                        GuiBoardUtilities.clrDMDisplay();
                        for (int i = 0; i < deviationDMD.length(); i++) {
                            IO.writeShort(0x40, deviationDMD.charAt(i));
                        }
                        PageSelectors.returnToFirstPage();
                    }
                }
                break;
        }
    }
}
//balls