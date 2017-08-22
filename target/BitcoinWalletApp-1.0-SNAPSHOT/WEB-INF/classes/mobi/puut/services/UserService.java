package mobi.puut.services;

import mobi.puut.entities.User;

import java.util.List;

public interface UserService {

    List<User> getCurrentStatuses();

    void create(User user);

    List<User> getAllUsers();
}
