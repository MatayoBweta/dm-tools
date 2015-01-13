/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.tool.dataQuality.entities.provider.evaluator;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
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
public final class MissingBioDatatEvaluator extends DataQualityEvaluator {

    public MissingBioDatatEvaluator() {
        evaluate(new DataIndividual());
    }

    @Override
    public String getName() {
        return "Bio Data Evaluator";
    }

    @Override
    public String getDescription() {
        return "Evaluate the quality of information related to Bio Data";
    }

    @Override
    public List<DataQualityMistake> evaluate(DataIndividual dataIndividual) {
        ArrayList<DataQualityMistake> list = null;
        list = checkMissingValue(list, dataIndividual, "Arrival Date", "arrivalDate", dataIndividual.getArrivalDate(), DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL);
        list = checkMissingValue(list, dataIndividual, "Asylum Country", "asylumCountryCode", dataIndividual.getAsylumCountryCode(), DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL);
        list = checkMissingValue(list, dataIndividual, "Birth Country ", "birthCountryCode", dataIndividual.getBirthCountryCode(), DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL);
        list = checkMissingValue(list, dataIndividual, "Birth Date", "dateofBirth", dataIndividual.getDateofBirth(), DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL);
        list = checkMissingValue(list, dataIndividual, "Education Level", "educationLevelCode", dataIndividual.getEducationLevelCode(), DefaultDataQualityContextFactory.GLOBAL, Severity.WARNING);
        list = checkMissingValue(list, dataIndividual, "Ethnicity", "ethnicityCode", dataIndividual.getEthnicityCode(), DefaultDataQualityContextFactory.GLOBAL, Severity.WARNING);
        list = checkMissingValue(list, dataIndividual, "Fled Date", "fledDate", dataIndividual.getFledDate(), DefaultDataQualityContextFactory.GLOBAL, Severity.WARNING);

        list = checkMissingValue(list, dataIndividual, "Interview Preference Language", "interviewPreferenceLanguageCode", dataIndividual.getInterviewPreferenceLanguageCode(), DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION);
        list = checkMissingValue(list, dataIndividual, "Interviewer Preference Sex", "interviewerPreferenceSexCode", dataIndividual.getInterviewerPreferenceSexCode(), DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION);
        list = checkMissingValue(list, dataIndividual, "Marriage Status", "marriageStatusCode", dataIndividual.getMarriageStatusCode(), DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL);
        list = checkMissingValue(list, dataIndividual, "Nationality", "nationalityCode", dataIndividual.getNationalityCode(), DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL);
        list = checkMissingValue(list, dataIndividual, "Occupation Level", "occupationCode", dataIndividual.getOccupationCode(), DefaultDataQualityContextFactory.GLOBAL, Severity.WARNING);
        list = checkMissingValue(list, dataIndividual, "Origin Country", "originCountryCode", dataIndividual.getOriginCountryCode(), DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL);
        list = checkMissingValue(list, dataIndividual, "Registration Date", "registrationDate", dataIndividual.getRegistrationDate(), DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL);

        list = checkMissingValue(list, dataIndividual, "Religion", "religionCode", dataIndividual.getReligionCode(), DefaultDataQualityContextFactory.GLOBAL, Severity.WARNING);
        list = checkMissingValue(list, dataIndividual, "Sex", "sexCode", dataIndividual.getSexCode(), DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL);
        list = checkMissingValue(list, dataIndividual, "Willing To Return", "willingToReturnCode", dataIndividual.getWillingToReturnCode(), DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION);
        list = checkMissingValue(list, dataIndividual, "Birth City Town Village", "birthCityTownVillage", dataIndividual.getBirthCityTownVillage(), DefaultDataQualityContextFactory.GLOBAL, Severity.WARNING);

        list = checkMissingValue(list, dataIndividual, "Commonly Used Name", "commonlyUsedName", dataIndividual.getCommonlyUsedName(), DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION);
        list = checkMissingValue(list, dataIndividual, "Concatenated Name", "concatenatedName", dataIndividual.getConcatenatedName(), DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL);

        list = checkMissingValue(list, dataIndividual, "Data Sharing Decision", "datasharing", dataIndividual.getDatasharing(), DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL);

        list = checkMissingValue(list, dataIndividual, "Family Name", "familyName", dataIndividual.getFamilyName(), DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL);
        list = checkMissingValue(list, dataIndividual, "Father Name", "fatherName", dataIndividual.getFatherName(), DefaultDataQualityContextFactory.GLOBAL, Severity.WARNING);
        list = checkMissingValue(list, dataIndividual, "Given Name", "givenName", dataIndividual.getGivenName(), DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL);
        list = checkMissingValue(list, dataIndividual, "Individual Age", "individualAge", dataIndividual.getIndividualAge(), DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL);
        list = checkMissingValue(list, dataIndividual, "Maiden Name", "maidenName", dataIndividual.getMaidenName(), DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION);

        list = checkMissingValue(list, dataIndividual, "Middle Name", "middleName", dataIndividual.getMiddleName(), DefaultDataQualityContextFactory.GLOBAL, Severity.WARNING);
        list = checkMissingValue(list, dataIndividual, "Mother Name", "motherName", dataIndividual.getMotherName(), DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION);
        list = checkMissingValue(list, dataIndividual, "Refugee Status", "refugeeStatusCode", dataIndividual.getRefugeeStatusCode(), DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL);
        list = checkMissingValue(list, dataIndividual, "Refugee Status Legal Basis", "refugeeStatusLegalBasisCode", dataIndividual.getRefugeeStatusLegalBasisCode(), DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL);
        list = checkMissingValue(list, dataIndividual, "Refugee Status Category", "refugeeStatusCategoryCode", dataIndividual.getRefugeeStatusCategoryCode(), DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL);

        list = checkMissingValue(list, dataIndividual, "Second Family Name", "secondFamilyName", dataIndividual.getSecondFamilyName(), DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION);
        list = checkMissingValue(list, dataIndividual, "Source Of Registration Data", "sourceOfRegistrationData", dataIndividual.getSourceOfRegistrationData(), DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION);
        list = checkMissingValue(list, dataIndividual, "Verbal Name", "verbalName", dataIndividual.getVerbalName(), DefaultDataQualityContextFactory.GLOBAL, Severity.WARNING);

        list = checkMissingValue(list, dataIndividual, "Addresses Folder", "dataAddressList", dataIndividual.getDataAddressList(), DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL);
        list = checkMissingValue(list, dataIndividual, "Documents Folder", "dataDocumentList", dataIndividual.getDataDocumentList(), DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL);
        list = checkMissingValue(list, dataIndividual, "Educations Folder", "dataEducationList", dataIndividual.getDataEducationList(), DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION);
        list = checkMissingValue(list, dataIndividual, "Employements Folder", "dataEmploymentList", dataIndividual.getDataEmploymentList(), DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION);
        list = checkMissingValue(list, dataIndividual, "Language Folder", "dataLanguageList", dataIndividual.getDataLanguageList(), DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION);
        list = checkMissingValue(list, dataIndividual, "Photographs Folder", "dataPhotographList", dataIndividual.getDataPhotographList(), DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL);
        list = checkMissingValue(list, dataIndividual, "Vulnerabilities Folder", "dataVulnerabilityList", dataIndividual.getDataVulnerabilityList(), DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION);

        return list;
    }

    public ArrayList<DataQualityMistake> checkMissingValue(ArrayList list, DataIndividual dataIndividual, String fieldName, String fieldLabel, Object fieldValue, final DataQualityContext context, final Severity severity) {
        final DataQualityMistakeDescription missingIndividualMistakeDescription = getMissingIndividualMistakeDescription(fieldName, context, severity, this);
        getDataQualityMistakeDescriptions().add(missingIndividualMistakeDescription);
        if (DataQualityEvaluator.isMissingValue(fieldValue)) {
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(new DataQualityMistake(dataIndividual, MessageFormat.format(MISSING_TEMPLATE, fieldName, DataQualityEvaluator.getFieldValue(fieldLabel, dataIndividual)), missingIndividualMistakeDescription, dataIndividual.getUpdateDate(), dataIndividual.getUserIDUpdate()) {
            });

        }
        return list;
    }
}
