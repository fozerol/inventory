/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkoc.itinventory;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author fatih
 */
@Entity
@Table(name = "cities")
@NamedQueries({
@NamedQuery(name="City.findAll", query="SELECT e FROM City e where 1=1"),
@NamedQuery(name="City.findByCountry", query="SELECT e FROM City e where e.country = :country"),
@NamedQuery(name="City.countAll", query="SELECT COUNT(e) FROM City e")
})
//@NamedNativeQuery(name="CustomerType.findAllWithTranslation",query = "SELECT s.id,s.code, case when ?languageid = 1 then translation1 when "
//        + "?languageid = 2 then translation2 else code end as name ,s.translation1,translation2,version FROM customertypes s",resultClass = CustomerType.class)



public class City extends AbstractEntity implements Serializable {

    private String name;
    @OneToOne
    @JoinColumn(name = "countryid")
    private Country country;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
    
    public City() {
    }

}
