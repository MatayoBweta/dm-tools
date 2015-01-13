/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.tool.dataQuality.entities.provider.context;

import org.unhcr.eg.registration.tool.dataQuality.entities.provider.DataQualityContext;
import org.unhcr.eg.registration.tool.dataQuality.entities.provider.ExtendedContext;

/**
 *
 * @author UNHCRUser
 */
public class DefaultDataQualityContextFactory {

    public static final DataQualityContext GLOBAL = new DataQualityContext() {
    };
    public static final DataQualityContext EGYPT = new DataQualityContext(ExtendedContext.COUNTRY, "ARE"){
    };
     public static final DataQualityContext SYRIA_SITUATION = new DataQualityContext(ExtendedContext.SITUATION, "Syria Operation"){
    };

}
