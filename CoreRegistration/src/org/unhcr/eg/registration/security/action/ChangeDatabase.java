/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.security.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;
import org.unhcr.eg.registration.security.DialogUtility;
import org.unhcr.eg.registration.security.ui.DatabaseCheckForm;

@ActionID(
        category = "Build",
        id = "org.unhcr.eg.registration.security.action.ChangeDatabase")
@ActionRegistration(
        iconBase = "org/unhcr/eg/registration/security/action/Misc-Database-3-icon.png",
        displayName = "#CTL_ChangeDatabase")
@ActionReferences({
    @ActionReference(path = "Menu/Tools", position = 3333),
    @ActionReference(path = "Toolbars/Tools", position = 850),
    @ActionReference(path = "Shortcuts", name = "DO-1")
})
@Messages("CTL_ChangeDatabase=Change Database")
public final class ChangeDatabase implements ActionListener {

    DatabaseCheckForm formDB = null;

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            formDB = DatabaseCheckForm.getDefaultForModif();
        } catch (InterruptedException | InvocationTargetException ex1) {
            Exceptions.printStackTrace(ex1);
        }
        DialogDescriptor nd = DialogUtility.createDialogDescriptor(formDB, "Change Database", true, new Object[]{formDB.getSave(), formDB.getTestConnection(), formDB.getCancel()}, new Object[]{formDB.getSave(), formDB.getCancel()}, formDB.getSave(), formDB);
        DialogDisplayer.getDefault().notify(nd);
    }
}
