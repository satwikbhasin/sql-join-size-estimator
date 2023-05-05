import java.sql.SQLException;
import java.util.Scanner;

public class Homework8_Satwik_Bhasin {
    public static void main(String[] args) throws SQLException {

        // creating a new object of class databaseConnector
        databaseConnector connector = new databaseConnector();

        // connecting to the database using the newly created object's connect() method
        connector.connect();

         /*creating a new databaseUtility object with the connector as a constructor
         argument which lets us perform actions on the database*/
        databaseUtility utility = new databaseUtility(connector);

        Scanner scanner = new Scanner(System.in);

        System.out.print("\nEnter 1st table name: ");
        String table1 = scanner.nextLine().toLowerCase();
        if(utility.doesTableExist(table1)) {
            System.out.print("Enter 2nd table name: ");
            String table2 = scanner.nextLine().toLowerCase();
            if (utility.doesTableExist(table2)) {

                utility.printTablesInfo(table1);
                utility.printTablesInfo(table2);
                utility.printIntersectingColumns(table1, table2);

                int estimatedJoinSize = utility.estimateJoinSize(table1, table2);
                int actualJoinSize = utility.actualJoinSize(table1, table2);
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
        connector.disconnect(connector.connection);

    }
}
