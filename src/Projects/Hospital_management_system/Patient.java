package Projects.Hospital_management_system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {

  private Connection connection;
  private Scanner input;

  public Patient(Scanner input, Connection connection) {
    this.input = input;
    this.connection = connection;
  }

  public void addPatient() {
    input.nextLine();
    System.out.print("Enter Patient Name: ");
    String name = input.nextLine();

    System.out.print("Enter Patient Age: ");
    int age = input.nextInt();

    System.out.print("Enter Patient Gender: ");
    String gender = input.next().toLowerCase();

    try {
      String query = "INSERT INTO patient(name, age, gender) VALUES (?, ?, ?);";
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setString(1, name);
      preparedStatement.setInt(2, age);
      preparedStatement.setObject(3, gender, java.sql.Types.OTHER);

      int rowsAffected = preparedStatement.executeUpdate();
      if (rowsAffected > 0) {
        System.out.println("Patient admitted successfullly!..");
      } else {
        System.out.println("Patient failed to add..");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  public void viewPatients() {
    String query = "SELECT * FROM patient;";
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      ResultSet resultSet = preparedStatement.executeQuery();

      System.out.println("Patients: ");
      System.out.println(" id |       name       | age |  gender  |");
      System.out.println("----+------------------+-----+----------+");
      while (resultSet.next()) {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        int age = resultSet.getInt("age");
        String gender = resultSet.getString("gender");
        System.out.printf("| %-1d | %-16s | %-3d | %-8s |\n", id, name, age, gender);
        System.out.println("----+------------------+-----+----------+");
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public boolean getPatientById(int id) {
    String query = "SELECT * FROM patient WHERE id = ?;";
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        return true;
      } else {
        return false;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

}
