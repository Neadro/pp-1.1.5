package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.exception.DaoException;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.SqlString;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final UserDaoJDBCImpl INSTANCE = new UserDaoJDBCImpl();
    private final Connection connection;


    private UserDaoJDBCImpl() {
        try {
            this.connection = Util.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static UserDaoJDBCImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public void createUsersTable() {
        executeUpdate(SqlString.CREATE);
    }

    @Override
    public void dropUsersTable() {
        executeUpdate(SqlString.DROP);
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement statement = connection.prepareStatement(SqlString.SAVE)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
    }

    @Override
    public void removeUserById(long id) {
        try (PreparedStatement statement = connection.prepareStatement(SqlString.REMOVE)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SqlString.GETALLSQL);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                User user = new User(
                        resultSet.getString("name"),
                        resultSet.getString("last_name"),
                        resultSet.getByte("age")
                );
                user.setId(resultSet.getLong("id"));
                userList.add(user);
            }
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        executeUpdate(SqlString.CLEAN);
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException exception) {
                throw new DaoException(exception);
            }
        }
    }
    private void executeUpdate(String sql) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
    }

    public void printAllUsers() {
        List<User> users = getAllUsers();
        for (User user : users) {
            System.out.println(user.toString());
        }
    }
}
