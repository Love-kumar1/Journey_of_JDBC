package ch04_Image_Handling;

import java.io.*;
import java.sql.*;

public class ImageRetrieval_2 {
  public static void main(String[] args) {

    String url = "jdbc:postgresql://localhost:5432/students";
    String username = "postgres";
    String password = "postgres";
    String folder_path = "C:\\Users\\HP\\IdeaProjects\\JDBC\\src\\images\\";// insertion of the \\ in the last is nesseasary
    String query = "SELECT image_data FROM image_table WHERE image_id = (?);";

    try {
      Class.forName("org.postgresql.Driver");
      System.out.println("drivers loaded successfully");

    } catch (ClassNotFoundException e) {
      System.out.println(e.getMessage());
    }

    try {
      Connection connection = DriverManager.getConnection(url, username, password);
      System.out.println("connection established successfully");

      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1, 1);
      ResultSet resultSet = preparedStatement.executeQuery();

      if (resultSet.next()) {
        byte[] image_data = resultSet.getBytes("image_data");
        String image_path = folder_path + "extractedImage.jpg";
        OutputStream outputStream = new FileOutputStream(image_path);
        outputStream.write(image_data);

        System.out.println("Image extracted successfully");

      } else {
        System.out.println("Image not found");
      }

      preparedStatement.close();
      connection.close();
      System.out.println("connection close successfully");

    }
    catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    catch (IOException e) {
      System.out.println(e.getMessage());
    }

  }
}
