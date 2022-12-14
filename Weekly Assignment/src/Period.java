
import java.time.*;
import java.time.temporal.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * A class to contain a period of time
 *
 * @author Johan Talboom
 * @version 2.0
 */
public class Period {
    private LocalDate beginPeriod;
    private LocalDate endPeriod;

    private int year;
    private ArrayList<Measurement> measurements;

    /**
     * default constructor, sets the period to today
     */
    public Period() {
        beginPeriod = LocalDate.now();
        endPeriod = LocalDate.now();
    }

    public Period(String year) {
        this.year = Integer.parseInt(year);
    }

    public Period(LocalDate beginPeriod, LocalDate endPeriod) {
        this.beginPeriod = beginPeriod;
        this.endPeriod = endPeriod;
    }

    public Period(LocalDate beginPeriod) {
        this.beginPeriod = beginPeriod;
        this.endPeriod = LocalDate.now();
    }

    public Period(int days) {
        this.beginPeriod = LocalDate.now().minus(java.time.Period.ofDays(days));
        this.endPeriod = LocalDate.now();
    }

    /**
     * Simple setter for start of period
     */
    public void setStart(int year, int month, int day) {
        beginPeriod = LocalDate.of(year, month, day);
    }

    /**
     * simple setter for end of period
     */
    public void setEnd(int year, int month, int day) {
        endPeriod = LocalDate.of(year, month, day);
    }

    /**
     * alternative setter for start of period
     *
     * @param beginPeriod
     */
    public void setStart(LocalDate beginPeriod) {
        this.beginPeriod = beginPeriod;
    }

    /**
     * alternative setter for end of period
     *
     * @param endPeriod
     */
    public void setEnd(LocalDate endPeriod) {
        this.endPeriod = endPeriod;
    }

    /**
     * calculates the number of days in the period
     */
    public long numberOfDays() {
        return ChronoUnit.DAYS.between(beginPeriod, endPeriod);
    }


    /**
     * gets all raw measurements of this period from the database
     *
     * @return a list of raw measurements
     */
    public ArrayList<RawMeasurement> getRawMeasurements() {
        return DatabaseConnection.getMeasurementsBetween(LocalDateTime.of(beginPeriod, LocalTime.of(0, 1)), LocalDateTime.of(endPeriod, LocalTime.of(23, 59)));
    }

    /**
     * Builds an ArrayList of measurements. This method also filters out any 'bad' measurements
     *
     * @return a filtered list of measurements
     */
    public ArrayList<Measurement> getMeasurements() {
        measurements = new ArrayList<>();
        ArrayList<RawMeasurement> rawMeasurements = getRawMeasurements();
        for (RawMeasurement rawMeasurement : rawMeasurements) {
            Measurement measurement = new Measurement(rawMeasurement);
            if (measurement.isValid()) {
                measurements.add(measurement);
            }
        }
        return measurements;
    }


    /**
     * todo
     *
     * @return
     */


    public double getAverageOutsideTemperature() {
        getMeasurements();
        double value = 0;
        int measurementCount = measurements.size();
        for (Measurement measurement : measurements) {
            value += measurement.getOutsideTemperature();
        }
        value /= measurementCount;
        return Utilities.rounder(value);
    }

    public double getAverageInsideTemperature() {
        getMeasurements();
        double value = 0;
        int measurementCount = measurements.size();
        for (Measurement measurement : measurements) {
            value += measurement.getInsideTemperature();
        }
        value /= measurementCount;
        return Utilities.rounder(value);
    }

    public double getAverageInsideHumidity() {
        getMeasurements();
        double value = 0;
        for (Measurement measurement : measurements) {
            value += measurement.getInsideHum();
        }
        value /= measurements.size();
        return Utilities.rounder(value);
    }

    public double getAverageOutsideHumidity() {
        getMeasurements();
        double value = 0;
        for (Measurement measurement : measurements) {
            value += measurement.getOutsideHum();
        }
        value /= measurements.size();
        return Utilities.rounder(value);
    }

    public double getAverageAirPressure() {
        getMeasurements();
        double value = 0;
        int measurementCount = measurements.size();
        for (Measurement measurement : measurements) {
            value += measurement.getAirPressure();
        }
        value /= measurementCount;
        return Utilities.rounder(value);
    }


    public double getLowestOutsideTemp() {
        getMeasurements();
        double value = measurements.get(0).getOutsideTemperature();
        for (Measurement measures : measurements) {
            if (measures.getOutsideTemperature() < value) {
                value = measures.getOutsideTemperature();
            }
        }
        return Utilities.rounder(value);
    }

