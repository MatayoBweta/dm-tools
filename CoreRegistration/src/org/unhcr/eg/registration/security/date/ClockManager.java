/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.security.date;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Calendar;
import java.util.Date;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.persistence.EntityManager;
import org.openide.util.Exceptions;
import org.unhcr.eg.registration.security.em.EntityManagerSingleton;

/**
 *
 * @author Matayo Bweta Doudoux Stanyslas
 */
public class ClockManager {

    public final static Date INITIAL_DATABASE_DATE = getNowDate();
    public final static Date INITIAL_LOCAL_DATE = new Date();
    private static ClockManager instance;
    public final static long MILLISECONDS_PER_DAY = 1000 * 60 * 60 * 24;
    public final static long MILLISECONDS_PER_WEEK = MILLISECONDS_PER_DAY * 7;

    public static int getNumberOfYear(Calendar c2, Calendar c1) {
        int numberOfYear = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
        if (c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH) < 0 && numberOfYear > 0) {
            numberOfYear -= 1;
        }
        return numberOfYear;
    }

    public static int getNumberOfDays(Calendar c1, Calendar c2) {
        int numberOfDays;
        numberOfDays = (int) ((c1.getTime().getTime() - c2.getTime().getTime()) / MILLISECONDS_PER_DAY);
        return numberOfDays;
    }

    public static java.sql.Date getSQLDate(Date date) {
        java.sql.Date value = new java.sql.Date(date.getTime());
        return value;
    }

    public static Date getUtilDate(java.sql.Date date) {
        Date value = new Date(date.getTime());
        return value;
    }

    public static Calendar DateToCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public static double getNumberOfMonth(Calendar date1, Calendar date2) {
        double monthsBetween = 0;
        //difference in month for years
        monthsBetween = (date1.get(Calendar.YEAR) - date2.get(Calendar.YEAR)) * 12;
        //difference in month for months
        monthsBetween += date1.get(Calendar.MONTH) - date2.get(Calendar.MONTH);
        //difference in month for days
        if (date1.get(Calendar.DAY_OF_MONTH) != date1.getActualMaximum(Calendar.DAY_OF_MONTH)
                && date1.get(Calendar.DAY_OF_MONTH) != date1.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            monthsBetween += ((date1.get(Calendar.DAY_OF_MONTH) - date2.get(Calendar.DAY_OF_MONTH)) / 31d);
        }
        return monthsBetween;
    }

    private ClockManager() {
    }

    public static synchronized ClockManager getInstance() {
        if (instance == null) {
            instance = new ClockManager();
        }
        return instance;
    }

    private static EntityManager getEntityManager() {
        EntityManager entityManager = null;
        try {
            entityManager = EntityManagerSingleton.getDefault().getEntityManagerFactory().createEntityManager();
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | BadPaddingException | InvalidAlgorithmParameterException | IllegalBlockSizeException | NoSuchProviderException | InvalidKeyException ex) {
            Exceptions.printStackTrace(ex);
        }
        return entityManager;

    }

    public static int getElapsedTime(Date laterDate, Date earlierDate) {
        long result = ((laterDate.getTime() / 60000) - (earlierDate.getTime() / 60000));
        return (int) result;
    }

    private static Date getNowDate() {
        EntityManager em = getEntityManager();
        Date date = (Date) em.createNativeQuery("SELECT GETDATE()").getSingleResult();
        return date;
    }

    public Date now() {
        return getNowDate();
    }

    public static Date getDateOnly(Date date) {
        if (date == null) {
            return null;
        }
// Get Calendar object set to the date and time of the given Date object
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

// Set time fields to zero
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

// Put it back in the Date object
        date = cal.getTime();
        return date;
    }

    public static Date getDateAndHourOnly(Date date) {
        if (date == null) {
            return null;
        }
// Get Calendar object set to the date and time of the given Date object
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

// Set time fields to zero
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

// Put it back in the Date object
        date = cal.getTime();
        return date;
    }

}
