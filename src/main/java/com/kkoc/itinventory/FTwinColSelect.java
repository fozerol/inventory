/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkoc.itinventory;

import com.vaadin.ui.TwinColSelect;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author fatih
 */
public class FTwinColSelect<T extends EntityObject> extends TwinColSelect {
        private List<T> t =  new ArrayList<>();
        private List<T> tempt =  new ArrayList<>();
        

    public FTwinColSelect() {
    }

    public FTwinColSelect(String caption, Collection t) {
        super(caption, t);
        this.t = (List<T>) t;
    }
    public List<T> getValueAsList(){
        return (List<T>) (this.getValue()).stream().collect(Collectors.toList());
    }
    public void setSelected(List<T> listentity){
          tempt.clear();
            for (EntityObject titems:t){
                for (EntityObject tempitems:listentity){
                    if (titems.getId()==tempitems.getId())
                        tempt.add((T) titems);
                }
           }
            this.setValue(tempt.stream().collect(Collectors.toSet()));
    }
    public List<T> getDeleted(){
        return findDifference(tempt,(List<T>) (this.getValue()).stream().collect(Collectors.toList()));
    }
    public List<T> getNewlyAdded(){
        return findDifference((List<T>) (this.getValue()).stream().collect(Collectors.toList()),tempt);
    }

    private List<T> findDifference(List<T> list1,List<T> list2){
            List<T> difference = new ArrayList<>();
            boolean isExist = false;
            for(T r1:list1){
                
                for (T r2:list2){
                    if (r1.getId()==r2.getId()){
                        isExist = true;
                        break;
                    }
                    }
                if (!isExist){
                        difference.add(r1);
                }
                isExist = false;
            }
            return difference;
        }
   
}