    public double getHighestOutsideTemp() {
        getMeasurements();
        double value = measurements.get(0).getOutsideTemperature();
        for (Measurement measures : measurements) {
            if (measures.getOutsideTemperature() > value) {
                value = measures.getOutsideTemperature();
            }
        }
        return Utilities.rounder(value);
    }

    public double getLowestInsideTemp() {
        getMeasurements();
        double value = measurements.get(0).getInsideTemperature();
        for (Measurement measures : measurements) {
            if (measures.getInsideTemperature() < value) {
                value = measures.getInsideTemperature();
            }
        }
        return Utilities.rounder(value);
    }

    public double getHighestInsideTemp() {
        getMeasurements();
        double value = measurements.get(0).getInsideTemperature();
        for (Measurement measures : measurements) {
            if (measures.getInsideTemperature() > value) {
                value = measures.getInsideTemperature();
            }
        }
        return Utilities.rounder(value);
    }

    public double lowestInsideHum() {
        getMeasurements();
        double value = measurements.get(0).getInsideHum();
        for (Measurement measures : measurements) {
            if (measures.getInsideHum() < value) {
                value = measures.getInsideHum();
            }
        }
        return Utilities.rounder(value);
    }

    public double getLowestOutsideHum() {
        getMeasurements();
        double value = measurements.get(0).getOutsideHum();
        for (Measurement measures : measurements) {
            if (measures.getOutsideHum() < value) {
                value = measures.getOutsideHum();
            }
        }
        return Utilities.rounder(value);
    }

    public double getHighestOutsideHum() {
        getMeasurements();
        double value = measurements.get(0).getOutsideHum();
        for (Measurement measures : measurements) {
            if (measures.getOutsideHum() > value) {
                value = measures.getOutsideHum();
            }
        }
        return Utilities.rounder(value);
    }

    public double getHighestInsideHum() {
        getMeasurements();
        double value = measurements.get(0).getInsideHum();
        for (Measurement measures : measurements) {
            if (measures.getInsideHum() < value) {
                value = measures.getInsideHum();
            }
        }
        return Utilities.rounder(value);
    }

    public double getLowestAirpressure() {
        getMeasurements();
        double value = measurements.get(0).getAirPressure();
        for (Measurement measures : measurements) {
            if (measures.getAirPressure() < value) {
                value = measures.getAirPressure();
            }
        }
        return Utilities.rounder(value);
    }

    public double getHighestAirpressure() {
        getMeasurements();
        double value = measurements.get(0).getAirPressure();
        for (Measurement measures : measurements) {
            if (measures.getAirPressure() > value) {
                value = measures.getAirPressure();
            }
        }
        return Utilities.rounder(value);
    }

    public double getTempDifference() {
        boolean higher = false; // false inside true outside
        boolean raise1 = false;
        boolean raise2 = false;
        getMeasurements();
        int insideCount = 0;
        int outsideCount = 0;
        for (Measurement measurement : measurements) {
            if (measurement.getInsideTemperature() > measurement.getOutsideTemperature() && !higher) {
                raise1 = false;
            } else if (measurement.getInsideTemperature() > measurement.getOutsideTemperature() && higher) {
                raise1 = true;
            }

            if (measurement.getOutsideTemperature() > measurement.getInsideTemperature() && higher) {
                raise2 = false;
            } else if (measurement.getOutsideTemperature() > measurement.getInsideTemperature() && !higher) {
                raise2 = true;
            }


            if (measurement.getInsideTemperature() > measurement.getOutsideTemperature() && raise1) {
                higher = false;
                insideCount++;
            } else if (measurement.getOutsideTemperature() > measurement.getInsideTemperature() && raise2) {
                higher = true;
                outsideCount++;
            }

        }
        return outsideCount + insideCount;
    }

    public String windChillAndOutsideTemperatureDifference() {
        getMeasurements();
        double outsideTemperature = measurements.get(0).getOutsideTemperature();
        double windChill = measurements.get(0).getWindChill();
        double windSpeed = measurements.get(0).getWindSpeed();
        LocalDate date = beginPeriod;
        double difference = 0.0;
        double highestDifference = 0.0;
        int j = 0;
        for (int i = 0; i < measurements.size(); i++) {
            outsideTemperature = measurements.get(i).getOutsideTemperature();
            windChill = measurements.get(i).getWindChill();
            difference = Math.abs(outsideTemperature - windChill);
            if (difference > highestDifference && outsideTemperature < 50 && windSpeed < 205) {
                highestDifference = difference;
                date = beginPeriod.plusDays((int) Math.floor(i / 1440));
            }
        }
        return "" + Utilities.rounder(highestDifference) + " on " + date;
    }

