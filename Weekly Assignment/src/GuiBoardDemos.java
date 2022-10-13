
import java.util.ArrayList;
import java.util.Scanner;

public class GuiBoardDemos {

    static Scanner reader = new Scanner(System.in);

    // algemeen

    public void clear(){
        System.out.println();
        for (int i = 0; i < 5; i++) {
            IO.writeShort(0x10 + (2 * i), 0x100);
        }
        IO.writeShort(0x42, 0b0011000000000000);
    }


    // meerdere keren gebruikt

    public static void clrDMDisplay() {
        IO.writeShort(0x40,0xFE);
        IO.writeShort(0x40,0x01);
    }

    public static void DisplayNumb(int Getal, int Display){
        while (Getal != 0){
            int getal2 = Getal % 10;
            IO.writeShort(Display, getal2);
            Getal = Getal / 10;
            Display += 2;
        }
    }



    // opdracht 1 (gert-jan)

    ArrayList<Integer> alladdresses = new ArrayList<>();
    ArrayList<Integer> bovenstevijf = new ArrayList<>();
    private int value1 = 0;

    public GuiBoardDemos() {
        alladdresses.add(0x10);
        alladdresses.add(0x12);
        alladdresses.add(0x14);
        alladdresses.add(0x16);
        alladdresses.add(0x18);
        alladdresses.add(0x24);
        alladdresses.add(0x22);
        alladdresses.add(0x20);
        alladdresses.add(0x34);
        alladdresses.add(0x32);
        alladdresses.add(0x30);
        bovenstevijf.add(0x10);
        bovenstevijf.add(0x12);
        bovenstevijf.add(0x14);
        bovenstevijf.add(0x16);
        bovenstevijf.add(0x18);

    }

    /** public static void clrDMDisplay() // vaker gebruikt */

    public void clrDisplay() {
        for(Integer adds : alladdresses) {
            IO.writeShort(adds, 0x100);
        }
    }

    public void alleseentotnegen() {
        for(int run = 0; run < 10; run++) {
            IO.writeShort(0x18, run); IO.writeShort(0x16, run); IO.writeShort(0x14, run); IO.writeShort(0x12, run); IO.writeShort(0x10, run);
            IO.delay(250);
        }
    }

    public void eentotnegen() {
        for(int i = 0; i < 10; i++) {
            IO.writeShort(0x10, i);
            IO.delay(250);
        }
    }

    public void allesaan() {
        for(Integer digits : alladdresses) {
            IO.writeShort(digits, 9);
        }
    }

    public void conterwithzero() {
        for(Integer boven : bovenstevijf) {
            IO.writeShort(boven, 0);
        }
        eentotnegen();
        if(IO.readShort(0x10) == 9) {
            IO.writeShort(0x12, value1++);
            eentotnegen();
        }

    }


    // opdracht 2 (kai)

    public static void VerloopZonderNullen(){
        for (int i = 0; i < 100000 ; i++) {
            DisplayNumb(i, 0x10);
        }
    }

    /** public static void DisplayNumb(int Getal, int Display) // vaker gebruikt */

    public static void verloopMetNullen() {
        IO.writeShort(0x18, 0);
        IO.writeShort(0x16, 0);
        IO.writeShort(0x14, 0);
        IO.writeShort(0x12, 0);
        IO.writeShort(0x10, 0);
        VerloopZonderNullen();
    }


    // opdracht 3 (victor)

    public void countToNine(){
        for (int i = 0; i < 10; i++) {
            IO.delay(250);
            IO.writeShort(0x10, i);
        }

    }

    public void dance(int location){
        for (int i = 0; i < 2; i++) {
            lap(location);
        }
        snakeLap(location);
        down(location);
    }

    public void lap(int location){
        int a = 0b00000001;
        for (int i = 0; i < 6; i++) {
            IO.delay(100);
            IO.writeShort(location, 0x100 | a);
            a = a << 1;
        }

    }

