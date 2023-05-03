import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class databaseConnection {
    String url;
    String username;
    String password;
    Connection connection;

    databaseConnection() {
        // postgreSQL database credentials
        this.url = "jdbc:postgresql://localhost:5432/universityDataset";
        this.username = "postgres";
        this.password = "Thumbsup10";
    }

    // establishes the connection to database
    public void connect() {
        try {
            this.connection = DriverManager.getConnection(url, username, password);
            System.out.println("\nConnected to the university database in PostgreSQL!");
        } catch (SQLException e) {
            System.out.println("\nConnection failure: " + e.getMessage());
        }
    }

    // disconnects from the database
    public void disconnect(Connection connection){
        try{
            connection.close();
            System.out.println("\nDisconnected from the university database in PostgreSQL!");
        } catch (SQLException e){
            System.out.println("\nDisconnection failure: " + e.getMessage());
        }
    }
}