    public double getMedianOutsideTemperature() {
        getMeasurements();

        if (measurements.size() % 2 == 0) {
            return Utilities.rounder(((measurements.get(measurements.size() / 2).getOutsideTemperature()) + (measurements.get(measurements.size() / 2 + 1).getOutsideTemperature())) / 2);
        } else {
            return Utilities.rounder(measurements.get(((measurements.size() - 1) / 2) + 1).getOutsideTemperature());
        }
    }

    public double getMedianInsideTemperature() {
        getMeasurements();

        if (measurements.size() % 2 == 0) {
            return Utilities.rounder(((measurements.get(measurements.size() / 2).getInsideTemperature()) + (measurements.get(measurements.size() / 2 + 1).getInsideTemperature())) / 2);
        } else {
            return Utilities.rounder(measurements.get(((measurements.size() - 1) / 2) + 1).getInsideTemperature());
        }
    }

    public double getMedianOutsideHumidity() {
        getMeasurements();

        if (measurements.size() % 2 == 0) {
            return Utilities.rounder(((measurements.get(measurements.size() / 2).getOutsideHum()) + (measurements.get(measurements.size() / 2 + 1).getOutsideHum())) / 2);
        } else {
            return Utilities.rounder(measurements.get(((measurements.size() - 1) / 2) + 1).getOutsideHum());
        }
    }

    public double getMedianInsideHumidity() {
        getMeasurements();

        if (measurements.size() % 2 == 0) {
            return Utilities.rounder(((measurements.get(measurements.size() / 2).getInsideHum()) + (measurements.get(measurements.size() / 2 + 1).getInsideHum())) / 2);
        } else {
            return Utilities.rounder(measurements.get(((measurements.size() - 1) / 2) + 1).getInsideHum());
        }
    }

    public double getMedianAirPressure() {
        getMeasurements();

        if (measurements.size() % 2 == 0) {
            return Utilities.rounder(((measurements.get(measurements.size() / 2).getAirPressure()) + (measurements.get(measurements.size() / 2 + 1).getAirPressure())) / 2);
        } else {
            return Utilities.rounder(measurements.get(((measurements.size() - 1) / 2) + 1).getAirPressure());
        }
    }

    public double getModeOutsideTemp() {
        ArrayList<Integer> counter = new ArrayList<>();
        ArrayList<Double> values = new ArrayList<>();
        getMeasurements();
        int bigger = 0;
        for (Measurement measures : measurements) {
            double rawValue = Utilities.rounder(measures.getOutsideTemperature());
            if (values.contains(rawValue)) {
                int index = values.indexOf(rawValue);
                counter.set(index, (counter.get(index) + 1));

            } else {
                values.add(rawValue);
                counter.add(1);
            }
        }
        for (int i : counter) {
            if (i > bigger) {
                bigger = i;
            }
        }
        int index2 = counter.indexOf(bigger);

        return values.get(index2);
    }

    public double getModeInsideTemp() {
        ArrayList<Integer> counter = new ArrayList<>();
        ArrayList<Double> values = new ArrayList<>();
        getMeasurements();
        int bigger = 0;
        for (Measurement measures : measurements) {
            double rawValue = Utilities.rounder(measures.getInsideTemperature());
            if (values.contains(rawValue)) {
                int index = values.indexOf(rawValue);
                counter.set(index, (counter.get(index) + 1));

            } else {
                values.add(rawValue);
                counter.add(1);
            }
        }
        for (int i : counter) {
            if (i > bigger) {
                bigger = i;
            }
        }
        int index2 = counter.indexOf(bigger);

        return values.get(index2);
    }

    public double getModeOutsideHum() {
        ArrayList<Integer> counter = new ArrayList<>();
        ArrayList<Double> values = new ArrayList<>();
        getMeasurements();
        int bigger = 0;
        for (Measurement measures : measurements) {
            double rawValue = Utilities.rounder(measures.getOutsideHum());
            if (values.contains(rawValue)) {
                int index = values.indexOf(rawValue);
                counter.set(index, (counter.get(index) + 1));

            } else {
                values.add(rawValue);
                counter.add(1);
            }
        }
        for (int i : counter) {
            if (i > bigger) {
                bigger = i;
            }
        }
        int index2 = counter.indexOf(bigger);

        return values.get(index2);
    }

    public double getModeInsideHum() {
        ArrayList<Integer> counter = new ArrayList<>();
        ArrayList<Double> values = new ArrayList<>();
        getMeasurements();
        int bigger = 0;
        for (Measurement measures : measurements) {
            double rawValue = Utilities.rounder(measures.getInsideHum());
            if (values.contains(rawValue)) {
                int index = values.indexOf(rawValue);
                counter.set(index, (counter.get(index) + 1));

            } else {
                values.add(rawValue);
                counter.add(1);
            }
        }
        for (int i : counter) {
            if (i > bigger) {
                bigger = i;
            }
        }
        int index2 = counter.indexOf(bigger);

        return values.get(index2);
    }

