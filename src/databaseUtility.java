import java.sql.*;
import java.util.ArrayList;

import static java.lang.System.exit;

public class databaseUtility {

    Connection connection;
    databaseConnection databaseConnection;
    databaseUtility(databaseConnection connection){
        this.databaseConnection = connection;
        this.connection = connection.connection;
    }

    // returns the metadata of the connected database
    private DatabaseMetaData getMetaData() throws SQLException {
        return this.connection.getMetaData();
    }

    public boolean doesTableExist(String tableName) throws SQLException {
        if(tableName.isEmpty()){
            System.out.println("Error: Blank table name!");
            this.databaseConnection.disconnect(connection);
            exit(0);
        }
        DatabaseMetaData metadata = getMetaData();
        ResultSet table = metadata.getTables(null, null, tableName, null);

        return table.next();
    }

    // finds and returns the columns of a table using its metadata
    private ArrayList<String> getColumns(String tableName) throws SQLException {
        ResultSet columnResultSet = getMetaData().getColumns(null, null, tableName, "");
        ArrayList<String> columnsTable = new ArrayList<>();
        while (columnResultSet.next()) {
            columnsTable.add(columnResultSet.getString("COLUMN_NAME"));
        }
        columnResultSet.close();
        return columnsTable;
    }

    // finds & returns an arraylist of primary keys of a table using its metadata
    private ArrayList<String> getPrimaryKey(String tableName) throws SQLException {
        ResultSet primaryKeyResultSet = getMetaData().getPrimaryKeys(null, null, tableName);
        ArrayList<String> primaryKeyTable = new ArrayList<>();
        while (primaryKeyResultSet.next()) {
            primaryKeyTable.add(primaryKeyResultSet.getString("COLUMN_NAME"));
        }
        primaryKeyResultSet.close();
        return primaryKeyTable;
    }

    // calculates & returns the number of rows in a table using a sql query
    private int getRowCountOfTable(String tableName) throws SQLException {
        int rowCount = 0;
        Statement statement = this.connection.createStatement();

        // execute the row count query
        ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM " + tableName);

         if(resultSet.next()) {
            rowCount = resultSet.getInt(1);
        }

        return rowCount;
    }

    // finds and returns intersecting columns between two tables
    private ArrayList<String> getIntersectingColumns(String table1, String table2) throws SQLException {
        // find intersecting columns
        ArrayList<String> intersectingColumns = new ArrayList<>();
        for(String column1 : getColumns(table1)){
            for(String column2 : getColumns(table2)){
                if(column1.equals(column2)){
                    intersectingColumns.add(column1);
                }
            }
        }
        return intersectingColumns;
    }

    // prints a table's columns, primary key(s), row count
    public void printTablesInfo(String tableName) throws SQLException {
        System.out.println("\nInformation on '" + tableName + "'");
        System.out.println("Columns: " + getColumns(tableName));
        System.out.println("Primary Key(s): " + getPrimaryKey(tableName));
        System.out.println("Row count: " + getRowCountOfTable(tableName));
    }

    // prints the intersecting columns between two tables
    public void printIntersectingColumns(String table1, String table2) throws SQLException {
        System.out.print("\nCommon columns in '" + table1 + "' & '" + table2 + "': ");
        System.out.println(getIntersectingColumns(table1, table2));
    }

    // calculates & returns the actual row count after naturally joining two given tables
    public int actualJoinSize(String table1, String table2) throws SQLException {
        if(table1.equals(table2)){
            return getRowCountOfTable(table1);
        }
        int actualRowCount = 0;
        Statement statement = this.connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM " +  table1 + " NATURAL JOIN " + table2);

        if (resultSet.next()) {
            actualRowCount = resultSet.getInt(1);
        }

        resultSet.close();
        statement.close();

        return actualRowCount;
    }

    // calculates & returns the estimated row count
    public int estimateJoinSize(String table1, String table2) throws SQLException {

        // handles the case when table 1 = table 2
        if(table1.equals(table2)){
            return getRowCountOfTable(table1);
        }

        // handles the case when table R ∩ S is a primary key for R or S
        if(getIntersectingColumns(table1, table2).equals(getPrimaryKey(table1)) && getIntersectingColumns(table1, table2).equals(getPrimaryKey(table2))){
            return Math.min(getRowCountOfTable(table1), getRowCountOfTable(table2));
        } else if(getIntersectingColumns(table1, table2).equals(getPrimaryKey(table1))){
            return getRowCountOfTable(table2);
        } else if(getIntersectingColumns(table1, table2).equals(getPrimaryKey(table2))){
            return getRowCountOfTable(table1);
        } else{
            return getRowCountOfTable(table1) * getRowCountOfTable(table2);
        }
    }
}
