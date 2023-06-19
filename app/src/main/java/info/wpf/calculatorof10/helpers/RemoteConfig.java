package info.wpf.calculatorof10.helpers;

import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.jetbrains.annotations.NonNls;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import info.wpf.calculatorof10.fan_admanager.FAN_CONSTANTS;
import info.wpf.calculatorof10.models.AdResourceManager;

public class RemoteConfig {

    /*
    * RETRIEVE DATA FROM ONEDRIVE OR FIREBASE
    * */

    public static AdResourceManager adResourceManager;
    public static DatabaseReference databaseRef;
    public static RemoteConfigLoadedEvent remoteConfigLoadedEvent;

    public RemoteConfig() { }

    public void InitRemoteConfig(RemoteConfigLoadedEvent remoteConfigLoadedEvent){
        new FetchJsonTask(remoteConfigLoadedEvent).execute();
    }

    private class FetchJsonTask extends AsyncTask<Void, Void, String> {

        public FetchJsonTask(RemoteConfigLoadedEvent RemoteConfigLoadedEvent) {
            RemoteConfig.remoteConfigLoadedEvent = RemoteConfigLoadedEvent;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                // Create a URL object from the JSON URL string
                URL url = new URL(CONSTANTS.jsonFileUrl);

                // Open a connection to the URL
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                // Set the request method
                connection.setRequestMethod("GET");

                // Get the response code
                int responseCode = connection.getResponseCode();

                // Check if the response code is successful (e.g., 200)
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Get the input stream from the connection
                    InputStream inputStream = connection.getInputStream();

                    // Create a BufferedReader to read the input stream
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                    // Read the JSON data line by line
                    StringBuilder jsonData = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        jsonData.append(line);
                    }

                    // Close the BufferedReader and input stream
                    reader.close();
                    inputStream.close();

                    // Return the JSON data as a string
                    return jsonData.toString();
                }
            } catch (IOException e) {
                e.printStackTrace();
                CONSTANTS.LogString("RemoteConfig > FetchJsonTask","doInBackground > Exception", e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String jsonData) {
            if (jsonData != null) {
                //if (false) {
                // JSON data is available, you can parse and process it here
                CONSTANTS.LogString(""+jsonData);
                CONSTANTS.LogJsonString(""+jsonData);

                Gson gson = new Gson();

                RemoteConfig.adResourceManager = gson.fromJson(jsonData, AdResourceManager.class);

                CONSTANTS.LogString("RemoteConfig","onPostExecute", "JSON_AD_UNIT_FB_BANNER["+RemoteConfig.adResourceManager.getJSON_AD_UNIT_FB_BANNER()+"]");
                CONSTANTS.LogString("RemoteConfig","onPostExecute", "JSON_AD_UNIT_ADMOB_BANNER["+RemoteConfig.adResourceManager.getJSON_AD_UNIT_ADMOB_BANNER()+"]");
                CONSTANTS.LogString("RemoteConfig","onPostExecute", "JSON_ACTIVE_AD_NETWORK["+RemoteConfig.adResourceManager.getJSON_ACTIVE_AD_NETWORK()+"]");
                CONSTANTS.LogString("RemoteConfig","onPostExecute", "JSON_SHOW_ADS["+RemoteConfig.adResourceManager.getJSON_SHOW_ADS().toString()+"]");
                CONSTANTS.LogString("RemoteConfig","onPostExecute", "DEV_MONITORING["+RemoteConfig.adResourceManager.getDEV_MONITOR().toString()+"]");


                CONSTANTS.showADs = Boolean.parseBoolean(RemoteConfig.adResourceManager.getJSON_SHOW_ADS().toString());
                CONSTANTS.DEV_MONITORING = Boolean.parseBoolean(RemoteConfig.adResourceManager.getDEV_MONITOR().toString());


                if(CONSTANTS.showADs){
                    CONSTANTS.JSON_ACTIVE_AD_NETWORK = RemoteConfig.adResourceManager.getJSON_ACTIVE_AD_NETWORK();
                    FAN_CONSTANTS.FACEBOOK_BANNER_UNIT_ID = RemoteConfig.adResourceManager.getJSON_AD_UNIT_FB_BANNER();
                }
                
                // Remote Config Loaded Successfully, Continue
                RemoteConfig.remoteConfigLoadedEvent.onRemoteConfigLoaded();
            } else {

                // Failed to fetch JSON data
                CONSTANTS.LogString("RemoteConfig","onPostExecute", "Failed to fetch JSON data from OneDrive, Try From Firebase.");

                // LOAD DATA FROM JSON
                // Get a reference to the Firebase Realtime Database
                RemoteConfig.databaseRef = FirebaseDatabase.getInstance().getReference();

                // Add a ValueEventListener to listen for data changes
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNls DataSnapshot dataSnapshot) {
                        try{
                            CONSTANTS.LogString("RemoteConfig","onDataChange", " JSON_SHOW_ADS "+dataSnapshot.child("AdResourceManager").child("JSON_SHOW_ADS").getValue(Boolean.class).toString());
                            CONSTANTS.showADs = dataSnapshot.child("AdResourceManager").child("JSON_SHOW_ADS").getValue(Boolean.class);
                            if(CONSTANTS.showADs){
                                RemoteConfig.adResourceManager = new AdResourceManager();
                                switch (dataSnapshot.child("AdResourceManager").child("JSON_ACTIVE_AD_NETWORK").getValue(String.class)){
                                    case AdResourceManager.AD_NETWORK_ADMOB: /* DO SOMETHING */ ;break;
                                    case AdResourceManager.AD_NETWORK_FB:
                                        /* DO SOMETHING */
                                        RemoteConfig.adResourceManager.setJSON_AD_UNIT_FB_BANNER(dataSnapshot.child("AdResourceManager").child("JSON_AD_UNIT_FB_BANNER").getValue(String.class));
                                        CONSTANTS.JSON_ACTIVE_AD_NETWORK = dataSnapshot.child("AdResourceManager").child("JSON_ACTIVE_AD_NETWORK").getValue(String.class);
                                        FAN_CONSTANTS.FACEBOOK_BANNER_UNIT_ID = dataSnapshot.child("AdResourceManager").child("JSON_AD_UNIT_FB_BANNER").getValue(String.class);
                                        CONSTANTS.LogString("RemoteConfig","onDataChange", "AD UNIT  FROM JSON: "+FAN_CONSTANTS.FACEBOOK_BANNER_UNIT_ID);
                                        /* LOAD FACEBOOK ADS */
                                        break;
                                }
                            }

                            // Remote Config Loaded Successfully, Continue
                            RemoteConfig.remoteConfigLoadedEvent.onRemoteConfigLoaded();
                        }catch (Exception e){
                            e.printStackTrace();
                            CONSTANTS.LogString("RemoteConfig","Exception", e.getMessage());
                        }

                    }

                    @Override
                    public void onCancelled(@NonNls DatabaseError databaseError) {
                        // Handle the error
                        CONSTANTS.LogString("RemoteConfig","onCancelled", " DatabaseError:  "+databaseError.toString());
                        // Remote Config Loaded Successfully, Continue
                        RemoteConfig.remoteConfigLoadedEvent.onRemoteConfigError();
                    }
                };

                // Attach the ValueEventListener to the database reference
                RemoteConfig.databaseRef.addValueEventListener(valueEventListener);
            }
        }
    }
    
    public interface RemoteConfigLoadedEvent{
        void onRemoteConfigLoaded();
        void onRemoteConfigError();
    }
}