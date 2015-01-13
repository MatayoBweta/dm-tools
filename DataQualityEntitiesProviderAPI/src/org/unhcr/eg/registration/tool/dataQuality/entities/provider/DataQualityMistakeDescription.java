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
public class DataQualityMistakeDescription {

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

//    public DataQualityMistakeDescription(String label, DataQualityEvaluator evaluator) {
//        this.label = label;
//        this.evaluator = evaluator;
//    }

    private String label;

    public static final String PROP_LABEL = "label";

    /**
     * Get the value of label
     *
     * @return the value of label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Set the value of label
     *
     * @param label new value of label
     */
    public void setLabel(String label) {
        String oldLabel = this.label;
        this.label = label;
        propertyChangeSupport.firePropertyChange(PROP_LABEL, oldLabel, label);
    }

    private DataQualityEvaluator evaluator;
    public static final String PROP_EVALUATOR = "evaluator";

    public DataQualityMistakeDescription(String label, DataQualityEvaluator evaluator, DataQualityContext context, Severity severity) {
        this.context = context;
        this.label = label;
        this.severity = severity;
        this.evaluator = evaluator;
    }

    /**
     * Get the value of evaluator
     *
     * @return the value of evaluator
     */
    public DataQualityEvaluator getEvaluator() {
        return evaluator;
    }

    private DataQualityContext context;

    public static final String PROP_CONTEXT = "context";

    /**
     * Get the value of context
     *
     * @return the value of context
     */
    public DataQualityContext getContext() {
        return context;
    }

    /**
     * Set the value of context
     *
     * @param context new value of context
     */
    public void setContext(DataQualityContext context) {
        DataQualityContext oldContext = this.context;
        this.context = context;
        propertyChangeSupport.firePropertyChange(PROP_CONTEXT, oldContext, context);
    }

    private Severity severity;

    public static final String PROP_SEVERITY = "severity";

    /**
     * Get the value of severity
     *
     * @return the value of severity
     */
    public Severity getSeverity() {
        return severity;
    }

    /**
     * Set the value of severity
     *
     * @param severity new value of severity
     */
    public void setSeverity(Severity severity) {
        Severity oldSeverity = this.severity;
        this.severity = severity;
        propertyChangeSupport.firePropertyChange(PROP_SEVERITY, oldSeverity, severity);
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
