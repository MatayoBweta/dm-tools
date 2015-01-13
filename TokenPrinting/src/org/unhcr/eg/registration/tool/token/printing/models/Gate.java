/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.tool.token.printing.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 *
 * @author UNHCRuser
 */
public class Gate {

    public Gate() {
    }

    @Override
    public String toString() {
        return gateDescription;
    }

    private String gateName;
    private String gateDescription;

    public Gate(String gateName, String gateDescription) {
        this.gateName = gateName;
        this.gateDescription = gateDescription;
    }

    public static final String PROP_GATEDESCRIPTION = "gateDescription";

    /**
     * Get the value of gateDescription
     *
     * @return the value of gateDescription
     */
    public String getGateDescription() {
        return gateDescription;
    }

    /**
     * Set the value of gateDescription
     *
     * @param gateDescription new value of gateDescription
     */
    public void setGateDescription(String gateDescription) {
        String oldGateDescription = this.gateDescription;
        this.gateDescription = gateDescription;
        propertyChangeSupport.firePropertyChange(PROP_GATEDESCRIPTION, oldGateDescription, gateDescription);
    }

    public static final String PROP_GATENAME = "gateName";

    /**
     * Get the value of gateName
     *
     * @return the value of gateName
     */
    public String getGateName() {
        return gateName;
    }

    /**
     * Set the value of gateName
     *
     * @param gateName new value of gateName
     */
    public void setGateName(String gateName) {
        String oldGateName = this.gateName;
        this.gateName = gateName;
        propertyChangeSupport.firePropertyChange(PROP_GATENAME, oldGateName, gateName);
    }

    private transient final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    /**
     * Add PropertyChangeListener.
     *
     * @param listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Remove PropertyChangeListener.
     *
     * @param listener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

}
