package ch02_CRUD_and_Connection;

import java.sql.*;
public class Deletion_JDBC_3 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        String url = "jdbc:postgresql://localhost:5432/students";
        String username = "postgres";
        String password = "postgres";
        String query = "DELETE FROM employees WHERE id = 3;";

        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("Driver loaded");
        }
        catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Database connection established");
            Statement statement = connection.createStatement();
            int rowsAffected = statement.executeUpdate(query);

            if (rowsAffected > 0) {
                System.out.println("Deletion successful. And "+rowsAffected +" row(s) affected");
            }else {
                System.out.println("Deletion failed "+rowsAffected +" Rows not affected");
            }

            statement.close();
            connection.close();

            System.out.println();
            System.out.println("Database connection closed");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
