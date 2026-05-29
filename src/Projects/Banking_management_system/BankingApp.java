package Projects.Banking_management_system;

import java.sql.*;
import java.util.Scanner;

public class BankingApp {
  private static final String url = "jdbc:postgresql://localhost:5432/Banking_system";
  private static final String username = "postgres";
  private static final String password = "postgres";

  public static void main(String[] args) throws ClassNotFoundException, SQLException {

    try {
      Class.forName("org.postgresql.Driver");
      System.out.println("Driver loaded");
    } catch (ClassNotFoundException e) {
      System.out.println(e.getMessage());
    }

    try {
      Connection connection = DriverManager.getConnection(url, username, password);
      Scanner input = new Scanner(System.in);

      User user = new User(connection, input);
      Accounts accounts = new Accounts(connection, input);
      AccountManager accountManager = new AccountManager(connection, input);
      
      String email;
      long account_number;

      while (true) {
        System.out.println();
        System.out.println();
        System.out.println("******* Welcome To  Banking System *******");
        System.out.println();
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
        int choice1 = input.nextInt();

        switch (choice1) {
          case 1:
            user.register();
            break;
          case 2:
              email = user.login();
              if (email != null) {
                System.out.println();
                System.out.println("User logged in!");
                if (!accounts.account_exist(email)) {
                  System.out.println();
                  System.out.println("1. Open A New Bank Account");
                  System.out.println("2. Exit");
                  System.out.print("Enter choice: ");

                  if (input.nextInt() == 1) {
                    account_number = accounts.open_account(email);
                    System.out.println("Account created successfully  ...");
                    System.out.println("Your Account number is: "+account_number);
                  } else {
                    break;
                  }
                } 
              } else {
                System.out.println("Invalid email");
              }

              account_number = accounts.get_account_number(email);
              int choice2 = 0;
              while (choice2 != 5) {
                System.out.println();
                System.out.println("1. Debit money");
                System.out.println("2. Credit money");
                System.out.println("3. Transfer money");
                System.out.println("4. check balance");
                System.out.println("5. log out");
                System.out.print("Enter your choice: ");
                choice2 = input.nextInt();

                switch (choice2) {
                  case 1:
                    accountManager.debit_money(account_number);
                    break;
                  case 2:
                    accountManager.credit_money(account_number);
                    break;
                  case 3:
                    accountManager.tranfer_money(account_number);
                    break;
                  case 4:
                    accountManager.getBalance(account_number);
                    break;
                  case 5:
                    break;
                
                  default:
                    System.out.println("Enter valid choice");
                    break;
                }
              }
            break;
        
          case 3:
            System.out.println("Exiting System");
            System.out.println("Thank you using Bnaking System...❤️");
            return;
        
          default:
            System.out.println("Enter valid choice");
            break;
        }
      }


    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