    public double getModeAirpressure() {
        ArrayList<Integer> counter = new ArrayList<>();
        ArrayList<Double> values = new ArrayList<>();
        getMeasurements();
        int bigger = 0;
        for (Measurement measures : measurements) {
            double rawValue = Utilities.rounder(measures.getAirPressure());
            if (values.contains(rawValue)) {
                int index = values.indexOf(rawValue);
                counter.set(index, (counter.get(index) + 1));

            } else {
                values.add(rawValue);
                counter.add(1);
            }
        }
        for (int i : counter) {
            if (i > bigger) {
                bigger = i;
            }
        }
        int index2 = counter.indexOf(bigger);

        return values.get(index2);
    }

    public double getAverageWindSpeed() {

        getMeasurements();
        double value = 0;
        for (Measurement measurement : measurements) {
                value += measurement.getWindSpeed();
        }
        value /= measurements.size();
        return Utilities.rounder(value);
    }

    public double getHighestWindSpeed() {
        getMeasurements();
        double value = measurements.get(0).getWindSpeed();
        for (Measurement measures : measurements) {
            if (measures.getWindSpeed() > value) {
                value = measures.getWindSpeed();
            }
        }
        return Utilities.rounder(value);
    }

    public double getLowestWindSpeed() {
        getMeasurements();
        double value = measurements.get(0).getWindSpeed();
        for (Measurement measures : measurements) {
            if (measures.getWindSpeed() < value) {
                value = measures.getWindSpeed();
            }
        }
        return Utilities.rounder(value);
    }

    public double getModeWindspeed() {
        ArrayList<Integer> counter = new ArrayList<>();
        ArrayList<Double> values = new ArrayList<>();
        getMeasurements();
        int bigger = 0;
        for (Measurement measures : measurements) {
            double rawValue = Utilities.rounder(measures.getWindSpeed());
            if (values.contains(rawValue)) {
                int index = values.indexOf(rawValue);
                counter.set(index, (counter.get(index) + 1));

            } else {
                values.add(rawValue);
                counter.add(1);
            }
        }
        for (int i : counter) {
            if (i > bigger) {
                bigger = i;
            }
        }
        int index2 = counter.indexOf(bigger);

        return values.get(index2);
    }

    public double getAverageRain() {
        getMeasurements();
        double value = 0;
        for (Measurement measurement : measurements) {
            value += measurement.getRainRate();
        }
        value /= measurements.size();
        return Utilities.rounder(value);
    }

    public double getLeastRain() {
        getMeasurements();
        double value = measurements.get(0).getRainRate();
        for (Measurement measures : measurements) {
            if (measures.getRainRate() < value) {
                value = measures.getRainRate();
            }
        }
        return Utilities.rounder(value);
    }

    public double getMostRain() {
        getMeasurements();
        double value = measurements.get(0).getRainRate();
        for (Measurement measures : measurements) {
            if (measures.getRainRate() > value) {
                value = measures.getRainRate();
            }
        }
        return Utilities.rounder(value);
    }

    public double getMedianRain() {
        getMeasurements();

        if (measurements.size() % 2 == 0) {
            return Utilities.rounder(((measurements.get(measurements.size() / 2).getRainRate()) + (measurements.get(measurements.size() / 2 + 1).getRainRate())) / 2);
        } else {
            return Utilities.rounder(measurements.get(((measurements.size() - 1) / 2) + 1).getRainRate());
        }
    }

    public double getModeRain() {
        ArrayList<Integer> counter = new ArrayList<>();
        ArrayList<Double> values = new ArrayList<>();
        getMeasurements();
        int bigger = 0;
        for (Measurement measures : measurements) {
            double rawValue = Utilities.rounder(measures.getRainRate());
            if (values.contains(rawValue)) {
                int index = values.indexOf(rawValue);
                counter.set(index, (counter.get(index) + 1));

            } else {
                values.add(rawValue);
                counter.add(1);
            }
        }
        for (int i : counter) {
            if (i > bigger) {
                bigger = i;
            }
        }
        int index2 = counter.indexOf(bigger);

        return values.get(index2);
    }

    public double getAverageUV() {
        getMeasurements();
        double value = 0;
        for (Measurement measurement : measurements) {
            value += measurement.getUVLevel();
        }
        value /= measurements.size();
        return Utilities.rounder(value);
    }

