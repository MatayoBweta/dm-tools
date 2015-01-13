/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.tool.dataQuality.node.factory;

import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.unhcr.eg.registration.tool.dataQuality.entities.provider.DataQualityEvaluator;
import org.unhcr.eg.registration.tool.dataQuality.entities.provider.DataQualityMistakeDescription;
import org.unhcr.eg.registration.tool.dataQuality.node.MistakeDescriptionNode;

/**
 *
 * @author UNHCRUser
 */
public class EvaluatorChildFactory extends ChildFactory<DataQualityMistakeDescription> {

    private DataQualityEvaluator evaluator;

    public EvaluatorChildFactory(DataQualityEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    @Override
    protected boolean createKeys(List<DataQualityMistakeDescription> toPopulate) {
        if (evaluator.getDataQualityMistakeDescriptions()!= null) {
            toPopulate.addAll(evaluator.getDataQualityMistakeDescriptions());
        }
        return true;
    }

    @Override
    protected Node createNodeForKey(DataQualityMistakeDescription key) {
        return new MistakeDescriptionNode(key); //To change body of generated methods, choose Tools | Templates.
    }

}
