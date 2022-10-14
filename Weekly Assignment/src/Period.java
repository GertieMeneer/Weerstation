import java.security.acl.LastOwnerException;
import java.time.*;
import java.time.temporal.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A class to contain a period of time
 *
 * @author Johan Talboom
 * @version 2.0
 */
public class Period {
	private LocalDate beginPeriod;
	private LocalDate endPeriod;
	private ArrayList<Measurement> measurements;

	/**
	 * default constructor, sets the period to today
	 */
	public Period() {
		beginPeriod = LocalDate.now();
		endPeriod = LocalDate.now();
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
	 * @return a list of raw measurements
	 */
	public ArrayList<RawMeasurement> getRawMeasurements() {
		return DatabaseConnection.getMeasurementsBetween(LocalDateTime.of(beginPeriod, LocalTime.of(0, 1)), LocalDateTime.of(endPeriod, LocalTime.of(23, 59)));
	}

	/**
	 * Builds an ArrayList of measurements. This method also filters out any 'bad' measurements
	 * @return a filtered list of measurements
	 */
	public ArrayList<Measurement> getMeasurements() {
		measurements = new ArrayList<>();
		ArrayList<RawMeasurement> rawMeasurements = getRawMeasurements();
		for (RawMeasurement rawMeasurement : rawMeasurements) {
			Measurement measurement = new Measurement(rawMeasurement);
			if(measurement.isValid()) {
				measurements.add(measurement);
			}
		}
		return measurements;
	}


	/**
	 * todo
	 * @return
	 */
	public double getAverageOutsideTemperature()
	{
		ArrayList<Measurement> measurements = getMeasurements();

		//calculate average outside temperature and return it
		return measurements.get(0).getOutsideTemperature();
	}

	/**
	 * Todo
	 */
	public ArrayList<Period> hasHeatWave() {
		return null;
	}

	/**
	 * Todo
	 */
	public Period longestDraught() {
		return new Period();
	}

	public double lowestOutsideTemp(LocalDate beginPeriod, LocalDate endPeriod) {
		this.beginPeriod = beginPeriod;
		this.endPeriod = endPeriod;
		getMeasurements();
		double value = measurements.get(0).getOutsideTemperature();
		for(Measurement measures : measurements) {
			if(measures.getOutsideTemperature() < value) {
				value = measures.getOutsideTemperature();
			} else {
				value = value;
			}
		}
		return Utilities.rounder(value);
	}

	public double highestOutsideTemp(LocalDate beginPeriod, LocalDate endPeriod) {
		this.beginPeriod = beginPeriod;
		this.endPeriod = endPeriod;
		getMeasurements();
		double value = measurements.get(0).getOutsideTemperature();
		for(Measurement measures : measurements) {
			if(measures.getOutsideTemperature() > value) {
				value = measures.getOutsideTemperature();
			}
		}
		return Utilities.rounder(value);
	}

	public double lowestInsideTemp(LocalDate beginPeriod, LocalDate endPeriod) {
		this.beginPeriod = beginPeriod;
		this.endPeriod = endPeriod;
		getMeasurements();
		double value = measurements.get(0).getInsideTemperature();
		for(Measurement measures : measurements) {
			if(measures.getInsideTemperature() < value) {
				value = measures.getInsideTemperature();
			}
		}
		return Utilities.rounder(value);
	}

	public double highestInsideTemp(LocalDate beginPeriod, LocalDate endPeriod) {
		this.beginPeriod = beginPeriod;
		this.endPeriod = endPeriod;
		getMeasurements();
		double value = measurements.get(0).getInsideTemperature();
		for(Measurement measures : measurements) {
			if(measures.getInsideTemperature() > value) {
				value = measures.getInsideTemperature();
			}
		}
		return Utilities.rounder(value);
	}

	public double lowestInsideHum(LocalDate beginPeriod, LocalDate endPeriod) {
		this.beginPeriod = beginPeriod;
		this.endPeriod = endPeriod;
		getMeasurements();
		double value = measurements.get(0).getInsideHum();
		for(Measurement measures : measurements) {
			if(measures.getInsideHum() < value) {
				value = measures.getInsideHum();
			}
		}
		return Utilities.rounder(value);
	}

	public double lowestOutsideHum(LocalDate beginPeriod, LocalDate endPeriod) {
		this.beginPeriod = beginPeriod;
		this.endPeriod = endPeriod;
		getMeasurements();
		double value = measurements.get(0).getOutsideHum();
		for(Measurement measures : measurements) {
			if(measures.getOutsideHum() < value) {
				value = measures.getOutsideHum();
			}
		}
		return Utilities.rounder(value);
	}

	public double highestOutsideHum(LocalDate beginPeriod, LocalDate endPeriod) {
		this.beginPeriod = beginPeriod;
		this.endPeriod = endPeriod;
		getMeasurements();
		double value = measurements.get(0).getOutsideHum();
		for(Measurement measures : measurements) {
			if(measures.getOutsideHum() > value) {
				value = measures.getOutsideHum();
			}
		}
		return Utilities.rounder(value);
	}

	public double highestInsideHum(LocalDate beginPeriod, LocalDate endPeriod) {
		this.beginPeriod = beginPeriod;
		this.endPeriod = endPeriod;
		getMeasurements();
		double value = measurements.get(0).getInsideHum();
		for(Measurement measures : measurements) {
			if(measures.getInsideHum() < value) {
				value = measures.getInsideHum();
			}
		}
		return Utilities.rounder(value);
	}

	public double lowestAirpressure(LocalDate beginPeriod, LocalDate endPeriod) {
		this.beginPeriod = beginPeriod;
		this.endPeriod = endPeriod;
		getMeasurements();
		double value = measurements.get(0).getAirPressure();
		for(Measurement measures : measurements) {
			if(measures.getAirPressure() < value) {
				value = measures.getAirPressure();
			}
		}
		return Utilities.rounder(value);
	}

	public double highestAirpressure(LocalDate beginPeriod, LocalDate endPeriod) {
		this.beginPeriod = beginPeriod;
		this.endPeriod = endPeriod;
		getMeasurements();
		double value = measurements.get(0).getAirPressure();
		for(Measurement measures : measurements) {
			if(measures.getAirPressure() > value) {
				value = measures.getAirPressure();
			}
		}
		return Utilities.rounder(value);
	}

	/**
	 * Todo more methods
	 */
	public double tempDifference(){
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

}
