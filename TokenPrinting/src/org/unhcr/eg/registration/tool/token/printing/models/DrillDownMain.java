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
public class DrillDownMain {

    private String category;
    private int y;
    private String color;
    private DrillDownDetails drilldown;

    public DrillDownMain(String category) {
        this.category = category;
    }

    public DrillDownMain() {

    }

    public DrillDownMain(String category, int y, String color, DrillDownDetails drilldown) {
        this.category = category;
        this.y = y;
        this.color = color;
        this.drilldown = drilldown;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public DrillDownDetails getDrilldown() {
        return drilldown;
    }

    public void setDrilldown(DrillDownDetails drilldown) {
        this.drilldown = drilldown;
    }

}
