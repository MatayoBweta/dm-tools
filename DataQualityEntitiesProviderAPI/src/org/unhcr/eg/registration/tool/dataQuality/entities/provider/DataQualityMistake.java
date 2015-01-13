/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.tool.dataQuality.entities.provider;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Date;
import org.unhcr.eg.data.quality.proGres.entity.DataIndividual;

/**
 *
 * @author UNHCRUser
 */
public abstract class DataQualityMistake {

    public DataQualityMistake(DataIndividual individual, String message, DataQualityMistakeDescription mistakeDescription) {
        this.individual = individual;
        this.message = message;
        this.mistakeDescription = mistakeDescription;
        this.lastModification = this.individual.getUpdateDate();
        this.lastModifier = this.individual.getUserIDUpdate();
    }

    public DataQualityMistake(DataIndividual individual, String message, DataQualityMistakeDescription mistakeDescription, Date lastModification, String lastModifier) {
        this.individual = individual;
        this.message = message;
        this.mistakeDescription = mistakeDescription;
        this.lastModification = lastModification;
        this.lastModifier = lastModifier;
    }
    private DataIndividual individual;
    public static final String PROP_INDIVIDUAL = "individual";
    private String message;
    public static final String PROP_MESSAGE = "message";
    private DataQualityMistakeDescription mistakeDescription;
    public static final String PROP_EVALUATOR = "mistakeDescription";
    private Date lastModification;
    public static final String PROP_LASTMODIFICATION = "lastModification";

    /**
     * Get the value of lastModification
     *
     * @return the value of lastModification
     */
    public Date getLastModification() {
        return lastModification;
    }

    /**
     * Set the value of lastModification
     *
     * @param lastModification new value of lastModification
     */
    public void setLastModification(Date lastModification) {
        Date oldLastModification = this.lastModification;
        this.lastModification = lastModification;
        propertyChangeSupport.firePropertyChange(PROP_LASTMODIFICATION, oldLastModification, lastModification);
    }
    private String lastModifier;

    /**
     * Get the value of lastModifier
     *
     * @return the value of lastModifier
     */
    public String getLastModifier() {
        return lastModifier;
    }

    /**
     * Set the value of lastModifier
     *
     * @param lastModifier new value of lastModifier
     */
    public void setLastModifier(String lastModifier) {
        this.lastModifier = lastModifier;
    }

    /**
     * Get the value of mistakeDescription
     *
     * @return the value of mistakeDescription
     */
    public DataQualityMistakeDescription getEvaluator() {
        return mistakeDescription;
    }

    /**
     * Get the value of message
     *
     * @return the value of message
     */
    public String getMessage() {
        return message;
    }

    public DataIndividual getIndividual() {
        return individual;
    }

    /**
     * Set the value of individual
     *
     * @param individual new value of individual
     */
    public void setIndividual(DataIndividual individual) {
        DataIndividual oldIndividual = this.individual;
        this.individual = individual;
        propertyChangeSupport.firePropertyChange(PROP_INDIVIDUAL, oldIndividual, individual);
    }
    protected final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

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
