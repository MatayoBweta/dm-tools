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
public class TokenDetails {

//TokenDistributedGUID
//TokenNumber
//VisitReason
//VisitNumber
//CaseNumber
//IssueToFix
//Conditions
//FamilySize
//Location
//AccesDateTime
//TokenStatus
//GateName
//ReasonForVisit
//IndividualGUID
    private String tokenDistributedGUID;
    private int tokenNumber;
    private String visitReason;
    private int visitNumber;
    private String caseNumber;
    private String issueToFix;
    private String conditions;
    private int familySize;
    private String location;
    private Date accesDateTime;
    private String tokenStatus;
    private String gateName;
    private String reasonForVisit;
    private String individualGUID;
    private String comments;
    private boolean requestOfService;

    public String getTokenDistributedGUID() {
        return tokenDistributedGUID;
    }

    public void setTokenDistributedGUID(String tokenDistributedGUID) {
        this.tokenDistributedGUID = tokenDistributedGUID;
    }

    public int getTokenNumber() {
        return tokenNumber;
    }

    public void setTokenNumber(int tokenNumber) {
        this.tokenNumber = tokenNumber;
    }

    public String getVisitReason() {
        return visitReason;
    }

    public void setVisitReason(String visitReason) {
        this.visitReason = visitReason;
    }

    public int getVisitNumber() {
        return visitNumber;
    }

    public void setVisitNumber(int visitNumber) {
        this.visitNumber = visitNumber;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public String getIssueToFix() {
        return issueToFix;
    }

    public void setIssueToFix(String issueToFix) {
        this.issueToFix = issueToFix;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public int getFamilySize() {
        return familySize;
    }

    public void setFamilySize(int familySize) {
        this.familySize = familySize;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getAccesDateTime() {
        return accesDateTime;
    }

    public void setAccesDateTime(Date accesDateTime) {
        this.accesDateTime = accesDateTime;
    }

    public String getTokenStatus() {
        return tokenStatus;
    }

    public void setTokenStatus(String tokenStatus) {
        this.tokenStatus = tokenStatus;
    }

    public String getGateName() {
        return gateName;
    }

    public void setGateName(String gateName) {
        this.gateName = gateName;
    }

    public String getIndividualGUID() {
        return individualGUID;
    }

    public void setIndividualGUID(String individualGUID) {
        this.individualGUID = individualGUID;
    }

    public String getReasonForVisit() {
        return reasonForVisit;
    }

    public void setReasonForVisit(String reasonForVisit) {
        this.reasonForVisit = reasonForVisit;
    }

    @Override
    public String toString() {
        return "\n tokenNumber=" + tokenNumber + "\n, visitReason=" + visitReason + "\n, visitNumber=" + visitNumber + "\n, caseNumber=" + caseNumber + "\n, familySize=" + familySize + "\n, location=" + location + "\n, accesDateTime=" + accesDateTime + "\n, gateName=" + gateName + "\n, reasonForVisit=" + reasonForVisit;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public boolean isRequestOfService() {
        return requestOfService;
    }

    public void setRequestOfService(boolean requestOfService) {
        this.requestOfService = requestOfService;
    }

}
