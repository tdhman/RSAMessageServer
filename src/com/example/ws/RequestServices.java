package com.example.ws;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

//Path: http://localhost:8080/<appln-folder-name>/rest/services
@Path("/services")
public class RequestServices {
	
	// HTTP Get Method
    @GET
    // Path: http://localhost:8080/<appln-folder-name>/rest/services/get
    @Path("/get")  
    // Produces JSON as response
    @Produces(MediaType.APPLICATION_JSON) 
    // Query parameters are parameters: http://localhost/<appln-folder-name>/rest/services/get?id=user_id
    public String getKey(@QueryParam("id") String userid){
    	String response = "";
        
        if(Utility.isNotNull(userid)) {
        	try {
        		String result = DBConnection.getKey(userid);
        		if (Utility.isNotNull(result)){
        			response = Utility.constructJSON("get", result, true);
        		} else {
        			response = Utility.constructJSON("get", "No public key found for this phone number!!", false);
        		}
        	} catch(Exception e) {
        		response = Utility.constructJSON("get", "Unknown Exception!!!", false);
        	}
        }
        
        return response;
 
    }
	
    // HTTP Get Method
    @GET
    // Path: http://localhost:8080/<appln-folder-name>/rest/services/register
    @Path("/register")  
    // Produces JSON as response
    @Produces(MediaType.APPLICATION_JSON) 
    // Query parameters are parameters: http://localhost/<appln-folder-name>/rest/services/register?id=user_id&key=public_key
    public String doRegister(@QueryParam("id") String userid, @QueryParam("key") String key){
        String response = "";
        
        // Get current datetime and random key for new insert
    	DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:MM:ss");
    	Date date = new Date();
    	String datetime = dateFormat.format(date);
    	//String userid = Utility.randomKey();
        int retCode = registerKey(userid, key, datetime);
        
        if(retCode == 0){
            response = Utility.constructJSON("register", userid, true);
        }else if(retCode == 1){
            response = Utility.constructJSON("register", "SQL Exception!!!", false);
        }else if(retCode == 2){
            response = Utility.constructJSON("register", "Unknown Exception!!!", false);
        }
        return response;
 
    }
 
    /**
     * Method to insert data into server database
     * @param userid
     * @param key
     * @param datetime
     * @return status code :  0 = OK, 1 = SQL Exception, 2 = Unknown Exception
     */
    private int registerKey(String userid, String key, String datetime){
        System.out.println("Send data to server to insert into DB.");
        int result = 3;
        if(Utility.isNotNull(key) && Utility.isNotNull(userid)){
            try {
                if(DBConnection.insertKey(userid, key, datetime)){
                    System.out.println("Inserted!");
                    result = 0;
                }
            } catch(SQLException sqle){
            	System.out.println("SQL Exception: " + sqle.getErrorCode());
            	result = 1;
            }
            catch (Exception e) {
                System.out.println("Unknown Exception!!!");
                result = 2;
            }
        }else{
            System.out.println("Invalid Parameters!!!!");
            result = 2;
        }
 
        return result;
    }
 
}