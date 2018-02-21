/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkoc.itinventory;

import genericdao.GenericDaoImp;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;

/**
 *
 * @author fatih
 */
@Stateless 
public class CityDao extends GenericDaoImp<City>{
    public CityDao() {
        super.setType(City.class);
    }
    public CityDao(Class type){
        super.setType(type);
    }
    public void setEntityManager(EntityManager em){
        super.setEm(em);
    }
    public List<City> findByCountry(Country country){
       List<City> cities = new ArrayList<>();
             cities = super.em.createNamedQuery("City.findByCountry").setParameter("country", country).getResultList();
        return cities;    
    }
}
