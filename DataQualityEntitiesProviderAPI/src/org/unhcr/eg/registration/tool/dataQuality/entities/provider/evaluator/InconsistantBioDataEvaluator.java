/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.tool.dataQuality.entities.provider.evaluator;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.unhcr.eg.data.quality.proGres.entity.CodeMaritalStatus;
import org.unhcr.eg.data.quality.proGres.entity.CodeRelationship;
import org.unhcr.eg.data.quality.proGres.entity.CodeSex;
import org.unhcr.eg.data.quality.proGres.entity.DataIndividual;
import org.unhcr.eg.registration.tool.dataQuality.entities.provider.DataQualityContext;
import org.unhcr.eg.registration.tool.dataQuality.entities.provider.DataQualityEvaluator;
import org.unhcr.eg.registration.tool.dataQuality.entities.provider.DataQualityMistake;
import org.unhcr.eg.registration.tool.dataQuality.entities.provider.DataQualityMistakeDescription;
import org.unhcr.eg.registration.tool.dataQuality.entities.provider.Severity;
import org.unhcr.eg.registration.tool.dataQuality.entities.provider.context.DefaultDataQualityContextFactory;

/**
 *
 * @author UNHCRUser
 */
public final class InconsistantBioDataEvaluator extends DataQualityEvaluator {

    @Override
    public String getName() {
        return "Inconsistant Bio Data Evaluator";
    }

    @Override
    public String getDescription() {
        return "Evaluate the consistancy of information in Individual File";
    }

    @Override
    public List<DataQualityMistake> evaluate(DataIndividual dataIndividual) {
        ArrayList<DataQualityMistake> list = null;
        list = checkChronologyConsistancies(list, dataIndividual);
        list = checkBioDataConsistancies(list, dataIndividual);
        return list;
    }

    protected ArrayList<DataQualityMistake> checkBioDataConsistancies(ArrayList<DataQualityMistake> list, DataIndividual dataIndividual) {
        CodeMaritalStatus maritalStatus = dataIndividual.getMarriageStatusCode();
        int numberOfSpouse = 0;
        Short age = dataIndividual.getIndividualAge();
        CodeSex sex = dataIndividual.getSexCode();
        Map<DataIndividualGroup, Map<CodeRelationship, Set<DataIndividual>>> individuals = getIndividuals(dataIndividual);

        // Principal Applicant 
        for (Map.Entry<DataIndividualGroup, Map<CodeRelationship, Set<DataIndividual>>> entry : individuals.entrySet()) {
            DataIndividualGroup dataIndividualGroup = entry.getKey();
            String mainRelationShip = (String) getFieldValue("codeRelationshipTextList[codeRelationshipTextPK/relationshipLanguageCode='ENG']/relationshipText", dataIndividualGroup.getCodeRelationship());
            String mainMaritalStatus = (String) getFieldValue("codeMaritalStatusTextList[codeMaritalStatusTextPK/maritalStatusLanguageCode='ENG']/maritalStatusText", maritalStatus);
            Map<CodeRelationship, Set<DataIndividual>> map = entry.getValue();
            if (!dataIndividualGroup.getCodeRelationship().getRelationshipCode().equals("U") && dataIndividualGroup.getCodeRelationship().getRelationshipRank().equals(Short.valueOf("1"))) {
                for (Map.Entry<CodeRelationship, Set<DataIndividual>> entry1 : map.entrySet()) {
                    CodeRelationship codeRelationship = entry1.getKey();
                    String dependantRelationShip = (String) getFieldValue("codeRelationshipTextList[codeRelationshipTextPK/relationshipLanguageCode='ENG']/relationshipText", codeRelationship);
                    // Marital status check
                    if (codeRelationship.getRelationshipRank().equals(Short.valueOf("2")) && !marriedMaritalStatusCode.contains(maritalStatus.getMaritalStatusCode())) {
                        final DataQualityMistakeDescription mistakeDescription = new DataQualityMistakeDescription(VALUE_MARITAL_STATUS_RELATIONSHIP_TEMPLATE_T, this, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL);
                        getDataQualityMistakeDescriptions().add(mistakeDescription);
                        list.add(new DataQualityMistakeImpl(dataIndividual, MessageFormat.format(VALUE_MARITAL_STATUS_RELATIONSHIP_TEMPLATE, mainRelationShip, mainMaritalStatus, sex.getSexCode(), dependantRelationShip), mistakeDescription));
                    }
                    if (codeRelationship.getRelationshipRank().equals(Short.valueOf("2"))) {
                        numberOfSpouse++;
                    }
                    Set<DataIndividual> set = entry1.getValue();
                    for (DataIndividual dataIndividualDependant : set) {
                        list = checkBloodAndValueComparaisonMistake(list, dataIndividual, "Ethinicity", "ethnicityCode", mainRelationShip, dataIndividualDependant, codeRelationship, DefaultDataQualityContextFactory.GLOBAL, Severity.WARNING);
                        list = checkBloodAndValueComparaisonMistake(list, dataIndividual, "Religion", "religionCode", mainRelationShip, dataIndividualDependant, codeRelationship, DefaultDataQualityContextFactory.GLOBAL, Severity.WARNING);
                        list = checkBloodAndValueComparaisonMistake(list, dataIndividual, "Family Name", "familyName", mainRelationShip, dataIndividualDependant, codeRelationship, DefaultDataQualityContextFactory.GLOBAL, Severity.WARNING);
                        list = checkValueComparaisonMistake(list, dataIndividual, "Nationality", "nationalityCode", mainRelationShip, dataIndividualDependant, codeRelationship, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL);
                        list = checkValueComparaisonMistake(list, dataIndividual, "Birth Country", "birthCountryCode", mainRelationShip, dataIndividualDependant, codeRelationship, DefaultDataQualityContextFactory.GLOBAL, Severity.WARNING);
                        list = checkValueComparaisonMistake(list, dataIndividual, "Asylum Country", "asylumCountryCode", mainRelationShip, dataIndividualDependant, codeRelationship, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL);
                        list = checkValueComparaisonMistake(list, dataIndividual, "Origin Country", "originCountryCode", mainRelationShip, dataIndividualDependant, codeRelationship, DefaultDataQualityContextFactory.GLOBAL, Severity.WARNING);
                    }
                }
                if (numberOfSpouse >= 2) {
                    final DataQualityMistakeDescription mistakeDescription = new DataQualityMistakeDescription(MessageFormat.format(VALUE_POLY_RELATIONSHIP_TEMPLATE, "Principal Applicant or Household Representative"), this, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL);
                    getDataQualityMistakeDescriptions().add(mistakeDescription);
                    list.add(new DataQualityMistakeImpl(dataIndividual, MessageFormat.format(VALUE_POLY_RELATIONSHIP_TEMPLATE, mainRelationShip), mistakeDescription));
                }
            }

        }
        return list;
    }

