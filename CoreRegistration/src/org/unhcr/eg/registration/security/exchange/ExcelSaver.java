/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.security.exchange;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.Exceptions;
import org.openide.util.RequestProcessor;

public class ExcelSaver {

    private String filename = null;             // File name
    private FileOutputStream out = null;            // Output Stream
    private TableModel tableModel = null;    // Table Model
    private JTable table = null;    // Table Model
    private String title = null;             // Sheet name
    private ProgressHandle handle;

    /**
     * ExcelSaver Constructor
     *
     * @param tableModel
     * @param filename
     * @param title
     */
    public ExcelSaver(TableModel tableModel, String filename, String title) {

        this.tableModel = tableModel;   // Table Model
        this.filename = filename;   // File name
        this.title = title;
    }

    /**
     * ExcelSaver Constructor
     *
     * @param table
     * @param filename
     * @param title
     */
    public ExcelSaver(JTable table, String filename, String title) {

        this.table = table;   // Table Model
        this.filename = filename;   // File name
        this.title = title;
    }

    /**
     * ExcelSaver Constructor
     *
     */
    public ExcelSaver() {
    }

    /**
     * This method returns the file name.
     *
     * @return String filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * This method returns the table model.
     *
     * @return TableModel tableModel
     */
    public TableModel getTableModel() {
        return tableModel;

    }

    public JTable getTableView() {
        return table;

    }

