/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genericdefination;

import com.kkoc.itinventory.GenericObject;
import com.vaadin.data.Binder;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Grid;
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
public  class GenericView<T> extends VerticalLayout implements GenericObject,View{
    protected Class<T> classtype;
    protected T t;
    protected Binder<T> binder;
    protected Grid<T> grid;
    protected GridCellFilter filter;
   

    boolean valid;

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
    
    
    public GenericView(){
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
            Logger.getLogger(GenericView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(GenericView.class.getName()).log(Level.SEVERE, null, ex);
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

}
