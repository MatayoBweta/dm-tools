/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.tool.dataQuality.entities.provider.evaluator;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import org.unhcr.eg.data.quality.proGres.entity.DataAddress;
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
public final class MissingAddresseEvaluator extends DataQualityEvaluator {

    public MissingAddresseEvaluator() {
        createMistakeDescription();
    }

    @Override
    public String getName() {
        return "Missing Fields Address Evaluator";
    }

    @Override
    public String getDescription() {
        return "Evaluate the quality of information related to Address in Individual File";
    }

    public void createMistakeDescription() {
        getDataQualityMistakeDescriptions().add(getMissingForAddressMistakeDescription("Country", "COA", DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL, this));
        getDataQualityMistakeDescriptions().add(getMissingForAddressMistakeDescription("Address Phone", "COA", DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL, this));
        getDataQualityMistakeDescriptions().add(getMissingForAddressMistakeDescription("Location Level 1", "COA", DefaultDataQualityContextFactory.GLOBAL, Severity.WARNING, this));
        getDataQualityMistakeDescriptions().add(getMissingForAddressMistakeDescription("Location Level 2", "COA", DefaultDataQualityContextFactory.GLOBAL, Severity.WARNING, this));
        getDataQualityMistakeDescriptions().add(getMissingForAddressMistakeDescription("Location Level 3", "COA", DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION, this));
        getDataQualityMistakeDescriptions().add(getMissingForAddressMistakeDescription("Location Level 4", "COA", DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION, this));
        getDataQualityMistakeDescriptions().add(getMissingForAddressMistakeDescription("Location Level 5", "COA", DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION, this));
        getDataQualityMistakeDescriptions().add(getMissingForAddressMistakeDescription("Location Level 5", "COA", DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION, this));
        getDataQualityMistakeDescriptions().add(getMissingForAddressMistakeDescription("Country", "COO", DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL, this));
        getDataQualityMistakeDescriptions().add(getMissingForAddressMistakeDescription("Address Phone", "COO", DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION, this));
        getDataQualityMistakeDescriptions().add(getMissingForAddressMistakeDescription("Location Level 1", "COO", DefaultDataQualityContextFactory.GLOBAL, Severity.WARNING, this));
        getDataQualityMistakeDescriptions().add(getMissingForAddressMistakeDescription("Location Level 2", "COO", DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION, this));
        getDataQualityMistakeDescriptions().add(getMissingForAddressMistakeDescription("Location Level 3", "COO", DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION, this));
        getDataQualityMistakeDescriptions().add(getMissingForAddressMistakeDescription("Location Level 4", "COO", DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION, this));
        getDataQualityMistakeDescriptions().add(getMissingForAddressMistakeDescription("Location Level 5", "COO", DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION, this));
        getDataQualityMistakeDescriptions().add(getMissingForAddressMistakeDescription("Location Level 5", "COO", DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION, this));
        getDataQualityMistakeDescriptions().add(getMissingForAddressMistakeDescription("Country", "Transit", DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL, this));
        getDataQualityMistakeDescriptions().add(getMissingForAddressMistakeDescription("Address Phone", "Transit", DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION, this));
        getDataQualityMistakeDescriptions().add(getMissingForAddressMistakeDescription("Location Level 1", "Transit", DefaultDataQualityContextFactory.GLOBAL, Severity.WARNING, this));
        getDataQualityMistakeDescriptions().add(getMissingForAddressMistakeDescription("Location Level 2", "Transit", DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION, this));
        getDataQualityMistakeDescriptions().add(getMissingForAddressMistakeDescription("Location Level 3", "Transit", DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION, this));
        getDataQualityMistakeDescriptions().add(getMissingForAddressMistakeDescription("Location Level 4", "Transit", DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION, this));
        getDataQualityMistakeDescriptions().add(getMissingForAddressMistakeDescription("Location Level 5", "Transit", DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION, this));
        getDataQualityMistakeDescriptions().add(getMissingForAddressMistakeDescription("Location Level 5", "Transit", DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION, this));
        getDataQualityMistakeDescriptions().add(getMissingForAddressMistakeDescription("Address Date Start", "Transit", DefaultDataQualityContextFactory.GLOBAL, Severity.WARNING, this));
        getDataQualityMistakeDescriptions().add(getMissingForAddressMistakeDescription("Address Date End", "Transit", DefaultDataQualityContextFactory.GLOBAL, Severity.WARNING, this));

    }

