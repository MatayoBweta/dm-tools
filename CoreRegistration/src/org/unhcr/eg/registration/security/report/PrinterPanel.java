/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.security.report;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.List;
import java.util.prefs.Preferences;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import org.openide.LifecycleManager;
import org.openide.util.NbPreferences;

/**
 *
 * @author UNHCRuser
 */
public class PrinterPanel extends javax.swing.JPanel implements ActionListener {

    private ActionListener actionListener;
    private String firstDefaultPrinter;
    private Preferences node = NbPreferences.root();
    private JButton save;
    private JButton cancel;
    private boolean beginning;

    public JButton getCancel() {
        return cancel;
    }

    public void setCancel(JButton cancel) {
        this.cancel = cancel;
    }

    public JButton getSave() {
        return save;
    }

    public void setSave(JButton save) {
        this.save = save;
    }

    /**
     * Creates new form PrinterPanel
     */
    public PrinterPanel(boolean beginning) {
        firstDefaultPrinter = node.get("default.token.printer", null);
        actionListener = (ActionEvent e) -> {
            JRadioButton button = (JRadioButton) e.getSource();
            node.put("default.token.printer", button.getText());
        };
        initComponents();
        save = new JButton("Save");
        save.setActionCommand("Save");
        this.beginning = beginning;

        cancel = new JButton("Cancel");
        cancel.setActionCommand("Cancel");

        List<String> allPrinters = PrinterManager.getPrinterServiceNameList();
        allPrinters.stream().forEach((String printer) -> {
            JRadioButton button = new JRadioButton(printer);
            defaultButtonGroup.add(button);
            printerPanel.add(button);
            if (firstDefaultPrinter != null && printer.equals(firstDefaultPrinter)) {
                button.setSelected(true);
            }
            button.addActionListener(actionListener);
        });
    }

    protected String getSelectedPrinter() {
        for (Enumeration<AbstractButton> buttons = defaultButtonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return null;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        defaultButtonGroup = new javax.swing.ButtonGroup();
        printerPanel = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        printerPanel.setLayout(new javax.swing.BoxLayout(printerPanel, javax.swing.BoxLayout.PAGE_AXIS));
        add(printerPanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup defaultButtonGroup;
    private javax.swing.JPanel printerPanel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Save":
                break;
            case "Cancel":
                if (firstDefaultPrinter != null) {
                    node.put("default.token.printer", firstDefaultPrinter);
                }
                if (beginning) {
                    System.exit(0);
                }

                break;

        }
    }
}
