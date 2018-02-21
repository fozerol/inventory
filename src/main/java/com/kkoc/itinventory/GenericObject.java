/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkoc.itinventory;

import com.vaadin.data.Binder;
import com.vaadin.ui.Grid;
import genericdao.GenericDaoImp;
import java.util.List;
import org.vaadin.gridutil.cell.GridCellFilter;

/**
 *
 * @author fatih
 */
public interface  GenericObject {
    void setObject(Object o);
    Object getObject();
    Object getNewInstance();
    Class<?> getType();
    Binder getBinder();
    Grid getGrid();
    GridCellFilter getFilter();
    boolean isValid();
    void setValid(boolean b);
}
