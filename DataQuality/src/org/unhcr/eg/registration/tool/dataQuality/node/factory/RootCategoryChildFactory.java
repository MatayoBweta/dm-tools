/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.tool.dataQuality.node.factory;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.unhcr.eg.registration.tool.dataQuality.entities.provider.CategoryProvider;
import org.unhcr.eg.registration.tool.dataQuality.entities.provider.DataQualityCategory;
import org.unhcr.eg.registration.tool.dataQuality.node.CategoryNode;

/**
 *
 * @author UNHCRUser
 */
public class RootCategoryChildFactory extends ChildFactory<DataQualityCategory> {

    public RootCategoryChildFactory() {

    }

    @Override
    protected boolean createKeys(List<DataQualityCategory> toPopulate) {
        Collection<? extends CategoryProvider> collection = Lookup.getDefault().lookupAll(CategoryProvider.class);
        for (CategoryProvider categoryProvider : collection) {
            toPopulate.addAll(categoryProvider.getCategories());
        }
        return true;
    }

    @Override
    protected Node createNodeForKey(DataQualityCategory key) {
        return new CategoryNode(key); //To change body of generated methods, choose Tools | Templates.
    }

}
