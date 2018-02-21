/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkoc.itinventory;


import static com.kkoc.itinventory.AuthService.getLanguage;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 *
 * @author fatih
 */
@MappedSuperclass
public abstract class AbstractEntityTranslation implements Serializable, Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String code;
    private String translation1;
    private String translation2;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        //return name;
        switch (getLanguage().getId()) {
            case 1:
                if (this.translation1 == null){
                    return this.code;
                }
                else
                {
                    return this.translation1;
                }
            case 2:
                if (this.translation2 == null){
                    return this.code;
                }
                else
                {
                    return this.translation2;
                }
            default:
                return translation1;
        }
    }

    public void setName(String name) {
        
        //this.name = name;
        switch (getLanguage().getId()) {
            case 1:
                this.translation1 = name;
                break;
            case 2:
                this.translation2 = name;
                break;
            default:
                this.translation1 = name;
                break;
        }
    }

    public String getTranslation1() {
        return translation1;
    }

    public void setTranslation1(String translation1) {
        this.translation1 = translation1;
    }

    public String getTranslation2() {
        return translation2;
    }

    public void setTranslation2(String translation2) {
        this.translation2 = translation2;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
    
    
    @Version
    private int version;

    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if(this.id == null) {
            return false;
        }

        if (obj instanceof AbstractEntity && obj.getClass().equals(getClass())) {
            return this.id.equals(((AbstractEntityTranslation) obj).id);
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.id);
        return hash;
    }
    
}
