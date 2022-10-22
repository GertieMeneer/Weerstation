import java.io.IOException;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        IO.init();
        firstPage();
        firstPageSelector();

    }

    public static void firstPage() {
        GuiBoardDemos.clrDMDisplay();
        GuiBoardDemos.clrSevenSegment();
        String temp = "Temperature      1";
        String hum = "Humidity";
        String wind = "Wind";
        IO.writeShort(0x40, ' ');
        IO.writeShort(0x40, ' ');
        for (int i = 0; i < temp.length(); i++) {
            IO.writeShort(0x40, temp.charAt(i));
        }
        IO.writeShort(0x40, '\n');
        IO.writeShort(0x40, ' ');
        IO.writeShort(0x40, ' ');
        for (int i = 0; i < hum.length(); i++) {
            IO.writeShort(0x40, hum.charAt(i));
        }
        IO.writeShort(0x40, '\n');
        IO.writeShort(0x40, ' ');
        IO.writeShort(0x40, ' ');
        for (int i = 0; i < wind.length(); i++) {
            IO.writeShort(0x40, wind.charAt(i));
        }
        IO.writeShort(0x42, 1);
    }

    public static void firstPageSelector() {
        firstPos();
        for (int i = 1; i > 0; i++) {
            if (IO.readShort(0x80) == 1) {
                selectTemp();
                i = -1;
            }
            else if (IO.readShort(0x100) == 1) {
                noSelection();
                secondPos();
                i = -1;
            }
        }
        for (int i = 1; i > 0; i++) {
            if (IO.readShort(0x80) == 1) {
                selectHum();
                i = -1;
            } else if (IO.readShort(0x100) == 0) {
                noSelection();
                thirdPos();
                i = -1;
            }
        }
        for (int i = 1; i > 0; i++) {
            if (IO.readShort(0x80) == 1) {
                selectWind();
                i = -1;
            } else if (IO.readShort(0x100) == 1) {
                noSelection();
                secondPage();
                secondPageSelector();
                i = -1;
            }
        }
    }

    public static void secondPage() {
        GuiBoardDemos.clrDMDisplay();
        GuiBoardDemos.clrSevenSegment();
        String airp = "Air pressure     2";
        String rainrate = "Rain rate";
        String uv = "UV-index";
        IO.writeShort(0x40, ' ');
        IO.writeShort(0x40, ' ');
        for (int i = 0; i < airp.length(); i++) {
            IO.writeShort(0x40, airp.charAt(i));
        }
        IO.writeShort(0x40, '\n');
        IO.writeShort(0x40, ' ');
        IO.writeShort(0x40, ' ');
        for (int i = 0; i < rainrate.length(); i++) {
            IO.writeShort(0x40, rainrate.charAt(i));
        }
        IO.writeShort(0x40, '\n');
        IO.writeShort(0x40, ' ');
        IO.writeShort(0x40, ' ');
        for (int i = 0; i < uv.length(); i++) {
            IO.writeShort(0x40, uv.charAt(i));
        }
        IO.writeShort(0x42, 1);
    }

    public static void secondPageSelector() {
        firstPos();
        for (int i = 1; i > 0; i++) {
            if (IO.readShort(0x80) == 1) {
                selectAirpressure();
                i = -1;
            }
            else if (IO.readShort(0x100) == 0) {
                noSelection();
                secondPos();
                i = -1;
            }
        }
        for (int i = 1; i > 0; i++) {
            if (IO.readShort(0x80) == 1) {
                selectRainraite();
                i = -1;
            } else if (IO.readShort(0x100) == 1) {
                noSelection();
                thirdPos();
                i = -1;
            }
        }
        for (int i = 1; i > 0; i++) {
            if (IO.readShort(0x80) == 1) {
                selectUV();
                i = -1;
            } else if (IO.readShort(0x100) == 0) {
                noSelection();
                firstPos();
                i = -1;
            }
        }
    }

    public static void noSelection() {
        for (int i = 2; i < 7; i++) {
            IO.writeShort(0x42, 0 << 12 | i << 5 | 5);
        }
        for (int i = 2; i < 7; i++) {
            IO.writeShort(0x42, 0 << 12 | i << 5 | 15);
        }
        for (int i = 2; i < 7; i++) {
            IO.writeShort(0x42, 0 << 12 | i << 5 | 25);
        }
    }

    public static void firstPos() {
        for (int i = 2; i < 7; i++) {
            IO.writeShort(0x42, 1 << 12 | i << 5 | 5);
        }
    }

    public static void secondPos() {
        for (int i = 2; i < 7; i++) {
            IO.writeShort(0x42, 1 << 12 | i << 5 | 15);
        }
    }

    public static void thirdPos() {
        for (int i = 2; i < 7; i++) {
            IO.writeShort(0x42, 1 << 12 | i << 5 | 25);
        }
    }

    public static void selectTemp() {
        printInsideOutsideBoth();
        for (int i = 1; i > 0; i++) {
            if (IO.readShort(0x80) == 0) {
                selectOutsideTemp();
                return;
            } else if (IO.readShort(0x100) == 1) {
                noSelection();
                secondPos();
                i = -1;
            }
        }
        for (int i = 1; i > 0; i++) {
            if (IO.readShort(0x80) == 0) {
                selectInsideTemp();
                return;
            } else if (IO.readShort(0x100) == 0) {
                noSelection();
                thirdPos();
                i = -1;
            }
        }
        for (int j = 1; j > 0; j++) {
            if (IO.readShort(0x80) == 0) {
                selectBothTemps();
                return;
            }   else if (IO.readShort(0x100) == 1) {
                noSelection();
                IO.delay(1000);
                firstPos();
                j = -1;
            }
            }
        }


    public static void selectOutsideTemp() {
        GuiBoardDemos.clrDMDisplay();
        Period now = new Period();
        now.getMeasurements();
        String outsideTemp = "" + Utilities.rounder(now.getMeasurements().get(0).getOutsideTemperature());
        IO.writeShort(0x24, outsideTemp.charAt(0));
        IO.writeShort(0x22, 0x100 | secondDigit("" + outsideTemp.charAt(1)));
        IO.writeShort(0x20, outsideTemp.charAt(3));
        String outsideTempDMD = "Outside temperature";
        for (int i = 0; i < outsideTempDMD.length(); i++) {
            IO.writeShort(0x40, outsideTempDMD.charAt(i));
        }
        returnToFirstPage();
    }

    public static void selectInsideTemp() {
        GuiBoardDemos.clrDMDisplay();
        Period now = new Period();
        now.getMeasurements();
        String insideTemp = "" + Utilities.rounder(now.getMeasurements().get(0).getInsideTemperature());
        IO.writeShort(0x24, insideTemp.charAt(0));
        IO.writeShort(0x22, 0x100 | secondDigit("" + insideTemp.charAt(1)));
        IO.writeShort(0x20, insideTemp.charAt(3));
        String insideTempDMD = "Inside temperature";
        for (int i = 0; i < insideTempDMD.length(); i++) {
            IO.writeShort(0x40, insideTempDMD.charAt(i));
        }
        returnToFirstPage();
    }

    public static void selectBothTemps() {
        GuiBoardDemos.clrDMDisplay();
        Period now = new Period();
        now.getMeasurements();
        String outsideTemp = "" + Utilities.rounder(now.getMeasurements().get(0).getOutsideTemperature());
        IO.writeShort(0x34, outsideTemp.charAt(0));
        IO.writeShort(0x32, 0x100 | secondDigit("" + outsideTemp.charAt(1)));
        IO.writeShort(0x30, outsideTemp.charAt(3));
        String insideTemp = "" + Utilities.rounder(now.getMeasurements().get(0).getInsideTemperature());
        IO.writeShort(0x24, insideTemp.charAt(0));
        IO.writeShort(0x22, 0x100 | secondDigit("" + insideTemp.charAt(1)));
        IO.writeShort(0x20, insideTemp.charAt(3));
        String insideTempDMD = "Inside = left";
        String outsideTempDMD = "Outside = right";
        String differenceTemptDMD = "Difference = " + Utilities.rounder((Utilities.rounder(now.getMeasurements().get(0).getInsideTemperature()) - Utilities.rounder(now.getMeasurements().get(0).getOutsideTemperature())));
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
        returnToFirstPage();
    }

    public static void selectHum() {
        printInsideOutsideBoth();
        for (int i = 1; i > 0; i++) {
            if (IO.readShort(0x80) == 0) {
                selectOutsideHum();
                return;
            } else if (IO.readShort(0x100) == 0) {
                noSelection();
                secondPos();
                i = -1;
            }
        }
        for (int i = 1; i > 0; i++) {
            if (IO.readShort(0x80) == 0) {
                selectInsideHum();
                return;
            } else if (IO.readShort(0x100) == 1) {
                noSelection();
                thirdPos();
                i = -1;
            }
        }
        for (int j = 1; j > 0; j++) {
            if (IO.readShort(0x80) == 0) {
                selectBothHum();
                return;
            }
        }
    }

    public static void selectOutsideHum() {
        GuiBoardDemos.clrDMDisplay();
        Period now = new Period();
        now.getMeasurements();
        String outsideHum = "" + Utilities.rounder(now.getMeasurements().get(0).getOutsideHum());
        IO.writeShort(0x24, outsideHum.charAt(0));
        IO.writeShort(0x22, 0x100 | secondDigit("" + outsideHum.charAt(1)));
        IO.writeShort(0x20, outsideHum.charAt(3));
        String outsideHumDMD = "Outside humidity";
        for (int i = 0; i < outsideHumDMD.length(); i++) {
            IO.writeShort(0x40, outsideHumDMD.charAt(i));
        }
        returnToFirstPage();
    }

    public static void selectInsideHum() {
        GuiBoardDemos.clrDMDisplay();
        Period now = new Period();
        now.getMeasurements();
        String insideHum = "" + Utilities.rounder(now.getMeasurements().get(0).getInsideHum());
        IO.writeShort(0x24, insideHum.charAt(0));
        IO.writeShort(0x22, 0x100 | secondDigit("" + insideHum.charAt(1)));
        IO.writeShort(0x20, insideHum.charAt(3));
        String insideHumDMD = "Inside humidity";
        for (int i = 0; i < insideHumDMD.length(); i++) {
            IO.writeShort(0x40, insideHumDMD.charAt(i));
        }
        returnToFirstPage();
    }

    public static void selectBothHum() {
        GuiBoardDemos.clrDMDisplay();
        Period now = new Period();
        now.getMeasurements();
        String insideHum = "" + Utilities.rounder(now.getMeasurements().get(0).getInsideHum());
        IO.writeShort(0x24, insideHum.charAt(0));
        IO.writeShort(0x22, 0x100 | secondDigit("" + insideHum.charAt(1)));
        IO.writeShort(0x20, insideHum.charAt(3));
        String outsideHum = "" + Utilities.rounder(now.getMeasurements().get(0).getOutsideHum());
        IO.writeShort(0x34, outsideHum.charAt(0));
        IO.writeShort(0x32, 0x100 | secondDigit("" + outsideHum.charAt(1)));
        IO.writeShort(0x30, outsideHum.charAt(3));
        String insideTempDMD = "Inside = left";
        String outsideTempDMD = "Outside = right";
        String differenceTempDMD = "Difference = " + (now.getMeasurements().get(0).getInsideHum() - now.getMeasurements().get(0).getOutsideHum());
        if (differenceTempDMD.contains("-")) {
            differenceTempDMD = differenceTempDMD.substring(0, 12) + " " + differenceTempDMD.substring(14);
        }
        for (int i = 0; i < insideTempDMD.length(); i++) {
            IO.writeShort(0x40, insideTempDMD.charAt(i));
        }
        IO.writeShort(0x40, '\n');
        for (int i = 0; i < outsideTempDMD.length(); i++) {
            IO.writeShort(0x40, outsideTempDMD.charAt(i));
        }
        IO.writeShort(0x40, '\n');
        for (int i = 0; i < differenceTempDMD.length(); i++) {
            IO.writeShort(0x40, differenceTempDMD.charAt(i));
        }
        returnToFirstPage();
    }

    public static void selectWind() {
        GuiBoardDemos.clrDMDisplay();
        Period now = new Period();
        now.getMeasurements();
        String windSpeed = "Wind speed = " + Utilities.rounder(now.getMeasurements().get(0).getWindSpeed()) + "Km/h";
        String windDirection = "Wind direction = " + now.getMeasurements().get(0).getWindDirection();
        for (int i = 0; i < windSpeed.length(); i++) {
            IO.writeShort(0x40, windSpeed.charAt(i));
        }
        IO.writeShort(0x40, '\n');
        for (int i = 0; i < windDirection.length(); i++) {
            IO.writeShort(0x40, windDirection.charAt(i));
        }
        returnToFirstPage();
    }

    private static void printInsideOutsideBoth() {
        GuiBoardDemos.clrDMDisplay();

        String outsideTemperature = "Outside";
        String insideTemperature = "Inside";
        String both = "Both";

        IO.writeShort(0x40, ' ');
        IO.writeShort(0x40, ' ');
        for (int i = 0; i < outsideTemperature.length(); i++) {
            IO.writeShort(0x40, outsideTemperature.charAt(i));
        }
        IO.writeShort(0x40, '\n');
        IO.writeShort(0x40, ' ');
        IO.writeShort(0x40, ' ');
        for (int i = 0; i < insideTemperature.length(); i++) {
            IO.writeShort(0x40, insideTemperature.charAt(i));
        }
        IO.writeShort(0x40, '\n');
        IO.writeShort(0x40, ' ');
        IO.writeShort(0x40, ' ');
        for (int i = 0; i < both.length(); i++) {
            IO.writeShort(0x40, both.charAt(i));
        }
        firstPos();
    }

    public static void selectAirpressure() {
        GuiBoardDemos.clrDMDisplay();
        GuiBoardDemos.clrSevenSegment();
        Period now = new Period();
        now.getMeasurements();
        String airPressure = "" + Utilities.rounder(now.getMeasurements().get(0).getAirPressure());
        IO.writeShort(0x18, airPressure.charAt(0));
        IO.writeShort(0x16, airPressure.charAt(1));
        IO.writeShort(0x14, airPressure.charAt(2));
        IO.writeShort(0x12, 0x100 | secondDigit("" + airPressure.charAt(3)));
        IO.writeShort(0x10, airPressure.charAt(5));
        String airPressureDMD = "Showing current air \n pressure in hPa.";
        for (int i = 0; i < airPressureDMD.length(); i++) {
            IO.writeShort(0x40, airPressureDMD.charAt(i));
        }
        returnToFirstPage();
    }

    public static void selectRainraite() {
        GuiBoardDemos.clrDMDisplay();
        GuiBoardDemos.clrSevenSegment();
        Period now = new Period();
        now.getMeasurements();
        String rainRate = "" + Utilities.rounder(now.getMeasurements().get(0).getRainRate());
        IO.writeShort(0x24, rainRate.charAt(0));
        IO.writeShort(0x22, rainRate.charAt(1));
        IO.writeShort(0x20, rainRate.charAt(2));
        String rainRateDMD = "Showing current rain \n rate in mm";
        for (int i = 0; i < rainRateDMD.length(); i++) {
            IO.writeShort(0x40, rainRateDMD.charAt(i));

        }
        returnToFirstPage();
    }

    public static void selectUV() {

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

    public static void returnToFirstPage() {
        for (int i = 1; i > 0; i++) {
            if (IO.readShort(0x90) == 1) {
                firstPage();
                firstPageSelector();
                i = -1;
            }
        }
    }
}
