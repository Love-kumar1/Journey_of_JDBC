package ch06_BatchProcessing;

import java.sql.*;
import java.util.Scanner;

public class BatchProcessing_pstmt_2 {
  public static void main(String[] args) {
    String url = "jdbc:postgresql://localhost:5432/students";
    String username = "postgres";
    String password = "postgres";
    try {
      Class.forName("org.postgresql.Driver");
      System.out.println("Drivers loaded successfully");
    } catch (ClassNotFoundException e) {
      System.out.println(e.getMessage());
    }

    try {
      Connection connection = DriverManager.getConnection(url, username, password);
      System.out.println("connection established successfully");

      connection.setAutoCommit(false);

      String query = "INSERT INTO employees(name, dept) VALUES (?, ?);";
      PreparedStatement preparedStatement = connection.prepareStatement(query);

      Scanner input = new Scanner(System.in);
      while (true) {
        System.out.print("Name: ");
        String name = input.nextLine();
        System.out.print("Department: ");
        String dept = input.nextLine();

        preparedStatement.setString(1, name);
        preparedStatement.setString(2, dept);
        preparedStatement.addBatch();

        System.out.print("Add more values (y/n): ");
        String decision = input.nextLine();

        if (decision.toUpperCase().equals("N")) {
          break;
        }

      }
      int[] batchResult = preparedStatement.executeBatch();
      connection.commit();
      System.out.println("Batch executed successfully");

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

  }
}
