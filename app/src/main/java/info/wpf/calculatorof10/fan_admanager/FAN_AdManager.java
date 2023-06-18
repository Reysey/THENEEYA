package info.wpf.calculatorof10.fan_admanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.facebook.ads.AdSettings;
import com.facebook.ads.AudienceNetworkAds;

import info.wpf.calculatorof10.activities.MainActivity;
import info.wpf.calculatorof10.activities.SplashActivity;
import info.wpf.calculatorof10.helpers.CONSTANTS;

public class FAN_AdManager {

    public static AudienceNetworkAds.InitListener initListener;
    public static AdNetworkedInitializedEvent adNetworkedInitializedEvent;


    public static void InitFacebookAds(Activity activity){
        FAN_AdManager.AdNetworkedInitializedEvent adNetworkedInitializedEvent = new FAN_AdManager.AdNetworkedInitializedEvent() {
            @Override
            public void onAdNetworkedInitialized() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Start the next activity
                        Intent intent = new Intent(activity.getApplicationContext(), MainActivity.class);
                        activity.startActivity(intent);

                        // Finish the current activity
                        activity.finish();
                    }
                }, CONSTANTS.SPLASH_DELAY_DURATION);

            }
        };

        FAN_AdManager.initialize(activity.getApplicationContext(),adNetworkedInitializedEvent);
    }

    public static void initialize(Context context, AdNetworkedInitializedEvent adNetworkedInitializedEvent) {
        FAN_AdManager.adNetworkedInitializedEvent = adNetworkedInitializedEvent;

        if (!AudienceNetworkAds.isInitialized(context)) {

            if (CONSTANTS.DEBUG) {
                AdSettings.turnOnSDKDebugger(context);
            }

            CONSTANTS.LogString("FAN_AdManager","onInitialized","FACEBOOK ADS INITIALIZE PROCESS STARTED");

            AudienceNetworkAds
                    .buildInitSettings(context)
                    .withInitListener(new AudienceNetworkAds.InitListener(){
                        @Override
                        public void onInitialized(AudienceNetworkAds.InitResult initResult) {
                            CONSTANTS.LogString("FAN_AdManager","onInitialized","FACEBOOK ADS INITIALIZED");
                            adNetworkedInitializedEvent.onAdNetworkedInitialized();
                        }
                    })
                    .initialize();
        }else{
            CONSTANTS.LogString("FAN_AdManager","onInitialized","FACEBOOK ADS ALREADY INITIALIZED");
        }
    }

    public interface AdNetworkedInitializedEvent {
        void onAdNetworkedInitialized();
    }

}


