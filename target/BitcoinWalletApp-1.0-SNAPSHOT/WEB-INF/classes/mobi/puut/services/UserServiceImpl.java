package mobi.puut.services;

import java.util.List;
import java.util.Objects;

import mobi.puut.database.IUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mobi.puut.entities.User;


/**
 * Created by Chaklader on 6/19/17.
 */
@Service("userService")
public class UserServiceImpl implements IUserService {

    @Autowired
    public IUserDao IUserDAO;

    public List<User> getCurrentStatuses() {
        return IUserDAO.getAllUsers();
    }

    public void create(User user) {
        IUserDAO.saveOrUpdate(user);
    }

    public List<User> getAllUsers() {
        List<User> users = IUserDAO.getAllUsers();

        if (Objects.isNull(users)) {
            return null;
        }
        return users;
    }
}

