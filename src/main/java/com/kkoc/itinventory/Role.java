/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkoc.itinventory;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author fatih
 */
@Entity
@Table(name = "roles")
@NamedQuery(name="Role.findAll", query="SELECT e FROM Role e")
public class Role implements Serializable,EntityObject{
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
private String name;
private boolean status;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public String getName() {
    return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
