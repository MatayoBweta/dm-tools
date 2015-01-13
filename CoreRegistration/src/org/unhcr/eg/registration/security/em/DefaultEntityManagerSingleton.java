/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.security.em;

import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.openide.util.Exceptions;
import org.openide.util.NbPreferences;
import org.unhcr.eg.registration.security.Crypto;
import org.unhcr.eg.registration.security.DatabaseUtility;

/**
 *
 * @author Stanyslas Matayo Bweta
 */
public class DefaultEntityManagerSingleton extends EntityManagerSingleton {

    private static EntityManagerFactory entityManagerFactory = null;
    private static EntityManagerFactory entityManagerFactoryForproGres = null;
    private static EntityManager em = null;

    public DefaultEntityManagerSingleton() throws ExceptionInInitializerError {
    }

    @Override
    public EntityManagerFactory getEntityManagerFactory() {
        if (entityManagerFactory == null) {
            try {
                Preferences node = NbPreferences.root();
                Map props = new HashMap();
                props.put("javax.persistence.jdbc.url", DatabaseUtility.getMSSQLDatabaseURL(node.get("database.hostname", "localhost"), Integer.toString(node.getInt("database.port", 1433)).toString(), node.get("database.name", "Staff")));
                props.put("javax.persistence.jdbc.user", Crypto.decrypt(node.get("database.user", Crypto.encrypt("Level1DBUser"))));
                props.put("javax.persistence.jdbc.password", Crypto.decrypt(node.get("database.password", Crypto.encrypt("sqladmin"))));
                entityManagerFactory = Persistence.createEntityManagerFactory("LocalPU", props);
            } catch (GeneralSecurityException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        return entityManagerFactory;
    }

    @Override
    public EntityManagerFactory getEntityManagerFactoryForproGres() throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchProviderException, InvalidKeyException {
        if (entityManagerFactoryForproGres == null) {
            try {
                Preferences node = NbPreferences.root();
                Map props = new HashMap();
                props.put("javax.persistence.jdbc.url", DatabaseUtility.getMSSQLDatabaseURL(node.get("database.hostname.proGres", "localhost"), Integer.toString(node.getInt("database.port.proGres", 1433)).toString(), node.get("database.name.proGres", "proGres")));
                props.put("javax.persistence.jdbc.user", Crypto.decrypt(node.get("database.user.proGres", Crypto.encrypt("proGres"))));
                props.put("javax.persistence.jdbc.password", Crypto.decrypt(node.get("database.password.proGres", Crypto.encrypt("sqladmin"))));
                entityManagerFactoryForproGres = Persistence.createEntityManagerFactory("proGresPU", props);
            } catch (GeneralSecurityException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        return entityManagerFactoryForproGres;
    }

    @Override
    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            Preferences node = NbPreferences.root();
            String hostName = node.get("database.hostname", "localhost");
            String port = new Integer(node.getInt("database.port", 1433)).toString();
            String database = node.get("database.name", "proGres");
            String url = DatabaseUtility.getMSSQLDatabaseURL(hostName, port, database);
            String userID = Crypto.decrypt(node.get("database.user", Crypto.encrypt("proGresDBUser")));
            String password = Crypto.decrypt(node.get("database.password", Crypto.encrypt("sqladmin")));
            connection = DriverManager.getConnection(url, userID, password);
        } catch (GeneralSecurityException | SQLException | ClassNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }
        return connection;
    }

    @Override
    public Connection getConnectionForproGres() {
        Connection connection = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            Preferences node = NbPreferences.root();
            String hostName = node.get("database.hostname.proGres", "localhost");
            String port = new Integer(node.getInt("database.port.proGres", 1433)).toString();
            String database = node.get("database.name.proGres", "proGres");
            String url = DatabaseUtility.getMSSQLDatabaseURL(hostName, port, database);
            String userID = Crypto.decrypt(node.get("database.user.proGres", Crypto.encrypt("proGresDBUser")));
            String password = Crypto.decrypt(node.get("database.password.proGres", Crypto.encrypt("sqladmin")));
            connection = DriverManager.getConnection(url, userID, password);
        } catch (GeneralSecurityException | SQLException ex) {
            Exceptions.printStackTrace(ex);
        } catch (ClassNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }
        return connection;
    }
}
