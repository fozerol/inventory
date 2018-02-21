/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkoc.itinventory;

import static com.kkoc.itinventory.TranslationSvc.getText;
import com.vaadin.data.TreeData;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.data.converter.StringToLongConverter;
import com.vaadin.data.provider.TreeDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import genericdefination.GenericView;
import java.text.NumberFormat;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 *
 * @author fatih
 */
public class CompanyView extends GenericView<Company>{
        @Inject Company company;
        @Inject CompanyDao dao;
        @Inject AddressFormUI addressform;
        private TextField name = new TextField("Company name");
        private TextField taxNumber = new TextField("Tax Number");
        private TextField address = new TextField("Company adress");
        private Button addressbtn = new Button(getText("ADDRESS"));
        private GenericButtonGroup<Company> genericbuttongroup;
        public CompanyView(){
            
        }
        @PostConstruct
        public void init(){
        this.setObject(company);
        this.grid.setItems( dao.findAll());
        this.binder.forField(name).bind(Company::getName,Company::setName);
        this.binder.forField(address).bind(Company::getAddress,Company::setAddress);
        StringToLongConverter s = new StringToLongConverter("Must be valid tax number") {
        protected java.text.NumberFormat getFormat(Locale locale) {
        NumberFormat format = super.getFormat(locale);
        format.setGroupingUsed(false);
        return format;
        };
        };
        this.binder.forField(taxNumber).withNullRepresentation("").withConverter(s).withValidator(tax -> tax >= 10000000000L && tax < 99999999999L, "Person must have a valid tax number")
        .bind(Company::getTaxNumber,Company::setTaxNumber);
        this.binder.bindInstanceFields( this );
        genericbuttongroup = new GenericButtonGroup<>(dao,this);
       this.addComponents(name,taxNumber,address,addressbtn,genericbuttongroup,grid);
       grid.addItemClickListener(e->{
           this.setObject((Company) e.getItem());
       });
       addressform.setModal(true);
        addressbtn.addClickListener(e->{
            addressform.setAddresses(((Company)this.getObject()).getAddresses());
            this.getUI().addWindow(addressform);
        });
        addressform.addCloseListener(e->{
                for (Address address:((Company)this.getObject()).getAddresses() ){
                address.setCompany((Company)this.getObject());
            }
        });
        }
}
        

    
