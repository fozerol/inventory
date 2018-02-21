package com.kkoc.itinventory;

import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import genericdao.GenericDaoImp;
import java.util.ArrayList;

import javax.servlet.http.Cookie;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javax.inject.Inject;

/**
 * @author Alejandro Duarte Edited by fatih.
 */
public class AuthService {
    private static final String COOKIE_NAME = "remember-me";
    public static final String SESSION_USERNAME = "username";
    public static         List<Language> langs = new ArrayList<Language>() {
    {
    add(new Language(1,"Turkish","tr","TR"));
    add(new Language(2,"English","en","US"));
    }
    };
    
    
    public static String getUsername() {
        return (String)VaadinSession.getCurrent().getAttribute(SESSION_USERNAME);
    }
    public static User getUser() {
            return (User)VaadinSession.getCurrent().getAttribute("USER");
    }

    public static Language getLanguage() {
        Language l = (Language)VaadinSession.getCurrent().getAttribute("Language");
        return l;
    }

    public static void setLanguage(Language language) {
        VaadinSession.getCurrent().setAttribute("Language", language);
    }

    public static void setUsername(String username) {
        VaadinSession.getCurrent().setAttribute(SESSION_USERNAME, username);
    }
    public static void setUser(User user){
        VaadinSession.getCurrent().setAttribute("USER", user);
    }



    public static boolean isAuthenticated() {
        //return VaadinSession.getCurrent().getAttribute(SESSION_USERNAME) != null || loginRememberedUser();
        return getUsername() != null || loginRememberedUser();
    }

    public static boolean login(String username, String password, boolean rememberMe,Language language) {
        if (UserService.isAuthenticUser(username, password)) {
            VaadinSession.getCurrent().setAttribute(SESSION_USERNAME, username);
//            setUser(userdao.findByUsername(username));
            setLanguage(language);

            if (rememberMe) {
                rememberUser(username);
            }
            return true;
        }

        return false;
    }
    
    public static VaadinSession getVaadinSession(){
        return VaadinSession.getCurrent();
    }
    public static void logOut() {
        Optional<Cookie> cookie = getRememberMeCookie();
        if (cookie.isPresent()) {
            String id = cookie.get().getValue();
            UserService.removeRememberedUser(id);
            deleteRememberMeCookie();
        }

        VaadinSession.getCurrent().close();
        Page.getCurrent().setLocation("");
    }
    

    private static Optional<Cookie> getRememberMeCookie() {
        Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();
        return Arrays.stream(cookies).filter(c -> c.getName().equals(COOKIE_NAME)).findFirst();
    }

    private static boolean loginRememberedUser() {
        Optional<Cookie> rememberMeCookie = getRememberMeCookie();

        if (rememberMeCookie.isPresent()) {
            String id = rememberMeCookie.get().getValue();
            String username = UserService.getRememberedUser(id);

            if (username != null) {
                VaadinSession.getCurrent().setAttribute(SESSION_USERNAME, username);
                return true;
            }
        }

        return false;
    }

    private static void rememberUser(String username) {
        String id = UserService.rememberUser(username);

        Cookie cookie = new Cookie(COOKIE_NAME, id);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 30);
        VaadinService.getCurrentResponse().addCookie(cookie);
    }

    private static void deleteRememberMeCookie() {
        Cookie cookie = new Cookie(COOKIE_NAME, "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        VaadinService.getCurrentResponse().addCookie(cookie);
    }

}
