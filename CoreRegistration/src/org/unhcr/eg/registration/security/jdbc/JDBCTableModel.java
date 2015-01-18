/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.security.jdbc;

import javax.swing.table.*;
import java.sql.*;
import java.util.*;

/**
 * an immutable table model built from getting metadata about a table in a jdbc
 * database
 */
public class JDBCTableModel extends AbstractTableModel {

    public void getTableContent(Connection conn, String request) throws SQLException {
        // get metadata: what columns exist and what
        // types (classes) are they?
        DatabaseMetaData meta = conn.getMetaData();
        System.out.println("got meta = " + meta);
        ResultSet results
                = meta.getColumns(null, null, request, null);
        System.out.println("got column results");
        ArrayList colNamesList = new ArrayList();
        ArrayList colClassesList = new ArrayList();
        while (results.next()) {
            colNamesList.add(results.getString("COLUMN_NAME"));
            System.out.println("name: "
                    + results.getString("COLUMN_NAME"));
            int dbType = results.getInt("DATA_TYPE");
            switch (dbType) {
                case Types.INTEGER:
                    colClassesList.add(Integer.class);
                    break;
                case Types.FLOAT:
                    colClassesList.add(Float.class);
                    break;
                case Types.DOUBLE:
                case Types.REAL:
                    colClassesList.add(Double.class);
                    break;
                case Types.DATE:
                case Types.TIME:
                case Types.TIMESTAMP:
                    colClassesList.add(java.sql.Date.class);
                    break;
                default:
                    colClassesList.add(String.class);
                    break;
            };
            System.out.println("type: "
                    + results.getInt("DATA_TYPE"));
        }
        columnNames = new String[colNamesList.size()];
        colNamesList.toArray(columnNames);
        columnClasses = new Class[colClassesList.size()];
        colClassesList.toArray(columnClasses);
        // get all data from table and put into
        // contents array
        Statement statement
                = conn.createStatement();
        results = statement.executeQuery("SELECT * FROM "
                + request);
        ArrayList rowList = new ArrayList();
        while (results.next()) {
            ArrayList cellList = new ArrayList();
            for (int i = 0; i < columnClasses.length; i++) {
                Object cellValue = null;

                if (columnClasses[i] == String.class) {
                    cellValue = results.getString(columnNames[i]);
                } else if (columnClasses[i] == Integer.class) {
                    cellValue = results.getInt(columnNames[i]);
                } else if (columnClasses[i] == Float.class) {
                    cellValue = results.getFloat(columnNames[i]);
                } else if (columnClasses[i] == Double.class) {
                    cellValue = results.getDouble(columnNames[i]);
                } else if (columnClasses[i] == java.sql.Date.class) {
                    cellValue = results.getDate(columnNames[i]);
                } else {
                    System.out.println("Can't assign "
                            + columnNames[i]);
                }
                cellList.add(cellValue);
            }// for
            Object[] cells = cellList.toArray();
            rowList.add(cells);

        } // while
        // finally create contents two-dim array
        contents = new Object[rowList.size()][];
        for (int i = 0; i < contents.length; i++) {
            contents[i] = (Object[]) rowList.get(i);
        }
        System.out.println("Created model with "
                + contents.length + " rows");
        // close stuff
        results.close();
        statement.close();
    }

    public enum QueryBy {

        TABLE_NAME, SELECT_QUERY, STORED_PROCEDURE
    }
    protected Object[][] contents;
    protected String[] columnNames;
    protected Class[] columnClasses;

    public JDBCTableModel() {
        super();
    }

    public void setTableStructureContents(Connection conn,
            String request, QueryBy queryBy)
            throws SQLException {
        switch (queryBy) {
            case TABLE_NAME:
                getTableContent(conn, request);
                break;
            case SELECT_QUERY:
                getQueryContents(conn, request);
                break;
        }
        refreshTable();
    }
    // AbstractTableModel methods

    protected void refreshTable() {
        fireTableStructureChanged();
        fireTableDataChanged();
    }

