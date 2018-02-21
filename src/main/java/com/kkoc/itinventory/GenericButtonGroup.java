/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkoc.itinventory;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import genericdao.GenericDaoImp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fatih
 */
public class  GenericButtonGroup<T> extends HorizontalLayout{
    private Button saveButton = new Button("Save");
    private Button newButton = new Button("New");
    private Button deleteButton = new  Button("Delete");
    private GenericObject o;
    private T t;
    
    //public GenericButtonGroup(Class<T> beanType,GenericDaoImp dao,Binder<T> binder,T t,Grid grid,GenericObject o) {
      public GenericButtonGroup(GenericDaoImp dao,GenericObject o) {    
        this.o=o;
        this.t=t;
        this.addComponents(saveButton,newButton,deleteButton);
        saveButton.addClickListener(e->{
            if (!o.getBinder().isValid())
                return;
            dao.create(o.getObject());
            setNewInstace();
            o.getGrid().setItems(dao.findAll());
            /* Change Filter API provider to new provider for searching */
            if (o.getFilter() != null) {
                o.getFilter().setDataProvider((ListDataProvider) o.getGrid().getDataProvider());
            }
        });

        newButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent e) {
                setNewInstace();
            }
        });
        deleteButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent e) {
                dao.delete(o.getObject());
                setNewInstace();
                o.getGrid().setItems(dao.findAll());
            }
        });
    }
    private void setNewInstace(){
                t = (T) o.getNewInstance();
                o.setObject(t);
    }
}
