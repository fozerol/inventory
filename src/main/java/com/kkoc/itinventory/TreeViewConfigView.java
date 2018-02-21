/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkoc.itinventory;

import com.vaadin.data.Binder;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import genericdefination.GenericView;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 *
 * @author fatih
 */
public class TreeViewConfigView extends GenericView<TreeViewConfig> implements View {
    @Inject TreeViewConfig treeviewconfig;
    @Inject TreeViewDao dao;
    @Inject RoleDao  roledao;
    
    private List<Role> roles = new ArrayList<>();
    private List<Role> grantedRoles = new ArrayList<>();
    private List<AppRole> appRoles = new ArrayList<>();
    private TextField nodeName = new TextField("nodeName");
    private TextField classFileName = new TextField("Class File Name");
    private TextField position = new TextField("Position");
    private TextField hierarchy = new TextField("Hierarchy");
    private TextField nodeCaption = new TextField("Caption");
    private FTwinColSelect<Role> tcsappRoles;
    
    
    private CheckBox status = new CheckBox("");
    //private Grid grid = new Grid<>(Role.class);
    private TextField name = new TextField("Role name");
    //private Binder<Role> binder = new Binder<>(Role.class);
    private GenericButtonGroup<Role> genericbuttongroup;

    public TreeViewConfigView() {
        
    }
    @PostConstruct
    public void init(){
        
        
        
        roles = roledao.findAll();
        tcsappRoles = new FTwinColSelect(null,roles);
        tcsappRoles.setItemCaptionGenerator(e-> ((Role)e).getName());
       this.setObject(treeviewconfig);
       grid.setItems( dao.findAll());
       
       binder.forField(position).bind(
               TreeViewConfig->Integer.toString(TreeViewConfig.getPosition()),
               (TreeViewConfig,position) -> TreeViewConfig.setPosition(Integer.valueOf(position))
       );
              binder.forField(hierarchy).bind(
               TreeViewConfig->Integer.toString(TreeViewConfig.getHierarchy()),
               (TreeViewConfig,hierarchy) -> TreeViewConfig.setHierarchy(Integer.valueOf(hierarchy))
       );
       binder.bindInstanceFields( this );
       genericbuttongroup = new GenericButtonGroup<>(dao,this);
       this.addComponents(nodeName,classFileName,position,hierarchy,nodeCaption,tcsappRoles,genericbuttongroup,grid);
       grid.addItemClickListener(e->{
           this.setObject((TreeViewConfig) e.getItem());
           this.tcsappRoles.setSelected(getGrantedRoles(((TreeViewConfig) e.getItem()).getAppRoles()));
       });
    }
    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        
    }
    public TreeViewConfig getObject(){
            
            for (Role r:tcsappRoles.getNewlyAdded()){
                AppRole ap = new AppRole();
                ap.setTreeviewconfig((TreeViewConfig)this.getT());
                ap.setRole(r);
               ((TreeViewConfig)this.getT()).getAppRoles().add(ap);
            }
            
             for (Role r:tcsappRoles.getDeleted()){
                    ((TreeViewConfig)this.getT()).removeAppRoleByRole(r);
            }
            return (TreeViewConfig)this.getT();
    }

    private List<Role> getGrantedRoles(List<AppRole> ap) {
        grantedRoles.clear();
        for (AppRole approle:ap){
            grantedRoles.add(approle.getRole());
        }
        return grantedRoles;
    }
}
