/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.tool.dataQuality.entities.provider.evaluator;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import org.unhcr.eg.data.quality.proGres.entity.DataDocument;
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
public final class MissingDocumentEvaluator extends DataQualityEvaluator {

    public MissingDocumentEvaluator() {
        checkBodyMethod(null, null, null);
    }

    @Override
    public String getName() {
        return "Document Evaluator";
    }

    @Override
    public String getDescription() {
        return "Evaluate the quality of information related to Document in Individual File";
    }

    @Override
    public List<DataQualityMistake> evaluate(DataIndividual dataIndividual) {
        ArrayList<DataQualityMistake> list = null;

        List<DataDocument> dataDocuments = dataIndividual.getDataDocumentList();
        for (DataDocument dataDocument : dataDocuments) {
            if (dataDocument.getDocumentStatusCode() == null || (!"I".equals(dataDocument.getDocumentStatusCode().getDocumentStatusCode()) && !"X".equals(dataDocument.getDocumentStatusCode().getDocumentStatusCode()))) {
                list = checkBodyMethod(list, dataDocument, dataIndividual);
            }
        }
        return list;
    }

    protected ArrayList<DataQualityMistake> checkBodyMethod(ArrayList<DataQualityMistake> list, DataDocument dataDocument, DataIndividual dataIndividual) {
        list = checkMissingValue(list, dataDocument, dataIndividual, "Issued By", "documentIssuedBy", DefaultDataQualityContextFactory.GLOBAL, Severity.WARNING);
        list = checkMissingValue(list, dataDocument, dataIndividual, "Date Issued", "documentDateIssued", DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL);
        list = checkMissingValue(list, dataDocument, dataIndividual, "Number ", "documentNumber", DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL);
        list = checkMissingValue(list, dataDocument, dataIndividual, "Type", "documentType", DefaultDataQualityContextFactory.GLOBAL, Severity.CRITICAL);
        list = checkMissingValue(list, dataDocument, dataIndividual, "Legal", "documentLegal", DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION);
        list = checkMissingValue(list, dataDocument, dataIndividual, "Original", "documentOriginal", DefaultDataQualityContextFactory.GLOBAL, Severity.INFORMATION);
        list = checkMissingValue(list, dataDocument, dataIndividual, "Expire Date", "documentDateExpire", DefaultDataQualityContextFactory.GLOBAL, Severity.WARNING);
        return list;
    }

    public ArrayList<DataQualityMistake> checkMissingValue(ArrayList list, DataDocument dataDocument, DataIndividual dataIndividual, String fieldName, String fieldLabel, final DataQualityContext context, final Severity severity) {
        final DataQualityMistakeDescription missingForDocumentMistakeDescription = getMissingForDocumentMistakeDescription(fieldName, context, severity, this);
        getDataQualityMistakeDescriptions().add(missingForDocumentMistakeDescription);
        if (dataIndividual != null && dataDocument != null && DataQualityEvaluator.isMissingValue(getFieldValue(fieldLabel, dataDocument))) {
            if (list == null) {
                list = new ArrayList<>();
            }

            list.add(new DataQualityMistake(dataIndividual, MessageFormat.format(MISSING_TEMPLATE_DOCUMENT, fieldName, DataQualityEvaluator.getFieldValue("documentType/codeDocumentTypeTextList[codeDocumentTypeTextPK/documentTypeLanguageCode='ENG']/documentTypeText", dataDocument)), missingForDocumentMistakeDescription, dataDocument.getUpdateDate(), dataDocument.getUserIDUpdate()) {
                
            });

        }
        return list;
    }
}
