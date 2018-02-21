/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkoc.itinventory;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author fatih
 */
public class WellCome extends VerticalLayout implements View{
    private final TextField t1 = new TextField("Hoş Geldiniz");
    public WellCome(){
        this.addComponent(t1);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        return;
    }
}
