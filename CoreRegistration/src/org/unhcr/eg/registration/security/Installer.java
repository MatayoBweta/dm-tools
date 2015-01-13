/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.security;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.jdesktop.swingx.JXBusyLabel;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.cookies.InstanceCookie;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.modules.ModuleInstall;
import org.openide.util.Exceptions;
import org.openide.util.NbPreferences;
import org.openide.windows.WindowManager;
import org.unhcr.eg.registration.security.ui.DatabaseCheckForm;
import org.unhcr.eg.registration.security.ui.DatabaseCheckFormproGres;

public class Installer extends ModuleInstall {

    private static List<Runnable> runOnClose = new ArrayList<>();
    private static final Logger logger = Logger.getLogger(Installer.class.getName());

    @Override
    public void restored() {

        System.setProperty("netbeans.buildnumber", "0.1 Beta");
        //To remove the annoying error alert on the right-corner
        //System.setProperty("netbeans.exception.alert.min.level", "99999");
        //To remove the annoying error dialog box
        //System.setProperty("netbeans.exception.report.min.level", "99999");

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = (JFrame) WindowManager.getDefault().getMainWindow();
                frame.setTitle("Data  Management Tools");
                frame.setGlassPane(new JXBusyLabel());
            }
        });

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }
        try {
            showDBForm();
            showDBFormproGres();
        } catch (IllegalBlockSizeException ex) {
            Exceptions.printStackTrace(ex);
        } catch (NoSuchPaddingException ex) {
            Exceptions.printStackTrace(ex);
        } catch (NoSuchAlgorithmException ex) {
            Exceptions.printStackTrace(ex);
        } catch (InvalidAlgorithmParameterException ex) {
            Exceptions.printStackTrace(ex);
        } catch (BadPaddingException ex) {
            Exceptions.printStackTrace(ex);
        } catch (InvalidKeyException ex) {
            Exceptions.printStackTrace(ex);
        } catch (NoSuchProviderException ex) {
            Exceptions.printStackTrace(ex);
        } catch (GeneralSecurityException ex) {
            Exceptions.printStackTrace(ex);
        }
        FileObject fileObject = FileUtil.getConfigFile("Actions");
        //FileObject fileObject1 = FileUtil.getConfigFile("Window");
        printAction(fileObject);

    }

    protected void printAction(FileObject fileObject) {
        if (fileObject.isFolder()) {
            FileObject[] fileObjects = fileObject.getChildren();
            for (FileObject fileObject1 : fileObjects) {
                if (fileObject1.isFolder()) {
                    printAction(fileObject1);
                } else {
                    printAttribute(fileObject1, "org-unhcr-level1");
                }
            }
        } else {
            printAttribute(fileObject, "org-unhcr-level1");
        }
    }

    protected void printAttribute(FileObject fileObject, String nameRegex) {
        if (fileObject.getPath().contains(nameRegex)) {
            try {
                System.out.println("---------------------------------------------------------------------------------- ");
                System.out.println("Action Name " + fileObject.getName());
                System.out.println("Action Path " + fileObject.getPath());
                DataObject d = DataObject.find(fileObject);
                InstanceCookie c = d.getLookup().lookup(InstanceCookie.class);
                c.instanceClass();
                System.out.println("Action Class " + c.instanceClass().getName());
                Enumeration<String> enumeration = fileObject.getAttributes();
                while (enumeration.hasMoreElements()) {
                    String string = enumeration.nextElement();
                    System.out.println("------ " + string + "  ");
                }

            } catch (IOException | ClassNotFoundException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public boolean closing() {
        if (!DatabaseCheckForm.isChangeDatabase() && !DatabaseCheckFormproGres.isChangeDatabase()) {
            NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation("Are you sure, you want exit Data Management Tool Application", "Exit Data Management Tool", NotifyDescriptor.YES_NO_OPTION);
            Object retVal = DialogDisplayer.getDefault().notify(descriptor);
            return retVal.equals(NotifyDescriptor.OK_OPTION);
        } else if (DatabaseCheckForm.isChangeDatabase()) {
            NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation("Restart Data Management Tool Application for to change Database", "Exit Data Management Tool", NotifyDescriptor.YES_NO_OPTION);
            Object retVal = DialogDisplayer.getDefault().notify(descriptor);
            boolean b = retVal.equals(NotifyDescriptor.OK_OPTION);
            if (b) {
                DatabaseCheckForm.setChangeDatabase(false);
            }
            return b;
        }
        return true;
    }

    @Override
    public void close() {
        super.close();
        for (Runnable onClose : runOnClose) {
            onClose.run();
        }

    }

    public static void runOnClose(Runnable run) {
        runOnClose.add(run);
    }

    public static void showDBForm() throws IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, InvalidKeyException, NoSuchProviderException, GeneralSecurityException {
        DatabaseCheckForm formDB = null;
        Preferences node = NbPreferences.root();
        String hostName = node.get("database.hostname", "localhost");
        String port = new Integer(node.getInt("database.port", 1433)).toString();
        String database = node.get("database.name", "proGres");
        String url = DatabaseUtility.getMSSQLDatabaseURL(hostName, port, database);
        String userID = Crypto.decrypt(node.get("database.user", Crypto.encrypt("proGresDBUser")));
        String password = Crypto.decrypt(node.get("database.password", Crypto.encrypt("sqladmin")));

        if (!DatabaseUtility.pingDatabase(url, userID, password)) {
            try {
                formDB = DatabaseCheckForm.getDefaultForLogin();
            } catch (InterruptedException | InvocationTargetException ex1) {
                logger.log(Level.SEVERE, null, ex1);
            }
            DialogDescriptor nd = DialogUtility.createDialogDescriptor(formDB, "Database Configuration", true, new Object[]{formDB.getSave(), formDB.getTestConnection(), formDB.getCancel()}, new Object[]{formDB.getSave(), formDB.getCancel()}, formDB.getSave(), formDB);
            DialogDisplayer.getDefault().notify(nd);
        }
    }

    public static void showDBFormproGres() throws IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, InvalidKeyException, NoSuchProviderException, GeneralSecurityException {
        DatabaseCheckFormproGres formDB = null;
        Preferences node = NbPreferences.root();
        String hostName = node.get("database.hostname.proGres", "localhost");
        String port = new Integer(node.getInt("database.port.proGres", 1433)).toString();
        String database = node.get("database.name.proGres", "proGres");
        String url = DatabaseUtility.getMSSQLDatabaseURL(hostName, port, database);
        String userID = Crypto.decrypt(node.get("database.user.proGres", Crypto.encrypt("proGresDBUser")));
        String password = Crypto.decrypt(node.get("database.password.proGres", Crypto.encrypt("sqladmin")));

        if (!DatabaseUtility.checkProGresDataabse(url, userID, password)) {
            try {
                formDB = DatabaseCheckFormproGres.getDefaultForLogin();
            } catch (InterruptedException | InvocationTargetException ex1) {
                logger.log(Level.SEVERE, null, ex1);
            }
            DialogDescriptor nd = DialogUtility.createDialogDescriptor(formDB, "proGres Database Configuration", true, new Object[]{formDB.getSave(), formDB.getTestConnection(), formDB.getCancel()}, new Object[]{formDB.getSave(), formDB.getCancel()}, formDB.getSave(), formDB);
            DialogDisplayer.getDefault().notify(nd);
        }
    }

    public static void showGlassPane() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = (JFrame) WindowManager.getDefault().getMainWindow();
                frame.getGlassPane().setVisible(true);
            }
        });

    }

    public static void hideGlassPane() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = (JFrame) WindowManager.getDefault().getMainWindow();
                frame.getGlassPane().setVisible(false);
            }
        });

    }
}
