/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkoc.itinventory;

import static com.kkoc.itinventory.TranslationSvc.getText;
import com.kkoc.itinventory.genericdefination.InventoryIp;
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
public class InventoryIpView extends GenericModalForm<InventoryIp> {
    private FormLayout form;
    private TextField ipaddress = new TextField(getText("IPADDRESS"));
    private Binder<InventoryIp> binder = new Binder<>(InventoryIp.class);
    public InventoryIpView(){
    }
    @PostConstruct
    public void init(){
        form = new FormLayout();
        this.center();
        form.setWidth("500px");
        this.setResizable(true);
        gridinit();
        binder.bindInstanceFields(this);
        super.binder = this.binder;
        form.addComponents(ipaddress,cancelBtn,okBtn,addBtn,deleteBtn,updateBtn,grid);
        this.setContent(form);
    }
    private void gridinit() {
            grid.getColumn("id").setHidden(true);
            grid.getColumn("inventory").setHidden(true);
            grid.addItemClickListener(e->{
                binder.readBean(e.getItem());
                this.setObject(e.getItem());
            });
    }
    
    public List<InventoryIp> getInventoryIp() {
        return super.getObjects();
   }

    public void setInventoryIp(List<InventoryIp> inventoryip) {
        grid.setItems(inventoryip);
        super.setObjects(inventoryip);
    }
}
