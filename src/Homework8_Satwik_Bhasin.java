import java.sql.SQLException;

public class Homework8_Satwik_Bhasin {
    public static void main(String[] args) throws SQLException {

        // creating a new object of class databaseConnector
        databaseConnector connector = new databaseConnector();

        // connecting to the database using the newly created object's connect() method
        connector.connect();

         /*creating a new databaseUtility object with the connector as a constructor
         argument which lets us perform actions on the database*/
        databaseUtility utility = new databaseUtility(connector);
        if(args.length <2){
            System.out.println("\nError: Table names are empty!");
        }
        else {
            String table1 = args[0].toLowerCase();
            System.out.print("\n1st table name: " + table1);
            if (utility.doesTableExist(table1)) {
                String table2 = args[1].toLowerCase();
                System.out.println("\n2nd table name: " + table2);
                if (utility.doesTableExist(table2)) {

                    utility.printTablesInfo(table1);
                    utility.printTablesInfo(table2);
                    utility.printIntersectingColumns(table1, table2);

                    int estimatedJoinSize = utility.estimateJoinSize(table1, table2);
                    int actualJoinSize = utility.actualJoinSize(table1, table2);
                    System.out.println(
                            "\n--------------------------------------- FINAL SOLUTION ---------------------------------------");
                    System.out.println("\nEstimated join size: " + estimatedJoinSize);
                    System.out.println("Actual join size: " + actualJoinSize);
                    System.out.println("Estimation error: " + (estimatedJoinSize - actualJoinSize));
                    System.out.println(
                            "\n----------------------------------------------------------------------------------------------");

                } else {
                    System.out.println("No table found with name: '" + table2 + "'");
                }
            } else {
                System.out.println("No table found with name: '" + table1 + "'");
            }
        }
        connector.disconnect(connector.connection);
    }
}
