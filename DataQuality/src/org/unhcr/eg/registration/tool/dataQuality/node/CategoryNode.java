/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.tool.dataQuality.node;

import java.awt.Image;
import org.openide.explorer.view.CheckableNode;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.ImageUtilities;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.unhcr.eg.registration.tool.dataQuality.entities.provider.DataQualityCategory;
import org.unhcr.eg.registration.tool.dataQuality.node.factory.CategoryChildFactory;

/**
 *
 * @author UNHCRUser
 */
public class CategoryNode extends AbstractNode implements CheckableNode {

    private DataQualityCategory category;

    private InstanceContent content;

    public CategoryNode(DataQualityCategory category) {
        this(category, new InstanceContent());
   }

    public CategoryNode(DataQualityCategory category, InstanceContent content) {
        super(Children.create(new CategoryChildFactory(category), true), new AbstractLookup(content));
        this.category = category;
        this.content = content;
        this.content.add(category);
    }

    @Override
    public boolean isCheckable() {
        return true;
    }

    @Override
    public boolean isCheckEnabled() {
        return true;
    }

    @Override
    public Boolean isSelected() {
        return category.isActive();
    }

    @Override
    public void setSelected(Boolean selected) {
        category.setActive(selected.booleanValue());
    }

    @Override
    public Image getIcon(int type) {
        return ImageUtilities.loadImage("org/unhcr/eg/registration/tool/dataQuality/node/1_category.png"); //To change body of generated methods, choose Tools | Templates.
    }
    
    
 @Override
    public Image getOpenedIcon(int type) {
        return getIcon(type); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String getHtmlDisplayName() {
        return category.getName(); //To change body of generated methods, choose Tools | Templates.
    }

}
