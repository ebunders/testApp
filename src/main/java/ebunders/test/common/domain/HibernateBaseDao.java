package ebunders.test.common.domain;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import ebunders.test.common.entity.Identifiable;
import ebunders.test.util.ReflectionUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Ernst Bunders on 21-10-14.
 */
public class HibernateBaseDao<T extends Identifiable> implements BaseDao<T> {
    protected final Class<T> entityClass;

    @Autowired
    private SessionFactory sessionFactory;

    public HibernateBaseDao() {
        this.entityClass = (Class<T>) ReflectionUtil.getTypeArguments(HibernateBaseDao.class, getClass()).get(0);
    }

    @Override
    @Transactional
    public Iterator<T> getAll() {
        //TODO: why do we get a list heren?
        return createCriteria(false).list().iterator();
    }

    @Override
    @Transactional
    public void saveOrUpdate(T entity) {
        getSession().saveOrUpdate(entity);
    }

    @Override
    @Transactional
    public Optional<T> findById(String id) {
        try {
            return Optional.of((T) getSession().load(entityClass, id));
        } catch (DataAccessException e) {
            return Optional.absent();
        }
    }

    @Override
    @Transactional
    public void delete(T entity) {
        getSession().delete(entity);
    }

    @Override
    @Transactional
    public void deleteAll() {
        //TODO: there must be a better way to batch delete entities.
        for (Iterator<T> i = getAll(); i.hasNext(); ) {
            delete(i.next());
        }
    }

    protected Criteria createCriteria(boolean useCache) {
        Criteria criteria = getSession().createCriteria(entityClass);
        criteria.setCacheable(useCache);
        return criteria;
    }

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    protected Optional<T> getByParameters(String field, Object value) {
        return getByParameters(ImmutableMap.of(field, value));
    }

    protected Iterator<T> getAllByParameters(String field, Object value){
        return getAllByParameters(ImmutableMap.of(field, value));
    }


    protected Optional<T> getByParameters(Map<String, ?> values) {
        Criteria criteria = createCriteria(true);
        for (String key : values.keySet()) {
            criteria.add(Restrictions.eq(key, values.get(key)));
        }
        T result = (T) criteria.uniqueResult();
        if (result != null) {
            return Optional.of(result);
        }
        return Optional.absent();
    }

    protected Iterator<T> getAllByParameters(Map<String, ?> values) {
        Criteria criteria = createCriteria(true);
        for (String key : values.keySet()) {
            criteria.add(Restrictions.eq(key,values.get(key)));
        }
        //TODO: why is this not an iterator?
        return (Iterator<T>) criteria.list().iterator();
    }
}
