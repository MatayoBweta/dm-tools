/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.tool.token.printing.service;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Set;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.ImageIcon;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import org.openide.util.Exceptions;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import org.unhcr.eg.registration.security.action.LongTaskBackgroundAction;
import org.unhcr.eg.registration.security.em.EntityManagerSingleton;
import org.unhcr.eg.registration.security.report.ReportViewer;

/**
 *
 * @author UNHCRuser
 */
public class PrinterManager {

    protected HashMap params;
    private InputStream is;
    private String reportLocation;
    private JasperPrint jasperPrint = null;

    public PrinterManager(File reportFile, HashMap params) {
        this.reportLocation = reportFile.getAbsolutePath();
        this.params = params;
    }

    public PrinterManager(InputStream is, HashMap params) {
        this.is = is;
        this.params = params;
    }

    public PrinterManager(String reportLocation, HashMap params) {
        this.params = params;
        this.reportLocation = reportLocation;
    }

    public void previewPrinter(String actionName, final String documentName) {

        LongTaskBackgroundAction action;
        action = new LongTaskBackgroundAction(actionName) {
            @Override
            protected void mainAction() {
                try {
                    Connection connection = EntityManagerSingleton.getDefault().getConnection();
                    JasperPrint jasperPrint = null;
                    if (is != null) {
                        jasperPrint
                                = JasperFillManager.fillReport(
                                        is, params, connection);
                    } else if (reportLocation != null) {
                        jasperPrint
                                = JasperFillManager.fillReport(
                                        new FileInputStream(new File(reportLocation)), params, connection);
                    }
                    final net.sf.jasperreports.view.JRViewer rViewer = new net.sf.jasperreports.view.JRViewer(jasperPrint);

                    //             rViewer.setSaveContributors(null);
                    WindowManager.getDefault().invokeWhenUIReady(new Runnable() {

                        @Override
                        public void run() {
                            boolean exist = false;
                            Set<TopComponent> openTopComponents = WindowManager.getDefault().getRegistry().getOpened();
                            for (TopComponent topComponent : openTopComponents) {
                                if (exist = (topComponent != null && topComponent.getDisplayName() != null && topComponent.getDisplayName().equalsIgnoreCase(documentName))) {
                                    ((ReportViewer) topComponent).setReportViewer(rViewer);
                                    topComponent.requestActive();
                                }
                            }
                            if (!exist) {
                                ReportViewer win = new ReportViewer();
                                win.setReportViewer(rViewer);
                                win.setDisplayName(documentName);
                                win.open();
                                win.requestActive();
                            }
                        }
                    });

                } catch (JRException | FileNotFoundException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        };
        action.actionPerformed(null);
    }

    public String print() throws JRException, FileNotFoundException {
        Connection connection = EntityManagerSingleton.getDefault().getConnectionForproGres();

        if (is != null) {
            jasperPrint = JasperFillManager.fillReport(is, params, connection);
        } else if (reportLocation != null) {
            jasperPrint = JasperFillManager.fillReport(new FileInputStream(new File(reportLocation)), params, connection);
        }

        PrinterJob printerJob = PrinterJob.getPrinterJob();
        PrintService selectedService = PrintServiceLookup.lookupDefaultPrintService();
        if (selectedService != null) {
            try {
                printerJob.setPrintService(selectedService);
                
                boolean printSucceed = JasperPrintManager.printReport(jasperPrint, false);
                if (printSucceed) {
                    System.out.println("Successfullly Printed");
                    return "Successfullly Printed";
                }
            } catch (PrinterException | JRException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        return null;
    }

    public HashMap getParams() {
        return params;
    }

    public void setParameter(String paramName, Object value) {
        params.put(paramName, value);
    }

    public void setImageParameter(String paramName, String imageLocation, ClassLoader loader) {
        URL isPic = loader.getResource(imageLocation);
        params.put(paramName, new ImageIcon(isPic).getImage());
    }

    public void setImageParameter(String paramName, byte[] imageBytes) {
        params.put(paramName, new ImageIcon(imageBytes).getImage());
    }

}
