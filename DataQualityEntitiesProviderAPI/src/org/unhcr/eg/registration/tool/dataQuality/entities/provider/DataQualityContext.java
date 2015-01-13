/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.tool.dataQuality.entities.provider;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 *
 * @author UNHCRUser
 */
public abstract class DataQualityContext {

    private ExtendedContext extendedContext;
    public static final String PROP_EXTENDEDCONTEXT = "extendedContext";
    private String countryOrSituationName;
    public static final String PROP_COUNTRYORSITUATIONNAME = "countryOrSituationName";
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
    public DataQualityContext(ExtendedContext extendedContext, String countryOrSituationName) {
        if (extendedContext.equals(ExtendedContext.GLOBAL)) {
            this.countryOrSituationName = null;
            return;
        }
        this.extendedContext = extendedContext;
        this.countryOrSituationName = countryOrSituationName;
    }

    public DataQualityContext() {
        this.extendedContext = ExtendedContext.GLOBAL;
        this.countryOrSituationName = null;

    }

    /**
     * Get the value of countryOrSituationName
     *
     * @return the value of countryOrSituationName
     */
    public String getCountryOrSituationName() {
        return countryOrSituationName;
    }

    /**
     * Get the value of extendedContext
     *
     * @return the value of extendedContext
     */
    public ExtendedContext getExtendedContext() {
        return extendedContext;
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
