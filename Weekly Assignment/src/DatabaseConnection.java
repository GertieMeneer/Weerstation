import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;

public class DatabaseConnection {

    public static final String host = "http://145.48.6.82:1337/";

    /**
     * Static initializer, makes sure the cache is cleaned on a regular base
     */
    static {
        try {
            Files.createDirectories(Paths.get(getCacheDir()));
            cleanCache();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the most recent measurement from the database
     */
    public static RawMeasurement getMostRecentMeasurement() {
        return buildMeasurement(new DataInputStream(buildStream(host + "last/bin")));
    }

    /**
     * Clears the cache on the filesystem
     */
    public static void clearCache() {
        try {
            Files.list(Paths.get(getCacheDir())).forEach(file ->
            {
                try {
                    Files.delete(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Returns an ArrayList of raw measurements, between the 2 dates and times given as parameters
     *
     * @param begin the date and time of the first measurement
     * @param end   the date and time of the last measurement
     * @return the arraylist of raw measurements
     */
    public static ArrayList<RawMeasurement> getMeasurementsBetween(LocalDateTime begin, LocalDateTime end) {
        return getMeasurements(host + "between/" + begin + "/" + end + "/bin");
    }

    /**
     * Returns all measurements since the given date and time
     * @param since
     * @return the arraylist of raw measurements
     */
    public static ArrayList<RawMeasurement> getMeasurementsSince(LocalDateTime since) {
        return getMeasurements(host + "between/" + since + "/" + LocalDateTime.now() + "/bin");
    }

    /**
     * Returns all measurements of the past year
     * @return the arraylist of raw measurements
     */
    public static ArrayList<RawMeasurement> getMeasurementsLastYear() {
        return getMeasurements(host + "lastmonths/12/bin");
    }

    /**
     * Returns all measurements of the past month
     * @return the arraylist of raw measurements
     */
    public static ArrayList<RawMeasurement> getMeasurementsLastMonth() {
        return getMeasurementsLastMonths(1);
    }

    /**
     * Returns all measurements of the past months
     * @param months	the number of months you want parameters for
     * @return the arraylist of raw measurements
     */
    public static ArrayList<RawMeasurement> getMeasurementsLastMonths(int months) {
        return getMeasurements(host + "lastmonths/" + months + "/bin");
    }


    /**
     * Returns all measurements of the past day
     * @return the arraylist of raw measurements
     */
    public static ArrayList<RawMeasurement> getMeasurementsLastDay() {
        return getMeasurementsLastDays(1);
    }

    /**
     * Returns all measurements of the past days
     * @param days the amount of days
     * @return the arraylist of raw measurements
     */
    public static ArrayList<RawMeasurement> getMeasurementsLastDays(int days) {
        return getMeasurements(host + "lastdays/" + days + "/bin");
    }

    /**
     * Returns all measurements of the past hour
     * @return the arraylist of raw measurements
     */
    public static ArrayList<RawMeasurement> getMeasurementsLastHour() {
        return getMeasurementsLastHours(1);
    }

    /**
     * Returns all measurements of the past hours
     * @param hours the amount of hours
     * @return the arraylist of raw measurements
     */
    public static ArrayList<RawMeasurement> getMeasurementsLastHours(int hours) {
        return getMeasurements(host + "lasthours/" + hours + "/bin");
    }

    /**
     * Builds up a list of measurements
     *
     * @param address the address (http or https protocol included) to call the REST API at
     * @return a list of raw measurements
     */
    private static ArrayList<RawMeasurement> getMeasurements(String address) {
        String cacheFile = getCacheDir() + buildCacheFileName(address) + ".bin";
        if (Files.exists(Paths.get(cacheFile))) {
            try {
                return (ArrayList<RawMeasurement>) new ObjectInputStream(new BufferedInputStream(new FileInputStream(cacheFile))).readObject();
            }catch(EOFException e)
            {
                //do nothiung
            }
            catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        try {

            InputStream is = buildStream(address);
            DataInputStream reader = new DataInputStream(is);

            ArrayList<RawMeasurement> measurements = new ArrayList<>();
            while (true) {
                RawMeasurement measurement = buildMeasurement(reader);
                if (measurement == null)
                    break;
                measurements.add(measurement);
            }
            new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(cacheFile))).writeObject(measurements);
            return measurements;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private static String getCacheDir() {
        return System.getProperty("java.io.tmpdir") + "ws/";
    }

    private static String buildCacheFileName(String address) {
        address = address.substring(host.length());
        address = address.replace('/', '_');
        address = address.replace(':', '_');
        if (address.isEmpty())
            return "";

        address = (System.currentTimeMillis() / (1000 * 60 * 60)) + address;

        return address;
    }


    /**
     * Builds up a stream for an url. Handles gzip compression if available( it should be available)
     *
     * @param address the address (http or https protocol included) to call the REST API at
     * @return a JsonReader object on this address
     */
    private static InputStream buildStream(String address) {
        try {
            URL url = new URL(address);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept-Encoding", "gzip");
            con.connect();


            if ("gzip".equals(con.getContentEncoding()))
                return new BufferedInputStream(new GZIPInputStream(con.getInputStream()));
            else
                return new BufferedInputStream(con.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Reads a raw raw measurement from a data inputstream
     * @param stream the datastream
     * @return one measurement, or null if no complete measurement can be read
     */
    private static RawMeasurement buildMeasurement(DataInputStream stream) {
        try {
            RawMeasurement measurement = new RawMeasurement();
            measurement.setBarometer(stream.readShort());
            measurement.setDateStamp(LocalDateTime.ofEpochSecond(stream.readLong(), 0, ZoneOffset.UTC));
            measurement.setInsideTemp(stream.readShort());
            measurement.setInsideHum(stream.readShort());
            measurement.setOutsideTemp(stream.readShort());
            measurement.setOutsideHum(stream.readShort());
            measurement.setWindSpeed(stream.readShort());
            measurement.setAvgWindSpeed(stream.readShort());
            measurement.setWindDir(stream.readShort());
            measurement.setRainRate(stream.readShort());
            measurement.setUVLevel(stream.readShort());
            measurement.setSolarRad(stream.readShort());
            measurement.setXmitBatt(stream.readShort());
            measurement.setBattLevel(stream.readShort());
            measurement.setSunrise(stream.readShort());
            measurement.setSunset(stream.readShort());

            measurement.setStationId(stream.readShort() + "");
            return measurement;
        } catch (EOFException e) {
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * Cleans the cache, only deletes old, expired cachefiles
     */
    private static void cleanCache() {
        try {
            Files.list(Paths.get(getCacheDir())).forEach(file ->
            {
                try {
                    int timestamp = ((Number) NumberFormat.getInstance().parse(file.getFileName().toString())).intValue();
                    long current = (System.currentTimeMillis() / (1000 * 60 * 60));
                    if (timestamp != current)
                        Files.delete(file);

                } catch (ParseException e) {

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}



