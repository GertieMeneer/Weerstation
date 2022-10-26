public class PageSelectors {
    public static void firstPageSelector() {
        firstPos();
        for (int i = 1; i > 0; i++) {
            if (IO.readShort(0x80) == 1) {
                PrintPage.selectCurrentOrCustomPage();
                PageSelectors.selectCurrentOrCustomPage("temp");
                i = -1;
            } else if (IO.readShort(0x100) == 1) {
                noSelection();
                secondPos();
                i = -1;
            }
        }
        for (int i = 1; i > 0; i++) {
            if (IO.readShort(0x80) == 1) {
                PrintPage.selectCurrentOrCustomPage();
                PageSelectors.selectCurrentOrCustomPage("hum");
                i = -1;
            } else if (IO.readShort(0x100) == 0) {
                noSelection();
                thirdPos();
                i = -1;
            }
        }
        for (int i = 1; i > 0; i++) {
            if (IO.readShort(0x80) == 1) {
                PrintPage.selectCurrentOrCustomPage();
                PageSelectors.selectCurrentOrCustomPage("wind");
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
                PrintPage.selectCurrentOrCustomPage();
                PageSelectors.selectCurrentOrCustomPage("airpressure");
                i = -1;
            } else if (IO.readShort(0x100) == 0) {
                noSelection();
                secondPos();
                i = -1;
            }
        }
        for (int i = 1; i > 0; i++) {
            if (IO.readShort(0x80) == 1) {
                PrintPage.selectCurrentOrCustomPage();
                PageSelectors.selectCurrentOrCustomPage("rain");
                i = -1;
            } else if (IO.readShort(0x100) == 1) {
                noSelection();
                thirdPos();
                i = -1;
            }
        }
        for (int i = 1; i > 0; i++) {
            if (IO.readShort(0x80) == 1) {
                PrintPage.selectCurrentOrCustomPage();
                PageSelectors.selectCurrentOrCustomPage("uv");
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
                PrintPage.selectCurrentOrCustomPage();
                PageSelectors.selectCurrentOrCustomPage("solarrad");
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
                PrintPage.selectCurrentOrCustomPage();
                PageSelectors.selectCurrentOrCustomPage("dewpoint");
                i = -1;
            } else if (IO.readShort(0x100) == 0) {
                noSelection();
                secondPos();
                i = -1;
            }
        }
        for (int i = 1; i > 0; i++) {
            if (IO.readShort(0x80) == 1) {
                PrintPage.selectCurrentOrCustomPage();
                PageSelectors.selectCurrentOrCustomPage("windchill");
                i = -1;
            } else if (IO.readShort(0x100) == 1) {
                noSelection();
                thirdPos();
                i = -1;
            }
        }
        for (int i = 1; i > 0; i++) {
            if (IO.readShort(0x80) == 1) {
                PrintPage.selectCurrentOrCustomPage();
                PageSelectors.selectCurrentOrCustomPage("heatindex");
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

    public static void selectCurrentOrCustomPage(String value) {
        firstPos();
        int selectoption = 0;
        if(value.equals("temp")) {
            selectoption = 0;
        } else if (value.equals("hum")) {
            selectoption = 1;
        } else if (value.equals("wind")) {
            selectoption = 2;
        } else if (value.equals("airpressure")) {
            selectoption = 3;
        } else if (value.equals("rain")) {
            selectoption = 4;
        } else if (value.equals("uv")) {
            selectoption = 5;
        } else if (value.equals("solarrad")) {
            selectoption = 6;
        } else if (value.equals("dewpoint")) {
            selectoption = 7;
        } else if (value.equals("windchill")) {
            selectoption = 8;
        } else if (value.equals("heatindex")) {
            selectoption = 9;
        }
        for (int i = 1; i > 0; i++) {
            if (IO.readShort(0x80) == 0) {
                if (selectoption == 0) {
                    SelectOptions.selectCurrentTemp();
                } else if (selectoption == 1) {
                    SelectOptions.selectHum();
                } else if (selectoption == 2) {
                    SelectOptions.selectWind();
                } else if (selectoption == 3) {
                    SelectOptions.selectAirpressure();
                } else if (selectoption == 4) {
                    SelectOptions.selectRainrate();
                } else if (selectoption == 5) {
                    SelectOptions.selectUV();
                } else if (selectoption == 6) {
                    SelectOptions.selectSolarrad();
                } else if (selectoption == 7) {
                    SelectOptions.selectDewpoint();
                } else if (selectoption == 8) {
                    SelectOptions.selectWind();
                } else if (selectoption == 9) {
                    SelectOptions.selectHeatindex();
                }
                i = -1;
            } else if (IO.readShort(0x100) == 1) {
                noSelection();
                secondPos();
                i = -1;
            }
        }
        for (int i = 1; i > 0; i++) {
            if (IO.readShort(0x80) == 0) {
                PrintPage.selectCustomInfoPage1();
                PageSelectors.selectCustomInfoTempPage1(value);
                i = -1;
            } else if (IO.readShort(0x100) == 0) {
                noSelection();
                firstPos();
                selectCurrentOrCustomPage(value);
                i = -1;
            }
        }
    }

    public static void selectCustomInfoTempPage1(String value) {
        firstPos();
        for (int i = 1; i > 0; i++) {
            if (IO.readShort(0x80) == 1) {
                PrintPage.selectCustomPeriod(value, "avg");
                i = -1;
            } else if (IO.readShort(0x100) == 0) {
                noSelection();
                secondPos();
                i--;
            }
        }
        for (int i = 1; i > 0; i++) {
            if (IO.readShort(0x80) == 1) {
                PrintPage.selectCustomPeriod(value, "low");
                i = -1;
            } else if (IO.readShort(0x100) == 1) {
                noSelection();
                thirdPos();
                i--;
            }
        }
        for (int i = 1; i > 0; i++) {
            if (IO.readShort(0x80) == 1) {
                PrintPage.selectCustomPeriod(value, "high");
                i = -1;
            } else if (IO.readShort(0x100) == 0) {
                noSelection();
                PrintPage.selectCustomInfoPage2();
                selectCustomInfoTempPage2(value);
                i--;
            }
        }
    }
    public static void selectCustomInfoTempPage2(String value) {
        firstPos();
        for (int i = 1; i > 0; i++) {
            if (IO.readShort(0x80) == 1) {
                PrintPage.selectCustomPeriod(value, "median");
                i = -1;
            } else if (IO.readShort(0x100) == 1) {
                noSelection();
                secondPos();
                i--;
            }
        }
        for (int i = 1; i > 0; i++) {
            if (IO.readShort(0x80) == 1) {
                PrintPage.selectCustomPeriod(value, "mode");
                i = -1;
            } else if (IO.readShort(0x100) == 0) {
                noSelection();
                PrintPage.selectCustomInfoPage1();
                selectCustomInfoTempPage1(value);
                firstPos();
                i--;
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
