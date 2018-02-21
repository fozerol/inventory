package com.kkoc.itinventory;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.kkoc.itinventory.GenericViewV2;
import com.kkoc.itinventory.Inventory;
import static com.kkoc.itinventory.TranslationSvc.getText;
import com.kkoc.itinventory.genericdefination.InventoryDao;
import com.kkoc.itinventory.genericdefination.InventoryIp;
import com.kkoc.itinventory.genericdefination.InventoryLicence;
import com.kkoc.itinventory.genericdefination.Os;
import com.vaadin.data.converter.StringToFloatConverter;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import genericdao.GenericDaoImp;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.vaadin.gridutil.cell.GridCellFilter;

/**
 *
 * @author fatih
 */
public class InventoryView extends GenericViewV2<Inventory> {

    @Inject
    InventoryDao dao;
    @Inject
    GenericDaoImp<Os> osdao;
    @Inject
    GenericDaoImp<InventoryIp> inventoryipdao;
    @Inject
    GenericDaoImp<InventoryLicence> inventorylicencedao;
    @Inject
    Inventory inventory;
    @Inject InventoryIpView inventoryipview;
    @Inject InventoryLicenceView inventorylicenceview;
    private TextField description = new TextField(getText("DESCRIPTION")) ;
    private TextField name = new TextField(getText("NAME")) ;
    private TextField accessdescription = new TextField(getText("ACCESSDESCRIPTION")) ;
    private TextField textipaddress = new TextField(getText("IPADDRESS")) ;
    private ComboBox<Os> os = new ComboBox(getText("OS"));
    private ComboBox<InventoryLicence> cblicence = new ComboBox(getText("LICENCES"));
    private Grid<InventoryIp> ipgrid = new Grid(InventoryIp.class);
    private Grid<InventoryLicence> licencegrid = new Grid(InventoryLicence.class);
    private Button inventoryip = new Button(getText("INVENTORYIP"));
    private Button inventorylicence = new Button(getText("INVENTORYLICENCE"));
    
    
    
    @PostConstruct
    public void init() {
        this.setDao(dao);
        this.setObject(inventory);
        
        prepareDao();
        prepareCombo();
        prepareMisc();
        prepareLayout();
    }

    private void prepareDao() {
        osdao.setType(Os.class);
        inventoryipdao.setType(InventoryIp.class);
        inventorylicencedao.setType(InventoryLicence.class);
    }

    private void prepareCombo() {
        
        os.setItems(osdao.findAll());
        os.setItemCaptionGenerator(e->e.getName());
        
    }

    private void prepareMisc() {
        ipgrid.setWidth("200px");
        ipgrid.getColumn("id").setHidden(true);
        ipgrid.getColumn("inventory").setHidden(true);
        licencegrid.setWidth("400px");
        licencegrid.getColumn("id").setHidden(true);
        licencegrid.getColumn("inventory").setHidden(true);
        licencegrid.getColumn("licence").setHidden(true);        
        licencegrid.addColumn(e->e.getLicence().getName());
        prepareBinder();
        grid.setItems(dao.findAll());
        grid.addItemClickListener(e->{
        refreshGrid();
    });
        filter = new GridCellFilter(grid,Inventory.class);
    }

    private void prepareBinder() {
                binder.forField ( this.description )
              .withNullRepresentation ( "" )
              .bind ( Inventory:: getDescription, Inventory:: setDescription );
        
               binder.forField ( this.accessdescription )
              .withNullRepresentation ( "" )
              .bind ( Inventory:: getAccessDescription, Inventory:: setAccessDescription );
               binder.forField ( this.os )
              .bind ( Inventory:: getOs, Inventory:: setOs );
               
               binder.forField ( this.name )
              .withNullRepresentation ( "" )
              .bind ( Inventory:: getName, Inventory:: setName );
               
              this.binder.bindInstanceFields(this);

    }
    

    private void prepareLayout() {
                HorizontalLayout mainlayout = new HorizontalLayout();
                VerticalLayout column1 = new VerticalLayout();
                VerticalLayout column2 = new VerticalLayout();
                this.getDeleteButton().addClickListener(e->{
                    ipgrid.setItems(((Inventory)this.getObject()).getInventoryIpaddress());
                    licencegrid.setItems(((Inventory)this.getObject()).getInventorylicence());
                });
                this.getNewButton().addClickListener(e->{
                    ipgrid.setItems(((Inventory)this.getObject()).getInventoryIpaddress());
                    licencegrid.setItems(((Inventory)this.getObject()).getInventorylicence());
                });
                this.getSaveButton().addClickListener(e->{
                    ipgrid.setItems(((Inventory)this.getObject()).getInventoryIpaddress());
                    licencegrid.setItems(((Inventory)this.getObject()).getInventorylicence());
                });
                inventoryip.addClickListener(e->{
                    inventoryip();
                });
                inventorylicence.addClickListener(e->{
                            inventorylicence();
                        });
                inventoryipview.addCloseListener(e->{
                    for (InventoryIp invip:((Inventory)this.getObject()).getInventoryIpaddress() ){
                invip.setInventory((Inventory)this.getObject());
                refreshGrid();
                    }
                });
                inventorylicenceview.addCloseListener(e->{
                    for (InventoryLicence i:((Inventory)this.getObject()).getInventorylicence() ){
                    i.setInventory((Inventory)this.getObject());
                    refreshGrid();
                    }
                });
               
                column1.addComponents(name,os,inventoryip,ipgrid);
                column2.addComponents(description,accessdescription,inventorylicence,licencegrid);
                mainlayout.addComponents(column1,column2);
                this.addComponents(mainlayout,
                buttons,grid);
    }

    private void inventoryip() {
    inventoryipview.setModal(true);
    inventoryipview.setInventoryIp(((Inventory)this.getObject()).getInventoryIpaddress());
            this.getUI().addWindow(inventoryipview);
     refreshGrid();
    }
    
    private void inventorylicence() {
    inventorylicenceview.setModal(true);
    inventorylicenceview.setInventoryLicence(((Inventory)this.getObject()).getInventorylicence());
    this.getUI().addWindow(inventorylicenceview);
    
    }
    private void refreshGrid()
    {
        ipgrid.setItems(((Inventory)this.getObject()).getInventoryIpaddress());
        licencegrid.setItems(((Inventory)this.getObject()).getInventorylicence());
    }
}
