/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.tool.token.printing.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import org.unhcr.eg.registration.security.em.EntityManagerSingleton;
import org.unhcr.eg.registration.tool.token.printing.models.AccessTimeReport;
import org.unhcr.eg.registration.tool.token.printing.models.TokenDetails;

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

    private static final String RECEPTION_CASE_DATA = "{call GetDailyCumulativeVisit(?,?,?)}";
    private static final String GET_NEXT_TOKEN = "{call Get_Token_Details_New(?,?,?,?)}";

    public static TreeMap<Timestamp, List<AccessTimeReport>> getAccessTimeReport(Date startingDate, Date endDate, Date lastUploadDate) throws SQLException {
        TreeMap<Timestamp, List<AccessTimeReport>> accessTimeReports = new TreeMap<>();
        Connection connection = EntityManagerSingleton.getDefault().getConnection();
        String getCaseData = RECEPTION_CASE_DATA;
        CallableStatement statement = connection.prepareCall(getCaseData);
        statement.setDate(1, startingDate);
        statement.setDate(2, endDate);
        statement.setTimestamp(3, new java.sql.Timestamp(lastUploadDate.getTime()));
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            Timestamp accesDateTime = rs.getTimestamp("AccesDateTime");
            Integer number = rs.getInt("TotalNumber");
            Integer cumulativeNumber = rs.getInt("CumulativeDailyTotalNumber");
            String gate = rs.getString("Gate");
            String typeOfNumber = rs.getString("TypeOfNumber");
            if (accessTimeReports.get(accesDateTime) == null) {
                accessTimeReports.put(accesDateTime, new ArrayList<>());
            }
            accessTimeReports.get(accesDateTime).add(new AccessTimeReport(accesDateTime, gate, typeOfNumber, number, cumulativeNumber));
        }
        return accessTimeReports;
    }

    public static TokenDetails addToken(String caseNumber, String reason, String gate, int numberOfIndividuals) throws SQLException {
        System.out.println("Inside the method");
        Connection connection = EntityManagerSingleton.getDefault().getConnection();
        TokenDetails details = new TokenDetails();
        String getNextToken = GET_NEXT_TOKEN;
        CallableStatement statement = connection.prepareCall(getNextToken);
        statement.setString(1, caseNumber);
        statement.setString(2, reason);
        statement.setString(3, gate);
        statement.setInt(4, numberOfIndividuals);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            details.setAccesDateTime(new java.util.Date(rs.getTimestamp("AccesDateTime").getTime()));
            details.setCaseNumber(rs.getString("CaseNumber").toUpperCase());
            details.setConditions(rs.getString("Conditions"));
            details.setFamilySize(rs.getInt("FamilySize"));
            details.setGateName(rs.getString("GateName"));
            details.setIndividualGUID(rs.getString("IndividualGUID"));
            
            details.setIssueToFix(rs.getString("IssueToFix"));
            details.setLocation(rs.getString("Location"));
            details.setTokenDistributedGUID(rs.getString("TokenDistributedGUID"));
            details.setTokenNumber(rs.getInt("TokenNumber"));
            
            details.setTokenStatus(rs.getString("TokenStatus"));
            details.setVisitNumber(rs.getInt("VisitNumber"));
            details.setVisitReason(rs.getString("VisitReason"));
            details.setReasonForVisit(rs.getString("ReasonForVisit"));
        }
        return details;
    }


}
