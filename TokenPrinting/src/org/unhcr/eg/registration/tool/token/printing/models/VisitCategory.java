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
public class VisitCategory {

    public VisitCategory() {
    }

    @Override
    public String toString() {
        return sectionText ;
    }
    
    
//org.unhcr.eg.registration.tool.token.printing.models.VisitCategory
    public VisitCategory(String sectionCode, String sectionText, boolean active) {
        this.sectionCode = sectionCode;
        this.sectionText = sectionText;
        this.active = active;
    }

    private String sectionCode;

    private String sectionText;

    private boolean active;

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

    public static final String PROP_SECTIONTEXT = "sectionText";

    /**
     * Get the value of sectionText
     *
     * @return the value of sectionText
     */
    public String getSectionText() {
        return sectionText;
    }

    /**
     * Set the value of sectionText
     *
     * @param sectionText new value of sectionText
     */
    public void setSectionText(String sectionText) {
        String oldSectionText = this.sectionText;
        this.sectionText = sectionText;
        propertyChangeSupport.firePropertyChange(PROP_SECTIONTEXT, oldSectionText, sectionText);
    }

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
