
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;

public class ReadExcelFile {

    public static void excelToDotMatrix() throws IOException {
        IO.init();
        GuiBoardDemos.clrDMDisplay();
//obtaining input bytes from a file
        FileInputStream fis = new FileInputStream(new File("C:\\Users\\niekh\\OneDrive - Avans Hogeschool\\Periode 1 Jaar 1\\Proftaak\\GUIboardDemo.xlsx"));
//creating workbook instance that refers to .xls file
        XSSFWorkbook wb = new XSSFWorkbook(fis);
//creating a Sheet object to retrieve the object
        XSSFSheet sheet = wb.getSheetAt(0);
//evaluating cell type
        FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();

        for (Row row : sheet)     //iteration over row using for each loop
        {
            for (Cell cell : row)    //iteration over cell using for each loop
            {
                switch (formulaEvaluator.evaluateInCell(cell).getCellType()) {
                    case Cell.CELL_TYPE_NUMERIC:   //field that represents numeric cell type
//getting the value of the cell as a number
                        double numericOutput = cell.getNumericCellValue();
                        String numericString = Double.toString(numericOutput);
                        for (int i = 0; i < numericString.length(); i++) {
                            IO.writeShort(0x40, numericString.charAt(i));
                        }
//                        System.out.print(cell.getNumericCellValue() + "\t\t");
                        break;
                    case Cell.CELL_TYPE_STRING:    //field that represents string cell type

                        //gets string from excel file
                        String output = cell.getStringCellValue() + ("\n");
                        for (int i = 0; i < output.length(); i++) {
                            IO.writeShort(0x40, output.charAt(i));
                        }

                        break;
                }
            }
            System.out.println();
        }
    }
}

