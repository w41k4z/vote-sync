import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        // Create a connection to oracle database
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/XEPDB1", "election", "election");
            connection.setAutoCommit(false);
            System.out.println("Connection established successfully!");
            Statement statement = connection.createStatement();

            // Read a file named "regions.sql" and print each line
            try {
                File file = new File("cleaned/region.sql");
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    statement.executeUpdate(line);
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            statement.close();
            connection.commit();
        } catch (Exception e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}