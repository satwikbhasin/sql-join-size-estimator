# SQL Join Size Estimator

## Overview
SQL Join Size Estimator is a Java-based tool that connects to a SQL database, retrieves table metadata, and estimates join sizes between two user-specified tables. It helps analyze database relationships and assess SQL join efficiency.

## Features
- Connects to a SQL database.
- Checks table existence.
- Retrieves table schema.
- Identifies common columns.
- Estimates and computes join size.
- Calculates estimation error.

## Prerequisites
- Java 8+
- A SQL database (MySQL, PostgreSQL, etc.)
- JDBC Driver for the chosen database

## Installation
1. Clone the repository:
   ```sh
   git clone https://github.com/satwikbhasin/sql-join-size-estimator.git
   ```
2. Compile the Java files:
   ```sh
   javac -cp .:path/to/jdbc/driver.jar SQLJoinSizeEstimator.java
   ```
3. Run the program:
   ```sh
   java -cp .:path/to/jdbc/driver.jar SQLJoinSizeEstimator
   ```

## Usage
1. Run the program and enter the names of two database tables.
2. The tool will:
   - Check if the tables exist.
   - Retrieve and display table schema.
   - Identify common columns.
   - Estimate the join size.
   - Compute the actual join size.
   - Display the estimation error.

## Example Output
```
Enter 1st table name: employees
Enter 2nd table name: departments

Table: employees
Columns: id, name, dept_id

Table: departments
Columns: id, dept_name

Common columns: id

Estimated join size: 5000
Actual join size: 4800
Estimation error: 200
```
