/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkoc.itinventory;


import genericdao.GenericDaoImp;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;

/**
 *
 * @author fatih
 */
@Stateless 
public class UserDao extends GenericDaoImp<User>{
    public UserDao() {
        super.setType(User.class);
    }
    public UserDao(Class type){
        super.setType(type);
    }
    public void setEntityManager(EntityManager em){
        super.setEm(em);
    }
    public User findByUsername(String username){
        List<User> result = em.createNamedQuery(this.type.getSimpleName()+".findByUsername").setParameter("username", username).getResultList();
        if (result.size() == 0){
            return null;
        }
        else{
        return result.iterator().next();
        }
    }
}
