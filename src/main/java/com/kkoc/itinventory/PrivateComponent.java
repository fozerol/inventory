/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkoc.itinventory;

import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Page;
import com.vaadin.server.Page.Styles;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 *
 * @author fatih
 */
@CDIUI("homescreen")
@SuppressWarnings("serial")
public class PrivateComponent extends HorizontalLayout {
    
    @Inject    TreeViewUI tvui;
    @Inject
    private CDIViewProvider viewProvider;
    //@Inject FlowDao flowdao;
    //@Inject FlowFormView flowformview;

    Navigator n;
    Panel rightlayout = new Panel();
    HorizontalLayout headerbar = new HorizontalLayout();
    VerticalLayout rightcontent = new VerticalLayout();
    HorizontalLayout content = new HorizontalLayout();
    //CssLayout leftlayout = new CssLayout();
    VerticalLayout leftlayout = new VerticalLayout();
    Label applicationLabel = new Label();
    
    
    public PrivateComponent()
    {
        
        
        }
    public void initMenu()
    {  
        /*root layout must full size  no space and no margin*/
        this.setSizeFull();
        this.setSpacing(false);
        this.setMargin(false);
        
       /* Panel full size */ 
       rightlayout.setSizeFull();
       rightcontent.addComponents(headerbar,content);
       //rightcontent.setExpandRatio(headerbar,0.01f);
       rightcontent.setExpandRatio(content,1f);
       rightcontent.addStyleName("layout-bggreytext");
       //headerbar.addStyleName("layout-bgwhite");
       headerbar.setHeight("30px");
       content.addStyleName("layout-bgwhite");
//       applicationLabel.addStyleName("v-label-stylename");
       applicationLabel.setContentMode(ContentMode.HTML);
       tvui.setLabel(applicationLabel);
       leftlayout.setSpacing(false);
       leftlayout.setMargin(false);

       tvui.setSpacing(false);
       tvui.setSizeFull();
       rightlayout.setContent(rightcontent);
       rightcontent.setSpacing(true);
       rightcontent.setMargin(true);
       //rightcontent.setSizeFull();
       content.setWidth("100%");
        headerbar.setMargin(true);
        headerbar.setSpacing(true);
        headerbar.addComponent(applicationLabel);
        headerbar.addStyleName("layout-with-bottom-border");
        headerbar.setComponentAlignment(applicationLabel, Alignment.TOP_LEFT);
        
        Button buttonlogout = new Button("Sign out", this::onLogout);
        n = new Navigator(MyUI.getCurrent(),content);
        headerbar.addComponents(buttonlogout);
        headerbar.setComponentAlignment(buttonlogout, Alignment.TOP_RIGHT);
        headerbar.setWidth("100%");
        
        leftlayout.setSizeFull();
        this.setSizeFull();
        leftlayout.setSpacing(false);
        leftlayout.setMargin(false);
        //leftlayout.setHeight("100%");
        Component labels = buildLabels();
        this.addComponents(leftlayout,rightlayout);
        content.addStyleName("layout-with-border");
        content.addStyleName("layout-bgwhite");
        Label userLabel = new Label(AuthService.getUser().getName().toUpperCase() + " "  + AuthService.getUser().getSurname().toUpperCase());
        //userLabel.addStyleName("valo-menu");
        leftlayout.addStyleName("valo-menu");
        leftlayout.addComponents(labels,userLabel,tvui);
        leftlayout.setComponentAlignment(userLabel,Alignment.MIDDLE_CENTER);
        leftlayout.setExpandRatio(tvui, 1.0f);
        //leftlayout.setComponentAlignment(labels, Alignment.MIDDLE_CENTER);
        leftlayout.setComponentAlignment(tvui, Alignment.TOP_CENTER);
        this.setExpandRatio(leftlayout, 0.15f);
        this.setExpandRatio(rightlayout, 0.85f);
        
        tvui.setNaviagator(n);
         n.addView("", WellCome.class);
         
        try {
            try {
                tvui.init();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(PrivateComponent.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(MyUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(MyUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    n.navigateTo("");
        
    }
    
    
    public void onLogout(Button.ClickEvent e){
        AuthService.logOut();
    }
    private Component buildLabels() {
        VerticalLayout labels = new VerticalLayout();
        labels.setSizeUndefined();
        labels.setMargin(false);
        labels.setSpacing(false);
        Label welcome = new Label("INVENTORY MANAGEMENT");
        labels.addStyleName("valo-menu-title");
        welcome.setSizeUndefined();
        welcome.addStyleName(ValoTheme.LABEL_H4);
        welcome.addStyleName(ValoTheme.LABEL_COLORED);
        labels.addComponents(welcome);
        labels.setComponentAlignment(welcome, Alignment.MIDDLE_CENTER);
        return labels;
    }
}