    public void snakeLap(int location){
        int a = 1;
        int b = 1;
        for (int i = 0; i < 6; i++) {
            IO.delay(100);
            IO.writeShort(location, 0x100 | a);
            a = a * 2 + 1;
        }
        a = (a - 1) / 2;
        for (int i = 0; i < 5; i++) {
            a -= b;
            IO.delay(100);
            IO.writeShort(location, 0x100 | a);
            b *= 2;
        }
    }

    public void down(int location){
        int[] a = {1, 35, 64, 84, 8, 0};
        for (int i = 0; i < a.length; i++) {
            IO.delay(100);
            IO.writeShort(location, 0x100 | (a[i]));
        }
    }

    public void danceThrice(int location){
        for (int i = 0; i < 2; i++) {
            lapThrice(0x30);
        }
        snakeLapThrice(location);
        downThrice(location);
    }

    public void lapThrice(int location){
        int a = 0b00000001;
        for (int i = 0; i < 6; i++) {
            IO.delay(100);
            IO.writeShort(location, 0x100 | a);
            IO.writeShort(location + 2, 0x100 | a);
            IO.writeShort(location + 4, 0x100 | a);
            a = a << 1;
        }

    }

    public void snakeLapThrice(int location){
        int a = 1;
        int b = 1;
        for (int i = 0; i < 6; i++) {
            IO.delay(100);
            IO.writeShort(location, 0x100 | a);
            IO.writeShort(location + 2, 0x100 | a);
            IO.writeShort(location + 4, 0x100 | a);
            a = a * 2 + 1;
        }
        a = (a - 1) / 2;
        for (int i = 0; i < 5; i++) {
            a -= b;
            IO.delay(100);
            IO.writeShort(location, 0x100 | a);
            b *= 2;
        }
    }

    public void downThrice(int location){
        int[] a = {1, 35, 64, 84, 8, 0};
        for (int i = 0; i < a.length; i++) {
            IO.delay(100);
            IO.writeShort(location, 0x100 | (a[i]));
            IO.writeShort(location + 2, 0x100 | (a[i]));
            IO.writeShort(location + 4, 0x100 | (a[i]));
        }
    }


    // opdracht 4 (marcel)

    public static void clrSevenSegment() {
        //top display
        IO.writeShort(0x18, 0x100);
        IO.writeShort(0x16, 0x100);
        IO.writeShort(0x14, 0x100);
        IO.writeShort(0x12, 0x100);
        IO.writeShort(0x10, 0x100);
        //bottom left
        IO.writeShort(0x24, 0x100);
        IO.writeShort(0x22, 0x100);
        IO.writeShort(0x20, 0x100);
        //bottom right
        IO.writeShort(0x34, 0x100);
        IO.writeShort(0x32, 0x100);
        IO.writeShort(0x30, 0x100);
    }

    /** public static void clrDMDisplay() // vaker gebruikt */

    public static void drawAxis() {

        clrDMDisplay();
        for (int x = 1; x <= 128; x++) {
            IO.writeShort(0x42, 1 << 12 | x << 5 | 15);
        }
        for (int y = 0; y <= 31; y++) {
            IO.writeShort(0x42, 1 << 12 | 64 << 5 | y);
        }

    }

    public static void dotMatrixParabola() {

        for (int x = 1; x <= 64; x++) {
            double parabola = (0.1 * Math.pow(x, 2)) - 10;
            int y = (int) parabola;
            if (y < 16 && y >= -15) {
                IO.writeShort(0x42, 1 << 12 | (x << 5) + (64 << 5) | (y + 15) - (y * 2));
                IO.writeShort(0x42, 1 << 12 | ((x << 5) + (64 << 5)) - ((x << 5) * 2) | (y + 15) - (y * 2));
            }

        }

    }

    public static void dotMatrixWord() {

        clrDMDisplay();
        System.out.print("Enter a word: ");
        String input = reader.nextLine();
        for (int i = 0; i < input.length(); i++) {
            IO.writeShort(0x40, input.charAt(i));
        }
    }


