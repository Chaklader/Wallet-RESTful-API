package mobi.puut.database;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Valeriy Kotenok on 23-Jun-17.
 */
public abstract class HibernateDaoSupport {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