    public double getLowestUV() {
        getMeasurements();
        double value = measurements.get(0).getUVLevel();
        for (Measurement measures : measurements) {
            if (measures.getRainRate() < value) {
                value = measures.getUVLevel();
            }
        }
        return Utilities.rounder(value);
    }

    public double getHighestUV() {
        getMeasurements();
        double value = measurements.get(0).getUVLevel();
        for (Measurement measures : measurements) {
            if (measures.getRainRate() > value) {
                value = measures.getUVLevel();
            }
        }
        return Utilities.rounder(value);
    }

    public double getMedianUV() {
        getMeasurements();

        if (measurements.size() % 2 == 0) {
            return Utilities.rounder(((measurements.get(measurements.size() / 2).getUVLevel()) + (measurements.get(measurements.size() / 2 + 1).getUVLevel())) / 2);
        } else {
            return Utilities.rounder(measurements.get(((measurements.size() - 1) / 2) + 1).getUVLevel());
        }
    }

    public double getModeUV() {
        ArrayList<Integer> counter = new ArrayList<>();
        ArrayList<Double> values = new ArrayList<>();
        getMeasurements();
        int bigger = 0;
        for (Measurement measures : measurements) {
            double rawValue = Utilities.rounder(measures.getUVLevel());
            if (values.contains(rawValue)) {
                int index = values.indexOf(rawValue);
                counter.set(index, (counter.get(index) + 1));

            } else {
                values.add(rawValue);
                counter.add(1);
            }
        }
        for (int i : counter) {
            if (i > bigger) {
                bigger = i;
            }
        }
        int index2 = counter.indexOf(bigger);

        return values.get(index2);
    }

    public double getAverageSolarrad() {
        getMeasurements();
        double value = 0;
        for (Measurement measurement : measurements) {
            value += measurement.getSolarRad();
        }
        value /= measurements.size();
        return Utilities.rounder(value);
    }

    public double getLowestSolarrad() {
        getMeasurements();
        double value = measurements.get(0).getSolarRad();
        for (Measurement measures : measurements) {
            if (measures.getRainRate() < value) {
                value = measures.getSolarRad();
            }
        }
        return Utilities.rounder(value);
    }

    public double getHighestSolarrad() {
        getMeasurements();
        double value = measurements.get(0).getSolarRad();
        for (Measurement measures : measurements) {
            if (measures.getRainRate() > value) {
                value = measures.getSolarRad();
            }
        }
        return Utilities.rounder(value);
    }

    public double getMedianSolarrad() {
        getMeasurements();

        if (measurements.size() % 2 == 0) {
            return Utilities.rounder(((measurements.get(measurements.size() / 2).getSolarRad()) + (measurements.get(measurements.size() / 2 + 1).getSolarRad())) / 2);
        } else {
            return Utilities.rounder(measurements.get(((measurements.size() - 1) / 2) + 1).getSolarRad());
        }
    }

    public double getModeSolarrad() {
        ArrayList<Integer> counter = new ArrayList<>();
        ArrayList<Double> values = new ArrayList<>();
        getMeasurements();
        int bigger = 0;
        for (Measurement measures : measurements) {
            double rawValue = Utilities.rounder(measures.getSolarRad());
            if (values.contains(rawValue)) {
                int index = values.indexOf(rawValue);
                counter.set(index, (counter.get(index) + 1));

            } else {
                values.add(rawValue);
                counter.add(1);
            }
        }
        for (int i : counter) {
            if (i > bigger) {
                bigger = i;
            }
        }
        int index2 = counter.indexOf(bigger);

        return values.get(index2);
    }

    public double getAverageDewpoint() {
        getMeasurements();
        double value = 0;
        for (Measurement measurement : measurements) {
            value += measurement.getDewPoint();
        }
        value /= measurements.size();
        return Utilities.rounder(value);
    }

    public double getLowestDewpoint() {
        getMeasurements();
        double value = measurements.get(0).getDewPoint();
        for (Measurement measures : measurements) {
            if (measures.getRainRate() < value) {
                value = measures.getDewPoint();
            }
        }
        return Utilities.rounder(value);
    }

    public double getHighestDewpoint() {
        getMeasurements();
        double value = measurements.get(0).getDewPoint();
        for (Measurement measures : measurements) {
            if (measures.getRainRate() > value) {
                value = measures.getDewPoint();
            }
        }
        return Utilities.rounder(value);
    }

    public double getMedianDewpoint() {
        getMeasurements();

        if (measurements.size() % 2 == 0) {
            return Utilities.rounder(((measurements.get(measurements.size() / 2).getDewPoint()) + (measurements.get(measurements.size() / 2 + 1).getDewPoint())) / 2);
        } else {
            return Utilities.rounder(measurements.get(((measurements.size() - 1) / 2) + 1).getDewPoint());
        }
    }