    protected void getPreparedQueryContents(Connection conn,
            String query, HashMap<Integer, Object> parameters)
            throws SQLException {
        try (Statement statement = conn.createStatement()) {
            statement.setMaxRows(1);
            try (ResultSet results = statement.executeQuery(query)) {
                ResultSetMetaData metaData = results.getMetaData();
                System.out.println("got column results");
                ArrayList colNamesList = new ArrayList();
                ArrayList colClassesList = new ArrayList();
                int nbreOfColumn = metaData.getColumnCount();
                for (int i = 0; i < nbreOfColumn; i++) {
                    colNamesList.add(metaData.getColumnName(i));
                    int dbType = metaData.getColumnType(i);
                    switch (dbType) {
                        case Types.INTEGER:
                            colClassesList.add(Integer.class);
                            break;
                        case Types.FLOAT:
                            colClassesList.add(Float.class);
                            break;
                        case Types.DOUBLE:
                        case Types.REAL:
                            colClassesList.add(Double.class);
                            break;
                        case Types.DATE:
                        case Types.TIME:
                        case Types.TIMESTAMP:
                            colClassesList.add(java.sql.Date.class);
                            break;
                        default:
                            colClassesList.add(String.class);
                            break;
                    }
                }
                columnNames = new String[colNamesList.size()];
                colNamesList.toArray(columnNames);
                columnClasses = new Class[colClassesList.size()];
                colClassesList.toArray(columnClasses);
                // close stuff
            }
        }

        PreparedStatement statement = conn.prepareStatement(query);
        for (Map.Entry<Integer, Object> parameter : parameters.entrySet()) {
            Integer key = parameter.getKey();
            Object value = parameter.getValue();
            statement.setObject(key, value);
        }
        ResultSet results = statement.executeQuery();
        ArrayList rowList = new ArrayList();
        while (results.next()) {
            ArrayList cellList = new ArrayList();
            for (int i = 0; i < columnClasses.length; i++) {
                Object cellValue = null;
                if (columnClasses[i] == String.class) {
                    cellValue = results.getString(columnNames[i]);
                } else if (columnClasses[i] == Integer.class) {
                    cellValue = results.getInt(columnNames[i]);
                } else if (columnClasses[i] == Float.class) {
                    cellValue = (float) results.getInt(columnNames[i]);
                } else if (columnClasses[i] == Double.class) {
                    cellValue = results.getDouble(columnNames[i]);
                } else if (columnClasses[i] == java.sql.Date.class) {
                    cellValue = results.getDate(columnNames[i]);
                } else {
                    System.out.println("Can't assign "
                            + columnNames[i]);
                }
                cellList.add(cellValue);
            }// for
            Object[] cells = cellList.toArray();
            rowList.add(cells);

        } // while
        // finally create contents two-dim array
        contents = new Object[rowList.size()][];
        for (int i = 0; i < contents.length; i++) {
            contents[i] = (Object[]) rowList.get(i);
        }
        System.out.println("Created model with "
                + contents.length + " rows");

    }

    public void executePreparedQuery(Connection conn,
            String query, HashMap<Integer, Object> parameters)
            throws SQLException {

        PreparedStatement statement = conn.prepareStatement(query);
        for (Map.Entry<Integer, Object> parameter : parameters.entrySet()) {
            Integer key = parameter.getKey();
            Object value = parameter.getValue();
            statement.setObject(key, value);
        }
        statement.executeUpdate();
        refreshTable();
    }

    public int getIntValueFromPreparedQuery(Connection conn,
            String query, HashMap<Integer, Object> parameters)
            throws SQLException {
        ResultSet rs;
        PreparedStatement statement = conn.prepareStatement(query);
        for (Map.Entry<Integer, Object> parameter : parameters.entrySet()) {
            Integer key = parameter.getKey();
            Object value = parameter.getValue();
            statement.setObject(key, value);
        }
        rs = statement.executeQuery();
        if (rs.next()) {
            int value = rs.getInt(1);
            return value;
        } else {
            return 0;
        }
    }

