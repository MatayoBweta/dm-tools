/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.security.filechooser.filter;

import java.io.File;

/**
 *
 * @author UNHCRUser
 */
public class ExcelFilter extends javax.swing.filechooser.FileFilter {

    @Override
    public boolean accept(File file) {
        String filename = file.getName();
        return filename.endsWith(".xls") || filename.endsWith(".xlsx");
    }

    @Override
    public String getDescription() {
        return "Excel";
    }
}