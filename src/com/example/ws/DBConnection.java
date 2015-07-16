package com.example.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 
public class DBConnection {
    /**
     * Method to create DB Connection
     * 
     * @return
     * @throws Exception
     */
    public static Connection createConnection() throws Exception {
        Connection conn = null;
        try {
            Class.forName(Constants.dbClass);
            conn = DriverManager.getConnection(Constants.dbUrl, Constants.dbUser, Constants.dbPwd);
        } catch (SQLException ex) {
        	// handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            //throw ex;
        }
        
        return conn;
    }
    
    /**
     * Method to get user's public key
     * 
     * @param userid
     * @return public key
     * @throws Exception
     */
    public static String getKey(String userid) throws Exception {
        Connection dbConn = null;
        String result = "";
        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Statement stmt = dbConn.createStatement();
            String query = "SELECT publickey FROM publickey WHERE userid = '" + userid
                    + "'";
            System.out.println(query);
            
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                result += rs.getString(1); // get public key
            }
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
        return result;
    }
    
    
    /**
     * Method to insert public key into DB
     * 
     * @param userid
     * @param key
     * @param datetime
     * @return true if inserted successfully, otherwise return false
     * @throws SQLException
     * @throws Exception
     */
    public static boolean insertKey(String userid, String key, String datetime) throws SQLException, Exception {
        boolean insertStatus = false;
        Connection dbConn = null;
        System.out.println("Inserting...");
        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception e) {
            	System.out.println("Some exception in creating db connection!");
                e.printStackTrace();
            }
            // Insert or update public key
            Statement stmt = dbConn.createStatement();
            String temp = getKey(userid);
            String query;
            if (temp == null || temp.isEmpty()){
                query = "INSERT into publickey(userid, publickey, generatedate) values('"+userid+ "',"+"'"
                        + key + "','" + datetime + "')";
            } else
                query = "UPDATE publickey set publickey='"+key+ "'" + " WHERE userid='" + userid + "'";

            System.out.println(query);
            
            int records = stmt.executeUpdate(query);
            System.out.println(records);
            if (records > 0) {
                insertStatus = true;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
        return insertStatus;
    }
}

