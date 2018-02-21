/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genericdefination;

import com.kkoc.itinventory.AbstractEntityTranslation;
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
@Table(name = "genericdefinations")
@NamedQueries({
@NamedQuery(name="GenericDefination.findAll", query="SELECT e FROM GenericDefination e"),
@NamedQuery(name="GenericDefination.countAll", query="SELECT COUNT(e) FROM GenericDefination e")})
@NamedNativeQuery(name="GenericDefination.findAllWithTranslation",query = "SELECT s.id,s.code, case when ?languageid = 1 then translation1 when "
        + "?languageid = 2 then translation2 else code end as name ,s.translation1,translation2,version FROM customertypes s",resultClass = GenericDefination.class)



public class GenericDefination extends AbstractEntityTranslation implements Serializable {

    private String className;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
   
    public GenericDefination() {
    }

}
