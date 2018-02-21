package com.kkoc.itinventory;

import static com.kkoc.itinventory.AuthService.SESSION_USERNAME;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Notification;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.session.ExpiredSessionException;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

/**
 * @author Alejandro Duarte.
 */
public class UserService {

    private static SecureRandom random = new SecureRandom();

    private static Map<String, String> rememberedUsers = new HashMap<>();
    private static IniSecurityManagerFactory  factory = new IniSecurityManagerFactory("classpath:/shiro.ini");
    //private static org.apache.shiro.mgt.SecurityManager  securitymanager = factory.getInstance();
    
    
    public static boolean isAuthenticUser(String username, String password) {
        
        org.apache.shiro.mgt.SecurityManager  securitymanager = factory.getInstance();
        //org.apache.shiro.SecurityUtils.setSecurityManager(securitymanager);
        VaadinSecurityContext vsc = new VaadinSecurityContext();
        vsc.setSecurityManager(securitymanager);
        
       //Subject subject = org.apache.shiro.SecurityUtils.getSubject();
       Subject subject = vsc.getSubject();
        try 
        { 
            subject.login(new UsernamePasswordToken(username,password));
            subject.getSession().setAttribute("a", username);
            VaadinSession.getCurrent().setAttribute("subject", subject);
            VaadinSession.getCurrent().setAttribute("username", username);
            Notification.show("Login Success");
            return true;
        }
        catch (UnknownSessionException use) {
        subject = new Subject.Builder().buildSubject();
        try{
        subject.login(new UsernamePasswordToken(username,password));
            return true;
        }
//    session = subject.getSession(true);
        catch (AuthenticationException e) {
            return false;
        } 
        }
    }
    public static boolean hasRole(String role){
        boolean hasrole = false;
        if ((Subject) VaadinSession.getCurrent().getAttribute("subject") == null)
            return false;
        else
        {
            try
            {
                hasrole = ((Subject) VaadinSession.getCurrent().getAttribute("subject")).hasRole(role);
            }
            catch (Exception e)
            {
                VaadinSession.getCurrent().close();
                Page.getCurrent().setLocation("");
            }
        }
        return  hasrole;
    }
    public static Date getLastTime(){
        return ((Subject) VaadinSession.getCurrent().getAttribute("subject")).getSession().getLastAccessTime();
    }
    
    public static String rememberUser(String username) {
        String randomId = new BigInteger(130, random).toString(32);
        rememberedUsers.put(randomId, username);
        return randomId;
    }

    public static String getRememberedUser(String id) {
        return rememberedUsers.get(id);
    }

    public static void removeRememberedUser(String id) {
        rememberedUsers.remove(id);
    }

}
