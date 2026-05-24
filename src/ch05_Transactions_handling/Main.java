package ch05_Transactions_handling;

import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    String url = "jdbc:postgresql://localhost:5432/students";
    String username = "postgres";
    String password = "postgres";
    String withdrawQuery = "UPDATE accounts SET balance = balance - ? WHERE accounts_number = ?;";
    String depositQuery = "UPDATE accounts SET balance = balance + ? WHERE accounts_number = ?;";

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
      try {
        PreparedStatement withdrawStatement = connection.prepareStatement(withdrawQuery);

        PreparedStatement depositStatement = connection.prepareStatement(depositQuery);

        withdrawStatement.setDouble(1, 500.00);
        withdrawStatement.setString(2, "account456");

        depositStatement.setDouble(1, 500.00);
        depositStatement.setString(2, "account44545");

        int rowsAffectedWithdrawal = withdrawStatement.executeUpdate();
        int rowsAffectedDeposit = depositStatement.executeUpdate();

        if (rowsAffectedWithdrawal > 0 && rowsAffectedDeposit > 0) {
          connection.commit();
          System.out.println("Transaction successful!. ");
        } else {
          connection.rollback();
          System.out.println("Transaction failed!. AND Rolled back to it state");
        }

      } catch (SQLException e) {
        System.out.println(e.getMessage());
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

  }
}
