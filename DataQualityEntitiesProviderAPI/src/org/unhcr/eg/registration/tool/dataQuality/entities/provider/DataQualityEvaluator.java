/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.tool.dataQuality.entities.provider;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.apache.commons.jxpath.JXPathContext;
import org.unhcr.eg.data.quality.proGres.entity.CodeRelationship;
import org.unhcr.eg.data.quality.proGres.entity.DataIndividual;
import org.unhcr.eg.data.quality.proGres.entity.DataIndividualProcessGroup;
import org.unhcr.eg.data.quality.proGres.entity.DataProcessGroup;

/**
 *
 * @author UNHCRUser
 */
public abstract class DataQualityEvaluator {

    public static final ArrayList<String> marriedMaritalStatusCode = new ArrayList<>(
            Arrays.asList("CL", "MA"));

    public static final ArrayList<String> activeProcessStatusCode = new ArrayList<>(
            Arrays.asList("A", "H"));

    public static final ArrayList<String> childrenBloodRelationshipCode = new ArrayList<>(
            Arrays.asList("DAU", "GCF", "GCM", "SON"));

    public static final ArrayList<String> bloodRelationshipCode = new ArrayList<>(
            Arrays.asList("ANT", "COF", "COM", "DAU", "GCF", "GCM", "GPF", "GPM", "HAB", "HAS", "NCE", "NEP", "PRF", "PRM", "SBF", "SBM", "SON", "UNC"));

    public static final ArrayList<String> bloodWomenRelationshipCode = new ArrayList<>(
            Arrays.asList("ANT", "COF", "COM", "DAU", "GCF", "GCM", "GPF", "GPM", "HAB", "HAS", "NCE", "NEP", "PRF", "PRM", "SBF", "SBM", "SON", "UNC"));

    public static final String DATE_COMPARAISON_TEMPLATE = "{0} must precede {1}";
    public static final String VALUE_COMPARAISON_TEMPLATE = "{0} is not the same as {1}";
    public static final String VALUE_COMPARAISON_TEMPLATE_T = "Mismatching of {0} with {1}";
    public static final String VALUE_COMPARAISON_TEMPLATE_TBR = "Blood Relationship : Mismatching of {0} with {1}";
    public static final String MISSING_TEMPLATE = "Missing {0}";
    public static final String MISSING_TEMPLATE_DOCUMENT = "Missing {0} for document {1}";
    public static final String MISSING_TEMPLATE_DOCUMENT_T = "Missing {0}";
    public static final String MISSING_TEMPLATE_ADDRESS = "Missing {0} for address type {1}";

    public static final String VALUE_MARITAL_STATUS_RELATIONSHIP_TEMPLATE = "{0} {1}, sex {2} with {3} Dependant";
    public static final String VALUE_MARITAL_STATUS_RELATIONSHIP_TEMPLATE_T = "Mismatching between Marital Status and Relationships";

    public static final String VALUE_POLY_RELATIONSHIP_TEMPLATE = "The {0} has more than one spouse in the same case";

    private boolean activeIndividualOnly;

    public static final String PROP_ACTIVEINDIVIDUALONLY = "activeIndividualOnly";

    private List<DataQualityMistakeDescription> dataQualityMistakeDescriptions;

    public static final String PROP_DATAQUALITYMISTAKEDESCRIPTIONS = "dataQualityMistakeDescriptions";

    /**
     * Get the value of dataQualityMistakeDescriptions
     *
     * @return the value of dataQualityMistakeDescriptions
     */
    public List<DataQualityMistakeDescription> getDataQualityMistakeDescriptions() {
        if (dataQualityMistakeDescriptions == null) {
            dataQualityMistakeDescriptions = new ArrayList<>();
        }
        return dataQualityMistakeDescriptions;
    }

    protected DataQualityMistakeDescription getChronologyMistakeDescription(String firstName, String secondName, DataQualityContext context, Severity severity, DataQualityEvaluator evaluator) {
        return new DataQualityMistakeDescription(MessageFormat.format(DATE_COMPARAISON_TEMPLATE, firstName, secondName), evaluator, context, severity);
    }

    protected DataQualityMistakeDescription getMissingIndividualMistakeDescription(String fieldName, DataQualityContext context, Severity severity, DataQualityEvaluator evaluator) {
        return new DataQualityMistakeDescription(MessageFormat.format(MISSING_TEMPLATE, fieldName), evaluator, context, severity);
    }

