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
        String temp = "Temperature";
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
