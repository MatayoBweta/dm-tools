/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.tool.dataQuality;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import org.openide.modules.ModuleInstall;
import org.openide.util.NbPreferences;

public class Installer extends ModuleInstall {

    private static final Logger logger = Logger.getLogger(Installer.class.getName());
    Preferences node = NbPreferences.root();
    ObjectContainer oc;

    @Override
    public void restored() {
        String databaseName = node.get("db4o.database.name", "localdb");
        oc = Db4oEmbedded.openFile(databaseName);
        logger.log(Level.INFO, "Open Database {0}", databaseName);
    }

    @Override
    public boolean closing() {
//        oc.close();
        return super.closing(); //To change body of generated methods, choose Tools | Templates.
    }
}
