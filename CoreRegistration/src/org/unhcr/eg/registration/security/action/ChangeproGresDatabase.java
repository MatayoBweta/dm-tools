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
import org.unhcr.eg.registration.security.ui.DatabaseCheckFormproGres;

@ActionID(
        category = "Build",
        id = "org.unhcr.eg.registration.security.action.ChangeproGresDatabase")
@ActionRegistration(
        iconBase = "org/unhcr/eg/registration/security/action/Places-network-server-database-icon.png",
        displayName = "#CTL_ChangeproGresDatabase")
@ActionReferences({
    @ActionReference(path = "Menu/Tools", position = 3334),
    @ActionReference(path = "Toolbars/Tools", position = 852),
    @ActionReference(path = "Shortcuts", name = "DO-2")
})
@Messages("CTL_ChangeproGresDatabase=Change proGres Database")
public final class ChangeproGresDatabase implements ActionListener {

    DatabaseCheckFormproGres formDB = null;

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            formDB = DatabaseCheckFormproGres.getDefaultForModif();
        } catch (InterruptedException | InvocationTargetException ex1) {
            Exceptions.printStackTrace(ex1);
        }
        DialogDescriptor nd = DialogUtility.createDialogDescriptor(formDB, "Change proGres Database", true, new Object[]{formDB.getSave(), formDB.getTestConnection(), formDB.getCancel()}, new Object[]{formDB.getSave(), formDB.getCancel()}, formDB.getSave(), formDB);
        DialogDisplayer.getDefault().notify(nd);
    }
}
