package ch06_BatchProcessing;

import java.sql.*;

public class BatchProcessing_stmt_1 {
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

      Statement statement = connection.createStatement();
      statement.addBatch("INSERT INTO employees(name, dept) VALUES ('Piyush garg', 'chaicode Manager');");
      statement.addBatch("INSERT INTO employees(name, dept) VALUES ('Hitesh chaudary', 'chaicode MD');");
      statement.addBatch("INSERT INTO employees(name, dept) VALUES ('Haris Ali', 'CodeWithHarry instructor');");

      int[] batchResult = statement.executeBatch();
      connection.commit();
      System.out.println("Batch executed successfully");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

  }
}
