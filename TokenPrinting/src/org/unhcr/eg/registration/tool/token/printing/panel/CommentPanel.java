/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.tool.token.printing.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 *
 * @author UNHCRuser
 */
public class CommentPanel extends javax.swing.JPanel implements ActionListener {

    private JButton ok;
    private JButton cancel;

    public JButton getOk() {
        return ok;
    }

    public JButton getCancel() {
        return cancel;
    }

    /**
     * Creates new form CommentPanel
     */
    public CommentPanel(String comment, int numberOfIndividual, boolean numberVIsible) {
        initComponents();
        ok = new JButton("Add Details");
        ok.setActionCommand("Ok");
        cancel = new JButton("Cancel");
        cancel.setActionCommand("Cancel");
        commentsTextArea.setText(comment);
        numberOfIndividualsTextField.setText(Integer.toString(numberOfIndividual));
        numberOfIndividualsTextField.setVisible(numberVIsible);
        numberOfIndividualLabel.setVisible(numberVIsible);
    }

    public boolean isValidData() {
        return numberOfIndividualsTextField.getText().matches("\\d+") && commentsTextArea.getText() != null && commentsTextArea.getText().replace(" ", "").length() > 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "Ok":
                if (!numberOfIndividualsTextField.getText().matches("\\d+")) {
                    throw new IllegalArgumentException("Number of individuals should be an integer");
                }
                if (commentsTextArea.getText() == null || commentsTextArea.getText().replace(" ", "").length() == 0) {
                    throw new IllegalArgumentException("Please comment the Request");
                }
                break;
            case "Cancel":
                break;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        numberOfIndividualLabel = new javax.swing.JLabel();
        numberOfIndividualsTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        commentsTextArea = new javax.swing.JTextArea();

        org.openide.awt.Mnemonics.setLocalizedText(numberOfIndividualLabel, org.openide.util.NbBundle.getMessage(CommentPanel.class, "CommentPanel.numberOfIndividualLabel.text")); // NOI18N

        numberOfIndividualsTextField.setText(org.openide.util.NbBundle.getMessage(CommentPanel.class, "CommentPanel.numberOfIndividualsTextField.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(CommentPanel.class, "CommentPanel.jLabel2.text")); // NOI18N

        commentsTextArea.setColumns(20);
        commentsTextArea.setRows(5);
        jScrollPane1.setViewportView(commentsTextArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(numberOfIndividualsTextField)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(numberOfIndividualLabel)
                            .addComponent(jLabel2))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(numberOfIndividualLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(numberOfIndividualsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea commentsTextArea;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel numberOfIndividualLabel;
    private javax.swing.JTextField numberOfIndividualsTextField;
    // End of variables declaration//GEN-END:variables

    public String getNumberOfIndividuals() {
        return numberOfIndividualsTextField.getText();
    }

    public String getComments() {
        return commentsTextArea.getText();
    }
}
