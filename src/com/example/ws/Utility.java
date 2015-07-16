package com.example.ws;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
 
public class Utility {

	/**
	 * Method to generate random key
	 * 
	 * @return random Integer key
	 */
	public static String randomKey() {
		return Long.toHexString(Double.doubleToLongBits(Math.random()));
	}
	  
	  
   /**
     * Null check Method
     * 
     * @param text
     * @return true/false
     */
    public static boolean isNotNull(String text) {
        return text != null && text.trim().length() > 0 ? true : false;
    }
 
    /**
     * Method to construct JSON
     * 
     * @param tag
     * @param message
     * @param status
     * @return JSON object
     */
    public static String constructJSON(String tag, String message, boolean status) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("tag", tag);
            obj.put("message", message);
            obj.put("status", status);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
        }
        return obj.toString();
    }
 
}
