/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkoc.itinventory;

import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.Setter;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import genericdefination.GenericView;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.vaadin.gridutil.cell.GridCellFilter;




/**
 *
 * @author fatih
 */
public class UserView extends GenericView<User>{
        @Inject User user;
        @Inject UserDao dao;
        @Inject RoleDao roledao;
        @Inject CompanyDao companydao;
        private TextField name = new TextField("Name");
        private TextField surName = new TextField("Sur Name");
        private TextField userName = new TextField("username");
        private TextField email = new TextField("email");
        private PasswordField password = new PasswordField("password");
        private List<UserRole> userroles = new ArrayList<>();
        private List<Role> roles = new ArrayList<>();
        private List<Role> usergrantedroles = new ArrayList<>();
        private List<Role> savedroles = new ArrayList<>();
        private ComboBox<Company> company = new ComboBox<>("Company");
        private FTwinColSelect<Role> tcsroles;
        private GenericButtonGroup<User> genericbuttongroup;
        private Button createflow = new Button("Create Flow");
        
        public UserView(){
            
        }
        @PostConstruct
        public void init(){
        name.setId("1");
        surName.setId("2");
        userName.setId("3");
        email.setId("4");
        company.setId("5");
        this.grid.setId("6");
        roles = roledao.findAll();
        tcsroles = new FTwinColSelect<>(null,roles);
        tcsroles.setId("7");
        tcsroles.setItemCaptionGenerator(e->((Role)e).getName());
        company.setItems(companydao.findAll());
        company.setItemCaptionGenerator(o->o.getName());
        this.grid.setItems(dao.findAll());
       
        this.binder.forField(name).bind(User::getName,User::setName);
        this.binder.forField(surName).bind(User::getSurname,User::setSurname);
        this.binder.forField(userName).bind(User::getUserName,User::setUserName);
        this.binder.forField(email).bind(User::getEmail,User::setEmail);
        this.binder.forField(password).bind(User::getPassword,User::setPassword);
        this.binder.forField(password).bind(new ValueProvider<User,String>(){
            public String apply(User user) {
                return null;
            }
            
        },new Setter<User,String>(){
            public void accept(User user,String password) {
            user.setPassword(generatePassord(password));
            }
        });
        this.binder.forField(company);
        this.binder.bindInstanceFields( this );
        genericbuttongroup = new GenericButtonGroup<>(dao,this);
        this.grid.getColumn("company").setHidden(true);
//        this.filter =new GridCellFilter(grid,User.class);
//        this.filter.setTextFilter("name");
        this.addComponents(name,surName,userName,password,email,company,tcsroles,genericbuttongroup,createflow,grid);
        grid.addItemClickListener(e->{
           this.setObject((User) e.getItem());
           //setTcsValue(((User)e.getItem()).getUserRoles());
           tcsroles.setSelected  (((User)e.getItem()).getUserRoles().stream().map(UserRole::getRole).collect(Collectors.toList()) );
                           
       });
        createflow.addClickListener(e->{
        });
        this.setObject(user);
     }
       
        @Override
        public User getObject(){
            /* add newly added roles */
            for (Role r:tcsroles.getNewlyAdded() ){
                    //sourceroles){
                UserRole ur = new UserRole();
                ur.setUser((User)this.getT());
                ur.setRole(r);
               ((User)this.getT()).getUserRoles().add(ur);
            }
            /*remove deleted roles */
             for (Role r:tcsroles.getDeleted()){
                    ((User)this.getT()).removeUserRoleByRole(r);
            }
            return (User)this.getT();
    }

    public String generatePassord(String password){
        DefaultHashService hashService = new DefaultHashService();
        DefaultPasswordService passwordService = new DefaultPasswordService();
        passwordService.setHashService(hashService);
        String encryptedPassword = passwordService.encryptPassword(password);
        return encryptedPassword;
    }
}