    public double getModeDewpoint() {
        ArrayList<Integer> counter = new ArrayList<>();
        ArrayList<Double> values = new ArrayList<>();
        getMeasurements();
        int bigger = 0;
        for (Measurement measures : measurements) {
            double rawValue = Utilities.rounder(measures.getDewPoint());
            if (values.contains(rawValue)) {
                int index = values.indexOf(rawValue);
                counter.set(index, (counter.get(index) + 1));

            } else {
                values.add(rawValue);
                counter.add(1);
            }
        }
        for (int i : counter) {
            if (i > bigger) {
                bigger = i;
            }
        }
        int index2 = counter.indexOf(bigger);

        return values.get(index2);
    }

    public double getAverageWindchill() {
        getMeasurements();
        double value = 0;
        for (Measurement measurement : measurements) {
            value += measurement.getWindChill();
        }
        value /= measurements.size();
        return Utilities.rounder(value);
    }

    public double getLowestWindchill() {
        getMeasurements();
        double value = measurements.get(0).getWindChill();
        for (Measurement measures : measurements) {
            if (measures.getRainRate() < value) {
                value = measures.getWindChill();
            }
        }
        return Utilities.rounder(value);
    }

    public double getHighestWindchill() {
        getMeasurements();
        double value = measurements.get(0).getWindChill();
        for (Measurement measures : measurements) {
            if (measures.getRainRate() > value) {
                value = measures.getWindChill();
            }
        }
        return Utilities.rounder(value);
    }

    public double getMedianWindchill() {
        getMeasurements();

        if (measurements.size() % 2 == 0) {
            return Utilities.rounder(((measurements.get(measurements.size() / 2).getWindChill()) + (measurements.get(measurements.size() / 2 + 1).getWindChill())) / 2);
        } else {
            return Utilities.rounder(measurements.get(((measurements.size() - 1) / 2) + 1).getWindChill());
        }
    }

    public double getModeWindchill() {
        ArrayList<Integer> counter = new ArrayList<>();
        ArrayList<Double> values = new ArrayList<>();
        getMeasurements();
        int bigger = 0;
        for (Measurement measures : measurements) {
            double rawValue = Utilities.rounder(measures.getWindChill());
            if (values.contains(rawValue)) {
                int index = values.indexOf(rawValue);
                counter.set(index, (counter.get(index) + 1));

            } else {
                values.add(rawValue);
                counter.add(1);
            }
        }
        for (int i : counter) {
            if (i > bigger) {
                bigger = i;
            }
        }
        int index2 = counter.indexOf(bigger);

        return values.get(index2);
    }

    public double getAverageHeatindex() {
        getMeasurements();
        double value = 0;
        for (Measurement measurement : measurements) {
            value += measurement.getHeatIndex();
        }
        value /= measurements.size();
        return Utilities.rounder(value);
    }

    public double getLowestHeatindex() {
        getMeasurements();
        double value = measurements.get(0).getHeatIndex();
        for (Measurement measures : measurements) {
            if (measures.getRainRate() < value) {
                value = measures.getHeatIndex();
            }
        }
        return Utilities.rounder(value);
    }

    public double getHighestHeatindex() {
        getMeasurements();
        double value = measurements.get(0).getHeatIndex();
        for (Measurement measures : measurements) {
            if (measures.getRainRate() > value) {
                value = measures.getHeatIndex();
            }
        }
        return Utilities.rounder(value);
    }

    public double getMedianHeatindex() {
        getMeasurements();

        if (measurements.size() % 2 == 0) {
            return Utilities.rounder(((measurements.get(measurements.size() / 2).getHeatIndex()) + (measurements.get(measurements.size() / 2 + 1).getHeatIndex())) / 2);
        } else {
            return Utilities.rounder(measurements.get(((measurements.size() - 1) / 2) + 1).getHeatIndex());
        }
    }

    public double getModeHeatindex() {
        ArrayList<Integer> counter = new ArrayList<>();
        ArrayList<Double> values = new ArrayList<>();
        getMeasurements();
        int bigger = 0;
        for (Measurement measures : measurements) {
            double rawValue = Utilities.rounder(measures.getHeatIndex());
            if (values.contains(rawValue)) {
                int index = values.indexOf(rawValue);
                counter.set(index, (counter.get(index) + 1));

            } else {
                values.add(rawValue);
                counter.add(1);
            }
        }
        for (int i : counter) {
            if (i > bigger) {
                bigger = i;
            }
        }
        int index2 = counter.indexOf(bigger);

        return values.get(index2);
    }

