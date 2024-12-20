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

        userList.add(new User("Marat" , "SuperMentor"     , (byte) 34));
        userList.add(new User("Artem" , "Krasava"   , (byte) 33));
        userList.add(new User("Alex", "Fefelov" , (byte) 58));
        userList.add(new User("Sergei", "Gousev", (byte) 31));

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
