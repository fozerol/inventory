/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkoc.itinventory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author fatih
 */
@Entity
@Table(name = "companies")
@NamedQueries({
@NamedQuery(name="Company.findAll", query="SELECT e FROM Company e"),
@NamedQuery(name="Company.countAll", query="SELECT COUNT(e) FROM Company e")})

public class Company implements Serializable,EntityObject {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
private String name;
private Long taxNumber;
private String address;
@OneToMany
(mappedBy = "company",orphanRemoval=true, cascade = CascadeType.ALL)
private List<Address> addresses = new ArrayList<>();

    public List<Address> getAddresses() {
        if (addresses == null)
        {
            this.addresses= new ArrayList<Address>();
        }
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
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

    public Long getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(Long taxNumber) {
        this.taxNumber = taxNumber;
    }

   
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