    // opdracht 5 (niek)

    public void sturingKnoppen () {

        int i = 0;
        while (i < 100000){
            if (IO.readShort(0x80) == 0){
                DisplayNumb(i, 0x10);
            }
            else {
                clear();
            }
            if (IO.readShort(0x100) == 0){ i++; }
            else {
                if (i == 9){ IO.writeShort(0x12, 0x100); }
                if (i == 99){ IO.writeShort(0x14, 0x100); }
                if (i == 999){ IO.writeShort(0x16, 0x100); }
                if (i == 9999){ IO.writeShort(0x18, 0x100); }
                if (i > 0){ i--; }
            }

            IO.delay(20);
        }





        /*
        int i = 1;
        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;
        int e = 0;

        while (i < 100000){

            // scherm staat aan
            if ( IO.readShort(0x80) != 1 ) {

                if (a != 9 && a != 1) {IO.writeShort(0x10, a);}

                if (b != 9 && b != 1) {IO.writeShort(0x12, b);}

                if (c != 9 && c != 1) {IO.writeShort(0x14, c);}

                if (d != 9 && d != 1) {IO.writeShort(0x16, d);}

                if (e != 9 && e != 1) {IO.writeShort(0x18, e);}

                if (i < 10) {

                    IO.writeShort(0x12, 0);

                    a = i;
                    IO.writeShort(0x10, a);
                }

                else if (i < 100) {

                    IO.writeShort(0x14, 0);

                    if (i % 10 == 0) {
                        b = ((i - (i % 10)) / 10);
                        IO.writeShort(0x12, b);
                        IO.writeShort(0x10, 0);
                    }

                    else {
                        a = (i % 10);
                        IO.writeShort(0x10, a);
                    }

                }

                else if (i < 1000) {

                    IO.writeShort(0x16, 0);

                    if (i % 100 == 0) {
                        c = (i / 100);
                        IO.writeShort(0x14, c);
                        IO.writeShort(0x12, 0);
                        IO.writeShort(0x10, 0);
                    }

                    else if (i % 10 == 0) {
                        b = ((i / 10) % 10);
                        IO.writeShort(0x12, b);
                        IO.writeShort(0x10, 0);
                    }

                    else {
                        a = (i % 10);
                        IO.writeShort(0x10, a);
                    }

                } else if (i < 10000) {

                    IO.writeShort(0x18, 0);

                    if (i % 1000 == 0) {
                        d = (i / 1000);
                        IO.writeShort(0x16, d);
                        IO.writeShort(0x14, 0);
                        IO.writeShort(0x12, 0);
                        IO.writeShort(0x10, 0);
                    }

                    else if (i % 100 == 0) {
                        c = ((i / 100) % 10);
                        IO.writeShort(0x14, c);
                        IO.writeShort(0x12, 0);
                        IO.writeShort(0x10, 0);
                    }

                    else if (i % 10 == 0) {
                        b = ((i / 10) % 10);
                        IO.writeShort(0x12, b);
                        IO.writeShort(0x10, 0);

                    }

                    else {
                        a = (i % 10);
                        IO.writeShort(0x10, a);
                    }

                } else {
                    if (i % 10000 == 0) {
                        e = (i / 10000);
                        IO.writeShort(0x18, e);
                        IO.writeShort(0x16, 0);
                        IO.writeShort(0x14, 0);
                        IO.writeShort(0x12, 0);
                        IO.writeShort(0x10, 0);
                    }

                    else if (i % 1000 == 0) {
                        d = ((i / 1000) % 10);
                        IO.writeShort(0x16, d);
                        IO.writeShort(0x14, 0);
                        IO.writeShort(0x12, 0);
                        IO.writeShort(0x10, 0);
                    }

                    else if (i % 100 == 0) {
                        c = ((i / 100) % 10);
                        IO.writeShort(0x14, c);
                        IO.writeShort(0x12, 0);
                        IO.writeShort(0x10, 0);
                    }

                    else if (i % 10 == 0) {
                        b = ((i / 10) % 10);
                        IO.writeShort(0x12, b);
                        IO.writeShort(0x10, 0);
                    }

                    else {
                        a = (i % 10);
                        IO.writeShort(0x10, a);
                    }
                }
            }

            // scherm staat uit
            else {
                IO.writeShort(0x10, 0b100000000);
                IO.writeShort(0x12, 0b100000000);
                IO.writeShort(0x14, 0b100000000);
                IO.writeShort(0x16, 0b100000000);
                IO.writeShort(0x18, 0b100000000);

                if (i < 10) {
                    a = i;
                    IO.writeShort(0x10, a);
                }

                else if (i < 100) {
                    if (i % 10 == 0) {
                        b = ((i - (i % 10)) / 10);
                    }

                    else {
                        a = (i % 10);
                    }
                }

                else if (i < 1000) {
                    if (i % 100 == 0) {
                        c = (i / 100);
                    }

                    else if (i % 10 == 0) {
                        b = ((i / 10) % 10);
                    }

                    else {
                        a = (i % 10);
                    }
                }

                else if (i < 10000) {
                    if (i % 1000 == 0) {
                        d = (i / 1000);
                    }

                    else if (i % 100 == 0) {
                        c = ((i / 100) % 10);
                    }

                    else if (i % 10 == 0) {
                        b = ((i / 10) % 10);
                    }

                    else {
                        a = (i % 10);
                    }
                }

                else {
                    if (i % 10000 == 0) {
                        e = (i / 10000);
                    }

                    else if (i % 1000 == 0) {
                        d = ((i / 1000) % 10);
                    }

                    else if (i % 100 == 0) {
                        c = ((i / 100) % 10);
                    }

                    else if (i % 10 == 0) {
                        b = ((i / 10) % 10);
                    }

                    else {
                        a = (i % 10);
                    }
                }
            }

            // optellen
            if (IO.readShort(0x100) != 1) {
                i++;
                IO.delay(200);
            }

            // aftellen
            else {
                i--;
                if (i < 0) {i = 0;}
                IO.delay(200);
            }
        }
        */
    }

