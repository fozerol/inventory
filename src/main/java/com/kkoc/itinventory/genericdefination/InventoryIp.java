/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkoc.itinventory.genericdefination;

import com.kkoc.itinventory.AbstractEntity;
import com.kkoc.itinventory.Inventory;
import genericdefination.DefinationClass;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author fatih
 */
@Entity
@Table(name = "INVENTORYIP")
@NamedQuery(name="InventoryIp.findAll", query="SELECT e FROM InventoryIp e")
public class InventoryIp extends AbstractEntity  {
    @ManyToOne(fetch=FetchType.EAGER,cascade = CascadeType.REMOVE)
    @JoinColumn(name="inventoryid",referencedColumnName = "id")
    private Inventory inventory;
    private String ipaddress;

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }
}
