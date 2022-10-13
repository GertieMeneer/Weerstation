public class Utilities {

    RawMeasurement rawMeasurement = DatabaseConnection.getMostRecentMeasurement();
    Measurement measurement = new Measurement(rawMeasurement);

    public Utilities() {

    }

    public String toString() {
        String outsidetemp = ("Outsidetemperature: " + rounder(measurement.getOutsideTemperature()) + " graden Celcius");
        String insidetemp = ("Insidetemperature: " + rounder(measurement.getInsideTemperature()) + " graden Celcius");
        String airpressure = ("Airpressure: " + rounder(measurement.getAirPressure()) + " hPa");
        String insidehum = ("Humidity Inside: " + rounder(measurement.getInsideHum()) + " %");
        String outsidehum = ("Humidity Outside: " + rounder(measurement.getOutsideHum()) + " %");
        String windspeed = ("Windspeed: " + rounder(measurement.getWindSpeed()) + " KM/h");
        String avgwindspeed = ("Average Windspeed: " + rounder(measurement.getAvgWindSpeed()) + " KM/h");
        String windir = ("Winddirection: " + measurement.getWindDirection());
        String rainrate = ("Rainrate: " + rounder(measurement.getRainRate()));
        String uvlevel = ("UV index: " + measurement.getUVLevel());
        String solarrad = ("Solar Radiation: " + rounder(measurement.getSolarRad()) + " W/m2");
        String xmitbatt = ("Battery Station: " + measurement.getXmitBatt() + " %");
        String consbatt = ("Battery Console: " + measurement.getBattLevel() + " %");
        String foricon = ("Icon: " + measurement.getForeIcon());
        String sunrise = ("Sunrise: " + measurement.getSunrise());
        String sunset = ("Sunset: " + measurement.getSunset());
        String dewpoint = ("Dewpoint: " + rounder(measurement.getDewPoint()) + " graden Celcius");
        String windchill = ("Wind Chill: " + rounder(measurement.getWindChill()) + " graden Celcius");
        String heatindex = ("Heat Index: " + rounder(measurement.getHeatIndex()) + " graden Celcius");

        String text = outsidetemp + "\n" + insidetemp + "\n" + airpressure +
                "\n" + insidehum + "\n" + outsidehum +
                "\n" + windspeed + "\n" + avgwindspeed +
                 "\n" + windir + "\n" + rainrate
                + "\n" + uvlevel + "\n" + solarrad
                + "\n" + xmitbatt + "\n" + consbatt
                + "\n" + foricon + "\n" + sunrise
                + "\n" + sunset + "\n" + dewpoint
                + "\n" + windchill + "\n" + heatindex;
        return text;
    }

    public static double rounder (double number){
        number = Math.round(number * 10.0) / 10.0;
        return number;
    }
}

