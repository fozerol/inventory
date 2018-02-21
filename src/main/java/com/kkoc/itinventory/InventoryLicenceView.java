/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkoc.itinventory;

import static com.kkoc.itinventory.TranslationSvc.getText;
import com.kkoc.itinventory.genericdefination.InventoryIp;
import com.kkoc.itinventory.genericdefination.InventoryLicence;
import com.vaadin.cdi.UIScoped;
import com.vaadin.data.Binder;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import genericdao.GenericDaoImp;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 *
 * @author fatih
 */
@UIScoped
public class InventoryLicenceView extends GenericModalForm<InventoryLicence> {
    @Inject GenericDaoImp<Licence> licencedao;
    private FormLayout form;
    private ComboBox<Licence> licence = new ComboBox(getText("LICENCE"));
    private Binder<InventoryLicence> binder = new Binder<>(InventoryLicence.class);
    public InventoryLicenceView(){
    }
    @PostConstruct
    public void init(){
        licencedao.setType(Licence.class);
        licence.setItems(licencedao.findAll());
        licence.setItemCaptionGenerator(e->e.getName());
        form = new FormLayout();
        this.center();
        form.setWidth("500px");
        this.setResizable(true);
        gridinit();
        binder.bindInstanceFields(this);
        super.binder = this.binder;
        form.addComponents(licence,cancelBtn,okBtn,addBtn,deleteBtn,updateBtn,grid);
        this.setContent(form);
    }
    private void gridinit() {
            grid.getColumn("id").setHidden(true);
            grid.getColumn("inventory").setHidden(true);
            grid.getColumn("licence").setHidden(true);
            grid.addColumn(e-> e.getLicence().getName());
            grid.addItemClickListener(e->{
                binder.readBean(e.getItem());
                this.setObject(e.getItem());
            });
    }
    
    public List<InventoryLicence> getInventoryLicence() {
        return super.getObjects();
   }

    public void setInventoryLicence(List<InventoryLicence> inventoryLicence) {
        grid.setItems(inventoryLicence);
        super.setObjects(inventoryLicence);
    }
}
