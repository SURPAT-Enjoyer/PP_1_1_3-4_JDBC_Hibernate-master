package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {
        sessionFactory = Util.getSessionFactory();
    }


    @Override
    public void createUsersTable() {
        Transaction transaction = sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession()
                .createSQLQuery("CREATE TABLE IF NOT EXISTS User(id MEDIUMINT NOT NULL AUTO_INCREMENT, name TEXT, lastName TEXT, age TINYINT, PRIMARY KEY (id))")
                .executeUpdate();
        transaction.commit();
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession()
                .createSQLQuery("DROP TABLE IF EXISTS User")
                .executeUpdate();
        transaction.commit();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().save(new User(name, lastName, age));
        transaction.commit();
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = sessionFactory.getCurrentSession().beginTransaction();
        User user = sessionFactory.getCurrentSession().load(User.class, id);
        sessionFactory.getCurrentSession().delete(user);
        transaction.commit();
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = sessionFactory.getCurrentSession().beginTransaction();
        CriteriaQuery<User> query = sessionFactory
                .getCriteriaBuilder()
                .createQuery(User.class);
        Root<User> variableRoot = query.from(User.class);
        query.select(variableRoot);
        List<User> userList = sessionFactory.getCurrentSession().createQuery(query).getResultList();
        transaction.commit();
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession()
                .createSQLQuery("TRUNCATE User")
                .executeUpdate();
        transaction.commit();
    }
}
