/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkoc.itinventory;

import static com.kkoc.itinventory.TranslationSvc.getText;
import com.vaadin.data.Binder;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import genericdao.GenericDaoImp;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.vaadin.gridutil.cell.GridCellFilter;




/**
 *
 * @author fatih
 */
public  class GenericViewV2<T> extends VerticalLayout implements GenericObject,View{
    protected Class<T> classtype;
    protected T t;
    protected Binder<T> binder;
    protected Grid<T> grid;
    protected GridCellFilter filter;
    private GenericDaoImp dao;
    protected HorizontalLayout buttons;
    private Button saveButton = new Button(getText("SAVE"));
    private Button newButton = new Button(getText("NEW"));
    private Button deleteButton = new  Button(getText("DELETE"));

    public GenericDaoImp getDao() {
        return dao;
    }

    public void setDao(GenericDaoImp dao) {
        this.dao = dao;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public void setSaveButton(Button saveButton) {
        this.saveButton = saveButton;
    }

    public Button getNewButton() {
        return newButton;
    }

    public void setNewButton(Button newButton) {
        this.newButton = newButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;
    }
    
   
/*
    boolean valid;

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
    */
    
    
    public GenericViewV2(){
        buttons = new HorizontalLayout();
        buttons.addComponents(saveButton,newButton,deleteButton);
        ParameterizedType genericSuperclass = null;
        try
        {
        genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.classtype = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
        }
        catch (Exception e)
                {
                    System.out.print(e.getMessage());
                }
        binder = new Binder(classtype);
        grid = new Grid<>(classtype);
        grid.setWidth("1200");
        ButtonListeners();
        grid.addItemClickListener(e->{
            this.setObject ((T)e.getItem());
        });
        //filter = new GridCellFilter(grid,classtype);
    }
    @PostConstruct
    public void init(){

    }
    
    
    public Class<T> getClasstype() {
        return classtype;
    }

    public void setClasstype(Class<T> classtype) {
        this.classtype = classtype;
    }

    
    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }



    @Override
    public void setObject(Object o) {
        this.t = (T) o;
        binder.setBean(t);
    }

    @Override
    public Object getObject() {
        return t;
    }

    @Override
    public Object getNewInstance() {
        T t = null;
        try {
           t =  classtype.newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(GenericViewV2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(GenericViewV2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return t;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        return;
    }

    @Override
    public Class<?> getType() {
        return this.classtype;
    }

    @Override
    public Binder getBinder() {
        return this.binder;
    }

    @Override
    public Grid getGrid() {
        return this.grid;
    }

    @Override
    public GridCellFilter getFilter() {
        return this.filter;
    }

    private void ButtonListeners() {
        saveButton.addClickListener(e->{
            savebutton();
        });
         newButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent e) {
                newbutton();
            }
        });
        deleteButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent e) {
                deletebutton();
            }
        });
        
    }
    public void deletebutton()
    {
        dao.delete(getObject());
        setNewInstace();
        if (t instanceof AbstractCompanyEntity)
            getGrid().setItems(dao.findAllByCompany());
        else
            getGrid().setItems(dao.findAll()); 
    }
    public void newbutton(){
        setNewInstace();   
    }
    public void savebutton(){
        if (!binder.isValid())
                return;
            dao.create(getObject());
            setNewInstace();
            if (t instanceof AbstractCompanyEntity)
                     getGrid().setItems(dao.findAllByCompany());
            else
                getGrid().setItems(dao.findAll());
            /* Change Filter API provider to new provider for searching */
            if (getFilter() != null) {
                getFilter().setDataProvider((ListDataProvider) getGrid().getDataProvider());
            }
    }
    private void setNewInstace(){
                t = (T) getNewInstance();
                setObject(t);
    }

    @Override
    public boolean isValid() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setValid(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
