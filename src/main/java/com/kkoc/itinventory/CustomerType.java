/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkoc.itinventory;

import genericdefination.DefinationClass;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author fatih
 */
@Entity
@Table(name = "customertypes")
@NamedQueries({
@NamedQuery(name="CustomerType.findAll", query="SELECT e FROM CustomerType e"),
@NamedQuery(name="CustomerType.countAll", query="SELECT COUNT(e) FROM CustomerType e")})
@NamedNativeQuery(name="CustomerType.findAllWithTranslation",query = "SELECT s.id,s.code, case when ?languageid = 1 then translation1 when "
        + "?languageid = 2 then translation2 else code end as name ,s.translation1,translation2,version FROM customertypes s",resultClass = CustomerType.class)



public class CustomerType extends AbstractEntityTranslation implements Serializable,DefinationClass {

    public CustomerType() {
    }

}
