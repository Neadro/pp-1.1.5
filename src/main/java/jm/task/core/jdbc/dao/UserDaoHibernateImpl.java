package jm.task.core.jdbc.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jm.task.core.jdbc.exception.DaoException;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.SqlString;
import jm.task.core.jdbc.util.Util;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final UserDaoHibernateImpl INSTANCE = new UserDaoHibernateImpl();
    private static final EntityManagerFactory entityManagerFactory;

    static {
        try {
            entityManagerFactory = Util.getEntityManagerFactory();
        } catch (Exception exception) {
            throw new DaoException(exception);
        }
    }

    public UserDaoHibernateImpl() {
    }

    public static UserDaoHibernateImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public void createUsersTable() {
        executeUpdate(SqlString.CREATE, "Ошибка при создании таблицы пользователей.");
    }

    @Override
    public void dropUsersTable() {
        executeUpdate(SqlString.DROP, "Ошибка при удалении таблицы пользователей.");
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.persist(new User(name, lastName, age));
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            throw new DaoException(exception);
        }
    }

    @Override
    public void removeUserById(long id) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            User user = entityManager.find(User.class, id);
            if (user != null) {
                entityManager.remove(user);
                entityManager.getTransaction().commit();
            } else {
                System.out.println("Пользователь с id " + id + " не найден.");
            }
        } catch (Exception exception) {
            throw new DaoException(exception);
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            List<User> users = entityManager.createQuery(SqlString.GETALLHQL, User.class).getResultList();

            StringBuilder output = new StringBuilder();
            for (User user : users) {
                output.append(user.toString()).append("\n");
            }

            System.out.println(output);
            return users;
        } catch (Exception exception) {
            throw new DaoException(exception);
        }
    }

    @Override
    public void cleanUsersTable() {
        executeUpdate(SqlString.CLEAN, "Ошибка при очистке таблицы пользователей.");
    }

    private void executeUpdate(String query, String errorMessage) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.createNativeQuery(query).executeUpdate();
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            throw new DaoException(exception);
        }
    }

    public void closeEntityManagerFactory() {
        if (entityManagerFactory != null) {
            try {
                entityManagerFactory.close();
            } catch (Exception exception) {
                throw new DaoException(exception);
            }
        }
    }
}