    protected DataQualityMistakeDescription getMissingForDocumentMistakeDescription(String fieldName, DataQualityContext context, Severity severity, DataQualityEvaluator evaluator) {
        return new DataQualityMistakeDescription(MessageFormat.format(MISSING_TEMPLATE_DOCUMENT_T, fieldName), evaluator, context, severity);
    }

    protected DataQualityMistakeDescription getMissingForAddressMistakeDescription(String fieldName, String addressType, DataQualityContext context, Severity severity, DataQualityEvaluator evaluator) {
        return new DataQualityMistakeDescription(MessageFormat.format(MISSING_TEMPLATE_ADDRESS, fieldName, addressType), evaluator, context, severity);
    }

    /**
     * Set the value of dataQualityMistakeDescriptions
     *
     * @param dataQualityMistakeDescriptions new value of
     * dataQualityMistakeDescriptions
     */
    public void setDataQualityMistakeDescriptions(List<DataQualityMistakeDescription> dataQualityMistakeDescriptions) {
        List<DataQualityMistakeDescription> oldDataQualityMistakeDescriptions = this.dataQualityMistakeDescriptions;
        this.dataQualityMistakeDescriptions = dataQualityMistakeDescriptions;
        propertyChangeSupport.firePropertyChange(PROP_DATAQUALITYMISTAKEDESCRIPTIONS, oldDataQualityMistakeDescriptions, dataQualityMistakeDescriptions);
    }

    /**
     * Get the value of activeIndividualOnly
     *
     * @return the value of activeIndividualOnly
     */
    public boolean isActiveIndividualOnly() {
        return activeIndividualOnly;
    }

    /**
     * Set the value of activeIndividualOnly
     *
     * @param activeIndividualOnly new value of activeIndividualOnly
     */
    public void setActiveIndividualOnly(boolean activeIndividualOnly) {
        boolean oldActiveIndividualOnly = this.activeIndividualOnly;
        this.activeIndividualOnly = activeIndividualOnly;
        propertyChangeSupport.firePropertyChange(PROP_ACTIVEINDIVIDUALONLY, oldActiveIndividualOnly, activeIndividualOnly);
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

    private boolean active = true;

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

    public abstract String getName();

    public abstract String getDescription();

    public abstract List<DataQualityMistake> evaluate(DataIndividual dataIndividual);

    public static String convertDateToString(Date date) {
        SimpleDateFormat dateformatYYYYMMDD = new SimpleDateFormat("yyyyMMdd");
        StringBuilder nowYYYYMMDD = new StringBuilder(dateformatYYYYMMDD.format(date));
        return nowYYYYMMDD.toString();
    }

    public static Object getFieldValue(String fieldPath, Object contextContent) {
        JXPathContext context = JXPathContext.newContext(contextContent);
        Object fieldValue = context.getValue(fieldPath);
        return fieldValue;
    }

    public static int getDiffYears(Date first, Date last) {
        Calendar a = getCalendar(first);
        Calendar b = getCalendar(last);
        int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
        if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH)
                || (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
            diff--;
        }
        return diff;
    }

