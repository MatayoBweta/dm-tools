/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.tool.token.printing.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import org.openide.util.Exceptions;
import org.unhcr.eg.registration.security.em.EntityManagerSingleton;
import org.unhcr.eg.registration.tool.token.printing.models.AccessTimeReport;

/**
 *
 * @author UNHCRuser
 */
public class TokenManagerService {

    public static Date getMaxReceptionDate(Date startingDate, Date endDate) throws SQLException {
        Date date = null;
        String sqlQuery = "SELECT MAX(t1.AccesDateTime) AS MaxTime FROM vAttendanceOffice t1 \n"
                + "	WHERE \n"
                + "	DATEADD(d, DATEDIFF(d, 0, t1.AccesDateTime), 0) BETWEEN DATEADD(d, DATEDIFF(d, 0, ?), 0) AND DATEADD(d, DATEDIFF(d, 0, ?), 0)";
        Connection connection = EntityManagerSingleton.getDefault().getConnection();
        PreparedStatement statement = connection.prepareStatement(sqlQuery);
        statement.setDate(1, startingDate);
        statement.setDate(2, endDate);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            date = rs.getDate("MaxTime");
        }
        return date;
    }

    public static Date getMaxReceptionDate(Date currentDate) throws SQLException {
        Date date = null;
        String sqlQuery = "SELECT MAX(t1.AccesDateTime) AS MaxTime FROM vAttendanceOffice t1 \n"
                + "	WHERE \n"
                + "	DATEADD(d, DATEDIFF(d, 0, t1.AccesDateTime), 0) = DATEADD(d, DATEDIFF(d, 0, ?), 0)";
        Connection connection = EntityManagerSingleton.getDefault().getConnection();
        PreparedStatement statement = connection.prepareStatement(sqlQuery);
        statement.setDate(1, currentDate);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            date = rs.getDate("MaxTime");
        }
        return date;
    }

    public static Date getMaxReceptionDate() throws SQLException {
        Date date = null;
        String sqlQuery = "SELECT MAX(t1.AccesDateTime) AS MaxTime FROM vAttendanceOffice t1";
        Connection connection = EntityManagerSingleton.getDefault().getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sqlQuery);
        if (rs.next()) {
            date = rs.getDate("MaxTime");
        }
        return date;
    }

    public static Date getMinReceptionDate(Date startingDate, Date endDate) throws SQLException {
        Date date = null;
        String sqlQuery = "SELECT MIN(t1.AccesDateTime) AS MaxTime FROM vAttendanceOffice t1 \n"
                + "	WHERE \n"
                + "	DATEADD(d, DATEDIFF(d, 0, t1.AccesDateTime), 0) BETWEEN DATEADD(d, DATEDIFF(d, 0, ?), 0) AND DATEADD(d, DATEDIFF(d, 0, ?), 0)";
        Connection connection = EntityManagerSingleton.getDefault().getConnection();
        PreparedStatement statement = connection.prepareStatement(sqlQuery);
        statement.setDate(1, startingDate);
        statement.setDate(2, endDate);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            date = rs.getDate("MaxTime");
        }
        return date;
    }

    public static Date getMinReceptionDate(Date currentDate) throws SQLException {
        Date date = null;
        String sqlQuery = "SELECT MIN(t1.AccesDateTime) AS MaxTime FROM vAttendanceOffice t1 \n"
                + "	WHERE \n"
                + "	DATEADD(d, DATEDIFF(d, 0, t1.AccesDateTime), 0) = DATEADD(d, DATEDIFF(d, 0, ?), 0)";
        Connection connection = EntityManagerSingleton.getDefault().getConnection();
        PreparedStatement statement = connection.prepareStatement(sqlQuery);
        statement.setDate(1, currentDate);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            date = rs.getDate("MaxTime");
        }
        return date;
    }

    public static Date getMinReceptionDate() throws SQLException {
        Date date = null;
        String sqlQuery = "SELECT MIN(t1.AccesDateTime) AS MaxTime FROM vAttendanceOffice t1";
        Connection connection = EntityManagerSingleton.getDefault().getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sqlQuery);
        if (rs.next()) {
            date = rs.getDate("MaxTime");
        }
        return date;
    }

    private static final String RECEPTION_CASE_DATA = "SELECT t1.AccesDateTime, t1.NumberOfCases, SUM(t2.NumberOfCases) as CumulativeCases\n"
            + "FROM  vAttendanceOffice t1\n"
            + "INNER JOIN vAttendanceOffice t2 ON t1.AccesDateTime >= t2.AccesDateTime\n"
            + "	WHERE \n"
            + "	DATEADD(d, DATEDIFF(d, 0, t1.AccesDateTime), 0) BETWEEN DATEADD(d, DATEDIFF(d, 0, ?), 0) AND DATEADD(d, DATEDIFF(d, 0, ?), 0)\n"
            + "GROUP by t1.AccesDateTime, t1.NumberOfCases\n"
            + "ORDER by t1.AccesDateTime\n";

    private static final String RECEPTION_INDIVIDUAL_DATA = "SELECT t1.AccesDateTime, t1.PotentialNumberOfIndividuals, SUM(t2.PotentialNumberOfIndividuals) as CumulativeIndividuals\n"
            + "FROM  vAttendanceOffice t1\n"
            + "INNER JOIN vAttendanceOffice t2 ON t1.AccesDateTime >= t2.AccesDateTime\n"
            + "	WHERE \n"
            + "	DATEADD(d, DATEDIFF(d, 0, t1.AccesDateTime), 0) BETWEEN DATEADD(d, DATEDIFF(d, 0, ?), 0) AND DATEADD(d, DATEDIFF(d, 0, ?), 0)\n"
            + "GROUP by t1.AccesDateTime, t1.PotentialNumberOfIndividuals\n"
            + "ORDER by t1.AccesDateTime";

    public static TreeMap<Date, AccessTimeReport> getCaseAccessTimeReport(Date startingDate, Date endDate) throws SQLException {
        TreeMap<Date, AccessTimeReport> accessTimeReports = new TreeMap<>();
        Connection connection = EntityManagerSingleton.getDefault().getConnection();
        String getCaseData = RECEPTION_CASE_DATA;
        PreparedStatement statement = connection.prepareStatement(getCaseData);
        statement.setDate(1, startingDate);
        statement.setDate(2, endDate);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            Date accesDateTime = rs.getDate("AccesDateTime");
            Integer numberOfCases = rs.getInt("NumberOfCases");
            Integer cumulativeCases = rs.getInt("CumulativeCases");
            accessTimeReports.put(accesDateTime, new AccessTimeReport(accesDateTime, numberOfCases, cumulativeCases));
        }
        return accessTimeReports;
    }

    public static TreeMap<Date, AccessTimeReport> getIndividualAccessTimeReport(Date startingDate, Date endDate) throws SQLException {
        TreeMap<Date, AccessTimeReport> accessTimeReports = new TreeMap<>();
        Connection connection = EntityManagerSingleton.getDefault().getConnection();
        String getCaseData = RECEPTION_INDIVIDUAL_DATA;
        PreparedStatement statement = connection.prepareStatement(getCaseData);
        statement.setDate(1, startingDate);
        statement.setDate(2, endDate);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            Date accesDateTime = rs.getDate("AccesDateTime");
            Integer numberOfCases = rs.getInt("PotentialNumberOfIndividuals");
            Integer cumulativeCases = rs.getInt("CumulativeIndividuals");
            accessTimeReports.put(accesDateTime, new AccessTimeReport(accesDateTime, numberOfCases, cumulativeCases));
        }

        return accessTimeReports;
    }
}
