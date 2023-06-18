package info.wpf.calculatorof10.helpers;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class CONSTANTS {


    public static boolean DEBUG             = true;
    public static boolean DEV_MONITORING    = false;
    public static boolean showADs           = false;
    public static String DEBUG_NAME         = "REYDEV";
    public static String JSON_ACTIVE_AD_NETWORK = "";

    public static final long SPLASH_ANIMATION_DURATION = 2500;
    public static final long SPLASH_DELAY_DURATION = 5000;

    public static String jsonFileUrl = "https://hkfgig.am.files.1drv.com/y4mW8kV8yJJGR9m20D6B37zmdQ_gMXKWJj6v2kT7GHBl4NhiAQZ8rBAiwwUevcbkJj-RK0bPymBA2SQfWvuS8KQImcrE8RA5E212VHCF4cOZ1ItuEjXFyxZG8XPTk8rLyvokz38sG04_MysTDeYR-ZMVCR5L85px9LBF2nR170vwZ7XtbP95fB4zfDGRi4BqV9iUsK3ga624SokgzqiJT5mBQ";




    public static void LogString(String ClassName, String Methode, String Message){
        if(DEBUG){
            Log.i(DEBUG_NAME, "########################################################################################################");
            Log.i(DEBUG_NAME, "[ "+ClassName+"] > [ "+Methode+" ] > [ "+Message+" ]");
            Log.i(DEBUG_NAME, "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        }
    }

    public static void LogString(String Message){
        if(DEBUG){
            Log.i(DEBUG_NAME, "########################################################################################################");
            Log.i(DEBUG_NAME, "[ "+Message+" ]");
            Log.i(DEBUG_NAME, "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        }
    }

    public static void LogArray(String ClassName, String Methode, String[] Array){
        if(DEBUG){
            Log.i(DEBUG_NAME, "########################################################################################################");
            for (String Element : Array) {
                Log.i(DEBUG_NAME, "[ "+ClassName+"] > [ "+Methode+" ] > [ "+Element+" ]");
            }
            Log.i(DEBUG_NAME, "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");

        }
    }

    public static void LogArray(String[] Array){
        if(DEBUG){
            Log.i(DEBUG_NAME, "########################################################################################################");
            for (String Element : Array) {
                Log.i(DEBUG_NAME, Element);
            }
            Log.i(DEBUG_NAME, "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        }
    }


    public static void LogJsonString(String JSON_STRING){
        try {
            // JSONArray jsonArray = new JSONArray(JSON_STRING);
            JSONObject jsonObject = new JSONObject(JSON_STRING);

            Iterator<String> keys = jsonObject.keys();

            Log.i(DEBUG_NAME, "[JSON] ########################################################################################################");
            while (keys.hasNext()) {
                String key = keys.next();
                String value = jsonObject.getString(key);

                // Display the key-value pair
                Log.i(DEBUG_NAME, "[JSON] Key: [ " + key + " ], Value: [ " + value+" ]");
            }
            Log.i(DEBUG_NAME, "[JSON] ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i(DEBUG_NAME, "[JSONException] "+e.getMessage());
        }
    }



}
