package org.unhcr.eg.registration.security.exchange;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JFileChooser;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author UNHCRUser
 */
public class LoadIndivualFromExcel {

    private static void showExcelData(List sheetData, String ext) {
        switch (ext) {
            case ".xlsx":
                //
                // Iterates the data and print it out to the console.
                //
                for (int i = 0; i < sheetData.size(); i++) {
                    List list = (List) sheetData.get(i);
                    for (int j = 0; j < list.size(); j++) {
                        XSSFCell cell = (XSSFCell) list.get(j);
                        cell.getCellType();

                        switch (cell.getCellType()) {
                            case Cell.CELL_TYPE_BLANK:
                                System.out.print("BLANK");
                                if (j < list.size() - 1) {
                                    System.out.print(", ");
                                }
                                break;
                            case Cell.CELL_TYPE_BOOLEAN:
                                System.out.print(cell.getBooleanCellValue() + " : ");
                                System.out.print("CELL_TYPE_BOOLEAN");
                                if (j < list.size() - 1) {
                                    System.out.print(", ");
                                }
                                break;
                            case Cell.CELL_TYPE_ERROR:
                                System.out.print(cell.getErrorCellString() + " : ");
                                System.out.print("CELL_TYPE_ERROR");
                                if (j < list.size() - 1) {
                                    System.out.print(", ");
                                }
                                break;
                            case Cell.CELL_TYPE_FORMULA:
                                System.out.print(cell.getCellFormula() + " : ");
                                System.out.print("CELL_TYPE_FORMULA");
                                if (j < list.size() - 1) {
                                    System.out.print(", ");
                                }
                                break;
                            case Cell.CELL_TYPE_NUMERIC:
                                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                    System.out.print(cell.getDateCellValue() + " : ");
                                    System.out.print("CELL_TYPE_DATE");
                                } else {
                                    System.out.print(cell.getNumericCellValue() + " : ");
                                    System.out.print("CELL_TYPE_NUMERIC");
                                }
                                if (j < list.size() - 1) {
                                    System.out.print(", ");
                                }
                                break;
                            case Cell.CELL_TYPE_STRING:
                                System.out.print(cell.getStringCellValue() + " : ");
                                System.out.print("CELL_TYPE_STRING");
                                if (j < list.size() - 1) {
                                    System.out.print(", ");
                                }
                                break;
                        }
                    }
                    System.out.println("");
                }
                break;
            case ".xls":
                //
                // Iterates the data and print it out to the console.
                //
                for (int i = 0; i < sheetData.size(); i++) {
                    List list = (List) sheetData.get(i);
                    for (int j = 0; j < list.size(); j++) {
                        HSSFCell cell = (HSSFCell) list.get(j);
                        cell.getCellType();
                        switch (cell.getCellType()) {
                            case Cell.CELL_TYPE_BLANK:
                                System.out.print("BLANK");
                                if (j < list.size() - 1) {
                                    System.out.print(", ");
                                }
                                break;
                            case Cell.CELL_TYPE_BOOLEAN:
                                System.out.print(cell.getBooleanCellValue() + " : ");
                                System.out.print("CELL_TYPE_BOOLEAN");
                                if (j < list.size() - 1) {
                                    System.out.print(", ");
                                }
                                break;
                            case Cell.CELL_TYPE_ERROR:
                                System.out.print(cell.getErrorCellValue() + " : ");
                                System.out.print("CELL_TYPE_ERROR");
                                if (j < list.size() - 1) {
                                    System.out.print(", ");
                                }
                                break;
                            case Cell.CELL_TYPE_FORMULA:
                                System.out.print(cell.getCellFormula() + " : ");
                                System.out.print("CELL_TYPE_FORMULA");
                                if (j < list.size() - 1) {
                                    System.out.print(", ");
                                }
                                break;
                            case Cell.CELL_TYPE_NUMERIC:
                                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                    System.out.print(cell.getDateCellValue() + " : ");
                                    System.out.print("CELL_TYPE_DATE");
                                } else {
                                    System.out.print(cell.getNumericCellValue() + " : ");
                                    System.out.print("CELL_TYPE_NUMERIC");
                                }
                                if (j < list.size() - 1) {
                                    System.out.print(", ");
                                }
                                break;
                            case Cell.CELL_TYPE_STRING:
                                System.out.print(cell.getStringCellValue() + " : ");
                                System.out.print("CELL_TYPE_STRING");
                                if (j < list.size() - 1) {
                                    System.out.print(", ");
                                }
                                break;
                        }
                    }
                    System.out.println("");
                }
                break;
        }
    }

    public static void getData(File filename) throws IOException {
        //
        // Create an ArrayList to store the data read from excel sheet.
        //
        List sheetData = new ArrayList();
        FileInputStream fis = null;
        String ext = null;
        try {
            //
            // Create a FileInputStream that will be use to read the 
            // excel file.
            //
            fis = new FileInputStream(filename);

            if (filename.getName().endsWith(".xlsx")) {
                ext = ".xlsx";
                //
                // Create an excel workbook from the file system.
                //
                XSSFWorkbook workbook = new XSSFWorkbook(fis);

                //
                // Get the first sheet on the workbook.
                //
                XSSFSheet sheet = workbook.getSheetAt(0);

                //
                // When we have a sheet object in hand we can iterator on 
                // each sheet's rows and on each row's cells. We store the 
                // data read on an ArrayList so that we can printed the 
                // content of the excel to the console.
                //
                Iterator rows = sheet.rowIterator();
                while (rows.hasNext()) {
                    XSSFRow row = (XSSFRow) rows.next();
                    Iterator cells = row.cellIterator();
                    List data = new ArrayList();
                    while (cells.hasNext()) {
                        XSSFCell cell = (XSSFCell) cells.next();
                        data.add(cell);
                    }
                    sheetData.add(data);
                }
            } else if (filename.getName().endsWith(".xls")) {
                ext = ".xls";
                // Create an excel workbook from the file system.
                //
                HSSFWorkbook workbook = new HSSFWorkbook(fis);

                //
                // Get the first sheet on the workbook.
                //
                HSSFSheet sheet = workbook.getSheetAt(0);

                //
                // When we have a sheet object in hand we can iterator on 
                // each sheet's rows and on each row's cells. We store the 
                // data read on an ArrayList so that we can printed the 
                // content of the excel to the console.
                //
                Iterator rows = sheet.rowIterator();
                while (rows.hasNext()) {
                    HSSFRow row = (HSSFRow) rows.next();
                    Iterator cells = row.cellIterator();
                    List data = new ArrayList();
                    while (cells.hasNext()) {
                        HSSFCell cell = (HSSFCell) cells.next();
                        data.add(cell);
                    }
                    sheetData.add(data);
                }
            }
        } catch (IOException e) {
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
        showExcelData(sheetData, ext);
    }

    public static void loadFile() throws IOException, HeadlessException {
        //
        // An excel file name. You can create a file name with a full 
        // path information.
        //
        //Create a file chooser
        File file = null;
        final JFileChooser fc = new JFileChooser();

        //In response to a button click:
        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fc.getSelectedFile();
            getData(file);
        } else {
        }
    }
}
