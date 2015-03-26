/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.tool.token.printing.models;

/**
 *
 * @author UNHCRuser
 */
public class VisitSummary {

    private Integer count;
    private Integer individuals;
    private String category;
    private String reason;

    public VisitSummary(String category, String reason, Integer count, Integer individuals) {
        this.count = count;
        this.category = category;
        this.reason = reason;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
