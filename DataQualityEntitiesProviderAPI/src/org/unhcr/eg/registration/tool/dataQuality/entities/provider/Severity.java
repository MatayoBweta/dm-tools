/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.tool.dataQuality.entities.provider;

/**
 *
 * @author UNHCRUser
 */
public enum Severity {

    CRITICAL("Critical"),
    WARNING("Warning"),
    INFORMATION("Information");

    private Severity(String severityLevel) {
        this.severityLevel = severityLevel;
    }
    private String severityLevel;

    public String getSeverityLevel() {
        return severityLevel;
    }
}
