package whosHome.common.dataProviders.db;

import com.google.inject.Inject;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import whosHome.common.dataProviders.IDataProvider;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDatabaseDao<I extends Serializable, T> implements IDataProvider<I, T> {
    private Hibernate _hibernate;
    private Class<I> _idType;
    private Class<T> _entityType;

    @Inject @Autowired
    public AbstractDatabaseDao(Hibernate hibernate) {
        this.setHibernate(hibernate);

        Type[] genericTypeArgs = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();
        this._idType = (Class<I>) genericTypeArgs[0];
        this._entityType = (Class<T>) genericTypeArgs[1];
    }

    @Override
    public synchronized Collection<T> fetchAll() {
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
    public synchronized I add(T record) {
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        I generatedID = (I) session.save(record); // TODO: 7/24/2018 check this
        session.getTransaction().commit();
        session.close();
        return generatedID;
    }

    @Override
    public synchronized void add(Iterable<T> records) {
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();

        for (T record : records) {
            session.save(record);
        }

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public synchronized void update(T record) {
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.update(record);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public synchronized void delete(I id) {
        Session session = getSessionFactory().getCurrentSession();
        Query query = session.createQuery("delete from " + this.getEntityType().getName() + " where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
        session.close();
    }

    @Override
    public synchronized void delete(Collection<I> ids) {
        Session session = getSessionFactory().getCurrentSession();
        Query query = session.createQuery("delete from " + this.getEntityType().getName() + " where id in (:ids)");
        query.setParameterList("ids", ids);
        query.executeUpdate();
        session.close();
    }

    protected void setHibernate(Hibernate hibernate) {
        this._hibernate = hibernate;
    }

    protected Hibernate getHibernate() {
        return this._hibernate;
    }

    protected SessionFactory getSessionFactory() {
        return this._hibernate.getSessionFactory();
    }

    protected Class<I> getIdType() {
        return _idType;
    }

    protected Class<T> getEntityType() {
        return _entityType;
    }
}
