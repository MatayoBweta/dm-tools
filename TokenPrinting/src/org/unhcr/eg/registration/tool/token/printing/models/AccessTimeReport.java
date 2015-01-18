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

    private Date acceTime;
    private int number;
    private int cumulativeNumber;

    public AccessTimeReport(Date acceTime, int number, int cumulativeNumber) {
        this.acceTime = acceTime;
        this.number = number;
        this.cumulativeNumber = cumulativeNumber;
    }

    public Date getAcceTime() {
        return acceTime;
    }

    public void setAcceTime(Date acceTime) {
        this.acceTime = acceTime;
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

}
