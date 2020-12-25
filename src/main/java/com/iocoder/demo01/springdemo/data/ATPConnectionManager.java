package com.iocoder.demo01.springdemo.data;

import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ATPConnectionManager {
 //   final static String DB_URL = "jdbc:oracle:thin:@db202003261453_tp?TNS_ADMIN=/home/opc/wallet";

    private static String walletPath="C:/Users/lq120/IdeaProjects/HomeProject_repo/src/main/resources/wallet_DB202003261453";
    final static String DB_URL=   "jdbc:oracle:thin:@db202003261453_high?TNS_ADMIN="+walletPath;
    // Use TNS alias when using tnsnames.ora.  Use it while connecting to the database service on cloud.
    // final static String DB_URL=   "jdbc:oracle:thin:@orcldbaccess";
    final static String DB_USER                 = "admin";
    final static String DB_PASSWORD             = "OracleCloudDB1";
    final static String CONN_FACTORY_CLASS_NAME = "oracle.jdbc.pool.OracleDataSource";
    private static PoolDataSource pds = null;

    static {
        try {
            initPoolDataSource();
        } catch (SQLException e) {
            System.err.println("Unable to create ATP Pool Data Source");
            e.printStackTrace();
        }
    }

    private static void initPoolDataSource() throws SQLException {
        pds = PoolDataSourceFactory.getPoolDataSource();

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
        System.out.println("Pool Data Source initialised");
//        System.out.println("Available connections before checkout: "
//                + pds.getAvailableConnectionsCount());
//        System.out.println("Borrowed connections before checkout: "
//                + pds.getBorrowedConnectionsCount());
        // Get the database connection from UCP.
//        try (Connection conn = pds.getConnection()) {
//            System.out.println("Available connections after checkout: "
//                    + pds.getAvailableConnectionsCount());
//            System.out.println("Borrowed connections after checkout: "
//                    + pds.getBorrowedConnectionsCount());
//
//        }
//        System.out.println("Available connections after checkin: "
//                + pds.getAvailableConnectionsCount());
//        System.out.println("Borrowed connections after checkin: "
//                + pds.getBorrowedConnectionsCount());
    }

    static Connection getATPConnection(){
        Connection conn = null;
        try {
            System.out.println("Available connections before checkout: "
                    + pds.getAvailableConnectionsCount());
            System.out.println("Borrowed connections before checkout: "
                    + pds.getBorrowedConnectionsCount());
            conn = pds.getConnection();
            System.out.println("Available connections after checkout: "
                    + pds.getAvailableConnectionsCount());
            System.out.println("Borrowed connections after checkout: "
                    + pds.getBorrowedConnectionsCount());
        } catch (SQLException e) {
            System.err.println("Unable to get ATP database connection");
            e.printStackTrace();
        }
        return conn;
    }
}
