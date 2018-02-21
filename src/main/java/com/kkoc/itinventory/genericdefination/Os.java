/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkoc.itinventory.genericdefination;

import com.kkoc.itinventory.AbstractEntity;
import genericdefination.DefinationClass;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author fatih
 */
@Entity
@Table(name = "OSES")
@NamedQuery(name="Os.findAll", query="SELECT e FROM Os e")
public class Os extends AbstractEntity implements DefinationClass {
    @Column(name = "name", nullable = false, length = 100)
    private String name; 
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setCode(String code) {
        this.name = name;
    }

    @Override
    public String getCode() {
      return name;  
    }
}
