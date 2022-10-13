import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class RawMeasurement implements Serializable
{

    private String stationId;
    private LocalDateTime dateStamp;
    private short barometer;
    private short insideTemp;
    private short insideHum;
    private short outsideTemp;
    private short windSpeed;
    private short avgWindSpeed;
    private short windDir;
    private short outsideHum;
    private short rainRate;
    private short UVLevel;
    private short solarRad;
    private short xmitBatt;
    private short battLevel;
    private short foreIcon;
    private short sunrise;
    private short sunset;

    public RawMeasurement ()
    {

    }

    // stationId
    public void setStationId (String str) { this.stationId = str;};
    public String getStationId () { return stationId; };

    // dateStamp
    public void setDateStamp (LocalDateTime ts) { this.dateStamp = ts;};
    public LocalDateTime getDateStamp () { return dateStamp; };

    // barometer
    public void setBarometer (short val) { this.barometer = val;};
    public short getBarometer () { return barometer; };

    // insideTemp
    public void setInsideTemp (short val) { this.insideTemp = val;};
    public short getInsideTemp () { return insideTemp; };

    // insideHum
    public void setInsideHum (short val) { this.insideHum = val;};
    public short getInsideHum () { return insideHum; };

    // outsideTemp
    public void setOutsideTemp (short val) { this.outsideTemp = val;};
    public short getOutsideTemp () { return outsideTemp; };

    // windSpeed
    public void setWindSpeed (short val) { this.windSpeed = val;};
    public short getWindSpeed () { return windSpeed; };

    // avgWindSpeed
    public void setAvgWindSpeed (short val) { this.avgWindSpeed = val;};
    public short getAvgWindSpeed () { return avgWindSpeed; };

    // windDir
    public void setWindDir (short val) { this.windDir = val;};
    public short getWindDir () { return windDir; };

    // outsideHum
    public void setOutsideHum (short val) { this.outsideHum = val;};
    public short getOutsideHum () { return outsideHum; };

    // rainRate
    public void setRainRate (short val) { this.rainRate = val;};
    public short getRainRate () { return rainRate; };

    // UVLevel
    public void setUVLevel (short val) { this.UVLevel = val;};
    public short getUVLevel () { return UVLevel; };

    // solarRad
    public void setSolarRad (short val) { this.solarRad = val;};
    public short getSolarRad () { return solarRad; };

    // xmitBatt
    public void setXmitBatt (short val) { this.xmitBatt = val;};
    public short getXmitBatt () { return xmitBatt; };

    // battLevel
    public void setBattLevel (short val) { this.battLevel = val;};
    public short getBattLevel () { return battLevel; };

    // foreIcon
    public void setForeIcon (short val) { this.foreIcon = val;};
    public short getForeIcon () { return foreIcon; };

    // sunrise
    public void setSunrise (short val) { this.sunrise = val;};
    public short getSunrise () { return sunrise; };

    // sunset
    public void setSunset (short val) { this.sunset = val;};
    public short getSunset () { return sunset; };

    public String toString()
    {
        String s = "RawMeasurement:"
                + "\nstationId = \t" + stationId
                + "\ndateStamp = \t" + dateStamp
                + "\nbarometer = \t" + barometer
                + "\ninsideTemp = \t" + insideTemp
                + "\ninsideHum = \t" + insideHum
                + "\noutsideTemp = \t" + outsideTemp
                + "\nwindSpeed = \t" + windSpeed
                + "\navgWindSpeed = \t" + avgWindSpeed
                + "\nwindDir = \t" + windDir
                + "\noutsideHum = \t" + outsideHum
                + "\nrainRate = \t" + rainRate
                + "\nUVLevel = \t" + UVLevel
                + "\nsolarRad = \t" + solarRad
                + "\nxmitBatt = \t" + xmitBatt
                + "\nbattLevel = \t" + battLevel
                + "\nforeIcon = \t" + foreIcon
                + "\nsunrise = \t" + sunrise
                + "\nsunset = \t" + sunset;
        return s;
    }


}
