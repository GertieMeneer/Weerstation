import java.io.IOException;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        IO.init();
        GuiBoardDemos.clrDMDisplay();
        GuiBoardDemos.clrSevenSegment();
        firstPage();
        firstPageSelector();

    }

    public static void firstPage() {
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
            } else if (IO.readShort(0x100) == 1) {
                noSelection();
                secondPos();
                i = -1;
//                for (int j = 1; j > 0; j++) {
//                    if (IO.readShort(0x80) == 1) {
//
//                    }
//                }
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
        GuiBoardDemos.clrDMDisplay();

        String outsideTemp = "outside";
        String insideTemp = "inside";
        String both = "both";

        IO.writeShort(0x40, ' ');
        IO.writeShort(0x40, ' ');
        for (int i = 0; i < outsideTemp.length(); i++) {
            IO.writeShort(0x40, outsideTemp.charAt(i));
        }
        IO.writeShort(0x40, '\n');
        IO.writeShort(0x40, ' ');
        IO.writeShort(0x40, ' ');
        for (int i = 0; i < insideTemp.length(); i++) {
            IO.writeShort(0x40, insideTemp.charAt(i));
        }
        IO.writeShort(0x40, '\n');
        IO.writeShort(0x40, ' ');
        IO.writeShort(0x40, ' ');
        for (int i = 0; i < both.length(); i++) {
            IO.writeShort(0x40, both.charAt(i));
        }
        firstPos();
        for (int i = 1; i > 0; i++) {
            if (IO.readShort(0x80) == 0) {
                selectOutsideTemp();
                i = -1;
            } else if (IO.readShort(0x100) == 1) {
                noSelection();
                secondPos();
                i = -1;

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
}
