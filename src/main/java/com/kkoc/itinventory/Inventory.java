/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkoc.itinventory;

import com.kkoc.itinventory.genericdefination.InventoryIp;
import com.kkoc.itinventory.genericdefination.InventoryLicence;
import com.kkoc.itinventory.genericdefination.Os;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author fatih
 */
@Entity
@Table(name = "INVENTORY")
@NamedQuery(name="Inventory.findAll", query="SELECT e FROM Inventory e")

public class Inventory extends AbstractEntity implements Serializable{
@OneToOne
@JoinColumn(name = "osid")
private Os os;
private String name;

@OneToMany
(mappedBy = "inventory",orphanRemoval=true, cascade = CascadeType.ALL,fetch = FetchType.LAZY)
private List<InventoryIp> inventoryIpaddress = new ArrayList<>();
@Column(length = 1000)
private String description;
@OneToMany
(mappedBy = "inventory",orphanRemoval=true, cascade = CascadeType.ALL,fetch = FetchType.LAZY)
private List<InventoryLicence> inventorylicence = new ArrayList<>();
@Column(length = 1000)
private String accessDescription;

    public Os getOs() {
        return os;
    }

    public void setOs(Os os) {
        this.os = os;
    }

    public List<InventoryIp> getInventoryIpaddress() {
        return inventoryIpaddress;
    }

    public void setInventoryIpaddress(List<InventoryIp> inventoryIpaddress) {
        this.inventoryIpaddress = inventoryIpaddress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<InventoryLicence> getInventorylicence() {
        return inventorylicence;
    }

    public void setInventorylicence(List<InventoryLicence> inventorylicence) {
        this.inventorylicence = inventorylicence;
    }

    public String getAccessDescription() {
        return accessDescription;
    }

    public void setAccessDescription(String accessDescription) {
        this.accessDescription = accessDescription;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}

