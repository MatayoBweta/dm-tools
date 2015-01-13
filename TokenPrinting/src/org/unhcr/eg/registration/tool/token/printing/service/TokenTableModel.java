/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.tool.token.printing.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import org.unhcr.eg.registration.security.jdbc.JDBCTableModel;

/**
 *
 * @author UNHCRuser
 */
public class TokenTableModel extends JDBCTableModel {

    public static final String GET_ALL_TOKEN = "SELECT dbo.tmp_TokenReport.TokenDistributedGUID, dbo.tmp_TokenReport.TokenNumber AS [Token Number], dbo.tmp_VistCategory.Category AS [Category], dbo.tmp_VisitReason.Item AS [Reason Of Visit], \n"
            + "                      dbo.tmp_TokenReport.VisitNumber AS [Visit Number], dbo.tmp_TokenReport.CaseNumber AS [Case Number], dbo.tmp_TokenReport.FamilySize AS [Family Size], dbo.tmp_TokenReport.Location, \n"
            + "                      dbo.tmp_TokenReport.AccesDateTime AS [Acces Date Time], DATEADD(d, DATEDIFF(d, 0, dbo.tmp_TokenReport.AccesDateTime), 0) AS [Access Date], \n"
            + "                      dbo.tmp_TokenReport.TokenStatus, dbo.tmp_VisitReason.ItemCodeID AS [Visit Reason Code], dbo.tmp_VistCategory.CategoryID AS [Visit Category Code], dbo.tmp_TokenReport.GateName AS [Gate Name]\n"
            + "FROM         dbo.tmp_VistCategory RIGHT OUTER JOIN\n"
            + "                      dbo.tmp_VisitReason ON dbo.tmp_VistCategory.CategoryID = dbo.tmp_VisitReason.Category RIGHT OUTER JOIN\n"
            + "                      dbo.tmp_TokenReport ON dbo.tmp_VisitReason.ItemCodeID = dbo.tmp_TokenReport.VisitReason";
    public static final String GET_ACTIVE_TOKEN = "SELECT     dbo.tmp_TokenReport.TokenDistributedGUID, dbo.tmp_TokenReport.TokenNumber AS [Token Number], dbo.tmp_VistCategory.Category AS [Category], dbo.tmp_VisitReason.Item AS [Reason Of Visit], \n"
            + "                      dbo.tmp_TokenReport.VisitNumber AS [Visit Number], dbo.tmp_TokenReport.CaseNumber AS [Case Number], dbo.tmp_TokenReport.FamilySize AS [Family Size], dbo.tmp_TokenReport.Location, \n"
            + "                      dbo.tmp_TokenReport.AccesDateTime AS [Acces Date Time], DATEADD(d, DATEDIFF(d, 0, dbo.tmp_TokenReport.AccesDateTime), 0) AS [Access Date], \n"
            + "                      dbo.tmp_TokenReport.TokenStatus, dbo.tmp_VisitReason.ItemCodeID AS [Visit Reason Code], dbo.tmp_VistCategory.CategoryID AS [Visit Category Code], dbo.tmp_TokenReport.GateName AS [Gate Name]\n"
            + "FROM         dbo.tmp_VistCategory RIGHT OUTER JOIN\n"
            + "                      dbo.tmp_VisitReason ON dbo.tmp_VistCategory.CategoryID = dbo.tmp_VisitReason.Category RIGHT OUTER JOIN\n"
            + "                      dbo.tmp_TokenReport ON dbo.tmp_VisitReason.ItemCodeID = dbo.tmp_TokenReport.VisitReason\n"
            + "WHERE     (dbo.tmp_TokenReport.TokenStatus <> N'X' OR\n"
            + "                      dbo.tmp_TokenReport.TokenStatus IS NULL)";
    public static final String GET_TODAY_TOKEN = "SELECT     dbo.tmp_TokenReport.TokenDistributedGUID, dbo.tmp_TokenReport.TokenNumber AS [Token Number], dbo.tmp_VistCategory.Category AS [Category], dbo.tmp_VisitReason.Item AS [Reason Of Visit], \n"
            + "                      dbo.tmp_TokenReport.VisitNumber AS [Visit Number], dbo.tmp_TokenReport.CaseNumber AS [Case Number], dbo.tmp_TokenReport.FamilySize AS [Family Size], dbo.tmp_TokenReport.Location, \n"
            + "                      dbo.tmp_TokenReport.AccesDateTime AS [Acces Date Time], DATEADD(d, DATEDIFF(d, 0, dbo.tmp_TokenReport.AccesDateTime), 0) AS [Access Date], \n"
            + "                      dbo.tmp_TokenReport.TokenStatus,dbo.tmp_VisitReason.ItemCodeID AS [Visit Reason Code], dbo.tmp_VistCategory.CategoryID AS [Visit Category Code], dbo.tmp_TokenReport.GateName AS [Gate Name]\n"
            + "FROM         dbo.tmp_VistCategory RIGHT OUTER JOIN\n"
            + "                      dbo.tmp_VisitReason ON dbo.tmp_VistCategory.CategoryID = dbo.tmp_VisitReason.Category RIGHT OUTER JOIN\n"
            + "                      dbo.tmp_TokenReport ON dbo.tmp_VisitReason.ItemCodeID = dbo.tmp_TokenReport.VisitReason\n"
            + "WHERE     (DATEADD(d, DATEDIFF(d, 0, dbo.tmp_TokenReport.AccesDateTime), 0) = DATEADD(d, DATEDIFF(d, 0, GETDATE()), 0))";
    public static final String GET_DATE_TOKEN = "SELECT     dbo.tmp_TokenReport.TokenDistributedGUID, dbo.tmp_TokenReport.TokenNumber AS [Token Number], dbo.tmp_VistCategory.Category AS [Category], dbo.tmp_VisitReason.Item AS [Reason Of Visit], \n"
            + "                      dbo.tmp_TokenReport.VisitNumber AS [Visit Number], dbo.tmp_TokenReport.CaseNumber AS [Case Number], dbo.tmp_TokenReport.FamilySize AS [Family Size], dbo.tmp_TokenReport.Location, \n"
            + "                      dbo.tmp_TokenReport.AccesDateTime AS [Acces Date Time], DATEADD(d, DATEDIFF(d, 0, dbo.tmp_TokenReport.AccesDateTime), 0) AS [Access Date], \n"
            + "                      dbo.tmp_TokenReport.TokenStatus,dbo.tmp_VisitReason.ItemCodeID AS [Visit Reason Code], dbo.tmp_VistCategory.CategoryID AS [Visit Category Code], dbo.tmp_TokenReport.GateName AS [Gate Name]\n"
            + "FROM         dbo.tmp_VistCategory RIGHT OUTER JOIN\n"
            + "                      dbo.tmp_VisitReason ON dbo.tmp_VistCategory.CategoryID = dbo.tmp_VisitReason.Category RIGHT OUTER JOIN\n"
            + "                      dbo.tmp_TokenReport ON dbo.tmp_VisitReason.ItemCodeID = dbo.tmp_TokenReport.VisitReason\n"
            + "WHERE     (DATEADD(d, DATEDIFF(d, 0, dbo.tmp_TokenReport.AccesDateTime), 0) = DATEADD(d, DATEDIFF(d, 0, ?), 0))";

    public void getAllToken(Connection conn) throws SQLException {
        super.setTableStructureContents(conn, GET_ALL_TOKEN, QueryBy.SELECT_QUERY);
    }

    public void getAllActiveToken(Connection conn) throws SQLException {
        super.setTableStructureContents(conn, GET_ACTIVE_TOKEN, QueryBy.SELECT_QUERY);
    }

    public void getTodayToken(Connection conn) throws SQLException {
        super.setTableStructureContents(conn, GET_TODAY_TOKEN, QueryBy.SELECT_QUERY);
    }

    public void getDateToken(Connection conn, Date date) throws SQLException {
        HashMap<Integer, Object> hashMap = new HashMap<>();
        hashMap.put(1, date);
        getPreparedQueryContents(conn, GET_DATE_TOKEN, hashMap);
        refreshTable();
    }

}
