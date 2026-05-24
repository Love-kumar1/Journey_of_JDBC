package ch03_prepared_statements;

import java.sql.*;

public class Retrieval_1 {
  public static void main(String[] args) {
    String url = "jdbc:postgresql://localhost:5432/students";
    String username = "postgres";
    String password = "postgres";
    String query = "SELECT * FROM employees WHERE name = ? AND dept = ?;";

    try {
      Class.forName("org.postgresql.Driver");
      System.out.println("Driver loaded");
    } catch (ClassNotFoundException e) {
      System.out.println(e.getMessage());
    }

    try {
      Connection connection = DriverManager.getConnection(url, username, password);
//      Statement statement = connection.createStatement(); // normal statement

      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setString(1, "Darpan");
      preparedStatement.setString(2, "nextjs specialist");

      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String department = resultSet.getString("dept");

        System.out.println("ID: "+id);
        System.out.println("NAME: "+name);
        System.out.println("DEPARTMENT: "+department);
      }

      resultSet.close();
      preparedStatement.close();
      connection.close();
      System.out.println();
      System.out.println("Connection closed successfully");

    } catch (SQLException e) {
    }

  }
}
