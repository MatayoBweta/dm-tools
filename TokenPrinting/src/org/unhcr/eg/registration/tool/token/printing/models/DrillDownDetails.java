/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.tool.token.printing.models;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author UNHCRuser
 */
public class DrillDownDetails {

    private String name;
    private List<ArrayList> data = new ArrayList<>();
    private String color;

    public DrillDownDetails(String id) {
        this.name = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ArrayList> getData() {
        return data;
    }

    public void setData(List<ArrayList> data) {
        this.data = data;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
