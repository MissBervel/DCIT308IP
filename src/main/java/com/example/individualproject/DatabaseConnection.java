package com.example.individualproject;
import java.sql.*;

public class DatabaseConnection {
    // Set the connection parameters
    String url = "jdbc:mysql://localhost:3306/Benedicta";
    String username = "root";
    String password = "kukuA123@";

    // Establish the connection
    public void ConnectToDatabase() {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to the database!");
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database.");
            e.printStackTrace();
        }
    }

}
