/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkoc.itinventory;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author fatih
 */
public class CdiConfig {
    /**
     * Exposes entity manager for CDI tools 
     * 
     */
    @Produces
    @Dependent
    @PersistenceContext(unitName = "pu")
    public EntityManager entityManager;
    
}
