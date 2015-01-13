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
public class VisitReason {
//org.unhcr.eg.registration.tool.token.printing.models.VisitReason

    public VisitReason() {
        
    }

    @Override
    public String toString() {
        return reasonText;
    }

    public VisitReason(String reasonCode, String reasonText, boolean active, String sectionCode) {
        this.reasonCode = reasonCode;
        this.reasonText = reasonText;
        this.active = active;
        this.sectionCode = sectionCode;
    }

    private String reasonCode;

    private String reasonText;

    private boolean active;

    private String sectionCode;

    public static final String PROP_SECTIONCODE = "sectionCode";

    /**
     * Get the value of sectionCode
     *
     * @return the value of sectionCode
     */
    public String getSectionCode() {
        return sectionCode;
    }

    /**
     * Set the value of sectionCode
     *
     * @param sectionCode new value of sectionCode
     */
    public void setSectionCode(String sectionCode) {
        String oldSectionCode = this.sectionCode;
        this.sectionCode = sectionCode;
        propertyChangeSupport.firePropertyChange(PROP_SECTIONCODE, oldSectionCode, sectionCode);
    }

    public static final String PROP_ACTIVE = "active";

    /**
     * Get the value of active
     *
     * @return the value of active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Set the value of active
     *
     * @param active new value of active
     */
    public void setActive(boolean active) {
        boolean oldActive = this.active;
        this.active = active;
        propertyChangeSupport.firePropertyChange(PROP_ACTIVE, oldActive, active);
    }

    public static final String PROP_REASONTEXT = "reasonText";

    /**
     * Get the value of reasonText
     *
     * @return the value of reasonText
     */
    public String getReasonText() {
        return reasonText;
    }

    /**
     * Set the value of reasonText
     *
     * @param reasonText new value of reasonText
     */
    public void setReasonText(String reasonText) {
        String oldReasonText = this.reasonText;
        this.reasonText = reasonText;
        propertyChangeSupport.firePropertyChange(PROP_REASONTEXT, oldReasonText, reasonText);
    }

    public static final String PROP_REASONCODE = "reasonCode";

    /**
     * Get the value of reasonCode
     *
     * @return the value of reasonCode
     */
    public String getReasonCode() {
        return reasonCode;
    }

    /**
     * Set the value of reasonCode
     *
     * @param reasonCode new value of reasonCode
     */
    public void setReasonCode(String reasonCode) {
        String oldReasonCode = this.reasonCode;
        this.reasonCode = reasonCode;
        propertyChangeSupport.firePropertyChange(PROP_REASONCODE, oldReasonCode, reasonCode);
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
