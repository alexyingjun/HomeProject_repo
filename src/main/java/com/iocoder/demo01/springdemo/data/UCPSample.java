package com.iocoder.demo01.springdemo.data;

/* Copyright (c) 2015, Oracle and/or its affiliates. All rights reserved.*/

/*
 DESCRIPTION
 The code sample demonstrates Universal Connection Pool (UCP) as a client
 side connection pool and does the following.    
 (a)Set the connection factory class name to 
 oracle.jdbc.pool.OracleDataSource before getting a connection.   
 (b)Set the driver connection properties(e.g.,defaultNChar,includeSynonyms).
 (c)Set the connection pool properties(e.g.,minPoolSize, maxPoolSize). 
 (d)Get the connection and perform some database operations.     
 Step 1: Enter the Database details in DBConfig.properties file. 
 USER, PASSWORD, UCP_CONNFACTORY and URL are required.                   
 Step 2: Run the sample with "ant UCPSample"
 NOTES
 Use JDK 1.7 and above  
 MODIFIED    (MM/DD/YY)
 nbsundar    02/13/15 - Creation (Contributor - tzhou)
 */
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import oracle.ucp.jdbc.PoolDataSourceFactory;
import oracle.ucp.jdbc.PoolDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class UCPSample {
    final static String DB_URL = "jdbc:oracle:thin:@db202003261453_tp?TNS_ADMIN=/home/opc/wallet";
    // Use TNS alias when using tnsnames.ora.  Use it while connecting to the database service on cloud.
    // final static String DB_URL=   "jdbc:oracle:thin:@orcldbaccess";
    final static String DB_USER                 = "admin";
    final static String DB_PASSWORD             = "OracleCloudDB1";
    final static String CONN_FACTORY_CLASS_NAME = "oracle.jdbc.pool.OracleDataSource";


    public void getFilePath() throws IOException {
        Resource resource = new ClassPathResource("wallet_DB202003261453");

        System.out.println(((ClassPathResource) resource).getURL().getPath());
        InputStream input = resource.getInputStream();

        File file = resource.getFile();
    }

    public static void main(String[]args) throws Exception {
   //       new UCPSample().getFilePath();
   //     connectUCP();
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
    }
    /*
     * The sample demonstrates UCP as client side connection pool.
     */
    public static void connectUCP() throws Exception {
        Resource resource = new ClassPathResource("wallet_DB202003261453");
        //String walletPath=resource.getURL().toString();
        String walletPath="C:/Development/Inchcape/demo01/target/classes/wallet_DB202003261453";
        final String DB_URL=   "jdbc:oracle:thin:@db202003261453_tp?TNS_ADMIN="+walletPath;

        // Get the PoolDataSource for UCP
        PoolDataSource pds = PoolDataSourceFactory.getPoolDataSource();

        // Set the connection factory first before all other properties
        pds.setConnectionFactoryClassName(CONN_FACTORY_CLASS_NAME);
        pds.setURL(DB_URL);
        pds.setUser(DB_USER);
        pds.setPassword(DB_PASSWORD);
        pds.setConnectionPoolName("JDBC_UCP_POOL");

        // Default is 0. Set the initial number of connections to be created
        // when UCP is started.
        pds.setInitialPoolSize(5);

        // Default is 0. Set the minimum number of connections
        // that is maintained by UCP at runtime.
        pds.setMinPoolSize(5);

        // Default is Integer.MAX_VALUE (2147483647). Set the maximum number of
        // connections allowed on the connection pool.
        pds.setMaxPoolSize(20);

        // Default is 30secs. Set the frequency in seconds to enforce the timeout
        // properties. Applies to inactiveConnectionTimeout(int secs),
        // AbandonedConnectionTimeout(secs)& TimeToLiveConnectionTimeout(int secs).
        // Range of valid values is 0 to Integer.MAX_VALUE. .
        pds.setTimeoutCheckInterval(5);

        // Default is 0. Set the maximum time, in seconds, that a
        // connection remains available in the connection pool.
        pds.setInactiveConnectionTimeout(10);

        // Set the JDBC connection properties after pool has been created
        Properties connProps = new Properties();
        connProps.setProperty("fixedString", "false");
        connProps.setProperty("remarksReporting", "false");
        connProps.setProperty("restrictGetTables", "false");
        connProps.setProperty("includeSynonyms", "false");
        connProps.setProperty("defaultNChar", "false");
        connProps.setProperty("AccumulateBatchResult", "false");

        // JDBC connection properties will be set on the provided
        // connection factory.
        pds.setConnectionProperties(connProps);
        System.out.println("Available connections before checkout: "
                + pds.getAvailableConnectionsCount());
        System.out.println("Borrowed connections before checkout: "
                + pds.getBorrowedConnectionsCount());
        // Get the database connection from UCP.
        try (Connection conn = pds.getConnection()) {
            System.out.println("Available connections after checkout: "
                    + pds.getAvailableConnectionsCount());
            System.out.println("Borrowed connections after checkout: "
                    + pds.getBorrowedConnectionsCount());
            // Perform a database operation
            doSQLWork(conn);
        }
        System.out.println("Available connections after checkin: "
                + pds.getAvailableConnectionsCount());
        System.out.println("Borrowed connections after checkin: "
                + pds.getBorrowedConnectionsCount());
    }

    /*
     * Creates an EMP table and does an insert, update and select operations on
     * the new table created.
     */
    public static void doSQLWork(Connection conn) {
        try {
            conn.setAutoCommit(false);
            // Prepare a statement to execute the SQL Queries.
            Statement statement = conn.createStatement();
            // Create table EMP
            statement.executeUpdate("create table EMP(EMPLOYEEID NUMBER,"
                    + "EMPLOYEENAME VARCHAR2 (20))");
            System.out.println("New table EMP is created");
            // Insert some records into the table EMP
            statement.executeUpdate("insert into EMP values(1, 'Jennifer Jones')");
            statement.executeUpdate("insert into EMP values(2, 'Alex Debouir')");
            System.out.println("Two records are inserted.");

            // Update a record on EMP table.
            statement.executeUpdate("update EMP set EMPLOYEENAME='Alex Deborie'"
                    + " where EMPLOYEEID=2");
            System.out.println("One record is updated.");

            // Verify the table EMP
            ResultSet resultSet = statement.executeQuery("select * from EMP");
            System.out.println("\nNew table EMP contains:");
            System.out.println("EMPLOYEEID" + " " + "EMPLOYEENAME");
            System.out.println("--------------------------");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2));
            }
            System.out.println("\nSuccessfully tested a connection from UCP");
        }
        catch (SQLException e) {
            System.out.println("UCPSample - "
                    + "doSQLWork()- SQLException occurred : " + e.getMessage());
        }
        finally {
            // Clean-up after everything
            try (Statement statement = conn.createStatement()) {
                statement.execute("drop table EMP");
            }
            catch (SQLException e) {
                System.out.println("UCPSample - "
                        + "doSQLWork()- SQLException occurred : " + e.getMessage());
            }
        }
    }
}