    /**
     * This method saves the given file to an excel spreadsheet.
     */
    public void addPicture(Workbook workbook, Sheet sheet, InputStream imageLocation, int row, int column) {
        InputStream is;
        try {
            is = imageLocation;
            byte[] bytes = IOUtils.toByteArray(is);
            int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
            CreationHelper helper = workbook.getCreationHelper();

            // Create the drawing patriarch.  This is the top level container for all shapes.
            Drawing drawing = sheet.createDrawingPatriarch();
            //add a picture shape
            ClientAnchor anchor = helper.createClientAnchor();
            //set top-left corner of the picture,
            //subsequent call of Picture#resize() will operate relative to it
            anchor.setCol1(row);
            anchor.setRow1(column);
            Picture pict = drawing.createPicture(anchor, pictureIdx);
            //auto-size picture relative to its top-left corner
            pict.resize();

        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
    private SecureRandom random = new SecureRandom();

    public String nextSessionId() {
        return new BigInteger(130, random).toString(32);
    }

    @SuppressWarnings("deprecation")
    public void saveTable() {
        handle = ProgressHandleFactory.createHandle("Export Excel file " + getFilename());
        int workUnitMax = (getTableView().getRowCount() + 1) * getTableView().getColumnCount();
        int workUnit = 0;
        handle.start(workUnitMax);
        /**
         * Create a new file
         */
        //      try {
//            out = new FileOutputStream(filename);
        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new File(filename));

        int returnVal = chooser.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try {
                out = new FileOutputStream(file);
            } catch (IOException ioe) {
                // ... handle errors!
            }

            /**
             * Set up workbook
             */
            HSSFWorkbook workbook = new HSSFWorkbook();             // New workbook
            HSSFSheet worksheet = workbook.createSheet(title);               // New worksheet
            HSSFRow row;                         // Row
            HSSFCell column;                         // Column

            //turn off gridlines
            worksheet.setDisplayGridlines(false);
            worksheet.setPrintGridlines(false);
            worksheet.setFitToPage(true);
            worksheet.setHorizontallyCenter(true);
            PrintSetup printSetup = worksheet.getPrintSetup();
            printSetup.setLandscape(true);

            //the following three statements are required only for HSSF
            worksheet.setAutobreaks(true);
            printSetup.setFitHeight((short) 1);
            printSetup.setFitWidth((short) 1);
            HSSFCellStyle headerCellStyle = getHeaderStyle(workbook);
            HSSFCellStyle stringCellStyle = getStringStyle(workbook);
            HSSFCellStyle integerCellStyle = getIntegerStyle(workbook);
            HSSFCellStyle headCellStyle = getHeaderStyle(workbook);

            worksheet.addMergedRegion(new CellRangeAddress(
                    0, //first row (0-based)
                    0, //last row  (0-based)
                    0, //first column (0-based)
                    getTableView().getColumnCount() - 1 //last column  (0-based)
                    ));
            row = worksheet.createRow(0);
            row.setHeightInPoints(24);

            column = row.createCell(0);
            column.setCellStyle(headCellStyle);
            column.setCellValue(title);

            // Create the freeze pane. Thismethod has four parameters.
            // The first defines the index number of the column that should be
            // locked. Passing zero will not lock any columns, pass 1 to lock
            // lock column A, 2 to lock A and B, etc.
            // The second parameter defines the number of rows to lock. Pass 1
            // to lock row 1, 2 to lock rows 1 and 2, etc.
            // The fourth parameter defines the index number of the left most
            // column that is visible in the pane. Pass 0 to start at column
            // A, 1 to start at column B, etc
            // The fourth parameter defines the index number of the row which
            // will be shown as the first row in the display. Pass 0 to show
            // the first row, 1 to show the second, etc.

            worksheet.createFreezePane(0, 8, 0, 8);

            row = worksheet.createRow(1);
            column = row.createCell(0);
            column.setCellValue("Location");
            column = row.createCell(1);
            column.setCellValue("");

            row = worksheet.createRow(2);
            column = row.createCell(0);
            column.setCellValue("Date");
            column = row.createCell(1);
            CellStyle dateCellStyleHour = getDateCellStyle(workbook);
            column.setCellValue(new Date());
            column.setCellStyle(dateCellStyleHour);
            CellStyle dateCellStyle = getDateCellStyle(workbook);

            row = worksheet.createRow(3);
            column = row.createCell(0);
            column.setCellValue("Number of Lines ");
            column = row.createCell(1);
            column.setCellValue(getTableView().getRowCount());

//        ImageUtilities.loadImage("UNHCR-logo.jpg");
//        addPicture(workbook, worksheet, ExcelSaver.class.getResourceAsStream("UNHCR-logo.jpg"), 0, 0);
            /**
             * Add headers to worksheet
             */
            row = worksheet.createRow(7);
            for (int colNumber = 0; colNumber < getTableView().getColumnCount(); colNumber++) {
                handle.progress(++workUnit);
                column = row.createCell(colNumber);
                column.setCellStyle(headerCellStyle);
                column.setCellValue(getTableView().getColumnName(colNumber));
            }

            /**
             * Add data to worksheet
             */
            int temp = 0; // Row value after the header row

            // Rows

            for (int rowNumber = 0; rowNumber < getTableView().getRowCount(); rowNumber++) {

                temp = rowNumber + 8; // Add one row to account for header
                row = worksheet.createRow(temp);

                // Columns

                for (int colNumber = 0; colNumber < getTableView().getColumnCount(); colNumber++) {
                    handle.progress(++workUnit);
                    column = row.createCell(colNumber);
                    Object valueAt = getTableView().getValueAt(rowNumber, colNumber);
                    Class<?> dataClass = getTableView().getColumnClass(colNumber);
                    if (valueAt != null) {
                        if (String.class.isAssignableFrom(dataClass)) {
                            column.setCellStyle(stringCellStyle);
                            column.setCellType(Cell.CELL_TYPE_STRING);
                            column.setCellValue(valueAt.toString());

                        } else if (Number.class.isAssignableFrom(dataClass)) {
                            column.setCellStyle(integerCellStyle);
                            column.setCellType(Cell.CELL_TYPE_NUMERIC);
                            column.setCellValue(Double.parseDouble(valueAt.toString()));

                        } else if (Date.class.isAssignableFrom(dataClass)) {
                            column.setCellStyle(dateCellStyle);
                            column.setCellValue((Date) valueAt);
                        } else {
                            column.setCellStyle(stringCellStyle);
                            column.setCellValue(valueAt.toString());
                        }
                    } else {
                        column.setCellStyle(stringCellStyle);
                        column.setCellValue("");
                    }

                }

            }
            for (int colNumber = 0; colNumber < getTableView().getColumnCount(); colNumber++) {
                worksheet.autoSizeColumn(colNumber);
            }
            saveWorkBook(workbook);

            /**
             * Ask the user if he wants to open the spreadsheet.
             */
            handle.finish();
            openFile(file);
        } else {
            handle.finish();
        }
    }

    public static String getDateString() {
        StringBuilder builder = new StringBuilder();
        builder.append(Integer.toString(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)));
        builder.append("d_");
        builder.append(Integer.toString(Calendar.getInstance().get(Calendar.MONTH)));
        builder.append("m_");
        builder.append(Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
        builder.append("y_");

        builder.append(Integer.toString(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)));
        builder.append("h_");
        builder.append(Integer.toString(Calendar.getInstance().get(Calendar.MINUTE)));
        builder.append("m_");
        builder.append(Integer.toString(Calendar.getInstance().get(Calendar.SECOND)));
        builder.append("s_");
        builder.append(Integer.toString(Calendar.getInstance().get(Calendar.MILLISECOND)));
        builder.append("ms");
        return builder.toString();
    }

