package repository;

import exceptions.UserNotFoundException;
import model.User;
import util.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    public static void insert(String name, String surname, int age, String email, String password) {
        Connection connection = DataSource.getConnection();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into users values (0,?,?,?,?,?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setInt(3, age);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, password);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static List<User> getAll() {
        Connection connection = DataSource.getConnection();
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select * from users");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                int age = resultSet.getInt("age");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                User user = new User(id, firstName, lastName, age, email, password);
                users.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public static User getById(int id) {
        Connection connection = DataSource.getConnection();
        User user = null;
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select * from users where id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                int age = resultSet.getInt("age");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                user = new User(id, firstName, lastName, age, email, password);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;

    }

    public static void update(User user) {
        User userDb = getById(user.getId());
        if (userDb == null) {
            throw new UserNotFoundException("User not found with given id" + user.getId());
        }

        Connection connection = DataSource.getConnection();

        try {
            PreparedStatement statement = connection
                    .prepareStatement("update users set first_name = ?,last_name = ?,age = ?,email = ?,password = ? where id = ?");
            statement.setString(1, user.getName() == null ? userDb.getName() : user.getName());
            statement.setString(2, user.getSurname() == null ? userDb.getSurname() : user.getSurname());
            statement.setInt(3, user.getAge() == 0 ? userDb.getAge() : user.getAge());
            statement.setString(4, user.getEmail() == null ? userDb.getEmail() : user.getEmail());
            statement.setString(5, user.getPassword() == null ? userDb.getPassword() : user.getPassword());
            statement.setInt(6, user.getId() == 0 ? userDb.getId() : user.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void delete(int id) {
        User user = getById(id);
        if (user == null) {
            throw new UserNotFoundException("User not found with given id" + user.getId());
        }
        Connection connection = DataSource.getConnection();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from users where id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
