package ch03_prepared_statements;

import java.sql.*;
import java.util.Scanner;

public class Insertion_2 {
  public static void main(String[] args) {

    String url = "jdbc:postgresql://localhost:5432/students";
    String username = "postgres";
    String password = "postgres";
    String query = "INSERT INTO employees(id, name, dept) VALUES (?, ?, ?)";

    try {
      Class.forName("org.postgresql.Driver");
      System.out.println("Drivers loaded succussfully");
    } catch (ClassNotFoundException e) {
      System.out.println(e.getMessage());
    }

    try {
      Connection connection = DriverManager.getConnection(url, username, password);
      System.out.println("Connection established successfully");

      Scanner input = new Scanner(System.in);

      System.out.print("Enter your ID: ");
      int id = input.nextInt();
      input.nextLine();

      System.out.print("Enter your Name: ");
      String name = input.nextLine();

      System.out.print("Enter your Department: ");
      String department = input.nextLine();

      PreparedStatement preparedStatement = connection.prepareStatement(query);

      preparedStatement.setInt(1, id);
      preparedStatement.setString(2, name);
      preparedStatement.setString(3, department);

      int rowsAffected = preparedStatement.executeUpdate();

      if (rowsAffected > 0) {
        System.out.println(" Data Insertion successfull. " + rowsAffected + " Row(s) affected");
      } else {
        System.out.println("Insertion of data is failed");
      }

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }


  }
}
