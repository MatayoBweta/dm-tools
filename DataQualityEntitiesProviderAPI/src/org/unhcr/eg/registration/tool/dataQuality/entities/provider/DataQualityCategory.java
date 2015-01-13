/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.unhcr.eg.registration.tool.dataQuality.entities.provider;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 *
 * @author UNHCRUser
 */
@Entity(name = "DataQualityCategory")
public class DataQualityCategory implements Serializable {

    @Transient
    public static final String MISSING__INFORMATION = "Missing Information";
    @Transient
    public static final String INCONSISTANT__INFORMATION = "Inconsistant Information";
    @Transient
    public static final String POTENTIAL__SPECIFIC_NEEDS = "Potential Specific needs";
    @Transient
    public static final String WRONG__EVENTS__CHRONOLOGY = "Wrong Events Chronology";
    @Id
    private String name;
    @Transient
    public static final String PROP_NAME = "name";
    @OneToMany(mappedBy = "")
    private List<DataQualitySubCategory> subCategories;
    public static final String PROP_SUBCATEGORIES = "subCategories";
    @Basic(optional = false)
    @Column(name = "active")
    private boolean active = true;

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

    public DataQualityCategory(String name) {
        this.name = name;
    }

    /**
     * Get the value of subCategories
     *
     * @return the value of subCategories
     */
    public List<DataQualitySubCategory> getSubCategories() {
        if (subCategories == null) {
            subCategories = new ArrayList<>();
        }
        return subCategories;
    }

    /**
     * Set the value of subCategories
     *
     * @param subCategories new value of subCategories
     */
    public void setSubCategories(List<DataQualitySubCategory> subCategories) {
        List<DataQualitySubCategory> oldSubCategories = this.subCategories;
        this.subCategories = subCategories;
        propertyChangeSupport.firePropertyChange(PROP_SUBCATEGORIES, oldSubCategories, subCategories);
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
    @Transient
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
}
