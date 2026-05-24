package ch04_Image_Handling;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;

public class ImageInsertion_1 {
  public static void main(String[] args) {
    String url = "jdbc:postgresql://localhost:5432/students";
    String username = "postgres";
    String password = "postgres";
    String image_path = "C:\\Users\\HP\\IdeaProjects\\JDBC\\src\\images\\template.png";
    String query = "INSERT INTO image_table(image_data) VALUES (?);";

    try {
      Class.forName("org.postgresql.Driver");
      System.out.println("drivers loaded successfully");

    } catch (ClassNotFoundException e) {
      System.out.println(e.getMessage());
    }

    try {
      Connection connection = DriverManager.getConnection(url, username, password);
      System.out.println("connection established successfully");

      FileInputStream fileInputStream = new FileInputStream(image_path);
      byte[] image_data = new byte[fileInputStream.available()];
      fileInputStream.read(image_data);

      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setBytes(1, image_data);

      int affectedRows = preparedStatement.executeUpdate();

      if (affectedRows>0){
        System.out.println("Image inserted successfully");
      }else {
        System.out.println("Image insertion failed");
      }

      preparedStatement.close();
      connection.close();

      System.out.println("connection close successfully");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    } catch (FileNotFoundException e) {
      System.out.println(e.getMessage());
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }

  }
}
