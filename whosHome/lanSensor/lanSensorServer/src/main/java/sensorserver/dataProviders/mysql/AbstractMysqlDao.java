package sensorserver.dataProviders.mysql;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import sensorserver.dataProviders.dao.IDataAccessor;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public abstract class AbstractMysqlDao<I extends Serializable, T> implements IDataAccessor<I, T> {
    private SessionFactory _sessionFactory;
    private Class<I> _idType;
    private Class<T> _entityType;

    public AbstractMysqlDao(SessionFactory sessionFactory) {
        this.setSession(sessionFactory);

        Type[] genericTypeArgs = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();
        this._idType = (Class<I>) genericTypeArgs[0];
        this._entityType = (Class<T>) genericTypeArgs[1];
    }

    @Override
    public synchronized Iterable<T> fetchAll() {
        Session session = this.getSessionFactory().openSession();
        Query query = session.createQuery(String.format("from %s", getEntityType().getName()));
        List<T> results = (List<T>) query.list();
        session.close();
        return results;
    }

    @Override
    public synchronized Optional<T> fetchById(I id) {
        Session session = this.getSessionFactory().openSession();
        T result = (T) session.get(_entityType, id);
        session.close();
        return Optional.ofNullable(result);
    }

    @Override
    public synchronized void add(T record) {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        session.save(record);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public synchronized void add(Iterable<T> records) {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();

        for (T record : records) {
            session.save(record);
        }

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public synchronized void update(T record) {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        session.update(record);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public synchronized void delete(I id) {
        Session session = getSessionFactory().openSession();
        Query query = session.createQuery("delete from " + this.getEntityType().getName() + " where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
        session.close();
    }

    @Override
    public synchronized void delete(Collection<I> ids) {
        Session session = getSessionFactory().openSession();
        Query query = session.createQuery("delete from " + this.getEntityType().getName() + " where id in (:ids)");
        query.setParameterList("ids", ids);
        query.executeUpdate();
        session.close();
    }

    protected void setSession(SessionFactory sessionFactory) {
        this._sessionFactory = sessionFactory;
    }

    protected SessionFactory getSessionFactory() {
        return this._sessionFactory;
    }

    protected Class<I> getIdType() {
        return _idType;
    }

    protected Class<T> getEntityType() {
        return _entityType;
    }
}
