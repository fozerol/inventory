/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkoc.itinventory;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TwinColSelect;
import genericdefination.GenericView;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 *
 * @author fatih
 */
public class UserRoleView extends GenericView<UserRole>{
        @Inject UserRole userrole;
        @Inject UserRoleDao dao;
        @Inject RoleDao roledao;
        @Inject UserDao userdao;
        private ComboBox<User> users = new ComboBox<>("Select User");
        private TwinColSelect<Role> roles;
        private GenericButtonGroup<UserRole> genericbuttongroup;
        public UserRoleView(){
        }
        @PostConstruct
        public void init(){
        this.setObject(userrole);
        users.setItems(userdao.findAll());
        users.setItemCaptionGenerator(e->e.getName());
        roles = new TwinColSelect<>(null,roledao.findAll());
        roles.setItemCaptionGenerator(e->e.getName());
        this.grid.setItems( dao.findAll());
        this.binder.forField(users);
        //this.binder.forField(roles).bind(UserRole::getRoles,UserRole::setRole);
        this.binder.forField(roles);
        this.binder.bindInstanceFields( this );

        genericbuttongroup = new GenericButtonGroup<>(dao,this);
       this.addComponents(users,roles,genericbuttongroup,grid);
       grid.addItemClickListener(e->{
           this.setObject((UserRole) e.getItem());
       });
    }
}
