/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.tool.dataQuality.entities.provider.ui;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import org.unhcr.eg.registration.security.ui.TableSyntaxRenderer;
import org.unhcr.eg.registration.tool.dataQuality.entities.provider.DataQualitySeverity;
import org.unhcr.eg.registration.tool.dataQuality.entities.provider.Severity;

/**
 *
 * @author UNHCRUser
 */
public class TableQualitySyntaxRenderer extends TableSyntaxRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (value != null && value instanceof DataQualitySeverity) {
            Severity severity = ((DataQualitySeverity) value).getSeverity();
            ColorIcon icon = new ColorIcon();
            switch (severity) {
                case CRITICAL:
                    icon.setColor(Color.RED);
                    break;
                case WARNING:
                    icon.setColor(Color.YELLOW);
                    break;
                case INFORMATION:
                    icon.setColor(Color.GREEN);
                    break;
            }
            ((JLabel) component).setIcon(icon);
            ((JLabel) component).setText(severity.getSeverityLevel());
        }
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); //To change body of generated methods, choose Tools | Templates.

    }
}
