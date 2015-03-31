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
public class DrillDownObject {

    private List<DrillDownDetails> series = new ArrayList<>();

    public List<DrillDownDetails> getSeries() {
        return series;
    }

    public void setSeries(List<DrillDownDetails> series) {
        this.series = series;
    }

}
