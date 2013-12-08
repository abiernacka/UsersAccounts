package pl.edu.agh.useraccounts.service.dao;

import org.hibernate.SessionFactory;
import pl.edu.agh.useraccounts.service.model.BaseEntity;

/**
 * Created with IntelliJ IDEA.
 * User: Khajiit
 * Date: 07.12.13
 * Time: 12:15
 * To change this template use File | Settings | File Templates.
 */
public class BaseDaoBean<T extends BaseEntity> implements BaseDao<T> {

    private SessionFactory sessionFactory;

    private Class<T> type;

    public BaseDaoBean(Class<T> type) {
        this.type = type;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(T entity) {
        sessionFactory.getCurrentSession().saveOrUpdate(entity);
    }

    @Override
    public void delete(T entity) {
        sessionFactory.getCurrentSession().delete(entity);
    }

    @Override
    public T get(Long id) {
        return (T) sessionFactory.getCurrentSession().get(type, id);
    }
}
