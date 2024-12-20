package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    private static final UserService INSTANCE = new UserServiceImpl();
    private static final UserDao USERD = UserDaoHibernateImpl.getInstance();

    public UserServiceImpl() {
    }

    public static UserService getInstance() {
        return INSTANCE;
    }

    @Override
    public void createUsersTable() {
        USERD.createUsersTable();
    }

    @Override
    public void dropUsersTable() {
        USERD.dropUsersTable();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        USERD.saveUser(name, lastName, age);
    }

    @Override
    public void removeUserById(long id) {
        USERD.removeUserById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return USERD.getAllUsers();
    }

    @Override
    public void cleanUsersTable() {
        USERD.cleanUsersTable();
    }
}