    @Override
    public List<DataQualityMistake> evaluate(DataIndividual dataIndividual) {
        ArrayList<DataQualityMistake> list = null;
        List<DataAddress> dataAddresss = dataIndividual.getDataAddressList();
        DataAddress coa = null;
        DataAddress coo = null;
        List<DataAddress> transitCs = null;
        for (DataAddress dataAddress : dataAddresss) {
            switch (dataAddress.getAddressType().getAddressTypeCode()) {
                case "COA":
                    coa = dataAddress;
                    list = checkMissingValue(list, dataAddress, dataIndividual, "Country", dataAddress.getAddressCountry(), DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL);
                    list = checkMissingValue(list, dataAddress, dataIndividual, "Address Phone", dataAddress.getAddressPhone(), DefaultDataQualityContextFactory.GLOBAL, Severity.WARNING);
                    list = checkMissingValue(list, dataAddress, dataIndividual, "Location Level 1", dataAddress.getLocationLevel1ID(), DefaultDataQualityContextFactory.GLOBAL, Severity.WARNING);
                    list = checkMissingValue(list, dataAddress, dataIndividual, "Location Level 2", dataAddress.getLocationLevel2ID(), DefaultDataQualityContextFactory.GLOBAL, Severity.WARNING);
                    list = checkMissingValue(list, dataAddress, dataIndividual, "Location Level 3", dataAddress.getLocationLevel3ID(), DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION);
                    list = checkMissingValue(list, dataAddress, dataIndividual, "Location Level 4", dataAddress.getLocationLevel4ID(), DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION);
                    list = checkMissingValue(list, dataAddress, dataIndividual, "Location Level 5", dataAddress.getLocationLevel5ID(), DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION);
                    list = checkMissingValue(list, dataAddress, dataIndividual, "Location Level 6", dataAddress.getLocationLevel6(), DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION);
                    list = checkMissingValue(list, dataAddress, dataIndividual, "Address Comments", dataAddress.getAddressComments(), DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION);
                    break;
                case "COO":
                    coo = dataAddress;
                    list = checkMissingValue(list, dataAddress, dataIndividual, "Country", dataAddress.getAddressCountry(), DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL);
                    list = checkMissingValue(list, dataAddress, dataIndividual, "Location Level 1", dataAddress.getLocationLevel1ID(), DefaultDataQualityContextFactory.GLOBAL, Severity.WARNING);
                    list = checkMissingValue(list, dataAddress, dataIndividual, "Location Level 2", dataAddress.getLocationLevel2ID(), DefaultDataQualityContextFactory.GLOBAL, Severity.WARNING);
                    list = checkMissingValue(list, dataAddress, dataIndividual, "Location Level 3", dataAddress.getLocationLevel3ID(), DefaultDataQualityContextFactory.GLOBAL, Severity.WARNING);
                    list = checkMissingValue(list, dataAddress, dataIndividual, "Location Level 4", dataAddress.getLocationLevel4ID(), DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION);
                    list = checkMissingValue(list, dataAddress, dataIndividual, "Location Level 5", dataAddress.getLocationLevel5ID(), DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION);
                    list = checkMissingValue(list, dataAddress, dataIndividual, "Location Level 6", dataAddress.getLocationLevel6(), DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION);
                    list = checkMissingValue(list, dataAddress, dataIndividual, "Address Comments", dataAddress.getAddressComments(), DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION);
                    break;
                case "Trans":
                    if (transitCs == null) {
                        transitCs = new ArrayList<>();
                    }
                    transitCs.add(dataAddress);
                    list = checkMissingValue(list, dataAddress, dataIndividual, "Country", dataAddress.getAddressCountry(), DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL);
                    list = checkMissingValue(list, dataAddress, dataIndividual, "Address Date Start", dataAddress.getAddressDateStart(), DefaultDataQualityContextFactory.GLOBAL, Severity.WARNING);
                    list = checkMissingValue(list, dataAddress, dataIndividual, "Address Date End", dataAddress.getAddressDateEnd(), DefaultDataQualityContextFactory.GLOBAL, Severity.WARNING);

                    list = checkMissingValue(list, dataAddress, dataIndividual, "Location Level 1", dataAddress.getLocationLevel1ID(), DefaultDataQualityContextFactory.GLOBAL, Severity.WARNING);
                    list = checkMissingValue(list, dataAddress, dataIndividual, "Location Level 2", dataAddress.getLocationLevel2ID(), DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION);
                    list = checkMissingValue(list, dataAddress, dataIndividual, "Location Level 3", dataAddress.getLocationLevel3ID(), DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION);
                    list = checkMissingValue(list, dataAddress, dataIndividual, "Location Level 4", dataAddress.getLocationLevel4ID(), DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION);
                    list = checkMissingValue(list, dataAddress, dataIndividual, "Location Level 5", dataAddress.getLocationLevel5ID(), DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION);
                    list = checkMissingValue(list, dataAddress, dataIndividual, "Location Level 6", dataAddress.getLocationLevel6(), DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION);
                    list = checkMissingValue(list, dataAddress, dataIndividual, "Address Comments", dataAddress.getAddressComments(), DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION);
                    break;
                default:
                    list = checkMissingValue(list, dataAddress, dataIndividual, "Country", dataAddress.getAddressCountry(), DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL);
                    list = checkMissingValue(list, dataAddress, dataIndividual, "Location Level 1", dataAddress.getLocationLevel1ID(), DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION);
                    list = checkMissingValue(list, dataAddress, dataIndividual, "Location Level 2", dataAddress.getLocationLevel2ID(), DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION);
                    list = checkMissingValue(list, dataAddress, dataIndividual, "Location Level 3", dataAddress.getLocationLevel3ID(), DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION);
                    list = checkMissingValue(list, dataAddress, dataIndividual, "Location Level 4", dataAddress.getLocationLevel4ID(), DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION);
                    list = checkMissingValue(list, dataAddress, dataIndividual, "Location Level 5", dataAddress.getLocationLevel5ID(), DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION);
                    list = checkMissingValue(list, dataAddress, dataIndividual, "Location Level 6", dataAddress.getLocationLevel6(), DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION);
                    list = checkMissingValue(list, dataAddress, dataIndividual, "Address Comments", dataAddress.getAddressComments(), DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION);
                    break;
            }

        }
        list = checkMissingFolderValue(list, dataIndividual, "Country Of Asylum in Addresses Folder", coa, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL);
        list = checkMissingFolderValue(list, dataIndividual, "Country Of Origin in Addresses Folder", coo, DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL);
        list = checkMissingFolderValue(list, dataIndividual, "Country Of Transit in Addresses Folder", transitCs, DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION);
        return list;
    }

