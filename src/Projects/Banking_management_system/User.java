package Projects.Banking_management_system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class User {

  private Connection connection;
  private Scanner input;

  public User(Connection connection, Scanner input) {
    this.connection = connection;
    this.input = input;
  }

  public void register() {
    input.nextLine();

    System.out.print("Enter Your Full Name: ");
    String full_name = input.nextLine();

    System.out.print("Enter Your Email Address: ");
    String email = input.nextLine();

    System.out.print("Enter Your Password: ");
    String password = input.nextLine();

    if (user_exists(email)) {
      System.out.println("User already exists for this email address..! ");
      return;
    }

    String register_query = "INSERT INTO users(full_name, email, password) VALUES (?, ?, ?)";

    try {
      PreparedStatement preparedStatement = connection.prepareStatement(register_query);
      preparedStatement.setString(1, full_name);
      preparedStatement.setString(2, email);
      preparedStatement.setString(3, password);

      int rowsAffected = preparedStatement.executeUpdate();

      if (rowsAffected > 0) {
        System.out.println("Registration successful ...!");
      } else {
        System.out.println("Registration failed !..");
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public String login() {

    input.nextLine();

    System.out.print("Enter Your Email Address: ");
    String email = input.nextLine();

    System.out.print("Enter Your Password: ");
    String password = input.nextLine();

    String login_query = "SELECT * FROM users WHERE email = ? AND password = ?";

    try {
      PreparedStatement preparedStatement = connection.prepareStatement(login_query);
      preparedStatement.setString(1, email);
      preparedStatement.setString(2, password);

      ResultSet resultSet = preparedStatement.executeQuery();

      if (resultSet.next()) {
        return email;
      } else {
        return null;
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return null;

  }

  public boolean user_exists(String email) {
    String query = "SELECT * FROM users WHERE email = ?;";

    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setString(1, email);

      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        return true;
      } else {
        return false;
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return false;
    
  }

  
}
