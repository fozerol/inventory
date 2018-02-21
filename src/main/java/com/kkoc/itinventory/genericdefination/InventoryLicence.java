/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkoc.itinventory.genericdefination;

import com.kkoc.itinventory.*;
import genericdefination.DefinationClass;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author fatih
 */
@Entity
@Table(name = "INVENTORYLICENCE")
@NamedQuery(name="InventoryLicence.findAll", query="SELECT e FROM InventoryLicence e")
public class InventoryLicence extends AbstractEntity  {
    @ManyToOne(fetch=FetchType.EAGER,cascade = CascadeType.REMOVE)
    @JoinColumn(name="inventoryid",referencedColumnName = "id")
    private Inventory inventory;
    @OneToOne
    @JoinColumn(name = "licenceid")
    private Licence licence;

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Licence getLicence() {
        return licence;
    }

    public void setLicence(Licence licence) {
        this.licence = licence;
    }
}
