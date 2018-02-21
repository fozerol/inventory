/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkoc.itinventory;

import static com.kkoc.itinventory.TranslationSvc.getText;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.toList;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 *
 * @author fatih
 * extend this form for modal operations with parameterized type,  pass current list object values for listing , and get after windows close 
 * do not forget setclasstype instance of using reflaction
 */
public abstract class GenericModalForm<T> extends Window {
    
    private List<T> senderobjects;
    private List<T> objects = new ArrayList<>();
    private Class<?> classtype;
    private T object;
    protected Binder<T> binder;
    protected Button cancelBtn = new Button(getText("CANCEL"));
    protected Button addBtn = new Button(getText("ADD"));
    protected Button okBtn = new Button(getText("OK"));
    protected Button deleteBtn = new Button(getText("DELETE"));
    protected Button updateBtn = new Button(getText("UPDATE"));
    protected Grid<T> grid;

    public GenericModalForm(){
        
        try {
            //this.classtype = (Class<?>) ((Class)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]).newInstance();
            this.classtype = (Class<T>) ((ParameterizedType) getClass()
                            .getGenericSuperclass()).getActualTypeArguments()[0];
            this.object = (T) classtype.newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(GenericModalForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(GenericModalForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        inits();
    }
    //@PostConstruct
    public void inits(){
        this.center();
        this.setResizable(true);
        gridinit();
        updateBtn.addClickListener(e->{
            try {
                binder.writeBean(object);
            } catch (ValidationException ex) {
                Logger.getLogger(GenericModalForm.class.getName()).log(Level.SEVERE, null, ex);
            }
            grid.setItems(objects);
        });
        deleteBtn.addClickListener(e->{
        objects.remove(object);
            grid.setItems(objects);
                binder.readBean(getnewInstance());
    });
        cancelBtn.addClickListener(e->{
            this.close();
        });
        okBtn.addClickListener(e->{
         senderobjects.clear();
           for (T object:objects){
               senderobjects.add(object);
           }
         this.close();
        });
        addBtn.addClickListener(e->{
            try {
                object = getnewInstance();
                binder.writeBean(object);
            } catch (ValidationException ex) {
                Logger.getLogger(GenericModalForm.class.getName()).log(Level.SEVERE, null, ex);
            }
            objects.add(object);
            grid.setItems(objects);
            binder.readBean(getnewInstance());
        });
    }
    

    private T getnewInstance(){
        T t = null;
        try {
                t = (T) classtype.newInstance();
            } catch (InstantiationException ex) {
                Logger.getLogger(GenericModalForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(GenericModalForm.class.getName()).log(Level.SEVERE, null, ex);
                t = null;
            }
        return t;
    }

    private void gridinit() {
            grid = new Grid<T>((Class<T>) classtype);
            //grid.setItems(objects);
    }
    
    public List<T> getObjects (){
        return this.objects;
    }
    public void setObjects(List<T> objects) {
        /*Copy sender objects to local objects to implement if user push cancel button after made some inserts or deletes
        in other word made modifications in copied object not the original one*/
        
        this.objects.clear();
        for (T object:objects){
            this.objects.add(object);
        }
        this.senderobjects = objects;
        grid.setItems(this.objects);
    }

    public List<T> getSenderobjects() {
        return senderobjects;
    }

    public void setSenderobjects(List<T> senderobjects) {
        this.senderobjects = senderobjects;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public Binder<T> getBinder() {
        return binder;
    }

    public void setBinder(Binder<T> binder) {
        this.binder = binder;
    }

    public Grid<T> getGrid() {
        return grid;
    }
    

}
