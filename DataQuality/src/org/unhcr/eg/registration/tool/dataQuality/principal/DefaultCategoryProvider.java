/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.tool.dataQuality.principal;

import java.util.ArrayList;
import java.util.List;
import org.openide.util.lookup.ServiceProvider;
import org.unhcr.eg.registration.tool.dataQuality.entities.provider.CategoryProvider;
import org.unhcr.eg.registration.tool.dataQuality.entities.provider.DataQualityCategory;
import org.unhcr.eg.registration.tool.dataQuality.entities.provider.DataQualityEvaluator;
import org.unhcr.eg.registration.tool.dataQuality.entities.provider.DataQualitySubCategory;
import org.unhcr.eg.registration.tool.dataQuality.entities.provider.evaluator.InconsistantBioDataEvaluator;
import org.unhcr.eg.registration.tool.dataQuality.entities.provider.evaluator.MissingAddresseEvaluator;
import org.unhcr.eg.registration.tool.dataQuality.entities.provider.evaluator.MissingBioDatatEvaluator;
import org.unhcr.eg.registration.tool.dataQuality.entities.provider.evaluator.MissingDocumentEvaluator;

/**
 *
 * @author UNHCRUser
 */
@ServiceProvider(service = CategoryProvider.class)
public class DefaultCategoryProvider extends CategoryProvider {

    @Override
    public List<DataQualityCategory> getCategories() {
        List<DataQualityCategory> categorys = new ArrayList<>();
        
        //Missing Information
        DataQualityCategory category = new DataQualityCategory(DataQualityCategory.MISSING__INFORMATION);
        categorys.add(category);

        DataQualitySubCategory dataQualitySubCategory = getMissingBioData();
        category.getSubCategories().add(dataQualitySubCategory);

        dataQualitySubCategory = getMissingAddresses();
        category.getSubCategories().add(dataQualitySubCategory);

        dataQualitySubCategory = getMissingDocuments();
        category.getSubCategories().add(dataQualitySubCategory);

        // Inconsistant Information
        category = new DataQualityCategory(DataQualityCategory.INCONSISTANT__INFORMATION);
        categorys.add(category);

        dataQualitySubCategory = getInconsistentBioDatas();
        category.getSubCategories().add(dataQualitySubCategory);

        category = new DataQualityCategory(DataQualityCategory.POTENTIAL__SPECIFIC_NEEDS);
        categorys.add(category);
        category = new DataQualityCategory(DataQualityCategory.WRONG__EVENTS__CHRONOLOGY);
        categorys.add(category);
        return categorys;
    }

    protected DataQualitySubCategory getMissingAddresses() {
        DataQualitySubCategory dataQualitySubCategory = new DataQualitySubCategory(DataQualitySubCategory.ADDRESSES);
        DataQualityEvaluator evaluator = new MissingAddresseEvaluator();
        dataQualitySubCategory.getEvaluators().add(evaluator);

        return dataQualitySubCategory;
    }

    protected DataQualitySubCategory getMissingBioData() {
        DataQualitySubCategory dataQualitySubCategory = new DataQualitySubCategory(DataQualitySubCategory.BIO__DATA);
        DataQualityEvaluator evaluator = new MissingBioDatatEvaluator();
        dataQualitySubCategory.getEvaluators().add(evaluator);
        return dataQualitySubCategory;
    }

    private DataQualitySubCategory getMissingDocuments() {
        DataQualitySubCategory dataQualitySubCategory = new DataQualitySubCategory(DataQualitySubCategory.DOCUMENTS);
        DataQualityEvaluator evaluator = new MissingDocumentEvaluator();
        dataQualitySubCategory.getEvaluators().add(evaluator);

        return dataQualitySubCategory;
    }

    private DataQualitySubCategory getInconsistentBioDatas() {
        DataQualitySubCategory dataQualitySubCategory = new DataQualitySubCategory(DataQualitySubCategory.BIO__DATA);

        DataQualityEvaluator evaluator = new InconsistantBioDataEvaluator();
        dataQualitySubCategory.getEvaluators().add(evaluator);

        return dataQualitySubCategory;
    }
}
