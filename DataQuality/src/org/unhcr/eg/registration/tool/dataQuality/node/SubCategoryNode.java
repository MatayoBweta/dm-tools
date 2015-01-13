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
import org.unhcr.eg.registration.tool.dataQuality.entities.provider.DataQualitySubCategory;
import org.unhcr.eg.registration.tool.dataQuality.node.factory.SubCategoryChildFactory;

/**
 *
 * @author UNHCRUser
 */
public class SubCategoryNode extends AbstractNode implements CheckableNode {

    private DataQualitySubCategory key;
    private InstanceContent content;

    public SubCategoryNode(DataQualitySubCategory key) {
        this(key, new InstanceContent());
        this.key = key;
    }

    public SubCategoryNode(DataQualitySubCategory key, InstanceContent content) {
        super(Children.create(new SubCategoryChildFactory(key), true), new AbstractLookup(content));
        this.key = key;
        this.content = content;
        this.content.add(key);
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
        return key.isActive();
    }

    @Override
    public Image getIcon(int type) {
        return ImageUtilities.loadImage("org/unhcr/eg/registration/tool/dataQuality/node/2_category.png"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Image getOpenedIcon(int type) {
        return getIcon(type); //To change body of generated methods, choose Tools | Templates.
    }
    
    

    @Override
    public void setSelected(Boolean selected) {
        key.setActive(selected.booleanValue());
    }

    @Override
    public String getHtmlDisplayName() {
        return key.getName(); //To change body of generated methods, choose Tools | Templates.
    }

}
