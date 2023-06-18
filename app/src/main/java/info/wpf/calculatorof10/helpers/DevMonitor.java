package info.wpf.calculatorof10.helpers;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.telephony.TelephonyManager;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.util.Enumeration;

import info.wpf.calculatorof10.models.IpApi;

public class DevMonitor {

    public DevMonitor() {
        if(CONSTANTS.DEV_MONITORING){
            gePhoneInfo();
        }
    }

    public void gePhoneInfo() {
        // Get the device manufacturer
        String manufacturer = Build.MANUFACTURER;
        CONSTANTS.LogString("Build.MANUFACTURER [" + Build.MANUFACTURER + "]");

        // Get the device model
        String model = Build.MODEL;
        CONSTANTS.LogString("Build.MODEL [" + Build.MODEL + "]");

        // Get the Android version
        String androidVersion = Build.VERSION.RELEASE;
        CONSTANTS.LogString("Build.VERSION.RELEASE [" + Build.VERSION.RELEASE + "]");

        // Get the API level
        int apiLevel = Build.VERSION.SDK_INT;
        CONSTANTS.LogString("Build.VERSION.SDK_INT [" + Build.VERSION.SDK_INT + "]");

        // Get IP ADDRESS

        // CONSTANTS.LogString("Get IP Address ["+getIPAddress()+"]");
        // CONSTANTS.LogString("getWanIPAddress ["+getWanIPAddress()+"]");

        new FetchISPAsyncTask().execute();

    }

    public void getNetworkInfo(Activity activity) {
        // Get the connectivity manager
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get the active network information
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        if (activeNetwork != null) {
            // Check if connected to mobile data or Wi-Fi
            boolean isConnectedToWifi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
            boolean isConnectedToMobileData = activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE;

            if (isConnectedToWifi) {
                // Connected to Wi-Fi, retrieve Wi-Fi network information if needed
            } else if (isConnectedToMobileData) {

                // Get the network type (e.g., LTE, HSPA)
                if (ActivityCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }

                // Connected to mobile data, retrieve mobile network information if needed
                TelephonyManager telephonyManager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);

                // Get the network operator name
                String networkOperatorName = telephonyManager.getNetworkOperatorName();
                CONSTANTS.LogString("networkOperatorName ["+networkOperatorName+"]");

                // Get the SIM operator name
                String simOperatorName = telephonyManager.getSimOperatorName();
                CONSTANTS.LogString("simOperatorName ["+simOperatorName+"]");

                int networkType = telephonyManager.getNetworkType();
                CONSTANTS.LogString("simOperatorName ["+networkType+"]");

                // Get the country ISO code
                String countryCode = telephonyManager.getSimCountryIso();
                CONSTANTS.LogString("simOperatorName ["+countryCode+"]");

            }
        }
    }

    public String getIPAddress() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getWanIPAddress() {
        BufferedReader reader = null;
        try {
            URL url = new URL("https://api.ipify.org"); // Web service that returns IP address
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            return reader.readLine(); // Read the response which contains the IP address
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public class FetchISPAsyncTask extends AsyncTask<Void, Void, String> {

        private static final String TAG = "FetchISPAsyncTask";
        private static final String IP_GEOLOCATION_API_URL = "https://api.ipify.org/?format=json";

        @Override
        protected String doInBackground(Void... voids) {
            BufferedReader reader = null;
            try {
                URL url = new URL("https://api.ipify.org"); // Web service that returns IP address
                reader = new BufferedReader(new InputStreamReader(url.openStream()));
                return getISPFromIPAddress(reader.readLine()); // Read the response which contains the IP address
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        public String getWanIPAddress() {
            BufferedReader reader = null;
            try {
                URL url = new URL("https://api.ipify.org"); // Web service that returns IP address
                reader = new BufferedReader(new InputStreamReader(url.openStream()));
                return reader.readLine(); // Read the response which contains the IP address
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        private String getISPFromIPAddress(String ipAddress) {

            CONSTANTS.LogString("IP ADDRESS ["+ipAddress+"]");

            try {
                // dHWmEbUhO1U2cRzL92gU6ZePqWrL2h7fftZePCS9lsq5euv9DYgNZRDAmaleCzCf
                // iptwist
                URL url = new URL("https://ipapi.co/" + ipAddress + "/json/?key=C6l8Xj0vRaYb2uEzyIifF2zhrhvIgYXeOLxxnSO9tU2pVG7bF6");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    // Parse the JSON response
                    JSONObject jsonResponse = new JSONObject(response.toString());

                    Gson gson = new Gson();
                    // Parse JSON and create IPDetails object
                    IpApi ipApi = gson.fromJson(response.toString(), IpApi.class);
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                    String key = databaseReference.child("Monitoring").push().getKey();

                    //ipApi.setOrg(jsonResponse.optString("org"));

                    databaseReference.child("AppLogMonitoring").child(key).setValue(ipApi).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            CONSTANTS.LogString("DATA STORED IN FIREBASE: ["+ipApi.getOrg()+"]");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NotNull Exception e) {
                            // Error occurred while storing data
                            CONSTANTS.LogString("DATA NOT STORED IN FIREBASE: ["+ipApi.getOrg()+"] "+e.getMessage());
                        }
                    });


                    return jsonResponse.optString("org");
                } else {
                    CONSTANTS.LogString( "Error: " + responseCode);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String isp) {
            if (isp != null) {
                CONSTANTS.LogString( "ISP: " + isp);
            } else {
                CONSTANTS.LogString( "Failed to fetch ISP information.");
            }
        }
    }
}
