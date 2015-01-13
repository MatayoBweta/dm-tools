/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.security;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Matayo Bweta Doudoux Stanyslas
 */
public class DatabaseUtility {

    public static String getMSSQLDatabaseURL(String hostName, String port, String databaseName) {
        return "jdbc:jtds:sqlserver://" + hostName + ":" + port + "/" + databaseName;
    }

    public static String getMSSQLURL(String hostName, String port) {
        return "jdbc:jtds:sqlserver://" + hostName + ":" + port;
    }

    public static boolean pingDatabase(String url, String userID, String password) {
        ResultSet ping = null;
        try {
            Connection connection = DriverManager.getConnection(url, userID, password);
            System.out.println("Connection PING");
            if (connection == null) {
                System.out.println("Connection NULL");
                return false;
            }
            if (connection.isClosed()) {
                System.out.println("Connection Closed");
                return false;
            }
            ping = connection.createStatement().executeQuery("SELECT 1");
            System.out.println("Connection OK");
            return ping.next();
        } catch (SQLException sqle) {
            return false;
        } finally {
            if (ping != null) {
                try {
                    ping.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean checkProGresDataabse(String url, String userID, String password) {
        ResultSet ping = null;
        boolean isproGresDataBase = false;
        System.out.println("Value obtained from proGres database!!! Yes for " + url);
        try {
            Connection connection = DriverManager.getConnection(url, userID, password);
            if (connection == null) {
                System.out.println("Connection null");
                return isproGresDataBase;
            }
            if (connection.isClosed()) {
                System.out.println("Connection closed");
                return isproGresDataBase;
            }
            ping = connection.prepareStatement("select name, value from ::fn_listextendedproperty(null, 'USER', 'dbo', 'table', 'configSiteID', null, null)").executeQuery();
            System.out.println("Connection " + ping.toString());
            while (ping.next()) {
                String name = ping.getString("name");
                String value = ping.getString("value");

                System.out.println("name " + name + " value " + value);
                if (name.equals("version") && value.contains("3")) {
                    isproGresDataBase = true;
                }
            }
            System.out.println("-----------------------------------------------------------------------------");
            return isproGresDataBase;
        } catch (SQLException sqle) {
            return isproGresDataBase;
        } finally {
            if (ping != null) {
                try {
                    ping.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static ArrayList<String> getDatabasesList(String url, String userID, String password) {
        ResultSet ping = null;
        ArrayList<String> allDatabases = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(url, userID, password);
            if (connection == null) {
                return null;
            }
            if (connection.isClosed()) {
                return null;
            }
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet catalogs = metaData.getCatalogs();
            while (catalogs.next()) {
                String name = catalogs.getString("TABLE_CAT");
                allDatabases.add(name);
            }
//            ping = connection.prepareCall("{call sp_helpdb}").executeQuery();
//            while (ping.next()) {
//                String name = ping.getString("name");
//                String owner = ping.getString("owner");
//                System.out.println(name + "     " + owner);
//                if (!owner.equals("sa")) {
//                    allDatabases.add(name);
//                }
//            }
            return allDatabases;
        } catch (SQLException sqle) {
            return null;
        } finally {
            if (ping != null) {
                try {
                    ping.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getProGresSite(String url, String userID, String password) {
        ResultSet ping = null;
        String query = "SELECT SiteID FROM configSiteID";
        try {
            Connection connection = DriverManager.getConnection(url, userID, password);
            if (connection == null) {
                return null;
            }
            if (connection.isClosed()) {
                return null;
            }
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                String siteID = resultSet.getString("SiteID");
                return siteID;
            }
//            ping = connection.prepareCall("{call sp_helpdb}").executeQuery();
//            while (ping.next()) {
//                String name = ping.getString("name");
//                String owner = ping.getString("owner");
//                System.out.println(name + "     " + owner);
//                if (!owner.equals("sa")) {
//                    allDatabases.add(name);
//                }
//            }
            return null;
        } catch (SQLException sqle) {
            return null;
        } finally {
            if (ping != null) {
                try {
                    ping.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getShortcut(String url, String userID, String password) {
        ResultSet ping = null;
        String query = "SELECT SiteID FROM configSiteID";
        try {
            Connection connection = DriverManager.getConnection(url, userID, password);
            if (connection == null) {
                return null;
            }
            if (connection.isClosed()) {
                return null;
            }
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                String siteID = resultSet.getString("SiteID");
                return siteID;
            }
//            ping = connection.prepareCall("{call sp_helpdb}").executeQuery();
//            while (ping.next()) {
//                String name = ping.getString("name");
//                String owner = ping.getString("owner");
//                System.out.println(name + "     " + owner);
//                if (!owner.equals("sa")) {
//                    allDatabases.add(name);
//                }
//            }
            return null;
        } catch (SQLException sqle) {
            return null;
        } finally {
            if (ping != null) {
                try {
                    ping.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
