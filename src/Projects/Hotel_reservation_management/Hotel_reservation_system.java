package Projects.Hotel_reservation_management;

import java.sql.*;
import java.util.Scanner;

import static java.lang.System.exit;

public class Hotel_reservation_system {

  private static final String url = "jdbc:postgresql://localhost:5432/hotel_db";
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

      while (true) {

        System.out.println();
        System.out.println("HOTEL MANAGEMENT SYSTEM");

        Scanner input = new Scanner(System.in);

        System.out.println("1. Reserve a room");
        System.out.println("2. View Reservations");
        System.out.println("3. Get a Room Number");
        System.out.println("4. Update Reservations");
        System.out.println("5. Delete Reservations");
        System.out.println("0. Exit");

        System.out.print("Enter choice: ");
        int choice = input.nextInt();

        switch (choice) {
          case 1:
            reserveRoom(connection, input);
            break;
          case 2:
            viewReservations(connection);
            break;
          case 3:
            getRoomNumber(connection, input);
            break;
          case 4:
            updateReservations(connection, input);
            break;
          case 5:
            deleteReservations(connection, input);
            break;
          case 0:
            exit();
            input.close();
            return;
          default:
            System.out.println("Invalid choice...!, Please try again");
        }

      }

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

  }

  private static void reserveRoom(Connection connection, Scanner input) throws SQLException {
    try {
      input.nextLine();

      System.out.print("Enter guest name: ");
      String guestName = input.nextLine();

      System.out.print("Enter room number: ");
      int roomNumber = input.nextInt();

      System.out.print("Enter contact number: ");
      String contactNumber = input.next();

      String sql = "INSERT INTO reservations(guest_name, room_number, contact_number) " +
          "VALUES ('" + guestName + "', '" + roomNumber + "', '" + contactNumber + "')";

      try (Statement statement = connection.createStatement()) {

        int affectedRows = statement.executeUpdate(sql);

        if (affectedRows > 0) {
          System.out.println("Reservation succeed!.");
        } else {
          System.out.println("Reservation failed!.");
        }

      } catch (SQLException e) {
        e.printStackTrace();
      }

    } catch (NumberFormatException e) {
    }
  }

  private static void viewReservations(Connection connection) throws SQLException {

    String sql = "SELECT * FROM reservations";

    try (Statement statement = connection.createStatement()) {

      ResultSet resultSet = statement.executeQuery(sql);

      System.out.println("\n================ CURRENT RESERVATIONS ================\n");

      System.out.printf("%-5s %-20s %-10s %-15s %-20s%n",
          "ID",
          "Guest Name",
          "Room",
          "Contact",
          "Date");

      System.out.println("--------------------------------------------------------------------------");

      while (resultSet.next()) {

        int reservationId = resultSet.getInt("reservation_id");
        String guestName = resultSet.getString("guest_name");
        int roomNumber = resultSet.getInt("room_number");
        String contactNumber = resultSet.getString("contact_number");

        String reservationDate = resultSet.getTimestamp("reservation_date")
            .toString()
            .substring(0, 16);

        System.out.printf("%-5d %-20s %-10d %-15s %-20s%n",
            reservationId,
            guestName,
            roomNumber,
            contactNumber,
            reservationDate);
      }

      System.out.println("--------------------------------------------------------------------------");
    }
  }

  private static void getRoomNumber(Connection connection, Scanner input) throws SQLException {

    try {
      System.out.print("Enter Reservation ID: ");
      int reservationID = input.nextInt();
      input.nextLine();
      System.out.print("Enter guest name: ");
      String guestName = input.nextLine();

      String sql = "SELECT room_number FROM reservations WHERE reservation_id = " + reservationID
          + " AND guest_name = '" + guestName + "'";

      try (Statement statement = connection.createStatement()) {
        ResultSet resultSet = statement.executeQuery(sql);

        if (resultSet.next()) {
          int roomNumber = resultSet.getInt("room_number");
          System.out.println(
              "Room Number for reservationID: " + reservationID + " | guest: " + guestName + " is: " + roomNumber);
        } else {
          System.out.println("Reservation for the given ID not found!");
        }

      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  private static void updateReservations(Connection connection, Scanner input) throws SQLException {
    try {
      System.out.print("Enter reservation ID to update: ");
      int reservationID = input.nextInt();
      input.nextLine(); // consume new line character

      if (!reservationExists(connection, reservationID)) {
        System.out.println("Reservation for given ID not found!");
        return;
      }

      System.out.print("Enter new guest name: ");
      String newGuestName = input.nextLine();
      System.out.print("Enter new room number: ");
      int newRoomNumber = input.nextInt();
      System.out.print("Enter new contact number: ");
      String newContactNumber = input.next();

      String sql = "UPDATE reservations SET guest_name = '" + newGuestName + "', " + "room_number = " + newRoomNumber
          + ", " + "contact_number = '" + newContactNumber + "' WHERE reservation_id = " + reservationID;

      try (Statement statement = connection.createStatement()) {
        int affectedRows = statement.executeUpdate(sql);

        if (affectedRows > 0) {
          System.out.println("Reservation successfully updated!");
        } else {
          System.out.println("Reservation update failed failed!!");
        }
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  private static void deleteReservations(Connection connection, Scanner input) throws SQLException {
    try {
      System.out.print("Enter reservation ID to delete: ");
      int reservationID = input.nextInt();

      if (!reservationExists(connection, reservationID)) {
        System.out.println("Reservation for given ID not found!");
        return;
      }

      String sql = "DELETE FROM reservations WHERE reservation_id = " + reservationID;

      try (Statement statement = connection.createStatement()) {
        int affectedRows = statement.executeUpdate(sql);

        if (affectedRows > 0) {
          System.out.println("Reservation successfully deleted!");
        } else {
          System.out.println("Reservation deletion failed!!");
        }

      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  private static boolean reservationExists(Connection connection, int reservationID) throws SQLException {
    try {
      String sql = "SELECT reservation_id FROM reservations WHERE reservation_id = " + reservationID;

      try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
        return resultSet.next(); // if there is a result, the reservation exists
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return false; // hnadle databse errors are needed
    }

  }

  public static void exit() throws InterruptedException {
    System.out.print("Exiting system ");
    int i = 5;
    while (i != 0) {
      System.out.print(".");
      Thread.sleep(450);
      i--;
    }
    System.out.println();
    System.out.println("Thnak you for using hotel reservation system");

  }

}
