/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.data.management.exchange;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import org.openide.DialogDisplayer;
import org.openide.WizardDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle;

// An example action demonstrating how the wizard could be called from within
// your code. You can move the code below wherever you need, or register an action:
@ActionID(category = "Build", id = "org.unhcr.eg.data.management.exchange.LoadListDetailsWizardAction")
@ActionRegistration(
        iconBase = "org/unhcr/eg/data/management/exchange/Actions-file-open-icon.png",
        displayName = "#CTL_LoadListDetailsWizardAction")
@ActionReferences({
    @ActionReference(path = "Menu/Exchange", position = 3333),
    @ActionReference(path = "Toolbars/Exchange", position = 850),
    @ActionReference(path = "Shortcuts", name = "DO-3")
})
@NbBundle.Messages("CTL_LoadListDetailsWizardAction=Load List Details")
public final class LoadListDetailsWizardAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        List<WizardDescriptor.Panel<WizardDescriptor>> panels = new ArrayList<>();
        panels.add(new LoadListDetailsWizardPanel1());
        panels.add(new LoadListDetailsWizardPanel2());
        panels.add(new LoadListDetailsWizardPanel3());
        panels.add(new LoadListDetailsWizardPanel4());
        panels.add(new LoadListDetailsWizardPanel5());
        String[] steps = new String[panels.size()];
        for (int i = 0; i < panels.size(); i++) {
            Component c = panels.get(i).getComponent();
            // Default step name to component name of panel.
            steps[i] = c.getName();
            if (c instanceof JComponent) { // assume Swing components
                JComponent jc = (JComponent) c;
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_SELECTED_INDEX, i);
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DATA, steps);
                jc.putClientProperty(WizardDescriptor.PROP_AUTO_WIZARD_STYLE, true);
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DISPLAYED, true);
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_NUMBERED, true);
            }
        }
        WizardDescriptor wiz = new WizardDescriptor(new WizardDescriptor.ArrayIterator<>(panels));
        // {0} will be replaced by WizardDesriptor.Panel.getComponent().getName()
        wiz.setTitleFormat(new MessageFormat("{0}"));
        wiz.setTitle("Load New List");
       
        if (DialogDisplayer.getDefault().notify(wiz) == WizardDescriptor.FINISH_OPTION) {
            // do something
        }
    }
}
