package Projects.Banking_management_system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountManager {

  private Connection connection;
  private Scanner input;

  public AccountManager(Connection connection, Scanner input) {
    this.connection = connection;
    this.input = input;
  }

  public void credit_money(long account_number) throws SQLException {
    input.nextLine();

    System.out.print("Enter amount: ");
    double amount = input.nextDouble();
    input.nextLine();

    System.out.print("Enter security pin: ");
    String security_pin = input.nextLine();
    String query = "SELECT * FROM accounts WHERE account_number = ? AND security_pin = ?;";

    try {
      connection.setAutoCommit(false);
      if (account_number != 0) {
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setLong(1, account_number);
        preparedStatement.setString(2, security_pin);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
          String credit_query = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?;";

          PreparedStatement preparedStatement1 = connection.prepareStatement(credit_query);

          preparedStatement1.setDouble(1, amount);
          preparedStatement1.setLong(2, account_number);

          int rowsAffected = preparedStatement1.executeUpdate();
          if (rowsAffected > 0) {
            System.out.println("Rs. " + amount + " credited successfully");
            connection.commit();
            connection.setAutoCommit(true);
            return;
          } else {
            System.out.println("Transaction failed !");
            connection.rollback();
            connection.setAutoCommit(true);
          }
        } else {
          System.out.println("Invalid security pin ");
        }
      } else {
        System.out.println("Invalid account number!..");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    connection.setAutoCommit(true);
  }

  public void debit_money(long account_number) throws SQLException {
    input.nextLine();

    System.out.print("Enter amount: ");
    double amount = input.nextDouble();
    input.nextLine();

    System.out.print("Enter security pin: ");
    String security_pin = input.nextLine();

    String query = "SELECT * FROM accounts WHERE account_number = ? AND security_pin = ?;";

    try {
      connection.setAutoCommit(false);
      if (account_number != 0) {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, account_number);
        preparedStatement.setString(2, security_pin);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
          double current_balance = resultSet.getDouble("balance");

          if (amount <= current_balance) {
            String debit_query = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?;";
            PreparedStatement preparedStatement1 = connection.prepareStatement(debit_query);

            preparedStatement1.setDouble(1, amount);
            preparedStatement1.setDouble(2, account_number);

            int rowsAffected = preparedStatement1.executeUpdate();
            if (rowsAffected > 0) {
              System.out.println("Rs. " + amount + " Debited successfully");
              connection.commit();
              connection.setAutoCommit(true);
            } else {
              System.out.println("Transaction failed ");
              connection.rollback();
              ;
              connection.setAutoCommit(true);
            }

          } else {
            System.out.println("Insufficient balance");
          }

        } else {
          System.out.println("Invalid pin");
        }

      } else {
        System.out.println("Invalid account number ...!");
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    connection.setAutoCommit(true);
  }

  public void tranfer_money(long sender_account_number) throws SQLException {
    input.nextLine();
    System.out.print("Enter Reciever Account Number: ");
    long reciever_account_number = input.nextLong();

    System.out.print("Enter amount: ");
    double amount = input.nextDouble();
    input.nextLine();

    System.out.print("Enter security pin: ");
    String security_pin = input.nextLine();

    String query = "SELECT * FROM accounts WHERE account_number = ? AND security_pin = ?;";

    try {
      connection.setAutoCommit(false);
      if (sender_account_number != 0 && reciever_account_number != 0) {
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setLong(1, reciever_account_number);
        preparedStatement.setString(2, security_pin);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
          double current_balance = resultSet.getDouble("balance");
          if (amount <= current_balance) {
            String debit_query = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?;";
            String credit_query = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?;";

            PreparedStatement debitPreparedStatement = connection.prepareStatement(debit_query);
            debitPreparedStatement.setDouble(1, amount);
            debitPreparedStatement.setLong(2, sender_account_number);

            PreparedStatement creditPreparedStatement = connection.prepareStatement(credit_query);
            creditPreparedStatement.setDouble(1, amount);
            creditPreparedStatement.setLong(2, reciever_account_number);

            int rowsAffected1 = debitPreparedStatement.executeUpdate();
            int rowsAffected2 = creditPreparedStatement.executeUpdate();

            if (rowsAffected1 > 0 && rowsAffected2 > 0) {
              System.out.println("Transaction successfull ..!");
              connection.commit();
              connection.setAutoCommit(true);
            } else {
              System.out.println("Transaction failed..!");
              connection.rollback();
              ;
              connection.setAutoCommit(true);
            }
          } else {
            System.out.println("Insufficient balance...");
          }
        } else {
          System.out.println("Invalid pin...");
        }
      } else {
        System.out.println("Invalid account number!!!");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    connection.setAutoCommit(true);

  }

  public void getBalance(long account_number) {
    input.nextLine();
    System.out.print("Enter security pin: ");
    String security_pin = input.nextLine();

    String balance_query = "SELECT balance FROM accounts WHERE account_number = ? AND security_pin = ?;";

    try {
      PreparedStatement preparedStatement = connection.prepareStatement(balance_query);

      preparedStatement.setLong(1, account_number);
      preparedStatement.setString(2, security_pin);

      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        double balance = resultSet.getDouble("balance");
        System.out.println("Balance: " + balance);
      } else {
        System.out.println("Invalid pin");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
