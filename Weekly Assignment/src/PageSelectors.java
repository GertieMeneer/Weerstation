public class PageSelectors {
    public static void firstPageSelector() {
        firstPos();
        for (int i = 1; i > 0; i++) {
            if (IO.readShort(0x80) == 1) {
                SelectOptions.selectTemp();
                i = -1;
            } else if (IO.readShort(0x100) == 1) {
                noSelection();
                secondPos();
                i = -1;
            }
        }
        for (int i = 1; i > 0; i++) {
            if (IO.readShort(0x80) == 1) {
                SelectOptions.selectHum();
                i = -1;
            } else if (IO.readShort(0x100) == 0) {
                noSelection();
                thirdPos();
                i = -1;
            }
        }
        for (int i = 1; i > 0; i++) {
            if (IO.readShort(0x80) == 1) {
                SelectOptions.selectWind();
                i = -1;
            } else if (IO.readShort(0x100) == 1) {
                noSelection();
                PrintPage.secondPage();
                secondPageSelector();
                i = -1;
            }
        }
    }


    public static void secondPageSelector() {
        firstPos();
        for (int i = 1; i > 0; i++) {
            if (IO.readShort(0x80) == 1) {
                SelectOptions.selectAirpressure();
                i = -1;
            } else if (IO.readShort(0x100) == 0) {
                noSelection();
                secondPos();
                i = -1;
            }
        }
        for (int i = 1; i > 0; i++) {
            if (IO.readShort(0x80) == 1) {
                SelectOptions.selectRainrate();
                i = -1;
            } else if (IO.readShort(0x100) == 1) {
                noSelection();
                thirdPos();
                i = -1;
            }
        }
        for (int i = 1; i > 0; i++) {
            if (IO.readShort(0x80) == 1) {
                SelectOptions.selectUV();
                i = -1;
            } else if (IO.readShort(0x100) == 0) {
                noSelection();
                PrintPage.thirdPage();
                thirdPageSelector();
                i = -1;
            }
        }
    }


    public static void thirdPageSelector() {
        firstPos();
        for (int i = 1; i > 0; i++) {
            if (IO.readShort(0x80) == 1) {
                SelectOptions.selectSolarrad();
                i = -1;
            } else if (IO.readShort(0x100) == 1) {
                noSelection();
                secondPos();
                i = -1;
            }
        }
        for (int i = 1; i > 0; i++) {
            if (IO.readShort(0x80) == 1) {
                SelectOptions.selectSun();
                i = -1;
            } else if (IO.readShort(0x100) == 0) {
                noSelection();
                thirdPos();
                i = -1;
            }
        }
        for (int i = 1; i > 0; i++) {
            if (IO.readShort(0x80) == 1) {
                SelectOptions.selectBatteries();
                i = -1;
            } else if (IO.readShort(0x100) == 1) {
                noSelection();
                PrintPage.fourthPage();
                fourthPageSelector();
                i = -1;
            }
        }
    }


    public static void fourthPageSelector() {
        firstPos();
        for (int i = 1; i > 0; i++) {
            if (IO.readShort(0x80) == 1) {
                SelectOptions.selectDewpoint();
                i = -1;
            } else if (IO.readShort(0x100) == 0) {
                noSelection();
                secondPos();
                i = -1;
            }
        }
        for (int i = 1; i > 0; i++) {
            if (IO.readShort(0x80) == 1) {
                SelectOptions.selectWindchill();
                i = -1;
            } else if (IO.readShort(0x100) == 1) {
                noSelection();
                thirdPos();
                i = -1;
            }
        }
        for (int i = 1; i > 0; i++) {
            if (IO.readShort(0x80) == 1) {
                SelectOptions.selectHeatindex();
                i = -1;
            } else if (IO.readShort(0x100) == 0) {
                noSelection();
                PrintPage.fifthPage();
                fifthPageSelector();
                i = -1;
            }
        }

    }

    public static void fifthPageSelector() {
        firstPos();
        for (int i = 1; i > 0; i++) {
            if (IO.readShort(0x80) == 1) {
                SelectOptions.selectReturnHome();
                i = -1;
            } else if (IO.readShort(0x100) == 1) {
                noSelection();
                secondPos();
                i = -1;
            }
        }
        for (int i = 1; i > 0; i++) {
            if (IO.readShort(0x100) == 0) {
                noSelection();
                PrintPage.firstPage();
                firstPageSelector();
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

    public static void returnToFirstPage() {
        for (int i = 1; i > 0; i++) {
            if (IO.readShort(0x90) == 1) {
                PrintPage.firstPage();
                firstPageSelector();
                i = -1;
            }
        }
    }
}
