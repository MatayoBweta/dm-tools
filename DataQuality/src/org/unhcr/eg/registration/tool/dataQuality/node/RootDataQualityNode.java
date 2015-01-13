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

/**
 *
 * @author UNHCRUser
 */
public class RootDataQualityNode extends AbstractNode implements CheckableNode {

    public RootDataQualityNode(Children children) {
        super(children);
    }

    @Override
    public Image getIcon(int type) {
        return ImageUtilities.loadImage("org/unhcr/eg/registration/tool/dataQuality/node/data-management-icon.png"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Image getOpenedIcon(int type) {
        return getIcon(type); //To change body of generated methods, choose Tools | Templates.
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
        return true;
    }

    @Override
    public void setSelected(Boolean selected) {
    }

}