    protected ArrayList<DataQualityMistake> checkBloodAndValueComparaisonMistake(ArrayList<DataQualityMistake> list, DataIndividual dataIndividual, String fieldName, String fieldPath, String mainRelationShip, DataIndividual depedant, CodeRelationship codeRelationship, DataQualityContext context, Severity severity) {
        DataQualityMistakeDescription mistakeDescription = null;
        if (mainRelationShip != null) {
            mistakeDescription = new DataQualityMistakeDescription(MessageFormat.format(VALUE_COMPARAISON_TEMPLATE_TBR, fieldName, mainRelationShip), this, context, severity);
            getDataQualityMistakeDescriptions().add(mistakeDescription);
        } else {
            mistakeDescription = new DataQualityMistakeDescription(MessageFormat.format(VALUE_COMPARAISON_TEMPLATE_TBR, fieldName, "Principal Applicant"), this, context, severity);
            getDataQualityMistakeDescriptions().add(mistakeDescription);
            mistakeDescription = new DataQualityMistakeDescription(MessageFormat.format(VALUE_COMPARAISON_TEMPLATE_TBR, fieldName, "Household Representative"), this, context, severity);
            getDataQualityMistakeDescriptions().add(mistakeDescription);
        }
        if (depedant != null && dataIndividual != null) {
            Object dependantValue = getFieldValue(fieldPath, depedant);
            Object mainValue = getFieldValue(fieldPath, dataIndividual);
            if (codeRelationship != null && mainValue != null && dependantValue != null && bloodRelationshipCode.contains(codeRelationship.getRelationshipCode()) && !mainValue.equals(dependantValue)) {
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(new DataQualityMistakeImpl(depedant, MessageFormat.format(VALUE_COMPARAISON_TEMPLATE, fieldName, mainRelationShip), mistakeDescription));
            }
        }
        return list;
    }

