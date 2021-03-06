/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.data.management.exchange;

import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

public class LoadListDetailsWizardPanel1 implements WizardDescriptor.ValidatingPanel<WizardDescriptor> {

    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private LoadListDetailsVisualPanel1 component;
    private boolean isValid;

    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    @Override
    public LoadListDetailsVisualPanel1 getComponent() {
        if (component == null) {
            component = new LoadListDetailsVisualPanel1();
        }
        return component;
    }

    @Override
    public void validate() throws WizardValidationException {
        String name = component.getFileSelectedTextField().getText();
        System.out.println("Text to test " + name);
        if (name.equals("")) {
            isValid = false;
            throw new WizardValidationException(null, "Please select a Data File", null);
        } else if (!name.endsWith(".xls") && !name.endsWith(".xlsx")) {
            isValid = false;
            throw new WizardValidationException(null, "Invalid Document Type. Please select an Excel File", null);
        }
        isValid = true;
    }

    @Override
    public HelpCtx getHelp() {
        // Show no Help button for this panel:
        return HelpCtx.DEFAULT_HELP;
        // If you have context help:
        // return new HelpCtx("help.key.here");
    }

    @Override
    public boolean isValid() {
        // If it is always OK to press Next or Finish, then:
        return true;
        // If it depends on some condition (form filled out...) and
        // this condition changes (last form field filled in...) then
        // use ChangeSupport to implement add/removeChangeListener below.
        // WizardDescriptor.ERROR/WARNING/INFORMATION_MESSAGE will also be useful.
    }

    @Override
    public void addChangeListener(ChangeListener l) {
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
    }

    @Override
    public void readSettings(WizardDescriptor wiz) {
        // use wiz.getProperty to retrieve previous panel state

    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        // use wiz.putProperty to remember current panel state
        wiz.putProperty("fileLocation", component.getFileSelectedTextField().getText());
        wiz.putProperty("file", component.getFile());
        
    }

    public void validInput() throws WizardValidationException {
    }
}
