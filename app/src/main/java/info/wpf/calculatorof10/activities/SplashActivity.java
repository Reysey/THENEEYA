package info.wpf.calculatorof10.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import info.wpf.calculatorof10.R;

import info.wpf.calculatorof10.fan_admanager.FAN_AdManager;
import info.wpf.calculatorof10.fan_admanager.FAN_CONSTANTS;

import info.wpf.calculatorof10.helpers.CONSTANTS;
import info.wpf.calculatorof10.helpers.DevMonitor;
import info.wpf.calculatorof10.helpers.RemoteConfig;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // CHECK FOR INTERNET CONNECTION

        // new DevMonitor().getNetworkInfo(SplashActivity.this);

        // LOAD REMOTE CONFIG DEFINITION
        new RemoteConfig().InitRemoteConfig(new RemoteConfig.RemoteConfigLoadedEvent() {
            @Override
            public void onRemoteConfigLoaded() {

                // Developer Monitor
                new DevMonitor();


                if(CONSTANTS.showADs){
                    CONSTANTS.LogString("SHOW ADS ON! ["+(CONSTANTS.JSON_ACTIVE_AD_NETWORK.equals(FAN_CONSTANTS.FACEBOOK))+"]");

                    if(CONSTANTS.JSON_ACTIVE_AD_NETWORK.equals(FAN_CONSTANTS.FACEBOOK)){
                        // INIT CASE FACEBOOK ADS ENABLED
                        FAN_AdManager.InitFacebookAds(SplashActivity.this);
                    }

                    // INIT CASE ADMOB ADS ENABLED
                    // -
                }else{
                    CONSTANTS.LogString("SHOW ADS OFF!");

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Start the next activity
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);

                            // Finish the current activity
                            finish();
                        }
                    }, CONSTANTS.SPLASH_DELAY_DURATION);
                }

            }

            @Override
            public void onRemoteConfigError() {
                // LOAD ERROR PAGE
                startActivity(new Intent(SplashActivity.this, ErrorReportActivity.class));
            }
        });
    }
}