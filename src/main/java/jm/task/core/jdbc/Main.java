package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<User> userList = new ArrayList<>();

        userList.add(new User("John" , "Doe"     , (byte) 25));
        userList.add(new User("Jane" , "Smith"   , (byte) 30));
        userList.add(new User("David", "Johnson" , (byte) 35));
        userList.add(new User("Sarah", "Williams", (byte) 28));

        UserService userService = UserServiceImpl.getInstance();

        userService.createUsersTable();

        for (User user : userList) {
            userService.saveUser(user.getName(),
                                 user.getLastName(),
                                 user.getAge());
            System.out.printf("User с именем — %s добавлен в базу данных\n",
                    user.getName());
        }

        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();

        UserDaoHibernateImpl.getInstance().closeEntityManagerFactory();
    }
}
