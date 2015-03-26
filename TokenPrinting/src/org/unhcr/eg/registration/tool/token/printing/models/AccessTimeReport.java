/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.tool.token.printing.models;

import java.util.Date;

/**
 *
 * @author UNHCRuser
 */
public class AccessTimeReport {

    private Date accessTime;
    private String gate;
    private String typeOfNumber;
    private int number;
    private int cumulativeNumber;

    public AccessTimeReport(Date accessTime, String gate, String typeOfNumber, int number, int cumulativeNumber) {
        this.accessTime = accessTime;
        this.gate = gate;
        this.typeOfNumber = typeOfNumber;
        this.number = number;
        this.cumulativeNumber = cumulativeNumber;
    }

    public Date getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(Date accessTime) {
        this.accessTime = accessTime;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getCumulativeNumber() {
        return cumulativeNumber;
    }

    public void setCumulativeNumber(int cumulativeNumber) {
        this.cumulativeNumber = cumulativeNumber;
    }

    public String getGate() {
        return gate;
    }

    public void setGate(String gate) {
        this.gate = gate;
    }

    public String getTypeOfNumber() {
        return typeOfNumber;
    }

    public void setTypeOfNumber(String typeOfNumber) {
        this.typeOfNumber = typeOfNumber;
    }

}