    public double standardDeviation(String type) {
        getMeasurements();
        double sum = 0.0;
        double standardDeviation = 0.0;
        int length = measurements.size();
        switch (type) {
            case "outsideTemperature":
                for (Measurement measurement : measurements) {
                    sum += measurement.getOutsideTemperature();
                }
                break;
            case "insideTemperature":
                for (Measurement measurement : measurements) {
                    sum += measurement.getInsideTemperature();
                }
                break;
            case "outsideHumidity":
                for (Measurement measurement : measurements) {
                    sum += measurement.getOutsideHum();
                }
                break;
            case "insideHumidity":
                for (Measurement measurement : measurements) {
                    sum += measurement.getInsideHum();
                }
                break;
            case "windSpeed":
                for (Measurement measurement : measurements) {
                    sum += measurement.getWindSpeed();
                }
                break;
            case "airPressure":
                for (Measurement measurement : measurements) {
                    sum += measurement.getAirPressure();
                }
                break;
            case "rain":
                for (Measurement measurement : measurements) {
                    sum += measurement.getRainRate();
                }
                break;
            case "UV":
                for (Measurement measurement : measurements) {
                    sum += measurement.getUVLevel();
                }
                break;
            case "solarRad":
                for (Measurement measurement : measurements) {
                    sum += measurement.getSolarRad();
                }
                break;
            case "dewPoint":
                for (Measurement measurement : measurements) {
                    sum += measurement.getDewPoint();
                }
                break;
            case "windChill":
                for (Measurement measurement : measurements) {
                    sum += measurement.getWindChill();
                }
                break;
            case "heatIndex":
                for (Measurement measurement : measurements) {
                    sum += measurement.getHeatIndex();
                }
                break;
        }

        double mean = sum / length;

        switch (type) {
            case "outsideTemperature":
                for (Measurement measurement : measurements) {
                    standardDeviation += Math.pow((measurement.getOutsideTemperature() - mean), 2);
                }
                break;
            case "insideTemperature":
                for (Measurement measurement : measurements) {
                    standardDeviation += Math.pow((measurement.getInsideTemperature() - mean), 2);
                }
                break;
            case "outsideHumidity":
                for (Measurement measurement : measurements) {
                    standardDeviation += Math.pow((measurement.getOutsideHum() - mean), 2);
                }
                break;
            case "insideHumidity":
                for (Measurement measurement : measurements) {
                    standardDeviation += Math.pow((measurement.getInsideHum() - mean), 2);
                }
                break;
            case "windSpeed":
                for (Measurement measurement : measurements) {
                    standardDeviation += Math.pow((measurement.getWindSpeed() - mean), 2);
                }
                break;
            case "airPressure":
                for (Measurement measurement : measurements) {
                    standardDeviation += Math.pow((measurement.getAirPressure() - mean), 2);
                }
                break;
            case "rain":
                for (Measurement measurement : measurements) {
                    standardDeviation += Math.pow((measurement.getRainRate() - mean), 2);
                }
                break;
            case "UV":
                for (Measurement measurement : measurements) {
                    standardDeviation += Math.pow((measurement.getUVLevel() - mean), 2);
                }
                break;
            case "solarRad":
                for (Measurement measurement : measurements) {
                    standardDeviation += Math.pow((measurement.getSolarRad() - mean), 2);
                }
                break;
            case "dewPoint":
                for (Measurement measurement : measurements) {
                    standardDeviation += Math.pow((measurement.getDewPoint() - mean), 2);
                }
                break;
            case "windChill":
                for (Measurement measurement : measurements) {
                    standardDeviation += Math.pow((measurement.getWindChill() - mean), 2);
                }
                break;
            case "heatIndex":
                for (Measurement measurement : measurements) {
                    standardDeviation += Math.pow((measurement.getHeatIndex() - mean), 2);
                }
                break;
        }

        return Utilities.rounder(Math.sqrt(standardDeviation / length));
    }

    public double aantalGraaddagen() {
        RawMeasurement rawMeasurement = DatabaseConnection.getMostRecentMeasurement();
        Measurement measures = new Measurement(rawMeasurement);
        double insideTemp = measures.getInsideTemperature();
        double averageOutsideTemp = getAverageOutsideTemperature();
        double amount = 0;
        if (averageOutsideTemp > 18.0) {
            amount = 0;
        } else if (averageOutsideTemp < 18.0) {
            amount = insideTemp - averageOutsideTemp;
        }

        return amount;
    }

