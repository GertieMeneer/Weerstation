public class Measurement {

    private double outsideTemperature;
    private double insideTemperature;
    private double airPressure;
    private double insideHumidity;
    private double windSpeed;
    private double avgWindSpeed;
    private String windDirection;
    private double outsideHumidity;
    private double rainRate;
    private double UVLevel;
    private double solarRad;
    private double xmitBatt;
    private double battLevel;
    private double foreIcon;
    private String sunrise;
    private String sunset;
    private double dewPoint;
    private double windChill;
    private double heatIndex;

    public Measurement(RawMeasurement rawMeasurement) {
        this.outsideTemperature = ValueConverter.Temperature(rawMeasurement.getOutsideTemp());
        this.insideTemperature = ValueConverter.Temperature(rawMeasurement.getInsideTemp());
        this.airPressure = ValueConverter.airPressure(rawMeasurement.getBarometer());
        this.insideHumidity = rawMeasurement.getInsideHum();
        this.windSpeed = ValueConverter.windSpeed(rawMeasurement.getWindSpeed());
        this.avgWindSpeed = ValueConverter.windSpeed(rawMeasurement.getAvgWindSpeed());
        this.windDirection = ValueConverter.windDirection(rawMeasurement.getWindDir());
        this.outsideHumidity = rawMeasurement.getOutsideHum();
        this.rainRate = ValueConverter.rainMeter(rawMeasurement.getRainRate());
        this.UVLevel = ValueConverter.uvIndex(rawMeasurement.getUVLevel());
        this.solarRad = rawMeasurement.getSolarRad();
        this.xmitBatt = rawMeasurement.getXmitBatt();
        this.battLevel = rawMeasurement.getBattLevel();
        this.foreIcon = rawMeasurement.getForeIcon();
        this.sunset = ValueConverter.Time(rawMeasurement.getSunset());
        this.sunrise = ValueConverter.Time(rawMeasurement.getSunrise());
        this.dewPoint = ValueConverter.dewPoint(ValueConverter.Temperature(rawMeasurement.getOutsideTemp()), rawMeasurement.getOutsideHum());
        this.windChill = ValueConverter.windChill(rawMeasurement.getWindSpeed(), rawMeasurement.getOutsideTemp());
        this.heatIndex = ValueConverter.heatIndex(rawMeasurement.getOutsideTemp(), rawMeasurement.getOutsideHum());

    }

    public double getOutsideTemperature() {

        return outsideTemperature;
    }

    public double getInsideTemperature() {

        return insideTemperature;
    }

    public double getAirPressure() {

        return airPressure;
    }

    public double getInsideHum() {

        return insideHumidity;
    }

    public double getWindSpeed() {

        return windSpeed;
    }

    public double getAvgWindSpeed() {

        return avgWindSpeed;
    }

    public String getWindDirection() {

        return windDirection;
    }

    public double getOutsideHum() {

        return outsideHumidity;
    }

    public double getRainRate() {

        return rainRate;
    }

    public double getUVLevel() {

        return UVLevel;
    }

    public double getSolarRad() {

        return solarRad;
    }

    public double getXmitBatt() {
        //ik denk niet dat de geprinte waarde goed is, maar je krijgt de rawwaarde hiermee

        return xmitBatt;
    }

    public double getBattLevel() {
        //ik denk niet dat de geprinte waarde goed is, maar je krijgt de rawwaarde hiermee

        return battLevel;
    }

    public double getForeIcon() {
        //ik denk niet dat de geprinte waarde goed is, maar je krijgt de rawwaarde hiermee

        return foreIcon;
    }

    public String getSunrise() {

        return sunrise;
    }

    public String getSunset() {

        return sunset;
    }

    public double getDewPoint() {

        return dewPoint;
    }

    public double getWindChill() {

        return windChill;
    }

    public double getHeatIndex() {

        return heatIndex;
    }

    public boolean isValid() {
        return outsideTemperature < 50 && outsideTemperature > -20 && insideTemperature < 50 && insideTemperature > -20 && airPressure > 950 && airPressure < 1050
                && windSpeed < 140 && rainRate < 50;
    }


}