    public float getFloatValueFromPreparedQuery(Connection conn,
            String query, HashMap<Integer, Object> parameters)
            throws SQLException {
        ResultSet rs;
        PreparedStatement statement = conn.prepareStatement(query);
        for (Map.Entry<Integer, Object> parameter : parameters.entrySet()) {
            Integer key = parameter.getKey();
            Object value = parameter.getValue();
            statement.setObject(key, value);
        }
        rs = statement.executeQuery();
        if (rs.next()) {
            float value = rs.getFloat(1);
            return value;
        } else {
            return 0;
        }
    }

    // AbstractTableModel methods
    protected void getQueryContents(Connection conn,
            String query)
            throws SQLException {
        try (Statement statement = conn.createStatement()) {
            statement.setMaxRows(1); 
            ResultSet results = statement.executeQuery(query);
            ResultSetMetaData metaData = results.getMetaData();
            ArrayList colNamesList = new ArrayList();
            ArrayList colClassesList = new ArrayList();
            int nbreOfColumn = metaData.getColumnCount();
            for (int i = 1; i <= nbreOfColumn; i++) {
                colNamesList.add(metaData.getColumnName(i));
                int dbType = metaData.getColumnType(i);
                switch (dbType) {
                    case Types.INTEGER:
                        colClassesList.add(Integer.class);
                        break;
                    case Types.FLOAT:
                        colClassesList.add(Float.class);
                        break;
                    case Types.DOUBLE:
                    case Types.REAL:
                        colClassesList.add(Double.class);
                        break;
                    case Types.DATE:
                    case Types.TIME:
                    case Types.TIMESTAMP:
                        colClassesList.add(java.sql.Date.class);
                        break;
                    default:
                        colClassesList.add(String.class);
                        break;
                }
            }

            columnNames = new String[colNamesList.size()];
            colNamesList.toArray(columnNames);
            columnClasses = new Class[colClassesList.size()];
            colClassesList.toArray(columnClasses);

            // get all data from table and put into
            // contents array
            statement.setMaxRows(0);
            results = statement.executeQuery(query);
            ArrayList rowList = new ArrayList();
            while (results.next()) {
                ArrayList cellList = new ArrayList();
                for (int i = 0; i < columnClasses.length; i++) {
                    Object cellValue = null;
                    if (columnClasses[i] == String.class) {
                        cellValue = results.getString(columnNames[i]);
                    } else if (columnClasses[i] == Integer.class) {
                        cellValue = results.getInt(columnNames[i]);
                    } else if (columnClasses[i] == Float.class) {
                        cellValue = (float) results.getInt(columnNames[i]);
                    } else if (columnClasses[i] == Double.class) {
                        cellValue = results.getDouble(columnNames[i]);
                    } else if (columnClasses[i] == java.sql.Date.class) {
                        cellValue = results.getDate(columnNames[i]);
                    } else {
                        System.out.println("Can't assign "
                                + columnNames[i]);
                    }
                    cellList.add(cellValue);
                }// for
                Object[] cells = cellList.toArray();
                rowList.add(cells);

            } // while
            // finally create contents two-dim array
            contents = new Object[rowList.size()][];
            for (int i = 0; i < contents.length; i++) {
                contents[i] = (Object[]) rowList.get(i);
            }
            System.out.println("Created model with "
                    + contents.length + " rows");

            // close stuff
            results.close();
        }
    }

    @Override
    public int getRowCount() {
        if (contents == null) {
            return 0;
        }
        return contents.length;
    }

    @Override
    public int getColumnCount() {
        if (contents == null || contents.length == 0) {
            return 0;
        } else {
            return contents[0].length;
        }
    }

    @Override
    public Object getValueAt(int row, int column) {
        return contents[row][column];
    }

    // overrides methods for which AbstractTableModel
    // has trivial implementations
    @Override
    public Class getColumnClass(int col) {
        return columnClasses[col];
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }
}
