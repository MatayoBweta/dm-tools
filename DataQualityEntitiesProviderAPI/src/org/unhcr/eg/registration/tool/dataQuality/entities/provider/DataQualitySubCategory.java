/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.tool.dataQuality.entities.provider;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author UNHCRUser
 */
@Entity
@Table(name = "DataQualitySubCategory")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DataQualitySubCategory.findAll", query = "SELECT d FROM DataQualitySubCategory d"),
    @NamedQuery(name = "DataQualitySubCategory.findByDataQualitySubCategoryGUID", query = "SELECT d FROM DataQualitySubCategory d WHERE d.dataQualitySubCategoryGUID = :dataQualitySubCategoryGUID"),
    @NamedQuery(name = "DataQualitySubCategory.findByName", query = "SELECT d FROM DataQualitySubCategory d WHERE d.name = :name"),
    @NamedQuery(name = "DataQualitySubCategory.findByActive", query = "SELECT d FROM DataQualitySubCategory d WHERE d.active = :active")})
public class DataQualitySubCategory {

    @Transient
    public static final String MIXED = "Mixed";
    @Transient
    public static final String LEGAL__STATUS = "Legal Status";
    @Transient
    public static final String ADDRESSES = "Addresses";
    @Transient
    public static final String DOCUMENTS = "Documents";
    @Transient
    public static final String CHRONOLOGY = "Chronology";
    @Transient
    public static final String BIO__DATA = "Bio Data";
    @Transient
    public static final String CHILD__AT__RISK = "Child At Risk";
    @Transient
    public static final String REGISTRATION = "Registration";
    @Transient
    public static final String RSD = "Refugee Status Determination";
    @Transient
    public static final String PROTECTION = "Protection";
    @Transient
    public static final String ASSISTANCE = "Assistance";
    @Transient
    public static final String VOLREP = "Voluntary Repatriation";
    @Transient
    public static final String RST = "Resettlement";
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "DataQualitySubCategoryGUID")
    private String dataQualitySubCategoryGUID;
    @Basic(optional = false)
    @Column(name = "Name")
    private String name;
    @Transient
    public static final String PROP_NAME = "name";
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dataQualitySubCategoryGUID")
    private List<DataQualityEvaluator> evaluators;
    public static final String PROP_EVALUATORS = "evaluators";
    @JoinColumn(name = "DataQualityCategoryGUID", referencedColumnName = "DataQualityCategoryGUID")
    @ManyToOne(optional = false)
    private DataQualityCategory dataQualityCategoryGUID;

    public DataQualitySubCategory(String name) {
        this.name = name;
    }
    @Basic(optional = false)
    @Column(name = "Active")
    private boolean active;
    @Transient
    public static final String PROP_ACTIVE = "active";

    /**
     * Get the value of active
     *
     * @return the value of active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Set the value of active
     *
     * @param active new value of active
     */
    public void setActive(boolean active) {
        boolean oldActive = this.active;
        this.active = active;
        propertyChangeSupport.firePropertyChange(PROP_ACTIVE, oldActive, active);
    }

    /**
     * Get the value of evaluators
     *
     * @return the value of evaluators
     */
    public List<DataQualityEvaluator> getEvaluators() {
        if (evaluators == null) {
            evaluators = new ArrayList<>();
        }
        return evaluators;
    }

    /**
     * Set the value of evaluators
     *
     * @param evaluators new value of evaluators
     */
    public void setEvaluators(List<DataQualityEvaluator> evaluators) {
        List<DataQualityEvaluator> oldEvaluators = this.evaluators;
        this.evaluators = evaluators;
        propertyChangeSupport.firePropertyChange(PROP_EVALUATORS, oldEvaluators, evaluators);
    }

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    public void setName(String name) {
        String oldName = this.name;
        this.name = name;
        propertyChangeSupport.firePropertyChange(PROP_NAME, oldName, name);
    }
    private transient final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    /**
     * Add PropertyChangeListener.
     *
     * @param listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Remove PropertyChangeListener.
     *
     * @param listener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public String getDataQualitySubCategoryGUID() {
        return dataQualitySubCategoryGUID;
    }

    public void setDataQualitySubCategoryGUID(String dataQualitySubCategoryGUID) {
        this.dataQualitySubCategoryGUID = dataQualitySubCategoryGUID;
    }

    public DataQualityCategory getDataQualityCategoryGUID() {
        return dataQualityCategoryGUID;
    }

    public void setDataQualityCategoryGUID(DataQualityCategory dataQualityCategoryGUID) {
        this.dataQualityCategoryGUID = dataQualityCategoryGUID;
    }

}
