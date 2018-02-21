package com.kkoc.itinventory;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.cdi.CDIUI;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import javax.inject.Inject;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
@CDIUI("")
public class MyUI extends UI {

    @Inject PublicComponent publiccontent;
    @Inject PrivateComponent privatecontent;

    
    @Override
    protected void init(VaadinRequest vaadinRequest) {
    
        if (!AuthService.isAuthenticated()) {
            showPublicComponent();
        } else {
            showPrivateComponent();
        }
    }
    
        

    public void showPublicComponent() {
        setContent(publiccontent);
    }

    public void showPrivateComponent() {
        privatecontent.initMenu();
        setContent(privatecontent);
    }    
}
