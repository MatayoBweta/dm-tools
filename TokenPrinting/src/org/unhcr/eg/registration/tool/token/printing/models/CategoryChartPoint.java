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
public class CategoryChartPoint {

    private String name;
    private long y;
    private String color;

    public CategoryChartPoint(String name, long y, String color) {
        this.name = name;
        this.y = y;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getY() {
        return y;
    }

    public void setY(long y) {
        this.y = y;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
