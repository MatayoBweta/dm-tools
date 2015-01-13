/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.tool.dataQuality.node;

import java.awt.Image;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.ImageUtilities;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.unhcr.eg.registration.tool.dataQuality.entities.provider.DataQualityMistake;

/**
 *
 * @author UNHCRUser
 */
public class MistakeNode extends AbstractNode {

    private DataQualityMistake key;
    private InstanceContent content;

    public MistakeNode(DataQualityMistake key) {
        this(key, new InstanceContent());
    }

    public MistakeNode(DataQualityMistake key, InstanceContent content) {
        super(Children.LEAF, new AbstractLookup(content));
        this.key = key;
        this.content = content;
        this.content.add(key);
    }
    
    @Override
    public Image getIcon(int type) {
        return ImageUtilities.loadImage("org/unhcr/eg/registration/tool/dataQuality/node/mistake_bell_error.png"); //To change body of generated methods, choose Tools | Templates.
    }
  @Override
    public Image getOpenedIcon(int type) {
        return getIcon(type); //To change body of generated methods, choose Tools | Templates.
    }
   

}
