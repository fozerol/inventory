/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkoc.itinventory;

import org.apache.shiro.subject.Subject;

/**
 *
 * @author fatih
 */
interface SecurityContext {
//public void setSecurityManager(SecurityManager securityManager);
public Subject getSubject();
}
