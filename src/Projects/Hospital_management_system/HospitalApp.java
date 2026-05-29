package Projects.Hospital_management_system;

import java.sql.Date;
import java.sql.*;
import java.util.Scanner;

public class HospitalApp {
  private static final String url = "jdbc:postgresql://localhost:5432/hospital";
  private static final String username = "postgres";
  private static final String password = "postgres";

  public static void main(String[] args) {
    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
      System.out.println(e.getMessage());
    }
    Scanner input = new Scanner(System.in);
    try {
      Connection connection = DriverManager.getConnection(url, username, password);
      Patient patient = new Patient(input, connection);
      Doctor doctor = new Doctor(connection);

      while (true) {
        System.out.println();
        System.out.println("***** HOSPITAL MANAGEMENT SYSTEM ****");
        System.out.println();
        System.out.println("1. Add Patient");
        System.out.println("2. View Patient");
        System.out.println("3. View Doctors");
        System.out.println("4. Book Appointment");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
        int choice = input.nextInt();

        switch (choice) {
          case 1:
            patient.addPatient();
            System.out.println();
            break;
          case 2:
            patient.viewPatients();
            System.out.println();
            break;
          case 3:
            doctor.viewDoctors();
            System.out.println();
            break;
          case 4:
            bookAppointment(patient, doctor, connection, input);
            System.out.println();
            break;
          case 5:
            System.out.println("Thank you for using Hospital system..");
            System.out.println("Bye bye....");
            return;
          default:
            System.out.println("Enter valid choice");
            break;
        }
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner input) {
    System.out.print("Enter Patient ID: ");
    int patientId = input.nextInt();
    System.out.print("Enter Doctor ID: ");
    int doctorId = input.nextInt();
    System.out.print("Enter Appointment Date(YYYY-MM-DD): ");
    String appointmentDate = input.next();

    if (patient.getPatientById(patientId) && doctor.getDoctorById(doctorId)) {
      if (checkDoctorAvailability(doctorId, appointmentDate, connection)) {
        try {
          String appointmentQuery = "INSERT INTO appointment(patient_id, doctor_id, appointment_date) VALUES (?, ?, ?);";
          PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
          preparedStatement.setInt(1, patientId);
          preparedStatement.setInt(2, doctorId);
          preparedStatement.setDate(3, Date.valueOf(appointmentDate));
          int rowsAffected = preparedStatement.executeUpdate();
          if (rowsAffected > 0) {
            System.out.println("Appointment Booked");
          } else {
            System.out.println("Appointment Failed to book");
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      } else {
        System.out.println("Doctor is not available for this date");
      }
    } else {
      System.out.println("Either Doctor OR Patient doesn't exist!...!");
    }
  }

  public static boolean checkDoctorAvailability(int doctorId, String appointmentDate, Connection connection) {
    String query = "SELECT COUNT(*) FROM appointment WHERE doctor_id = ? AND appointment_date = ?;";
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1, doctorId);
      preparedStatement.setDate(2, Date.valueOf(appointmentDate));
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        int count = resultSet.getInt(1);
        if (count == 0) {
          return true;
        } else {
          return false;
        }
      } else {
        System.out.println("Invalid doctor id or date..!");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

}
