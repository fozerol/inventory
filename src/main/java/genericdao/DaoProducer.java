package genericdao;
import genericdao.GenericDaoImp;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionPoint;


public class DaoProducer implements Serializable {
 
    private static final long serialVersionUID = 1L;
 
    @Produces
    @Dependent//must be dependent pseudoScope cause baseDao is a SLB
    @Dao
    public <T> GenericDaoImp<T> produce(InjectionPoint ip, BeanManager bm) {
        if (ip.getAnnotated().isAnnotationPresent(Dao.class)) {
            GenericDaoImp<T> genericDao = (GenericDaoImp<T>)  this.getBeanByName("baseDao", bm);//ask bean manager for a instance of GenericDao
            ParameterizedType type = (ParameterizedType) ip.getType();
            //Type[] typeArgs = type.getActualTypeArguments();
            Class<T> entityClass = (Class<T>) type.getActualTypeArguments()[0];
            //genericDao.setEntityClass(entityClass);
            genericDao.setType(entityClass);
            return genericDao;
        }
        throw new IllegalArgumentException("Annotation @Dao is required when injecting BaseDao");
    }
 
    public Object getBeanByName(String name, BeanManager bm) { // eg. name=availableCountryDao{
        Bean bean = bm.getBeans(name).iterator().next();
        CreationalContext ctx = bm.createCreationalContext(bean); // could be inlined below
        Object o = bm.getReference(bean, bean.getBeanClass(), ctx); // could be inlined with return
        return o;
    }
}