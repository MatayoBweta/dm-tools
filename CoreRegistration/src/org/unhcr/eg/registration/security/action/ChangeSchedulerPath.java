/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.security.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;
import org.unhcr.eg.registration.security.em.EntityManagerSingleton;

@ActionID(
        category = "Build",
        id = "org.unhcr.eg.registration.security.action.ChangeSchedulerPath"
)
@ActionRegistration(
        iconBase = "org/unhcr/eg/registration/security/action/Timetable.png",
        displayName = "#CTL_ChangeSchedulerPath"
)
@ActionReferences({
    @ActionReference(path = "Menu/BuildProject", position = 500),
    @ActionReference(path = "Toolbars/Tools", position = 750)
})
@Messages("CTL_ChangeSchedulerPath=Change Scheduler Path")
public final class ChangeSchedulerPath implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String txt = "Scheduler Path: ";
            String title = "Enter Scheduler Path";
            String schedulerPath = null;
            Connection c = EntityManagerSingleton.getDefault().getConnection();
            PreparedStatement p = c.prepareStatement("SELECT [Value] FROM tmp_DMParam WHERE [Name] = ?");
            p.setString(1, "scheduler.path");
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                schedulerPath = rs.getString(1);
            }
            NotifyDescriptor.InputLine input = new NotifyDescriptor.InputLine(txt, title);
            input.setInputText(schedulerPath); // specify a default name
            Object result = DialogDisplayer.getDefault().notify(input);
            if (result != NotifyDescriptor.OK_OPTION) {
                return;
            }

            String userInput = input.getInputText();
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

    }
}
