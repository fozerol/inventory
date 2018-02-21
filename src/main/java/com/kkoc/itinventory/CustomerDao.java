/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkoc.itinventory;

import genericdao.GenericDaoImp;
import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;

/**
 *
 * @author fatih
 */
@Stateless 
@Default
public class CustomerDao extends GenericDaoImp<Customer>{
    public CustomerDao() {
        super.setType(Customer.class);
    }
    public CustomerDao(Class type){
        super.setType(type);
    }
    public void setEntityManager(EntityManager em){
        super.setEm(em);
    }
}
