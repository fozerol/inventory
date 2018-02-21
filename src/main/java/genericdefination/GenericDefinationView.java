/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genericdefination;

import com.kkoc.itinventory.GenericButtonGroup;
import static com.kkoc.itinventory.TranslationSvc.getText;
import com.vaadin.cdi.UIScoped;
import com.vaadin.ui.TextField;
import genericdao.Dao;
import genericdao.GenericDaoImp;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.inject.Named;
import org.vaadin.gridutil.cell.GridCellFilter;





/**
 *
 * @author fatih
 */
public class GenericDefinationView extends GenericView<GenericDefination> {
        @Inject GenericDefination genericdefination;
        @Inject GenericDaoImp<GenericDefination> dao;
        private TextField name = new TextField("Name");
        private TextField className = new TextField(getText("CLASS_NAME"));
        private TextField code = new TextField(getText("CODE"));
        private GenericButtonGroup<GenericDefination> genericbuttongroup;
        
        public GenericDefinationView(){
            
        }
        @PostConstruct
        public void init(){
        this.setClasstype(GenericDefination.class);
        this.setObject(genericdefination);
        dao.setType(GenericDefination.class);
        this.grid.setItems(dao.findAll());
        this.filter =new GridCellFilter(this.grid,GenericDefination.class);
        this.filter.setTextFilter("name", true, true);
        this.binder.forField(name).bind(GenericDefination::getName,GenericDefination::setName);
        this.binder.forField(code).bind(GenericDefination::getCode,GenericDefination::setCode);
        this.binder.forField(className).bind(GenericDefination::getClassName,GenericDefination::setClassName);
        this.binder.bindInstanceFields( this );
        genericbuttongroup = new GenericButtonGroup<>(dao,this);
        grid.addItemClickListener(e->{
           this.setObject((GenericDefination) e.getItem());
        });
        this.addComponents(name,className,code,genericbuttongroup,grid);
        }
}
