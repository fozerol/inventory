/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkoc.itinventory;

import static com.kkoc.itinventory.TranslationSvc.getText;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.ValueProvider;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.data.provider.DataCommunicator;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HasComponents;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import genericdefination.GenericView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Inject;



/**
 *
 * @author fatih
 */
public class CustomerView extends GenericView<Customer> implements View{
    @Inject CustomerDao dao;
    @Inject Customer customer;
    @Inject CustomerType customertype;
    @Inject CustomerTypeDao typedao;
    @Inject AddressFormUI addressform;
    
    //@Inject FlowFormDataDao flowformdatadao;
    private TextField name = new TextField("Name");
    private TextField surname = new TextField("Surname");;
    private TextField taxnumber = new TextField("Tax Number");
    private ComboBox<CustomerType>  type =new ComboBox<>("Select Type");
    private Button send = new Button("Send to Flow");
    private Button addressbtn = new Button(getText("ADDRESS"));
    private List<Customer> customers = new ArrayList<>();
    private GenericButtonGroup<Customer> genericbuttongroup;

    public CustomerView(){
        name.setId("tf1");
        surname.setId("tf2");
        taxnumber.setId("3");
        type.setId("combo1");
        send.setId("Button1");
     //   grid.setId("grid1");
    }
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        return;
    }
    @PostConstruct
    public void init(){
        initBinder();
        type.setItems(typedao.findAll());
        type.setItemCaptionGenerator(CustomerType::getName);
        initGrid();
        grid.setId("grid1");
        grid.setItems(dao.findAll());
        this.setObject(customer);
//        this.addComponents(grid);
        //genericbuttongroup = new GenericButtonGroup<>(Customer.class,dao,binder,customer,grid,this);
        genericbuttongroup = new GenericButtonGroup<>(dao,this);
        this.addComponents(send,name,surname,type,taxnumber,addressbtn,genericbuttongroup,grid);
        this.setMargin(true);
        this.setSpacing(true);
        send.addClickListener(e->{
        });
        addressform.setModal(true);
        addressbtn.addClickListener(e->{
            addressform.setAddresses(((Customer)this.getObject()).getAddresses());
            this.getUI().addWindow(addressform);
        });
        addressform.addCloseListener(e->{
                for (Address address:((Customer)this.getObject()).getAddresses() ){
                address.setCustomer((Customer)this.getObject());
            }
        });
    }
    public void initGrid(){
                 grid.addColumn(e -> {
             if (((Customer)e).getType() == null)
             {
                 return "null";
             }
             return ((Customer)e).getType().getName();
         });
         grid.getColumn("type").setHidden(true);
         //()grid.getDataProvider()
         grid.addItemClickListener(e->{
            this.setObject((Customer)e.getItem());
         });
    }
    public void initBinder()
    {
                
      binder.forField(taxnumber).withConverter(
      new StringToIntegerConverter("Must enter a number")).
      bind(Customer::getTaxnumber,Customer::setTaxnumber);
      binder.forField(name).bind(Customer::getName,Customer::setName);
      binder.forField(surname).bind(Customer::getSurname,Customer::setSurname);
      binder.forField(type).bind(Customer::getType,Customer::setType);  
    }
   
}