    protected ArrayList<DataQualityMistake> checkValueComparaisonMistake(ArrayList<DataQualityMistake> list, DataIndividual dataIndividual, String fieldName, String fieldPath, String mainRelationShip, DataIndividual depedant, CodeRelationship codeRelationship, DataQualityContext context, Severity severity) {
        DataQualityMistakeDescription mistakeDescription = null;
        if (mainRelationShip != null) {
            mistakeDescription = new DataQualityMistakeDescription(MessageFormat.format(VALUE_COMPARAISON_TEMPLATE_TBR, fieldName, mainRelationShip), this, context, severity);
            getDataQualityMistakeDescriptions().add(mistakeDescription);
        } else {
            mistakeDescription = new DataQualityMistakeDescription(MessageFormat.format(VALUE_COMPARAISON_TEMPLATE_TBR, fieldName, "Principal Applicant"), this, context, severity);
            getDataQualityMistakeDescriptions().add(mistakeDescription);
            mistakeDescription = new DataQualityMistakeDescription(MessageFormat.format(VALUE_COMPARAISON_TEMPLATE_TBR, fieldName, "Household Representative"), this, context, severity);
            getDataQualityMistakeDescriptions().add(mistakeDescription);
        }
        if (depedant != null && dataIndividual != null) {
            Object dependantValue = getFieldValue(fieldPath, depedant);
            Object mainValue = getFieldValue(fieldPath, dataIndividual);
            if (codeRelationship != null && mainValue != null && dependantValue != null && !mainValue.equals(dependantValue)) {
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(new DataQualityMistakeImpl(depedant, MessageFormat.format(VALUE_COMPARAISON_TEMPLATE, fieldName, mainRelationShip), mistakeDescription));
            }
        }
        return list;
    }

    protected void createBioDataMistakesList() {
        DataQualityMistakeDescription mistakeDescription = new DataQualityMistakeDescription(VALUE_MARITAL_STATUS_RELATIONSHIP_TEMPLATE_T, this, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL);
        getDataQualityMistakeDescriptions().add(mistakeDescription);
        checkBloodAndValueComparaisonMistake(null, null, "Ethinicity", "ethnicityCode", null, null, null, DefaultDataQualityContextFactory.GLOBAL, Severity.WARNING);
        checkBloodAndValueComparaisonMistake(null, null, "Religion", "religionCode", null, null, null, DefaultDataQualityContextFactory.GLOBAL, Severity.WARNING);
        checkBloodAndValueComparaisonMistake(null, null, "Family Name", "familyName", null, null, null, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL);
        checkValueComparaisonMistake(null, null, "Nationality", "nationalityCode", null, null, null, DefaultDataQualityContextFactory.GLOBAL, Severity.WARNING);
        checkValueComparaisonMistake(null, null, "Birth Country", "birthCountryCode", null, null, null, DefaultDataQualityContextFactory.GLOBAL, Severity.WARNING);
        checkValueComparaisonMistake(null, null, "Asylum Country", "asylumCountryCode", null, null, null, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL);
        checkValueComparaisonMistake(null, null, "Origin Country", "originCountryCode", null, null, null, DefaultDataQualityContextFactory.GLOBAL, Severity.WARNING);
        mistakeDescription = new DataQualityMistakeDescription(MessageFormat.format(VALUE_POLY_RELATIONSHIP_TEMPLATE, "Principal Applicant or Household Representative"), this, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL);
        getDataQualityMistakeDescriptions().add(mistakeDescription);
    }

    public InconsistantBioDataEvaluator() {
        createBioDataMistakesList();
        checkChronologyConsistancies(null, new DataIndividual());
    }