    public String biggestDifference() {
        LocalDate currentDay = beginPeriod;
        LocalDate bigDifferenceDate = beginPeriod;
        double highest;
        double lowest;
        double difference;
        double biggestDifference = 0;
        for (int i = 0; i < (int) numberOfDays(); i++) {
            highest = getHighestOutsideTemp();
            lowest = getLowestOutsideTemp();
            difference = highest - lowest;
            currentDay = beginPeriod.plusDays(1);
            if (difference > biggestDifference) {
                biggestDifference = difference;
                bigDifferenceDate = currentDay;
            }
        }
        return ("Date = " + bigDifferenceDate + "\n Difference = " + Utilities.rounder(biggestDifference) + " C");
    }

    public String mostRainInGivenYear() {
        //Opdracht G
        //initializing variables for each month
        double januaryRain = 0.0;
        double februaryRain = 0.0;
        double marchRain = 0.0;
        double aprilRain = 0.0;
        double mayRain = 0.0;
        double juneRain = 0.0;
        double julyRain = 0.0;
        double augustRain = 0.0;
        double septemberRain = 0.0;
        double octoberRain = 0.0;
        double novemberRain = 0.0;
        double decemberRain = 0.0;

        ArrayList<Double> rain = new ArrayList<>();

        setStart(year, 1, 1);
        setEnd(year, 1, 31);
        getMeasurements();
        for (Measurement measurement : measurements) {
            januaryRain += measurement.getRainRate();
        }
        rain.add(januaryRain);

        setStart(year, 2, 1);
        //checks for leap year
        if (year % 4 == 0 || year % 400 == 0) {
            setEnd(year, 2, 29);
        } else {
            setEnd(year, 2, 28);
        }
        getMeasurements();
        for (Measurement measurement : measurements) {
            februaryRain += measurement.getRainRate();
        }
        rain.add(februaryRain);

        setStart(year, 3, 1);
        setEnd(year, 3, 31);
        getMeasurements();
        for (Measurement measurement : measurements) {
            marchRain += measurement.getRainRate();
        }
        rain.add(marchRain);

        setStart(year, 4, 1);
        setEnd(year, 4, 30);
        getMeasurements();
        for (Measurement measurement : measurements) {
            aprilRain += measurement.getRainRate();
        }
        rain.add(aprilRain);

        setStart(year, 5, 1);
        setEnd(year, 5, 31);
        getMeasurements();
        for (Measurement measurement : measurements) {
            mayRain += measurement.getRainRate();
        }
        rain.add(mayRain);

        setStart(year, 6, 1);
        setEnd(year, 6, 30);
        getMeasurements();
        for (Measurement measurement : measurements) {
            juneRain += measurement.getRainRate();
        }
        rain.add(juneRain);

        setStart(year, 7, 1);
        setEnd(year, 7, 31);
        getMeasurements();
        for (Measurement measurement : measurements) {
            julyRain += measurement.getRainRate();
        }
        rain.add(julyRain);

        setStart(year, 8, 1);
        setEnd(year, 8, 31);
        getMeasurements();
        for (Measurement measurement : measurements) {
            augustRain += measurement.getRainRate();
        }
        rain.add(augustRain);

        setStart(year, 9, 1);
        setEnd(year, 9, 30);
        getMeasurements();
        for (Measurement measurement : measurements) {
            septemberRain += measurement.getRainRate();
        }
        rain.add(septemberRain);

        setStart(year, 10, 1);
        setEnd(year, 10, 31);
        getMeasurements();
        for (Measurement measurement : measurements) {
            octoberRain += measurement.getRainRate();
        }
        rain.add(octoberRain);

        setStart(year, 11, 1);
        setEnd(year, 11, 30);
        getMeasurements();
        for (Measurement measurement : measurements) {
            novemberRain += measurement.getRainRate();
        }
        rain.add(novemberRain);

        setStart(year, 12, 1);
        setEnd(year, 12, 31);
        getMeasurements();
        for (Measurement measurement : measurements) {
            decemberRain += measurement.getRainRate();
        }
        rain.add(decemberRain);
        //for each that finds the month with the most amount of rain
        double mostRain = 0.0;
        int indexOfMostRain = 0;
        for (double rainAmount : rain) {
            if (rainAmount > mostRain) {
                mostRain = rainAmount;
                indexOfMostRain = rain.indexOf(rainAmount);
            }
        }
        return switch (indexOfMostRain) {
            case 0 -> "january";
            case 1 -> "february";
            case 2 -> "march";
            case 3 -> "april";
            case 4 -> "may";
            case 5 -> "june";
            case 6 -> "july";
            case 7 -> "august";
            case 8 -> "september";
            case 9 -> "october";
            case 10 -> "november";
            case 11 -> "december";
            default -> "null";
        };
    }
}

