/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkoc.itinventory;

import static com.kkoc.itinventory.TranslationSvc.getText;
import com.vaadin.cdi.UIScoped;
import com.vaadin.data.Binder;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 *
 * @author fatih
 */
@UIScoped
public class AddressFormUI extends GenericModalForm<Address> {
    @Inject CountryDao countrydao;
    @Inject CityDao citydao;
    private FormLayout form;
    private TextField address = new TextField(getText("ADDRESS"));
    private ComboBox<Country> country = new ComboBox<>(getText("COUNTRIES"));
    private ComboBox<City> city = new ComboBox<>(getText("CITIES"));
    private TextField zipcode = new TextField(getText("ZIPCODE"));
    private Binder<Address> binder = new Binder<>(Address.class);
    public AddressFormUI(){
    }
    @PostConstruct
    public void init(){
        form = new FormLayout();
        this.center();
        form.setWidth("500px");
        this.setResizable(true);
        gridinit();
        country.setItems(countrydao.findAll());
        country.addValueChangeListener(e->{
            city.setItems(citydao.findByCountry((Country)e.getValue()));
        });
        city.setItemCaptionGenerator(e-> e.getName());
        country.setItemCaptionGenerator(e->e.getName());
        binder.bindInstanceFields(this);
        super.binder = this.binder;
        form.addComponents(address,zipcode,country,city,cancelBtn,okBtn,addBtn,deleteBtn,updateBtn,grid);
        this.setContent(form);
    }
    private void gridinit() {
            grid.getColumn("city").setHidden(true);
            grid.addColumn(e-> e.getCity().getName());
            grid.addItemClickListener(e->{
                binder.readBean(e.getItem());
                this.setObject(e.getItem());
            });
    }
    
    public List<Address> getAddresses() {
        return super.getObjects();
   }

    public void setAddresses(List<Address> addresses) {
        grid.setItems(addresses);
        super.setObjects(addresses);
    }
}
