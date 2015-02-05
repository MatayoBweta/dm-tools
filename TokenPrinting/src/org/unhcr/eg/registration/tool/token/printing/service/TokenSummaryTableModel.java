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
 * @author Stanyslas Matayo
 */
public class TokenSummaryTableModel extends JDBCTableModel {

    public static final String GET_ACTIVE_TOKEN_SUMMARY = "SELECT TOP (100) PERCENT DATEADD(d, DATEDIFF(d, 0, dbo.tmp_TokenReport.AccesDateTime), 0) AS [Access Date], dbo.tmp_VistCategory.Category, \n"
            + "                      dbo.tmp_VisitReason.Item, COUNT(*) AS [Number Of Token Provided]\n"
            + "FROM         dbo.tmp_TokenReport LEFT OUTER JOIN\n"
            + "                      dbo.tmp_VisitReason ON dbo.tmp_TokenReport.VisitReason = dbo.tmp_VisitReason.ItemCodeID LEFT OUTER JOIN\n"
            + "                      dbo.tmp_VistCategory ON dbo.tmp_VisitReason.Category = dbo.tmp_VistCategory.CategoryID\n"
            + "WHERE     (dbo.tmp_TokenReport.TokenStatus IS NULL OR\n"
            + "                      dbo.tmp_TokenReport.TokenStatus <> N'X')\n"
            + "GROUP BY DATEADD(d, DATEDIFF(d, 0, dbo.tmp_TokenReport.AccesDateTime), 0), dbo.tmp_VisitReason.Item, dbo.tmp_VistCategory.Category\n"
            + "ORDER BY [Access Date] DESC, dbo.tmp_VistCategory.Category DESC, dbo.tmp_VisitReason.Item DESC";

    public static final String GET_ALL_TOKEN_SUMMARY = "SELECT     TOP (100) PERCENT DATEADD(d, DATEDIFF(d, 0, dbo.tmp_TokenReport.AccesDateTime), 0) AS [Access Date], dbo.tmp_VistCategory.Category, \n"
            + "                      dbo.tmp_VisitReason.Item, COUNT(*) AS [Number Of Token Provided]\n"
            + "FROM         dbo.tmp_TokenReport LEFT OUTER JOIN\n"
            + "                      dbo.tmp_VisitReason ON dbo.tmp_TokenReport.VisitReason = dbo.tmp_VisitReason.ItemCodeID LEFT OUTER JOIN\n"
            + "                      dbo.tmp_VistCategory ON dbo.tmp_VisitReason.Category = dbo.tmp_VistCategory.CategoryID\n"
            + "GROUP BY DATEADD(d, DATEDIFF(d, 0, dbo.tmp_TokenReport.AccesDateTime), 0), dbo.tmp_VisitReason.Item, dbo.tmp_VistCategory.Category\n"
            + "ORDER BY [Access Date] DESC, dbo.tmp_VistCategory.Category DESC, dbo.tmp_VisitReason.Item DESC";

    public static final String GET_TODAY_TOKEN_SUMMARY = "SELECT TOP (100) PERCENT DATEADD(d, DATEDIFF(d, 0, dbo.tmp_TokenReport.AccesDateTime), 0) AS [Access Date], dbo.tmp_VistCategory.Category, \n"
            + "                      dbo.tmp_VisitReason.Item, COUNT(*) AS [Number Of Token Provided]\n"
            + "FROM         dbo.tmp_TokenReport LEFT OUTER JOIN\n"
            + "                      dbo.tmp_VisitReason ON dbo.tmp_TokenReport.VisitReason = dbo.tmp_VisitReason.ItemCodeID LEFT OUTER JOIN\n"
            + "                      dbo.tmp_VistCategory ON dbo.tmp_VisitReason.Category = dbo.tmp_VistCategory.CategoryID\n"
            + "WHERE     (dbo.tmp_TokenReport.TokenStatus IS NULL OR\n"
            + "                      dbo.tmp_TokenReport.TokenStatus <> N'X') AND (DATEADD(d, DATEDIFF(d, 0, dbo.tmp_TokenReport.AccesDateTime), 0) = DATEADD(d, DATEDIFF(d, 0, GETDATE()), 0))\n"
            + "GROUP BY DATEADD(d, DATEDIFF(d, 0, dbo.tmp_TokenReport.AccesDateTime), 0), dbo.tmp_VisitReason.Item, dbo.tmp_VistCategory.Category\n"
            + "ORDER BY [Access Date] DESC, dbo.tmp_VistCategory.Category DESC, dbo.tmp_VisitReason.Item DESC";
  
    public static final String GET_DATE_TOKEN = "SELECT TOP (100) PERCENT DATEADD(d, DATEDIFF(d, 0, dbo.tmp_TokenReport.AccesDateTime), 0) AS [Access Date], dbo.tmp_VistCategory.Category, \n"
            + "                      dbo.tmp_VisitReason.Item, COUNT(*) AS [Number Of Token Provided]\n"
            + "FROM         dbo.tmp_TokenReport LEFT OUTER JOIN\n"
            + "                      dbo.tmp_VisitReason ON dbo.tmp_TokenReport.VisitReason = dbo.tmp_VisitReason.ItemCodeID LEFT OUTER JOIN\n"
            + "                      dbo.tmp_VistCategory ON dbo.tmp_VisitReason.Category = dbo.tmp_VistCategory.CategoryID\n"
            + "WHERE     (dbo.tmp_TokenReport.TokenStatus IS NULL OR\n"
            + "                      dbo.tmp_TokenReport.TokenStatus <> N'X') AND (DATEADD(d, DATEDIFF(d, 0, dbo.tmp_TokenReport.AccesDateTime), 0) = DATEADD(d, DATEDIFF(d, 0, ?), 0))\n"
            + "GROUP BY DATEADD(d, DATEDIFF(d, 0, dbo.tmp_TokenReport.AccesDateTime), 0), dbo.tmp_VisitReason.Item, dbo.tmp_VistCategory.Category\n"
            + "ORDER BY [Access Date] DESC, dbo.tmp_VistCategory.Category DESC, dbo.tmp_VisitReason.Item DESC";

    public void getAllToken(Connection conn) throws SQLException {
        super.setTableStructureContents(conn, GET_ALL_TOKEN_SUMMARY, QueryBy.SELECT_QUERY);
    }

    public void getAllActiveToken(Connection conn) throws SQLException {
        super.setTableStructureContents(conn, GET_ACTIVE_TOKEN_SUMMARY, QueryBy.SELECT_QUERY);
    }

    public void getTodayToken(Connection conn) throws SQLException {
        super.setTableStructureContents(conn, GET_TODAY_TOKEN_SUMMARY, QueryBy.SELECT_QUERY);
    }

    public void getDateToken(Connection conn, Date date) throws SQLException {
        HashMap<Integer, Object> hashMap = new HashMap<>();
        hashMap.put(1, date);
        getPreparedQueryContents(conn, GET_DATE_TOKEN, hashMap);
        refreshTable();
    }
}
