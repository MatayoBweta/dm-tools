/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.tool.token.printing.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Stanyslas Matayo
 * The panel report contains information about activities
 */
public class PanelReport extends javax.swing.JPanel {

    private final String FONT_LOCATION = "org/unhcr/eg/registration/tool/token/printing/panel/OpenSans-Light.ttf";

    public String getTitle() {
        return titleLabel.getText();
    }

    public final void setTitle(String title) {
        titleLabel.setText(title);
    }

    public String getValue() {
        return detailsLabel.getText();
    }

    public void setValue(String value) {
        detailsLabel.setText(value);
    }

    public String getEvolution() {
        return variationLabel.getText();
    }

    public void setEvolution(String evolution) {
        variationLabel.setText(evolution);
    }

    /**
     * Creates new form PanelReport
     */
    public PanelReport(String title, Color backgroungColor, Color textColor) throws FontFormatException, IOException {
        initComponents();
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(FONT_LOCATION);
        Font font = Font.createFont(Font.TRUETYPE_FONT, is);
        setTitle(title);
        Font titleFont = font.deriveFont(12f);

        titleLabel.setFont(titleFont);
        titleLabel.setForeground(textColor);
        titlePanel.setBackground(backgroungColor.brighter());

        Font detailsFont = font.deriveFont(24f).deriveFont(Font.BOLD);
        detailsLabel.setFont(detailsFont);
        detailsLabel.setForeground(textColor);
        detailsPanel.setBackground(backgroungColor.darker());

        Font variationFont = font.deriveFont(12f);
        variationLabel.setFont(variationFont);
        variationLabel.setForeground(textColor);
        variationPanel.setBackground(backgroungColor.darker());

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        reportPanel = new javax.swing.JPanel();
        titlePanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        detailsPanel = new javax.swing.JPanel();
        detailsLabel = new javax.swing.JLabel();
        iconLabel = new javax.swing.JLabel();
        variationPanel = new javax.swing.JPanel();
        variationLabel = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        reportPanel.setLayout(new java.awt.BorderLayout());

        titlePanel.setLayout(new java.awt.BorderLayout());

        org.openide.awt.Mnemonics.setLocalizedText(titleLabel, org.openide.util.NbBundle.getMessage(PanelReport.class, "PanelReport.titleLabel.text")); // NOI18N
        titlePanel.add(titleLabel, java.awt.BorderLayout.CENTER);

        reportPanel.add(titlePanel, java.awt.BorderLayout.NORTH);

        detailsPanel.setLayout(new java.awt.BorderLayout());

        org.openide.awt.Mnemonics.setLocalizedText(detailsLabel, org.openide.util.NbBundle.getMessage(PanelReport.class, "PanelReport.detailsLabel.text")); // NOI18N
        detailsLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        detailsPanel.add(detailsLabel, java.awt.BorderLayout.CENTER);

        org.openide.awt.Mnemonics.setLocalizedText(iconLabel, org.openide.util.NbBundle.getMessage(PanelReport.class, "PanelReport.iconLabel.text")); // NOI18N
        detailsPanel.add(iconLabel, java.awt.BorderLayout.EAST);

        reportPanel.add(detailsPanel, java.awt.BorderLayout.CENTER);

        add(reportPanel, java.awt.BorderLayout.CENTER);

        variationPanel.setLayout(new java.awt.BorderLayout());

        org.openide.awt.Mnemonics.setLocalizedText(variationLabel, org.openide.util.NbBundle.getMessage(PanelReport.class, "PanelReport.variationLabel.text")); // NOI18N
        variationPanel.add(variationLabel, java.awt.BorderLayout.CENTER);

        add(variationPanel, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel detailsLabel;
    private javax.swing.JPanel detailsPanel;
    private javax.swing.JLabel iconLabel;
    private javax.swing.JPanel reportPanel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JPanel titlePanel;
    private javax.swing.JLabel variationLabel;
    private javax.swing.JPanel variationPanel;
    // End of variables declaration//GEN-END:variables
}
