/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.tool.dataQuality.entities.provider;

/**
 *
 * @author UNHCRUser
 */
public enum ExtendedContext {

    GLOBAL("Global"),
    COUNTRY("Country"),
    SITUATION("Situation");

    private ExtendedContext(String extendedContextName) {
        this.extendedContextName = extendedContextName;
    }
    private String extendedContextName;

    public String getExtendedContextName() {
        return extendedContextName;
    }
}
