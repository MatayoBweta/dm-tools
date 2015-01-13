/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.security.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.util.Enumeration;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import org.jdesktop.swingx.JXTable;

/**
 *
 * @author Matayo Bweta Doudoux Stanyslas
 */
public class TableSyntaxRenderer extends DefaultTableCellRenderer {

    public TableSyntaxRenderer() {
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (value != null && value instanceof String) {
            String code = (String) value;
            ColorIcon icon = new ColorIcon();
            String details = "";
            if (code.startsWith("A")) {
                icon.setColor(Color.GREEN);
                details = "Active";
            } else if (code.startsWith("H")) {
                icon.setColor(Color.YELLOW);
                details = "Hold";
            } else if (code.startsWith("I")) {
                icon.setColor(Color.RED);
                details = "Inactive";
            } else if (code.startsWith("C")) {
                icon.setColor(Color.RED);
                details = "Closed";
            }
            if (code.length() > 1) {
                details = "";
            }
            ((JLabel) component).setIcon(icon);
            ((JLabel) component).setText("<html><b>" + code + "</b> - " + details + "</html> ");
            return component;
        }
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }

    public static void setTableColumnRenderer(JXTable table) {
        Enumeration<TableColumn> allColumnsAvailable = table.getColumnModel().getColumns();
        while (allColumnsAvailable.hasMoreElements()) {
            TableColumn tableColumn = allColumnsAvailable.nextElement();
            int modelIndex = tableColumn.getModelIndex();
            Class value = table.getColumnClass(table.convertColumnIndexToView(modelIndex));
            tableColumn.setCellRenderer(new TableSyntaxRenderer());
        }
    }

    public static void setTableColumnRenderer(TableColumn tableColumn) {
        tableColumn.setCellRenderer(new TableSyntaxRenderer());
    }

    public static class ColorIcon implements Icon {

        private Color color;

        public void setColor(Color color) {
            this.color = color;
        }

        @Override
        public int getIconHeight() {
            return 9;
        }

        @Override
        public int getIconWidth() {
            return 18;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor(color);
            g.fillRect(x, y, getIconWidth(), getIconHeight());
        }
    }
}
