/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.security;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.Border;
import org.jdesktop.swingx.JXDatePicker;
import org.netbeans.validation.api.Severity;
import org.netbeans.validation.api.ui.ComponentDecorator;
import org.openide.DialogDescriptor;

/**
 *
 * @author Matayo Bweta Doudoux Stanyslas
 */
public class DialogUtility {

    protected static final Color LABEL_COLOR = new Color(0, 70, 213);
    protected static ComponentDecorator decorator = new ComponentDecorator();
    protected static Border originalBorder = new JXDatePicker().getEditor().getBorder();

    public static void addSeparator(JPanel panel, String text, boolean great) {
        JLabel l = new JLabel(text);
        l.setFont(l.getFont().deriveFont(15));
        l.setFont(l.getFont().deriveFont(Font.BOLD));
        l.setForeground(LABEL_COLOR);
        panel.add(l, "gapbottom 1, span, split 2, aligny center");
        panel.add(new JSeparator(), "gapleft rel,growx");
    }

    public static void addSeparator(JPanel panel, String text) {
        JLabel l = new JLabel(text);
        l.setForeground(LABEL_COLOR);
        panel.add(l, "gapbottom 1, span, split 2, aligny center");
        panel.add(new JSeparator(), "gapleft rel,growx");
    }

    public static void changeDateBorder(boolean result, JXDatePicker datePicker) {
        if (originalBorder != null) {
            if (result) {
                //Test to avoid unncessary re-layout
                if (datePicker.getEditor().getBorder() != originalBorder) {
                    datePicker.getEditor().setBorder(originalBorder);
                }
            } else {
                datePicker.getEditor().setBorder(decorator.createProblemBorder(datePicker.getEditor(), originalBorder, Severity.FATAL));
            }
        }
    }

    public static DialogDescriptor createDialogDescriptor(Object innerPane, String title, boolean modal, Object[] options, Object[] closingOption, Object initialValue, ActionListener listener) {
        DialogDescriptor dd = new DialogDescriptor(innerPane,
                title,
                modal,
                options,
                initialValue,
                DialogDescriptor.DEFAULT_ALIGN,
                null,
                listener);
        // no option should close dialog by default...
        dd.setClosingOptions(closingOption);

        dd.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
//                if ("value".equalsIgnoreCase(evt.getPropertyName())) {
//                    // Escape pressed or dialog closed...
//                   if (NotifyDescriptor.CLOSED_OPTION.equals(evt.getNewValue())) {
//                    }
//                }
            }
        });

        return dd;
    }


}
