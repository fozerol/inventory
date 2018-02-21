/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkoc.itinventory;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author fatih
 */
@Entity
@Table(name = "addresses")
@NamedQueries({@NamedQuery(name="Address.findAll", query="SELECT e FROM Company e"),@NamedQuery(name="Address.countAll", query="SELECT COUNT(e) FROM Address e")}
)

public class Address extends AbstractEntity implements Serializable {
    private String address;
    private String zipcode;
    @OneToOne
    @JoinColumn(name = "cityid")
    private City city;
    @ManyToOne(fetch=FetchType.EAGER,cascade = CascadeType.REMOVE)
    @JoinColumn(name="customerid",referencedColumnName = "id")
    private Customer customer;
    @ManyToOne(fetch=FetchType.EAGER,cascade = CascadeType.REMOVE)
    @JoinColumn(name="companyid",referencedColumnName = "id")
    private Company company;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
    

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
    
    
public Address(){

}  
   
}

