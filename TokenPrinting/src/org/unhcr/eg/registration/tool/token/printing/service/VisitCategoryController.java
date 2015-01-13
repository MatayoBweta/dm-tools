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
import org.openide.util.Exceptions;
import org.unhcr.eg.registration.security.em.EntityManagerSingleton;
import org.unhcr.eg.registration.tool.token.printing.models.Gate;
import org.unhcr.eg.registration.tool.token.printing.models.VisitCategory;
import org.unhcr.eg.registration.tool.token.printing.models.VisitReason;

/**
 *
 * @author UNHCRuser
 */
public class VisitCategoryController {

    public static List<VisitCategory> getSectionList() {
        ArrayList<VisitCategory> categorys = new ArrayList<>();

        try {
            Connection connection = EntityManagerSingleton.getDefault().getConnection();
            String getCategory = "SELECT CategoryID, Category FROM tmp_VistCategory WHERE Active = 1";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(getCategory);
            while (rs.next()) {
                String categoryID = rs.getString("CategoryID");
                String category = rs.getString("Category");
                categorys.add(new VisitCategory(categoryID, category, true));
            }

        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        return categorys;
    }

    public static List<Gate> getGateList() {
        ArrayList<Gate> gates = new ArrayList<>();

        try {
            Connection connection = EntityManagerSingleton.getDefault().getConnection();

            String getGates = "SELECT GateName, GateDescription FROM tmp_TokenGate WHERE Active = 1";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(getGates);
            while (rs.next()) {
                String gateName = rs.getString("GateName");
                String gateDescription = rs.getString("GateDescription");
                gates.add(new Gate(gateName, gateDescription));
            }

        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        return gates;
    }

    public static List<VisitReason> getReasonList(String category) {
        ArrayList<VisitReason> vistiReason = new ArrayList<>();

        try {
            Connection connection = EntityManagerSingleton.getDefault().getConnection();
            String getCategory = "SELECT ItemCodeID, Item, Conditions FROM tmp_VisitReason WHERE (Active = 1) AND (Category = ?)";
            PreparedStatement statement = connection.prepareStatement(getCategory);
            statement.setString(1, category);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String itemCodeID = rs.getString("ItemCodeID");
                String item = rs.getString("Item");
                vistiReason.add(new VisitReason(itemCodeID, item, true, category));
            }

        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        return vistiReason;
    }

    public static boolean checkCaseNumber(String caseNumber) {
        try {
            Connection connection = EntityManagerSingleton.getDefault().getConnection();
            String getCategory = "SELECT DISTINCT ProcessingGroupNumber FROM dbo.dataProcessGroup WHERE ProcessingGroupNumber = ? ";
            PreparedStatement statement = connection.prepareStatement(getCategory);
            statement.setString(1, caseNumber);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

        return false;
    }

    public static boolean checkDuplicateToken(String visitReason, String caseNumber, String gate) {
        try {
            Connection connection = EntityManagerSingleton.getDefault().getConnection();
            String getCategory = "SELECT VisitReason, CaseNumber FROM tmp_TokenReport WHERE VisitReason = ? AND CaseNumber = ? AND GateName = ? AND DATEADD(d, DATEDIFF(d, 0, AccesDateTime), 0) = DATEADD(d, DATEDIFF(d, 0, GEDATE()), 0)";
            PreparedStatement statement = connection.prepareStatement(getCategory);
            statement.setString(1, visitReason);
            statement.setString(2, caseNumber);
            statement.setString(3, gate);

            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

        return false;
    }

    public static boolean checkDuplicateToken(String visitReason, String caseNumber, String gate, Date date) {
        try {
            Connection connection = EntityManagerSingleton.getDefault().getConnection();
            String getCategory = "SELECT VisitReason, CaseNumber FROM tmp_TokenReport WHERE VisitReason = ? AND CaseNumber = ? AND GateName = ? AND DATEADD(d, DATEDIFF(d, 0, AccesDateTime), 0) = DATEADD(d, DATEDIFF(d, 0, ?), 0)";
            PreparedStatement statement = connection.prepareStatement(getCategory);
            statement.setString(1, visitReason);
            statement.setString(2, caseNumber);
            statement.setString(3, gate);
            statement.setDate(4, date);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

        return false;
    }

}
