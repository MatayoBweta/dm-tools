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
import org.unhcr.eg.registration.tool.dataQuality.entities.provider.DataQualityEvaluator;
import org.unhcr.eg.registration.tool.dataQuality.node.factory.EvaluatorChildFactory;

/**
 *
 * @author UNHCRUser
 */
public class EvaluatorNode extends AbstractNode implements CheckableNode {

    private DataQualityEvaluator key;
    private InstanceContent content;

    public EvaluatorNode(DataQualityEvaluator key) {
        this(key, new InstanceContent());
     
    }

    public EvaluatorNode(DataQualityEvaluator key, InstanceContent content) {
        super(Children.create(new EvaluatorChildFactory(key), true), new AbstractLookup(content));
        this.key = key;
        this.content = content;
        this.content.add(key);
    }

    @Override
    public String getHtmlDisplayName() {
        return key.getName(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Image getIcon(int type) {
        return ImageUtilities.loadImage("org/unhcr/eg/registration/tool/dataQuality/node/evaluator_test_paper-icon.png"); //To change body of generated methods, choose Tools | Templates.
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
        key.setActive(selected);
    }

}