    /** public static void DisplayNumb(int Getal, int Display) // vaker gebruikt */




    public void sus() {
        IO.writeShort(0x42, 0b0001001001100101);
        IO.writeShort(0x42, 0b0001001010000101);
        IO.writeShort(0x42, 0b0001001010100101);
        IO.writeShort(0x42, 0b0001001011000101);
        IO.writeShort(0x42, 0b0001001011100101);
        IO.writeShort(0x42, 0b0001001100000101);
        IO.writeShort(0x42, 0b0001001100100110);
        IO.writeShort(0x42, 0b0001001101000111);
        IO.writeShort(0x42, 0b0001001100101000);
        IO.writeShort(0x42, 0b0001001100001000);
        IO.writeShort(0x42, 0b0001001100001000);
        IO.writeShort(0x42, 0b0001001011101000);
        IO.writeShort(0x42, 0b0001001011001000);
        IO.writeShort(0x42, 0b0001001010101000);
        IO.writeShort(0x42, 0b0001001010001001);
        IO.writeShort(0x42, 0b0001001001101010);
        IO.writeShort(0x42, 0b0001001001101011);
        IO.writeShort(0x42, 0b0001001001101100);
        IO.writeShort(0x42, 0b0001001010001101);
        IO.writeShort(0x42, 0b0001001010101110);
        IO.writeShort(0x42, 0b0001001011001110);
        IO.writeShort(0x42, 0b0001001011101110);
        IO.writeShort(0x42, 0b0001001100001110);
        IO.writeShort(0x42, 0b0001001100101110);
        IO.writeShort(0x42, 0b0001001101001110);
        IO.writeShort(0x42, 0b0001001101001000);
        IO.writeShort(0x42, 0b0001001101101001);
        IO.writeShort(0x42, 0b0001001110001010);
        IO.writeShort(0x42, 0b0001001110001011);
        IO.writeShort(0x42, 0b0001001110001100);
        IO.writeShort(0x42, 0b0001001101101101);
        IO.writeShort(0x42, 0b0001001101101110);
        IO.writeShort(0x42, 0b0001001101101111);
        IO.writeShort(0x42, 0b0001001101110000);
        IO.writeShort(0x42, 0b0001001101110001);
        IO.writeShort(0x42, 0b0001001101110010);
        IO.writeShort(0x42, 0b0001001101110011);
        IO.writeShort(0x42, 0b0001001101110100);
        IO.writeShort(0x42, 0b0001001101110101);
        IO.writeShort(0x42, 0b0001001101110110);
        IO.writeShort(0x42, 0b0001001101110111);
        IO.writeShort(0x42, 0b0001001101111000);
        IO.writeShort(0x42, 0b0001001101111000);
        IO.writeShort(0x42, 0b0001001101011001);
        IO.writeShort(0x42, 0b0001001100111001);
        IO.writeShort(0x42, 0b0001001100011001);
        IO.writeShort(0x42, 0b0001001011111000);
        IO.writeShort(0x42, 0b0001001011110111);
        IO.writeShort(0x42, 0b0001001011110110);
        IO.writeShort(0x42, 0b0001001011010110);
        IO.writeShort(0x42, 0b0001001010110110);
        IO.writeShort(0x42, 0b0001001010010110);
        IO.writeShort(0x42, 0b0001001010010111);
        IO.writeShort(0x42, 0b0001001010011000);
        IO.writeShort(0x42, 0b0001001001111001);
        IO.writeShort(0x42, 0b0001001001011001);
        IO.writeShort(0x42, 0b0001001001011001);
        IO.writeShort(0x42, 0b0001001000111001);
        IO.writeShort(0x42, 0b0001001000111001);
        IO.writeShort(0x42, 0b0001001000011000);
        IO.writeShort(0x42, 0b0001001000010111);
        IO.writeShort(0x42, 0b0001001000010110);
        IO.writeShort(0x42, 0b0001001000010101);
        IO.writeShort(0x42, 0b0001000111110101);
        IO.writeShort(0x42, 0b0001000111010101);
        IO.writeShort(0x42, 0b0001000110110100);
        IO.writeShort(0x42, 0b0001000110110011);
        IO.writeShort(0x42, 0b0001000110110010);
        IO.writeShort(0x42, 0b0001000110110001);
        IO.writeShort(0x42, 0b0001000110110000);
        IO.writeShort(0x42, 0b0001000110101111);
        IO.writeShort(0x42, 0b0001000110101110);
        IO.writeShort(0x42, 0b0001000110101101);
        IO.writeShort(0x42, 0b0001000110101100);
        IO.writeShort(0x42, 0b0001000111001011);
        IO.writeShort(0x42, 0b0001000111101011);
        IO.writeShort(0x42, 0b0001001000010100);
        IO.writeShort(0x42, 0b0001001000010011);
        IO.writeShort(0x42, 0b0001001000010010);
        IO.writeShort(0x42, 0b0001001000010001);
        IO.writeShort(0x42, 0b0001001000010000);
        IO.writeShort(0x42, 0b0001001000001111);
        IO.writeShort(0x42, 0b0001001000001110);
        IO.writeShort(0x42, 0b0001001000001101);
        IO.writeShort(0x42, 0b0001001000001100);
        IO.writeShort(0x42, 0b0001001000001011);
        IO.writeShort(0x42, 0b0001001000001010);
        IO.writeShort(0x42, 0b0001001000001001);
        IO.writeShort(0x42, 0b0001001000001000);
        IO.writeShort(0x42, 0b0001001001000110);
        IO.writeShort(0x42, 0b0001001000100111);
    }

}