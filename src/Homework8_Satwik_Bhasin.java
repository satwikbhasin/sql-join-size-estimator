import java.sql.SQLException;
import java.util.Scanner;

public class Homework8_Satwik_Bhasin {
    public static void main(String[] args) throws SQLException {

        databaseConnection connection = new databaseConnection();
        connection.connect();
        databaseUtility databaseUtility = new databaseUtility(connection);

        Scanner scanner = new Scanner(System.in);

        System.out.print("\nEnter 1st table name: ");
        String table1 = scanner.nextLine();
        if(databaseUtility.doesTableExist(table1)) {
            System.out.print("Enter 2nd table name: ");
            String table2 = scanner.nextLine();
            if (databaseUtility.doesTableExist(table2)) {

                databaseUtility.printTablesInfo(table1);
                databaseUtility.printTablesInfo(table2);
                databaseUtility.printIntersectingColumns(table1, table2);

                int estimatedJoinSize = databaseUtility.estimateJoinSize(table1, table2);
                int actualJoinSize = databaseUtility.actualJoinSize(table1, table2);
                System.out.println("\n--------------------------------------- FINAL SOLUTION ---------------------------------------");
                System.out.println("\nEstimated join size: " + estimatedJoinSize);
                System.out.println("Actual join size: " + actualJoinSize);
                System.out.println("Estimation error: " + (estimatedJoinSize - actualJoinSize));
                System.out.println("\n----------------------------------------------------------------------------------------------");
            }
            else {
                System.out.println("No table found with name: '" + table2 + "'");
            }
        } else {
            System.out.println("No table found with name: '" + table1 + "'");
        }
        connection.disconnect(connection.connection);

    }
}
