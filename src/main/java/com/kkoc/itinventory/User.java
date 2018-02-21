/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkoc.itinventory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author fatih
 */
@Entity
@Table(name = "users")
@NamedQueries({
@NamedQuery(name="User.findAll", query="SELECT e FROM User e where 1=1"),
@NamedQuery(name="User.countAll", query="SELECT COUNT(e) FROM User e where 1=1"),
@NamedQuery(name="User.findByUsername", query="SELECT e FROM User e where e.userName = :username")
})



public class User implements Serializable {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
private String userName;
private String name;
private String surname;
private String email;
private String password;
@OneToOne
@JoinColumn(name = "companyid")
private Company company;
@OneToMany
(mappedBy = "user",orphanRemoval=true, cascade = CascadeType.ALL)
private List<UserRole> userRoles = new ArrayList<>();
@Transient
private List<Role> roles = new ArrayList<>();


    public User () {
         
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return null;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }
    public void removeUserRoleByRole(Role role){
        
            Iterator<UserRole> ur = this.userRoles.iterator();
            while (ur.hasNext()) {
            UserRole r = ur.next(); 
            if (r.getRole().getId()==role.getId()){
                r.setUser(null);
                ur.remove();
            }
        }
    }
    
}
