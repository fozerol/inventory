package genericdefination;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import com.kkoc.itinventory.CustomerType;
import static com.kkoc.itinventory.TranslationSvc.getText;
import com.vaadin.cdi.UIScoped;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import genericdao.Dao;
import genericdao.GenericDaoImp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.inject.Named;





/**
 *
 * @author fatih
 */
public class DefinationView extends VerticalLayout implements View{
        @Inject GenericDaoImp<GenericDefination> genericdefinationdao;
        @Inject GenericDaoImp<DefinationClass> dao;
        private DefinationClass object;
        private ComboBox<GenericDefination> genDefination = new ComboBox<>(getText("GEN_DEFINATIONS"));
        private TextField name = new TextField(getText("NAME"));
        private TextField code = new TextField(getText("CODE"));
        //private Grid<DefinationClass> grid = new Grid<>();
        private Binder<DefinationClass> binder;
        private Button saveBtn = new Button(getText("SAVE"));
        private Button newBtn = new Button(getText("NEW"));
        private Button deleteBtn = new Button(getText("DELETE"));
        private Grid grid;
        private Class<?> classtype;
        
        public DefinationView(){
            
        }
        @PostConstruct
        public void init(){
        dao.setType(DefinationClass.class);
        genericdefinationdao.setType(CustomerType.class);
        initCombo();
        initButtons();
        binder = new Binder(DefinationClass.class);
        this.binder.forField(name).bind(DefinationClass::getName,DefinationClass::setName);
        this.binder.forField(code).bind(DefinationClass::getCode,DefinationClass::setCode);
        this.binder.bindInstanceFields( this );
        
         
        this.addComponents(name,genDefination,code,saveBtn,newBtn,deleteBtn);
        }

    private void initCombo() {
        genericdefinationdao.setType(GenericDefination.class);
        genDefination.setItems(genericdefinationdao.findAll());
        genDefination.setItemCaptionGenerator(GenericDefination::getName);
        genDefination.addValueChangeListener(e->{
            try {
                classtype = getType(((GenericDefination)e.getValue()).getClassName());
                dao.setType(classtype);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DefinationView.class.getName()).log(Level.SEVERE, null, ex);
            }
            dao.setType(classtype);
            setNewInstance();
            binder.setBean(object);
            if (grid !=null){
                this.removeComponent(grid);
            }
            grid = new Grid<>(classtype) ;
             grid.addItemClickListener(p->{object = (DefinationClass) p.getItem();});
            this.grid.setItems(dao.findAll());
            this.addComponent(grid);
        });
    }
    public Class<?> getType(String classname) throws ClassNotFoundException{
        return Class.forName(classname);
    }

    private void initButtons() {
        saveBtn.addClickListener(e->{
            if (!this.binder.isValid())
                return;
            try {
                binder.writeBean(object);
            } catch (ValidationException ex) {
                Logger.getLogger(DefinationView.class.getName()).log(Level.SEVERE, null, ex);
            }
            dao.create(object);
            grid.setItems(dao.findAll());
            setNewInstance();
            /*if (o.getFilter() != null) {
                o.getFilter().setDataProvider((ListDataProvider) o.getGrid().getDataProvider());
            }*/
        });
        
        
        newBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent e) {
                setNewInstance();
            }
        });
        deleteBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent e) {
                dao.delete(object);
                setNewInstance();
                grid.setItems(dao.findAll());
            }
        });
    }
    public void setNewInstance(){
        try {
                object = (DefinationClass) getType(genDefination.getValue().getClassName()).newInstance();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DefinationView.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(DefinationView.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(DefinationView.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
}
