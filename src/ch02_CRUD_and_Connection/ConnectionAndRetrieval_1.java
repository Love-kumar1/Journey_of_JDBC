package ch02_CRUD_and_Connection;

import java.sql.*;

public class ConnectionAndRetrieval_1 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        String url = "jdbc:postgresql://localhost:5432/students";
        String username = "postgres";
        String password = "postgres";
        String query = "select * from employees;";

        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("Driver loaded");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Database connection established");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String department = resultSet.getString("dept");

                System.out.println();
                System.out.println("==================");
                // System.out.println(id + " " + name + " " + department);
                System.out.println("ID: " + id);
                System.out.println("Name: " + name);
                System.out.println("Department: " + department);
            }
            resultSet.close();
            statement.close();
            connection.close();

            System.out.println();
            System.out.println("Database connection closed");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
