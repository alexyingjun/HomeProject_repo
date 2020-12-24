package com.iocoder.demo01.springdemo.data;

import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ATPDao {
    public void createTable(String createTableStatement){
        Connection thisConnection = ATPConnectionManager.getATPConnection();
        try {
            thisConnection.setAutoCommit(false);
            // Prepare a statement to execute the SQL Queries.
            Statement statement = thisConnection.createStatement();
            // Create table EMP
            statement.executeUpdate(createTableStatement);
            System.out.println("ATPDao - "
                    + "createTable()- "+createTableStatement+" - executed successfully");
        }
        catch (SQLException e) {
                System.out.println("ATPDao - "
                        + "createTable()- "+createTableStatement+" - SQLException occurred : " + e.getMessage());
        }finally {
            try {
                thisConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void insertRecord(String tableName, Stock stock){
        Connection thisConnection = ATPConnectionManager.getATPConnection();
        try {
            thisConnection.setAutoCommit(false);
            // Prepare a statement to execute the SQL Queries.
            Statement statement = thisConnection.createStatement();

            PreparedStatement st = thisConnection.prepareStatement("insert into "+tableName+" (STOCKNAME,RECORDDATE,UNITPRICE,RECORDID) values (?, ?, ?, ?)");

            System.out.println("insert into "+tableName+" values('"+stock.getStockName()+"','"+stock.getRecordDate()+"','" +stock.getUnitPrice()+"',"+stock.getId()+")");

            st.setString(1,stock.getStockName());
            st.setString(2,stock.getRecordDate());
            st.setString(3,stock.getUnitPrice());
            st.setLong(4,stock.getId());
            st.executeUpdate();
            st.close();
            System.out.println("ATPDao - "
                    + "insertRecord()- "+tableName+" - executed successfully");
            thisConnection.commit();
        }
        catch (SQLException e) {
            System.out.println("ATPDao - "
                    + "insertRecord()- "+tableName+" - SQLException occurred : " + e.getMessage());
        }finally {
            try {
                thisConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateRecord(String tableName, String keyfield, long key, String field, String value){
        Connection thisConnection = ATPConnectionManager.getATPConnection();
        try {
            thisConnection.setAutoCommit(false);
            // Prepare a statement to execute the SQL Queries.
            Statement statement = thisConnection.createStatement();

            PreparedStatement st = thisConnection.prepareStatement("UPDATE "+tableName+" SET "+field+"=? WHERE "+keyfield+"=?");

            st.setString(1,value);
            st.setLong(2,key);
            st.executeUpdate();
            st.close();
            System.out.println("ATPDao - "
                    + "insertRecord()- "+tableName+" - executed successfully");
            thisConnection.commit();
        }
        catch (SQLException e) {
            System.out.println("ATPDao - "
                    + "insertRecord()- "+tableName+" - SQLException occurred : " + e.getMessage());
        }finally {
            try {
                thisConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Stock> viewTable(String tableName) {
        Connection thisConnection = ATPConnectionManager.getATPConnection();
        List<Stock> stockList = new ArrayList<>();
        try {
            System.out.println("ATPDao - "
                    + "viewTable()- "+tableName+" - executing ");
            thisConnection.setAutoCommit(false);
            // Prepare a statement to execute the SQL Queries.
            Statement statement = thisConnection.createStatement();
            // Create table EMP
            ResultSet resultSet =  statement.executeQuery("select * from "+tableName);
            System.out.println("ATPDao - "
                    + "viewTable()- "+tableName+" - executed successfully");
            while (resultSet.next()) {
       //         System.out.println("Get record: "+resultSet.getLong("recordid")+resultSet.getString("stockname") + resultSet.getString("recorddate")+resultSet.getString("unitprice"));
                stockList.add(new Stock(resultSet.getLong("recordid"),resultSet.getString("stockname") , resultSet.getString("recorddate"),resultSet.getString("unitprice")));
            }
        }
        catch (SQLException e) {
            System.out.println("ATPDao - "
                    + "viewTable()- "+tableName+" - SQLException occurred : " + e.getMessage());
        }finally {
            try {
                thisConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return stockList;
    }
}
