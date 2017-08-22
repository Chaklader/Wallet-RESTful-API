package mobi.puut.database;

import mobi.puut.entities.User;

import java.util.List;

public interface UserDao {

    boolean create(User user);

    void saveOrUpdate(User user);

    boolean create(List<User> users);

    List<User> getAllUsers();

    User getById(int id);
}
