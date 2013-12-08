package pl.edu.agh.useraccounts.service.dao;

/**
 * Created with IntelliJ IDEA.
 * User: Khajiit
 * Date: 07.12.13
 * Time: 14:20
 * To change this template use File | Settings | File Templates.
 */
public interface BaseDao<T> {

    T get(Long id);

    void delete(T entity);

    void save(T entity);
}
