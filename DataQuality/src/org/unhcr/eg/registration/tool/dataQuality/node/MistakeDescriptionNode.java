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
import org.unhcr.eg.registration.tool.dataQuality.entities.provider.DataQualityMistakeDescription;
import org.unhcr.eg.registration.tool.dataQuality.entities.provider.Severity;

/**
 *
 * @author UNHCRUser
 */
public class MistakeDescriptionNode extends AbstractNode implements CheckableNode {

    private DataQualityMistakeDescription key;
    private InstanceContent content;

    public MistakeDescriptionNode(DataQualityMistakeDescription key) {
        this(key, new InstanceContent());
    }

    @Override
    public String getHtmlDisplayName() {
        return key.getLabel(); //To change body of generated methods, choose Tools | Templates.
    }

    public MistakeDescriptionNode(DataQualityMistakeDescription key, InstanceContent content) {
        super(Children.LEAF, new AbstractLookup(content));
        this.key = key;
        this.content = content;
        this.content.add(key);
    }

    @Override
    public Image getIcon(int type) {
        if (key.getSeverity().equals(Severity.CRITICAL)) {
            return ImageUtilities.loadImage("org/unhcr/eg/registration/tool/dataQuality/node/error.png"); //To change body of generated methods, choose Tools | Templates.

        } else if (key.getSeverity().equals(Severity.WARNING)) {
            return ImageUtilities.loadImage("org/unhcr/eg/registration/tool/dataQuality/node/warning.png"); //To change body of generated methods, choose Tools | Templates.

        } else if (key.getSeverity().equals(Severity.INFORMATION)) {
            return ImageUtilities.loadImage("org/unhcr/eg/registration/tool/dataQuality/node/information-balloon.png"); //To change body of generated methods, choose Tools | Templates.
        }

        return ImageUtilities.loadImage("org/unhcr/eg/registration/tool/dataQuality/node/mistake_bell_error.png"); //To change body of generated methods, choose Tools | Templates.
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
        return key.isActive();
    }

    @Override
    public void setSelected(Boolean selected) {
        key.setActive(true);
    }

}
