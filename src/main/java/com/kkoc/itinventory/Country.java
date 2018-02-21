/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkoc.itinventory;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author fatih
 */
@Entity
@Table(name = "countries")
@NamedQueries({
@NamedQuery(name="Country.findAll", query="SELECT e FROM Country e"),
@NamedQuery(name="Country.countAll", query="SELECT COUNT(e) FROM Country e")})


public class Country extends AbstractEntityTranslation implements Serializable {
    
    public Country() {
    }

}
