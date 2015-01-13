/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.tool.dataQuality.entities.provider.evaluator;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.jxpath.JXPathContext;
import org.unhcr.eg.data.quality.proGres.entity.DataIndividual;
import org.unhcr.eg.registration.tool.dataQuality.entities.provider.DataQualityContext;
import org.unhcr.eg.registration.tool.dataQuality.entities.provider.DataQualityEvaluator;
import org.unhcr.eg.registration.tool.dataQuality.entities.provider.DataQualityMistake;
import org.unhcr.eg.registration.tool.dataQuality.entities.provider.DataQualityMistakeDescription;
import org.unhcr.eg.registration.tool.dataQuality.entities.provider.Severity;

/**
 *
 * @author UNHCRUser
 */
public class MissingSimpleInformationEvaluator extends DataQualityEvaluator {

    private String fieldName;
    public static final String PROP_FIELDNAME = "fieldName";
    private boolean iterate;
    public static final String PROP_ITERATE = "iterate";
    private static final String MISSING_TEMPLATE = "Missing {0}";

    /**
     * Get the value of iterate
     *
     * @return the value of iterate
     */
    public boolean isIterate() {
        return iterate;
    }

    /**
     * Set the value of iterate
     *
     * @param iterate new value of iterate
     */
    public void setIterate(boolean iterate) {
        boolean oldIterate = this.iterate;
        this.iterate = iterate;
        propertyChangeSupport.firePropertyChange(PROP_ITERATE, oldIterate, iterate);
    }

    public MissingSimpleInformationEvaluator(String fieldName, String fieldPath, Severity severity, DataQualityContext qualityContext) {
        this.fieldName = fieldName;
        this.iterate = false;
        this.fieldPath = fieldPath;
        this.severity = severity;
        this.context = qualityContext;
    }

    public MissingSimpleInformationEvaluator(String fieldName, String fieldPath, Severity severity, DataQualityContext context, boolean iterate) {
        this.fieldName = fieldName;
        this.iterate = iterate;
        this.fieldPath = fieldPath;
        this.severity = severity;
        this.context = context;
    }
    private String fieldPath;
    public static final String PROP_FIELDPATH = "fieldPath";
    private Severity severity;
    private DataQualityContext context;

    /**
     * Get the value of context
     *
     * @return the value of context
     */
    public DataQualityContext getContext() {
        return context;
    }

    /**
     * Get the value of severity
     *
     * @return the value of severity
     */
    public Severity getSeverity() {
        return severity;
    }

    /**
     * Get the value of fieldPath
     *
     * @return the value of fieldPath
     */
    public String getFieldPath() {
        return fieldPath;
    }

    /**
     * Set the value of fieldPath
     *
     * @param fieldPath new value of fieldPath
     */
    public void setFieldPath(String fieldPath) {
        String oldFieldPath = this.fieldPath;
        this.fieldPath = fieldPath;
        propertyChangeSupport.firePropertyChange(PROP_FIELDPATH, oldFieldPath, fieldPath);
    }

    /**
     * Get the value of fieldName
     *
     * @return the value of fieldName
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Set the value of fieldName
     *
     * @param fieldName new value of fieldName
     */
    public void setFieldName(String fieldName) {
        String oldFieldName = this.fieldName;
        this.fieldName = fieldName;
        propertyChangeSupport.firePropertyChange(PROP_FIELDNAME, oldFieldName, fieldName);
    }
    private transient final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    /**
     * Add PropertyChangeListener.
     *
     * @param listener
     */
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Remove PropertyChangeListener.
     *
     * @param listener
     */
    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    @Override
    public String getName() {
        return MessageFormat.format(MISSING_TEMPLATE, fieldName);
    }

    @Override
    public String getDescription() {
        return "Evaluate the presence of information in Individual File";
    }

    @Override
    public List<DataQualityMistake> evaluate(DataIndividual dataIndividual) {
        JXPathContext contextPath = JXPathContext.newContext(dataIndividual);
        ArrayList list = null;
        if (!iterate) {
            Object fieldValue = contextPath.getValue(fieldPath);
            DataQualityMistake mistake = getMistake(dataIndividual, fieldValue);
            if (mistake != null) {
                list = new ArrayList();
                list.add(mistake);
            }
        } else {
            Iterator iterator = contextPath.iterate(fieldPath);
            if (iterator.hasNext()) {
                list = new ArrayList();
            }
            for (Iterator it = iterator; it.hasNext();) {
                Object fieldValue = it.next();
                DataQualityMistake mistake = getMistake(dataIndividual, fieldValue);
                if (mistake != null) {
                    list.add(mistake);
                }
            }
        }
        return list;
    }

    protected DataQualityMistake getMistake(DataIndividual dataIndividual, Object fieldValue) {
        final DataQualityMistakeDescription missingIndividualMistakeDescription = getMissingIndividualMistakeDescription(fieldName, context, severity, this);
        getDataQualityMistakeDescriptions().add(missingIndividualMistakeDescription);
        if (isMissingValue(fieldValue)) {
            return new DataQualityMistake(dataIndividual, MessageFormat.format(MISSING_TEMPLATE, fieldName), missingIndividualMistakeDescription) {

            };
        }
        return null;
    }

}
