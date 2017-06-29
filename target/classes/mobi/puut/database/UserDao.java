package mobi.puut.database;

/**
 * Created by Chaklader on 6/19/17.
 */

import java.util.List;

import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import mobi.puut.entities.User;

/**
 * Created by Chaklader on 6/13/17.
 */
public interface UserDao {

    User getById(int id);

    List<User> getAllUsers();

    void saveOrUpdate(User user);

}

