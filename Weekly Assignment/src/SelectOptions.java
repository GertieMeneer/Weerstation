import java.util.ArrayList;

public class SelectOptions {
    public static void selectTemp() {
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
        IO.writeShort(0x24, outsideTemp.charAt(0));
        IO.writeShort(0x22, 0x100 | secondDigit("" + outsideTemp.charAt(1)));
        IO.writeShort(0x20, outsideTemp.charAt(3));
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
        String battsDMD = "Current battery stats \n Left: xmit (%) \n Right: otherbat";
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

    public static void selectReturnHome() {
        GuiBoardUtilities.clrDMDisplay();
        GuiBoardUtilities.clrSevenSegment();
        PrintPage.homePage();
    }

    public static void selectGoToTop() {
        PageSelectors.noSelection();
        PrintPage.firstPage();
    }
}
