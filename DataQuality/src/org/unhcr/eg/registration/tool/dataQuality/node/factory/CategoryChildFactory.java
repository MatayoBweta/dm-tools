/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.tool.dataQuality.node.factory;

import java.util.Iterator;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.unhcr.eg.registration.tool.dataQuality.entities.provider.DataQualityCategory;
import org.unhcr.eg.registration.tool.dataQuality.entities.provider.DataQualitySubCategory;
import org.unhcr.eg.registration.tool.dataQuality.node.SubCategoryNode;

/**
 *
 * @author UNHCRUser
 */
public class CategoryChildFactory extends ChildFactory<DataQualitySubCategory> {

    private DataQualityCategory category;

    public CategoryChildFactory(DataQualityCategory category) {
        this.category = category;
    }

    @Override
    protected boolean createKeys(List<DataQualitySubCategory> toPopulate) {
        if (category.getSubCategories() != null) {
            toPopulate.addAll(category.getSubCategories());
        }
        return true;
    }

    @Override
    protected Node createNodeForKey(DataQualitySubCategory key) {
        return new SubCategoryNode(key); //To change body of generated methods, choose Tools | Templates.
    }
    
    

}
