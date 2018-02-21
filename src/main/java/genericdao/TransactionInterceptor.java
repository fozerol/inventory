/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genericdao;

import java.io.Serializable;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Interceptor
@TransactionDebugger
public class TransactionInterceptor {
    
    
    @AroundInvoke
    public Object runInTransaction(InvocationContext invocationContext) throws Exception {
        Object result = null;
        try {
            result = invocationContext.proceed();
        } catch (Exception e) {
            System.out.print("Error encountered during Transaction." + e.getMessage());
            throw e;
        }
        return result;
    }
}