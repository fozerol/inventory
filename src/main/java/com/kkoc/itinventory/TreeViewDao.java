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


/**
 *
 * @author fatih
 */
@Stateless
public class TreeViewDao extends GenericDaoImp<TreeViewConfig> {
    public TreeViewDao()
    {
        super.setType(TreeViewConfig.class);
    }
    public List<TreeViewConfig> findByNode(Integer node){
         List<TreeViewConfig> result = super.em.createNamedQuery("TreeViewConfig.findByNode").setParameter("hierarchy", node.intValue()).getResultList();
        return result;
    }
    public List<TreeViewConfig> findByGrantedUser(int userid) {
        List<TreeViewConfig> result = new ArrayList<>();
        result  = em.createNamedQuery("TreeViewConfig.findByGrantedUser").setParameter("username", userid).getResultList();
        if (result.size() == 0){
            return null;
        }
        else{
        return result;
        }
    }
}
