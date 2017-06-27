package mobi.puut.database;

import java.util.List;
import mobi.puut.entities.User;

/**
 * Created by Chaklader on 6/13/17.
 */
public interface UserDao {

    User getById(int id);

    List<User> getAllUsers();

    void saveOrUpdate(User user);
}

