/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.security.em;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.Connection;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.persistence.EntityManagerFactory;
import org.openide.util.Lookup;

/**
 *
 * @author Stanyslas Matayo Bweta
 */
public abstract class EntityManagerSingleton {

    public static EntityManagerSingleton getDefault() {
        EntityManagerSingleton entityManager = Lookup.getDefault().lookup(EntityManagerSingleton.class);
        if (entityManager == null) {
            entityManager = new DefaultEntityManagerSingleton();
        }
        return (entityManager);
    }

    public abstract EntityManagerFactory getEntityManagerFactory() throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchProviderException, InvalidKeyException;

    public abstract EntityManagerFactory getEntityManagerFactoryForproGres() throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchProviderException, InvalidKeyException;

    public abstract EntityManagerFactory getEntityManagerFactoryForScheduler() throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchProviderException, InvalidKeyException;

    public abstract Connection getConnection();

    public abstract Connection getConnectionForproGres();
    
   public abstract Connection getConnectionForScheduler();
}
