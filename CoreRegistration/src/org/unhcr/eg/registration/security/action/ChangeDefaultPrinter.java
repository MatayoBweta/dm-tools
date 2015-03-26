/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.security.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import org.unhcr.eg.registration.security.DialogUtility;
import org.unhcr.eg.registration.security.report.PrinterPanel;

@ActionID(
        category = "Build",
        id = "org.unhcr.eg.registration.security.action.ChangeDefaultPrinter")
@ActionRegistration(
        iconBase = "org/unhcr/eg/registration/security/action/print-config.png",
        displayName = "#CTL_ChangeDefaultPrinter")
@ActionReferences({
    @ActionReference(path = "Menu/Tools", position = 4444),
    @ActionReference(path = "Toolbars/Tools", position = 900),
    @ActionReference(path = "Shortcuts", name = "DO-3")
})
@Messages("CTL_ChangeDefaultPrinter=Change Default Printer")
public final class ChangeDefaultPrinter implements ActionListener {

    PrinterPanel formPrinter = null;

    @Override
    public void actionPerformed(ActionEvent e) {
        formPrinter = new PrinterPanel(false);
        DialogDescriptor nd = DialogUtility.createDialogDescriptor(formPrinter, "Change Default Token Printer", true, new Object[]{formPrinter.getSave(), formPrinter.getCancel()}, new Object[]{formPrinter.getSave(), formPrinter.getCancel()}, formPrinter.getSave(), formPrinter);
        DialogDisplayer.getDefault().notify(nd);
    }
}
