package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
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
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            sessionFactory.getCurrentSession()
                    .createSQLQuery("CREATE TABLE IF NOT EXISTS User(id MEDIUMINT NOT NULL AUTO_INCREMENT, name TEXT, lastName TEXT, age TINYINT, PRIMARY KEY (id))")
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            sessionFactory.getCurrentSession()
                    .createSQLQuery("DROP TABLE IF EXISTS User")
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            sessionFactory.getCurrentSession().save(new User(name, lastName, age));
            session.getTransaction().commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            User user = sessionFactory.getCurrentSession().load(User.class, id);
            sessionFactory.getCurrentSession().delete(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            CriteriaQuery<User> query = sessionFactory
                    .getCriteriaBuilder()
                    .createQuery(User.class);
            Root<User> variableRoot = query.from(User.class);
            query.select(variableRoot);
            List<User> userList = sessionFactory.getCurrentSession().createQuery(query).getResultList();
            session.getTransaction().commit();
            return userList;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            sessionFactory.getCurrentSession()
                    .createSQLQuery("TRUNCATE User")
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }
}