    public static Map<DataIndividualGroup, Map<CodeRelationship, Set<DataIndividual>>> getIndividuals(DataIndividual dataIndividual) {
        Map<DataIndividualGroup, Map<CodeRelationship, Set<DataIndividual>>> groupDetails = null;
        List<DataIndividualProcessGroup> individualProcessGroups = dataIndividual.getDataIndividualProcessGroupList();
        for (Iterator<DataIndividualProcessGroup> it = individualProcessGroups.iterator(); it.hasNext();) {
            if (groupDetails == null) {
                groupDetails = new HashMap<>();
            }
            DataIndividualProcessGroup dataIndividualProcessGroup = it.next();
            final DataProcessGroup processingGroup = dataIndividualProcessGroup.getProcessingGroupGUID();
            final DataIndividualGroup dataIndividualGroup = new DataIndividualGroup(dataIndividualProcessGroup.getRelationshipToPrincipalRepresentative(), processingGroup);
            if (!groupDetails.containsKey(dataIndividualGroup)) {
                groupDetails.put(dataIndividualGroup, new HashMap<CodeRelationship, Set<DataIndividual>>());
            }
            Map<CodeRelationship, Set<DataIndividual>> individuals = groupDetails.get(dataIndividualGroup);
            for (DataIndividualProcessGroup dataIndividualProcessGroup1 : processingGroup.getDataIndividualProcessGroupList()) {
                if (!dataIndividualProcessGroup1.getIndividualGUID().getIndividualID().equals(dataIndividual.getIndividualID())) {
                    if (!individuals.containsKey(dataIndividualProcessGroup1.getRelationshipToPrincipalRepresentative())) {
                        individuals.put(dataIndividualProcessGroup1.getRelationshipToPrincipalRepresentative(), new HashSet<DataIndividual>());
                    }
                    Set<DataIndividual> dataIndividuals = individuals.get(dataIndividualProcessGroup1.getRelationshipToPrincipalRepresentative());
                    dataIndividuals.add(dataIndividualProcessGroup1.getIndividualGUID());
                }
            }
        }
        return groupDetails;
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public ArrayList<DataQualityMistake> dateABeforeDateB(DataIndividual dataIndividual, String firstLabel, String firstName, String secondLabel, String secondName, DataQualityEvaluator evaluator, ArrayList<DataQualityMistake> list, final DataQualityContext context, final Severity severity, Date lastModification, String lastModifier) {
        Date first = (Date) getFieldValue(firstLabel, dataIndividual);
        Date second = (Date) getFieldValue(secondLabel, dataIndividual);
        final DataQualityMistakeDescription chronologyMistakeDescription = getChronologyMistakeDescription(firstName, secondName, context, severity, evaluator);
        getDataQualityMistakeDescriptions().add(chronologyMistakeDescription);
        if (second == null || first == null) {
            return list;
        }
        if (second.before(first)) {
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(new DataQualityMistakeImpl(dataIndividual, MessageFormat.format(DATE_COMPARAISON_TEMPLATE, firstName, secondName), chronologyMistakeDescription, lastModification, lastModifier));
        }
        return list;
    }

    public ArrayList<DataQualityMistake> dateABeforeDateB(DataIndividual dataIndividual, String firstLabel, String firstName, String secondLabel, String secondName, DataQualityEvaluator evaluator, ArrayList<DataQualityMistake> list, final DataQualityContext context, final Severity severity) {
        Date first = (Date) getFieldValue(firstLabel, dataIndividual);
        Date second = (Date) getFieldValue(secondLabel, dataIndividual);
        final DataQualityMistakeDescription chronologyMistakeDescription = getChronologyMistakeDescription(firstName, secondName, context, severity, evaluator);
        getDataQualityMistakeDescriptions().add(chronologyMistakeDescription);
        if (second == null || first == null) {
            return list;
        }
        if (second.before(first)) {
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(new DataQualityMistakeImpl(dataIndividual, MessageFormat.format(DATE_COMPARAISON_TEMPLATE, firstName, secondName), chronologyMistakeDescription, dataIndividual.getUpdateDate(), dataIndividual.getUserIDUpdate()));
        }
        return list;
    }

    public static boolean isMissingValue(Object fieldValue) {
        if (fieldValue instanceof Collection) {
            return null == fieldValue || ((Collection) fieldValue).isEmpty();
        }

        return fieldValue == null || fieldValue.toString().equals("") || fieldValue.toString().equals("-");
    }

    public static class DataIndividualGroup {

        private CodeRelationship codeRelationship;
        private DataProcessGroup dataProcessGroup;

        public DataIndividualGroup(CodeRelationship codeRelationship, DataProcessGroup dataProcessGroup) {
            this.codeRelationship = codeRelationship;
            this.dataProcessGroup = dataProcessGroup;
        }

        public CodeRelationship getCodeRelationship() {
            return codeRelationship;
        }

        public DataProcessGroup getDataProcessGroup() {
            return dataProcessGroup;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 61 * hash + Objects.hashCode(this.codeRelationship);
            hash = 61 * hash + Objects.hashCode(this.dataProcessGroup);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final DataIndividualGroup other = (DataIndividualGroup) obj;
            if (!Objects.equals(this.codeRelationship, other.codeRelationship)) {
                return false;
            }
            if (!Objects.equals(this.dataProcessGroup, other.dataProcessGroup)) {
                return false;
            }
            return true;
        }
    }

    public static class DataQualityMistakeImpl extends DataQualityMistake {

        public DataQualityMistakeImpl(DataIndividual individual, String message, DataQualityMistakeDescription mistakeDescription, Date lastModification, String lastModifier) {
            super(individual, message, mistakeDescription, lastModification, lastModifier);

        }

        public DataQualityMistakeImpl(DataIndividual individual, String message, DataQualityMistakeDescription mistakeDescription) {
            super(individual, message, mistakeDescription, individual.getUpdateDate(), individual.getUserIDUpdate());

        }
    }
}
