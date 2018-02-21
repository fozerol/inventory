/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genericdao;

import com.kkoc.itinventory.AbstractCompanyEntity;
import static com.kkoc.itinventory.AuthService.getLanguage;
import static com.kkoc.itinventory.AuthService.getUser;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;


/**
 *
 * @author fatih
 */
//@Stateless
//@Named("baseDao")
public  class GenericDaoImp<T>{// implements GenericDao<T>{
    
    @PersistenceContext(unitName = "pu")
    protected EntityManager em;    
    protected Class<T> type;
    public void setType(Class type) {
        this.type = type;
    }

    public GenericDaoImp() {
    }
    
   @PostConstruct
   public void init(){
       
     
   }
    public void setEm(EntityManager em) {
        this.em = em;
    }

    //@Override
    public long countAll(){
        long count = (long) em.createNamedQuery(type.getSimpleName()+".countAll").getSingleResult();
        return count;
    }
    

    //@Override
    @Transactional @TransactionDebugger
    public T create(T t) {
      //  try
//        {
        if (t instanceof AbstractCompanyEntity)
        {
            if (((AbstractCompanyEntity) t).getCompany() == null)
            {
                ((AbstractCompanyEntity) t).setCompany(getUser().getCompany());
            }
        }
        t = em.merge(t);
        this.em.persist(t);
  //      }
    //    }
      //   catch (Exception e){
      //              System.out.print(e.getStackTrace());
        //    }
                return t;
    }
    //@Override
    @Transactional @TransactionDebugger
    public void delete(T t) {
        
            
        t = em.merge(t);
        em.remove(t);
        }
       
    
    //@Override
    public T find(Object id) {
        return this.em.find(type, id);
                //find(type, id);
    }
    public List<T> findAll()
    {
        
        List<T> result = em.createNamedQuery(type.getSimpleName()+".findAll").getResultList();
        return result;
    }
    
    public List<T> findAllByCompany()
    {
        List<T> result = em.createNamedQuery(type.getSimpleName()+".findAllByCompany").setParameter("company",getUser().getCompany()).getResultList();
        return result;
    }
    
    //@Override
    public T update(T t) {
        this.em.merge(t);
        return t;
    }

    //@Override
    public T findById(int id) {
        List<T> result = em.createNamedQuery(type.getSimpleName()+".findById").setParameter("id", id).getResultList();
        if (result.size() == 0){
            return null;
        }
        else{
        return result.iterator().next();
        }
    }
    
    
    public List<T> findAllWithTranslation() {
        List<T> result = new ArrayList<>();
            //result  = em.createNamedQuery(type.getSimpleName()+".findAllWithTranslation").setParameter(1, getLanguage().getId() ).getResultList();
            int i = getLanguage().getId();
            result  = em.createNamedQuery(type.getSimpleName() + ".findAllWithTranslation").setParameter("languageid", getLanguage().getId()).getResultList();
        if (result.size() == 0){
            result = new ArrayList<>();
            return result;
        }
        else{
        return result;
        }
    }
}