    protected ArrayList<DataQualityMistake> checkMissingValue(ArrayList list, DataAddress dataAddress, DataIndividual dataIndividual, String fieldName, Object fieldValue, final DataQualityContext context, final Severity severity) {
        final DataQualityMistakeDescription missingForAddressMistakeDescription = getMissingForAddressMistakeDescription(fieldName, dataAddress.getAddressType().getAddressTypeCode(), context, severity, this);
        getDataQualityMistakeDescriptions().add(missingForAddressMistakeDescription);

        if (DataQualityEvaluator.isMissingValue(fieldValue)) {
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(new DataQualityMistakeImpl(dataIndividual, MessageFormat.format(MISSING_TEMPLATE_ADDRESS, fieldName, DataQualityEvaluator.getFieldValue("addressType/codeAddressTypeTextList[codeAddressTypeTextPK/addressTypeLanguageCode='ENG']/addressTypeText", dataAddress)), missingForAddressMistakeDescription, dataAddress.getUpdateDate(), dataAddress.getUserIDUpdate()));

        }
        return list;
    }

    protected ArrayList<DataQualityMistake> checkMissingFolderValue(ArrayList list, DataIndividual dataIndividual, String fieldName, DataAddress dataAddress, final DataQualityContext context, final Severity severity) {
        final DataQualityMistakeDescription missingForAddressMistakeDescription = getMissingForAddressMistakeDescription(fieldName, dataAddress.getAddressType().getAddressTypeCode(), context, severity, this);
        getDataQualityMistakeDescriptions().add(missingForAddressMistakeDescription);

        if (DataQualityEvaluator.isMissingValue(dataAddress)) {
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(new DataQualityMistakeImpl(dataIndividual, MessageFormat.format(MISSING_TEMPLATE, fieldName), missingForAddressMistakeDescription, dataIndividual.getUpdateDate(), dataIndividual.getUserIDUpdate()));

        }
        return list;
    }

    protected ArrayList<DataQualityMistake> checkMissingFolderValue(ArrayList list, DataIndividual dataIndividual, String fieldName, List<DataAddress> dataAddress, final DataQualityContext context, final Severity severity) {
        final DataQualityMistakeDescription missingForAddressMistakeDescription = getMissingForAddressMistakeDescription(fieldName, "Trans", context, severity, this);
        getDataQualityMistakeDescriptions().add(missingForAddressMistakeDescription);

        if (DataQualityEvaluator.isMissingValue(dataAddress)) {
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(new DataQualityMistakeImpl(dataIndividual, MessageFormat.format(MISSING_TEMPLATE, fieldName), missingForAddressMistakeDescription, dataIndividual.getUpdateDate(), dataIndividual.getUserIDUpdate()));

        }
        return list;
    }
}