//    public void export() {
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    save();             // Call save() method
//                } catch (Exception e) {
//                    handle.finish();
//                }
//            }
//        };
//        RequestProcessor.getDefault().execute(runnable);
//    }
    public void exportTable() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    saveTable();             // Call save() method
                } catch (Exception e) {
                    handle.finish();
                }
            }
        };

        RequestProcessor.getDefault().execute(runnable);
    }

    public HSSFCellStyle getHeaderStyle(HSSFWorkbook workbook) {
        /**
         * Header Cell Style
         */
        HSSFCellStyle headerCellStyle = workbook.createCellStyle();     // Header Cell Style
        HSSFFont headerFont = workbook.createFont();                // Header Font
        headerFont.setFontHeightInPoints((short) 10);             // Font Size
        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);           // Bold Font
        headerCellStyle.setFont(headerFont);                  // Set style
        headerCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);     // Center align
        headerCellStyle.setFillBackgroundColor(IndexedColors.AQUA.getIndex());
        headerCellStyle.setBorderBottom((short) 5);
        headerCellStyle.setBorderLeft((short) 5);
        headerCellStyle.setBorderRight((short) 5);
        headerCellStyle.setBorderTop((short) 5);
        return headerCellStyle;
    }

    public HSSFCellStyle getStringStyle(HSSFWorkbook workbook) {
        /**
         * String Cell Style
         */
        HSSFCellStyle stringCellStyle = workbook.createCellStyle();     // Data Cell Style
        HSSFFont stringFont = workbook.createFont();                // Data Font
        stringFont.setFontHeightInPoints((short) 10);             // Font Size
        stringCellStyle.setFont(stringFont);                    // Set style
        stringCellStyle.setBorderBottom((short) 1);
        stringCellStyle.setBorderLeft((short) 1);
        stringCellStyle.setBorderRight((short) 1);
        stringCellStyle.setBorderTop((short) 1);
        return stringCellStyle;
    }

    public HSSFCellStyle getIntegerStyle(HSSFWorkbook workbook) {
        /**
         * Integer Cell Style
         */
        HSSFCellStyle integerCellStyle = workbook.createCellStyle();        // Data Cell Style
        HSSFDataFormat format = workbook.createDataFormat();            // Data Format
        HSSFFont integerFont = workbook.createFont();               // Data Font
        integerFont.setFontHeightInPoints((short) 10);            // Font Size
        integerCellStyle.setFont(integerFont);                  // Set style
        integerCellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);     // Right align
        integerCellStyle.setDataFormat(format.getFormat("0"));        // Data Format
        integerCellStyle.setBorderBottom((short) 1);
        integerCellStyle.setBorderLeft((short) 1);
        integerCellStyle.setBorderRight((short) 1);
        integerCellStyle.setBorderTop((short) 1);
        return integerCellStyle;
    }

    public CellStyle getDateCellStyle(HSSFWorkbook workbook) {
        CellStyle dateCellStyleHour = workbook.createCellStyle();
        dateCellStyleHour.setDataFormat(
                workbook.getCreationHelper().createDataFormat().getFormat("dd/mm/yyyy hh:mm"));
        return dateCellStyleHour;
    }

    /**
     *
     * @param workbook
     * @return
     */
    public HSSFCellStyle getHeaderStyle_(HSSFWorkbook workbook) {
        // Create a new font and alter it.
        HSSFCellStyle headCellStyle = workbook.createCellStyle();        // Data Cell Style
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 18);
        font.setColor(HSSFColor.DARK_BLUE.index);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headCellStyle.setFont(font);
        headCellStyle.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
        headCellStyle.setBorderBottom((short) 1);
        return headCellStyle;
    }

    public void openFile(File file) {
        Toolkit.getDefaultToolkit().beep();
        //JOptionPane.showMessageDialog(null, "<html><body>Successfully saved results to:<br><strong>" + getFilename(), "Successfully Saved Results", JOptionPane.INFORMATION_MESSAGE);
        NotifyDescriptor descriptor = new NotifyDescriptor.Message("<html><body>Successfully saved results to:<br><strong>" + getFilename(), NotifyDescriptor.INFORMATION_MESSAGE);
        DialogDisplayer.getDefault().notify(descriptor);
        try {
            Runtime.getRuntime().exec("rundll32 SHELL32.DLL,ShellExec_RunDLL " + file.getAbsolutePath());
        } catch (IOException e) {
        }
    }

    public void saveWorkBook(HSSFWorkbook workbook) throws HeadlessException {
        /**
         * Write workbook to output stream
         */
        try {
            workbook.write(out);
            out.close();

        } catch (IOException e) {

            JOptionPane.showMessageDialog(null, "Could not save file. Please try again.", "Error Saving File",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
