package com.kkoc.itinventory;

import static com.kkoc.itinventory.AuthService.setUser;
import com.vaadin.cdi.CDIUI;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Alejandro Duarte.
*/
@CDIUI("loginscreen")
@SuppressWarnings("serial")
public class PublicComponent extends CustomComponent {
    @Inject UserDao userdao;
    ComboBox<Language> language = new ComboBox<>();
        

    public PublicComponent() {
        
    }
    @PostConstruct
    public void init(){
        
        TextField username = new TextField("Username");
        language.setItems(AuthService.langs);
        language.setEmptySelectionAllowed(false);
        language.setValue(AuthService.langs.iterator().next());
        language.setItemCaptionGenerator(e-> e.getName());
        username.focus();
        PasswordField password = new PasswordField("Password");
        CheckBox rememberMe = new CheckBox("Remember me");
        Button button = new Button("Login", e -> onLogin(username.getValue(), password.getValue(), rememberMe.getValue()));
        button.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        FormLayout formLayout = new FormLayout(username, password, button,language,rememberMe);
        formLayout.setSizeUndefined();
        VerticalLayout layout = new VerticalLayout(formLayout);
        layout.setSizeFull();
        layout.setComponentAlignment(formLayout, Alignment.MIDDLE_CENTER);
        setCompositionRoot(layout);
        setSizeFull();
    }

    private void onLogin(String username, String password, boolean rememberMe) {
        if (AuthService.login(username, password, rememberMe,language.getSelectedItem().get())) {
            MyUI ui = (MyUI) UI.getCurrent();
            setUser(userdao.findByUsername(username));
            ui.showPrivateComponent();
            
        } else {
            Notification.show("Invalid credentials (for demo use: admin/password)", Notification.Type.ERROR_MESSAGE);
        }
    }
}
