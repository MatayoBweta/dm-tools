/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * LoginForm.java
 *
 * Created on 19-May-2010, 13:14:47
 */
package org.unhcr.eg.registration.security.ui;

import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.prefs.Preferences;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.netbeans.validation.api.Problem;
import org.netbeans.validation.api.Severity;
import org.netbeans.validation.api.builtin.Validators;
import org.netbeans.validation.api.ui.ValidationPanel;
import org.openide.DialogDisplayer;
import org.openide.LifecycleManager;
import org.openide.NotifyDescriptor;
import org.openide.util.Exceptions;
import org.openide.util.NbPreferences;
import org.unhcr.eg.registration.security.Crypto;
import org.unhcr.eg.registration.security.DatabaseUtility;

/**
 *
 * @author Matayo Bweta Doudoux Stanyslas
 */
public final class DatabaseCheckForm extends JPanel implements java.awt.event.ActionListener {

    private static DatabaseCheckForm instance;
    private JButton save;
    private JButton cancel;
    private JButton testConnection;
    private static boolean changeDatabase;

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

    public static boolean isChangeDatabase() {
        return changeDatabase;
    }

    public static void setChangeDatabase(boolean changeDatabase) {
        DatabaseCheckForm.changeDatabase = changeDatabase;
    }

    /**
     * Creates new form LoginForm
     */
    private DatabaseCheckForm() {
        super();
        try {
            initComponents();
            save = new JButton("Save");
            save.setActionCommand("Save");
            //         save.addActionListener(this);
            cancel = new JButton("Cancel");
            cancel.setActionCommand("Cancel");
            //           cancel.addActionListener(this);
            testConnection = new JButton("Test");
            testConnection.setActionCommand("Test");
            //           testConnection.addActionListener(this);
            getValue();
            centralPanel.setLayout(CustomMigLayout.PANEL_2COLUMN_LAYOUT);
            buildPanel(centralPanel);
            validationPanel.setInnerComponent(centralPanel);
            validationPanel.getValidationGroup().add(serverHostNameText, Validators.REQUIRE_NON_EMPTY_STRING);
            validationPanel.getValidationGroup().add(serverPortText, Validators.REQUIRE_NON_EMPTY_STRING, Validators.REQUIRE_VALID_NUMBER, Validators.REQUIRE_VALID_INTEGER);
            validationPanel.getValidationGroup().add(serverUserNameText, Validators.REQUIRE_NON_EMPTY_STRING, Validators.NO_WHITESPACE);
            validationPanel.getValidationGroup().add(serverDatabaseNameText, Validators.REQUIRE_NON_EMPTY_STRING, Validators.NO_WHITESPACE);
            validationPanel.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    save.setEnabled(isValidData());
                    testConnection.setEnabled(isValidData());
                }
            });
        } catch (NoSuchProviderException | BadPaddingException | InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | IllegalBlockSizeException ex) {
            Exceptions.printStackTrace(ex);
        } catch (GeneralSecurityException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    public JButton getTestConnection() {
        return testConnection;
    }

    public static synchronized DatabaseCheckForm getDefaultForLogin() throws InterruptedException, InvocationTargetException {
        if (instance == null) {
            if (!SwingUtilities.isEventDispatchThread()) {
                SwingUtilities.invokeAndWait(new Runnable() {
                    @Override
                    public void run() {
                        instance = new DatabaseCheckForm();
                    }
                });
            } else {
                instance = new DatabaseCheckForm();
            }
        }
        changeDatabase = false;
        return instance;
    }

    public static synchronized DatabaseCheckForm getDefaultForModif() throws InterruptedException, InvocationTargetException {
        if (instance == null) {
            if (!SwingUtilities.isEventDispatchThread()) {
                SwingUtilities.invokeAndWait(new Runnable() {
                    @Override
                    public void run() {
                        instance = new DatabaseCheckForm();
                    }
                });
            } else {
                instance = new DatabaseCheckForm();
            }
        }
        changeDatabase = true;
        return instance;
    }

    protected void saveValue() throws GeneralSecurityException {
        Preferences node = NbPreferences.root();
        node.put("database.user", Crypto.encrypt(serverUserNameText.getText()));
        node.put("database.password", Crypto.encrypt(new String(serverPasswordText.getPassword())));
        node.put("database.hostname", serverHostNameText.getText());
        node.put("database.name", serverDatabaseNameText.getText());
        node.putInt("database.port", new Integer(serverPortText.getText()));
    }

    protected void getValue() throws GeneralSecurityException {
        Preferences node = NbPreferences.root();
        serverUserNameText.setText(Crypto.decrypt(node.get("database.user", Crypto.encrypt("proGresDBUser"))));
        serverPasswordText.setText(Crypto.decrypt(node.get("database.password", Crypto.encrypt("sqladmin"))));
        serverHostNameText.setText(node.get("database.hostname", "localhost"));
        serverPortText.setText(Integer.toString(node.getInt("database.port", 1433)));
        serverDatabaseNameText.setText(node.get("database.name", "proGres"));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        serverHostNameLabel = new javax.swing.JLabel();
        serverHostNameText = new javax.swing.JTextField();
        serverPortLabel = new javax.swing.JLabel();
        serverPortText = new javax.swing.JTextField();
        serverUserNameLabel = new javax.swing.JLabel();
        serverUserNameText = new javax.swing.JTextField();
        serverPasswordLabel = new javax.swing.JLabel();
        serverPasswordText = new javax.swing.JPasswordField();
        centralPanel = new javax.swing.JPanel();
        serverDatabaseNameLabel = new javax.swing.JLabel();
        serverDatabaseNameText = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        entryMessage = new javax.swing.JLabel();
        iconLabel = new javax.swing.JLabel();
        validationPanel = new org.netbeans.validation.api.ui.ValidationPanel();

        serverHostNameLabel.setText(org.openide.util.NbBundle.getMessage(DatabaseCheckForm.class, "DatabaseCheckForm.serverHostNameLabel.text")); // NOI18N

        serverPortLabel.setText(org.openide.util.NbBundle.getMessage(DatabaseCheckForm.class, "DatabaseCheckForm.serverPortLabel.text")); // NOI18N

        serverUserNameLabel.setText(org.openide.util.NbBundle.getMessage(DatabaseCheckForm.class, "DatabaseCheckForm.serverUserNameLabel.text")); // NOI18N

        serverPasswordLabel.setText(org.openide.util.NbBundle.getMessage(DatabaseCheckForm.class, "DatabaseCheckForm.serverPasswordLabel.text")); // NOI18N

        centralPanel.setLayout(null);

        serverDatabaseNameLabel.setText(org.openide.util.NbBundle.getMessage(DatabaseCheckForm.class, "DatabaseCheckForm.serverDatabaseNameLabel.text")); // NOI18N

        setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.BorderLayout());

        entryMessage.setFont(new java.awt.Font("Trebuchet MS", 3, 14)); // NOI18N
        entryMessage.setForeground(new java.awt.Color(204, 51, 0));
        entryMessage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        entryMessage.setText(org.openide.util.NbBundle.getMessage(DatabaseCheckForm.class, "DatabaseCheckForm.entryMessage.text_1")); // NOI18N
        entryMessage.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(entryMessage, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.NORTH);

        iconLabel.setFont(new java.awt.Font("Viner Hand ITC", 1, 14)); // NOI18N
        iconLabel.setForeground(new java.awt.Color(0, 0, 255));
        iconLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/unhcr/eg/registration/security/ui/db.png"))); // NOI18N
        iconLabel.setText(org.openide.util.NbBundle.getMessage(DatabaseCheckForm.class, "DatabaseCheckForm.iconLabel.text_1")); // NOI18N
        iconLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        iconLabel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        add(iconLabel, java.awt.BorderLayout.WEST);
        add(validationPanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel centralPanel;
    private javax.swing.JLabel entryMessage;
    private javax.swing.JLabel iconLabel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel serverDatabaseNameLabel;
    private javax.swing.JTextField serverDatabaseNameText;
    private javax.swing.JLabel serverHostNameLabel;
    private javax.swing.JTextField serverHostNameText;
    private javax.swing.JLabel serverPasswordLabel;
    private javax.swing.JPasswordField serverPasswordText;
    private javax.swing.JLabel serverPortLabel;
    private javax.swing.JTextField serverPortText;
    private javax.swing.JLabel serverUserNameLabel;
    private javax.swing.JTextField serverUserNameText;
    private org.netbeans.validation.api.ui.ValidationPanel validationPanel;
    // End of variables declaration//GEN-END:variables

    private void buildPanel(JPanel panel) {
        panel.add(serverHostNameLabel, "skip");
        panel.add(serverHostNameText, "growx,span,wrap");
        panel.add(serverPortLabel, "skip");
        panel.add(serverPortText, "growx,span,wrap");
        panel.add(serverUserNameLabel, "skip");
        panel.add(serverUserNameText, "growx,span,wrap");
        panel.add(serverPasswordLabel, "skip");
        panel.add(serverPasswordText, "growx,span,wrap");
        panel.add(serverDatabaseNameLabel, "skip");
        panel.add(serverDatabaseNameText, "growx,span,wrap");
    }

    public ValidationPanel getValidationPanel() {
        return validationPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Save":
                if (isValidData() && DatabaseUtility.pingDatabase(DatabaseUtility.getMSSQLDatabaseURL(getHostName(), getPort(), getDatabaseName()), getUserID(), new String(serverPasswordText.getPassword()))) {
                    try {
                        saveValue();
                        if (changeDatabase) {
                            LifecycleManager.getDefault().markForRestart();
                            LifecycleManager.getDefault().exit();
                        }
                    } catch (GeneralSecurityException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                } else {
                    try {
                        throw new Exception("Connection failed");
                    } catch (Exception ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
                break;
            case "Cancel":
                if (!changeDatabase) {
                    System.exit(0);
                } else {
                }
                break;
            case "Test":
                if (DatabaseUtility.pingDatabase(DatabaseUtility.getMSSQLDatabaseURL(getHostName(), getPort(), getDatabaseName()), getUserID(), new String(serverPasswordText.getPassword()))) {
                    NotifyDescriptor nd = new NotifyDescriptor.Message("Successfully Access to Database");
                    DialogDisplayer.getDefault().notify(nd);
                } else {
                    try {
                        throw new Exception("Connection failed");
                    } catch (Exception ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
                break;
        }
    }

    public boolean isValidData() {
        Problem p = validationPanel.getProblem();
        boolean enable = p == null ? true : p.severity() != Severity.FATAL;
        return enable;
    }

    private String getHostName() {
        return serverHostNameText.getText();
    }

    private String getUserID() {
        return serverUserNameText.getText();
    }

    private String getPort() {
        return serverPortText.getText();
    }

    private String getDatabaseName() {
        return serverDatabaseNameText.getText();
    }
}
