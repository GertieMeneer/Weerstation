public class Utilities {

    RawMeasurement rawMeasurement = DatabaseConnection.getMostRecentMeasurement();
    Measurement measurement = new Measurement(rawMeasurement);

    public Utilities() {

    }

    public String toString() {
        String outsidetemp = ("Buitentemperatuur: " + rounder(measurement.getOutsideTemperature()) + " graden Celcius");
        String insidetemp = ("Binnentemperatuur: " + rounder(measurement.getInsideTemperature()) + " graden Celcius");
        String airpressure = ("Luchtdruk: " + rounder(measurement.getAirPressure()) + " hPa");
        String insidehum = ("Luchtvochtigheid binnen: " + rounder(measurement.getInsideHum()) + " %");
        String outsidehum = ("Luchtvochtigheid buiten: " + rounder(measurement.getOutsideHum()) + " %");
        String windspeed = ("Windsnelheid: " + rounder(measurement.getWindSpeed()) + " KM/h");
        String avgwindspeed = ("Gemiddelde windsnelheid: " + rounder(measurement.getAvgWindSpeed()) + " KM/h");
        String windir = ("Windrichting: " + measurement.getWindDirection());
        String rainrate = ("Hoeveelheid neerslag: " + rounder(measurement.getRainRate()));
        String uvlevel = ("UV index: " + measurement.getUVLevel());
        String solarrad = ("Zonintensiteit: " + rounder(measurement.getSolarRad()) + " W/m2");
        String xmitbatt = ("Batterijniveau station: " + measurement.getXmitBatt() + " %");
        String consbatt = ("Batterijniveau console: " + measurement.getBattLevel() + " %");
        String foricon = ("Icon: " + measurement.getForeIcon());
        String sunrise = ("Zonsopgang: " + measurement.getSunrise());
        String sunset = ("Zonsondergang: " + measurement.getSunset());
        String dewpoint = ("Dauwpunt: " + rounder(measurement.getDewPoint()) + " graden Celcius");
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