    protected ArrayList<DataQualityMistake> checkChronologyConsistancies(ArrayList<DataQualityMistake> list, DataIndividual dataIndividual) {
        list = (dateABeforeDateB(dataIndividual, "fledDate", "Fled Date", "arrivalDate", "Arrival Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "arrivalDate", "Arrival Date", "registrationDate", "Registration Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "arrivalDate", "Arrival Date", "departureDate", "Departure Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "arrivalDate", "Arrival Date", "deceasedDate", "Registration Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "arrivalDate", "Arrival Date", "durableSolutionStatusDate", "Departure Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "arrivalDate", "Arrival Date", "deceasedDate", "Deceased Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "arrivalDate", "Arrival Date", "localIntegrationStatusDate", "Local Integration Status Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "arrivalDate", "Arrival Date", "processStatusDate", "Process Status Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "arrivalDate", "Arrival Date", "protectionStatusDate", "Protection Status Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        //    list = (dateABeforeDateB(dataIndividual, "arrivalDate", "Arrival Date", "rSDStatusDate", "RSD Status Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "arrivalDate", "Arrival Date", "refugeeStatusCategoryDate", "Refugee Status Category Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "arrivalDate", "Arrival Date", "refugeeStatusDate", "Refugee Status Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "arrivalDate", "Arrival Date", "refugeeStatusLegalBasisDate", "Refugee Status Legal Basis Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "arrivalDate", "Arrival Date", "registrationDataVerifiedDate", "Registration Data Verified Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "arrivalDate", "Arrival Date", "registrationDate", "Registration Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "arrivalDate", "Arrival Date", "registrationStatusDate", "Registration Status Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "arrivalDate", "Arrival Date", "resettlementStatusDate", "Resettlement Status Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "arrivalDate", "Arrival Date", "volRepStatusDate", "VolRep Status Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "dateofBirth", "Birth Date", "arrivalDate", "Refugee Status Legal Basis Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "dateofBirth", "Birth Date", "fledDate", "Fled Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "dateofBirth", "Birth Date", "marriageDate", "Marriage Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "dateofBirth", "Birth Date", "registrationDate", "Registration Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "dateofBirth", "Birth Date", "departureDate", "Departure Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "dateofBirth", "Birth Date", "durableSolutionStatusDate", "Durable Solution Status Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "dateofBirth", "Birth Date", "deceasedDate", "Deceased Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "dateofBirth", "Birth Date", "localIntegrationStatusDate", "Local Integration Status Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "dateofBirth", "Birth Date", "processStatusDate", "Process Status Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "dateofBirth", "Birth Date", "protectionStatusDate", "Protection Status Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
//        list = (dateABeforeDateB(dataIndividual, "dateofBirth", "Birth Date", "rSDStatusDate", "RSD Status Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "dateofBirth", "Birth Date", "refugeeStatusCategoryDate", "Refugee Status Category Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "dateofBirth", "Birth Date", "refugeeStatusDate", "Refugee Status Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "dateofBirth", "Birth Date", "refugeeStatusLegalBasisDate", "Refugee Status Legal Basis Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "dateofBirth", "Birth Date", "registrationDataVerifiedDate", "Registration Data Verified Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "dateofBirth", "Birth Date", "registrationDate", "Registration Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "dateofBirth", "Birth Date", "registrationStatusDate", "Registration Status Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "dateofBirth", "Birth Date", "resettlementStatusDate", "Resettlement Status Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "dateofBirth", "Birth Date", "volRepStatusDate", "VolRep Status Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "registrationDate", "Registration Date", "volRepStatusDate", "VolRep Status Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "registrationDate", "Registration Date", "departureDate", "Departure Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "registrationDate", "Registration Date", "deceasedDate", "Registration Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "registrationDate", "Registration Date", "durableSolutionStatusDate", "Departure Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "registrationDate", "Registration Date", "deceasedDate", "Deceased Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "registrationDate", "Registration Date", "localIntegrationStatusDate", "Local Integration Status Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "registrationDate", "Registration Date", "processStatusDate", "Process Status Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "registrationDate", "Registration Date", "protectionStatusDate", "Protection Status Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
//        list = (dateABeforeDateB(dataIndividual, "registrationDate", "Registration Date", "rSDStatusDate", "RSD Status Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "registrationDate", "Registration Date", "refugeeStatusCategoryDate", "Refugee Status Category Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "registrationDate", "Registration Date", "refugeeStatusDate", "Refugee Status Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "registrationDate", "Registration Date", "refugeeStatusLegalBasisDate", "Refugee Status Legal Basis Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "registrationDate", "Registration Date", "registrationDataVerifiedDate", "Registration Data Verified Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "registrationDate", "Registration Date", "registrationStatusDate", "Registration Status Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "registrationDate", "Registration Date", "resettlementStatusDate", "Resettlement Status Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "registrationDate", "Registration Date", "volRepStatusDate", "VolRep Status Date", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "fatherBirthDate", "Father Birth Date", "dateofBirth", "Date Of Birth", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        list = (dateABeforeDateB(dataIndividual, "motherBirthDate", "Mother Birth Date", "dateofBirth", "Date Of Birth", this, list, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL));
        return list;
    }

}
