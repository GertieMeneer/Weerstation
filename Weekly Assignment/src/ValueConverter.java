public class ValueConverter {

    public static double airPressure(short rawValue) {
        double air = (rawValue / 100.0) * 3.386;
        return air;
    }

    public static double Temperature(short rawValue) {
        double temperature = (((rawValue / 10.0)) - 32.0) * 5.0 / 9.0;
        return temperature;
    }


    public static double windSpeed(short rawValue) {
        double windspeed = rawValue * 1.609;
        return windspeed;
    }

    public static String windDirection(short rawValue) {
        String winddir = "";
        if (rawValue >= 0 && rawValue <= 22.5) {
            winddir = "N/NE";
            return winddir;
        } else if (rawValue > 22.5 && rawValue <= 45) {
            winddir = "NE";
            return winddir;
        } else if (rawValue > 45 && rawValue <= 67.5) {
            winddir = "E/NE";
            return winddir;
        } else if (rawValue > 67.5 && rawValue <= 90) {
            winddir = "E";
            return winddir;
        } else if (rawValue > 90 && rawValue <= 135) {
            winddir = "SE";
            return winddir;
        } else if (rawValue > 135 && rawValue <= 157.5) {
            winddir = "S/SE";
            return winddir;
        } else if (rawValue > 157.5 && rawValue <= 180) {
            winddir = "S";
            return winddir;
        } else if (rawValue > 180 && rawValue <= 202.5) {
            winddir = "S/SW";
            return winddir;
        } else if (rawValue > 202.5 && rawValue <= 225) {
            winddir = "SW";
            return winddir;
        } else if (rawValue > 225 && rawValue <= 247.5) {
            winddir = "W/SW";
            return winddir;
        } else if (rawValue > 247.5 && rawValue <= 270) {
            winddir = "W";
            return winddir;
        } else if (rawValue > 270 && rawValue <= 292.5) {
            winddir = "W/NW";
            return winddir;
        } else if (rawValue > 292.5 && rawValue <= 315) {
            winddir = "NW";
            return winddir;
        } else if (rawValue > 315 && rawValue <= 337.5) {
            winddir = "N/NW";
            return winddir;
        } else if (rawValue > 337.5 && rawValue <= 360) {
            winddir = "N";
            return winddir;
        }
        return winddir;
    }

    public static double rainMeter(short rawValue) {
        return rawValue / 5.0;
    }

    public static double uvIndex(double rawValue) {
        return rawValue / 10;
    }

    public static String Time(short rawValue) {
        //in this method we use string manipulation to change any normal number into military time
        String sunrise = "" + rawValue;
        //in this loop we make sure we have 4 characters in the string and the next manipulation works
        while (sunrise.length() <= 3) {
            sunrise = ("0" + sunrise);
        }
        //here we split the string and put a ":" in the middle to make it a time
        sunrise = sunrise.substring(0, 2) + ":" + sunrise.substring(2);

        return sunrise;
    }

    public static double windChill(double windspeed, double temperaturevalue) {
        double outsidetemperaturevalue = temperaturevalue / 10.0;
        double windchillvalue = 35.74 + (0.6215 * outsidetemperaturevalue) - 35.75 * (Math.pow(windspeed, 0.16)) + 0.4275 * outsidetemperaturevalue * (Math.pow(windspeed, 0.16));
        double windchill = (windchillvalue - 32.0) * 5.0 / 9.0;
        return windchill;
    }

    public static double dewPoint(double outsidetemperature, double outsidehumvalue) {
        double dewpoint = outsidetemperature - ((100 - outsidehumvalue) / 5);
        return dewpoint;
    }

    public static double heatIndex(short rawoutsidetemp, short rawoutsidehum) {
        double outsidetemp = ValueConverter.Temperature(rawoutsidetemp);
        double c1 = -8.78469475556;
        double c2 = 1.61139411;
        double c3 = 2.33854883889;
        double c4 = -0.14611605;
        double c5 = -0.012308094;
        double c6 = -0.0164248277778;
        double c7 = 2.211732 * Math.pow(10, -3);
        double c8 = 7.2546 * Math.pow(10, -4);
        double c9 = -3.582 * Math.pow(10, -6);
        double heatindex = c1 + c2 * outsidetemp +
                c3 * rawoutsidehum + c4 * outsidetemp * rawoutsidehum +
                c5 * Math.pow(outsidetemp, 2) + c6 * Math.pow(rawoutsidehum, 2) +
                c7 * Math.pow(outsidetemp, 2) * rawoutsidehum +
                c8 * outsidetemp * Math.pow(rawoutsidehum, 2) +
                c9 * Math.pow(outsidetemp, 2) * Math.pow(rawoutsidehum, 2);
        return heatindex;
    }

}



