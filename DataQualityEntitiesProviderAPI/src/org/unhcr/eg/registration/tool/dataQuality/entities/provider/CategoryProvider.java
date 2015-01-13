/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.tool.dataQuality.entities.provider;

import java.util.List;

/**
 *
 * @author UNHCRUser
 */
public abstract class CategoryProvider {

    public abstract List<DataQualityCategory> getCategories();
}
