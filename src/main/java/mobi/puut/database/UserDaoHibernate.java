package mobi.puut.database;


import mobi.puut.entities.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Chaklader on 6/13/17.
 */
@Repository
public class UserDaoHibernate extends HibernateDaoSupport implements UserDao {

    /**
     * @param user wallet user such as balance, address, transaction history and id of the user
     * @return true if the creation of the user is successful, otherwise, return false.
     */
    public boolean create(User user) {
        try {
            Session session = getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.save(user);
            tx.commit();
            session.close();
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * @param user wallet user such as balance, address, transaction history and id of the user
     * @return true if the creation of the user is successful, otherwise, return false.
     */
    public boolean createUniqueUser(User user) {
        try {
            Session session = getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.saveOrUpdate(user);
            tx.commit();
            session.close();
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void saveOrUpdate(User user) {
        try {
            Session session = getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.save(user);
            tx.commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param user
     * @return
     */
    // public int[] create(List<User> user) {
    public void create(List<User> user) {
        try {
            Session session = getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            for (User user2 : user) {
                session.save(user2);
            }
            tx.commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return get the list of all the wallet useres with the corresponding financial informations
     */
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        try {
            Session session = getSessionFactory().openSession();
            List<User> userList = session.createQuery("from User").list();
            session.close();
            return userList;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param id Id of the bitcoin wallet user
     * @return the bitcoin wallet user with the corresponding financial information's
     */
    public User getById(int id) {
        try (Session session = getSessionFactory().openSession()){
            User user = session.get(User.class, id);
            return user;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }
}

