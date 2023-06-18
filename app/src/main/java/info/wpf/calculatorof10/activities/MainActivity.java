package info.wpf.calculatorof10.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.LinearLayout;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;

import info.wpf.calculatorof10.R;
import info.wpf.calculatorof10.fan_admanager.FAN_AdManager;
import info.wpf.calculatorof10.fan_admanager.FAN_BANNER_MANAGER;
import info.wpf.calculatorof10.fan_admanager.FAN_CONSTANTS;
import info.wpf.calculatorof10.helpers.CONSTANTS;

public class MainActivity extends AppCompatActivity {

    public static AdView adView;
    public static AdListener facebookBannerAdListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ACTIVITY ADS MANAGER
        if(CONSTANTS.showADs){
            new FAN_BANNER_MANAGER(MainActivity.this, MainActivity.facebookBannerAdListener);
        }


    }

    @Override
    protected void onDestroy() {
        if(FAN_BANNER_MANAGER.adView != null){
            CONSTANTS.LogString("FAN_BANNER_MANAGER","activityBannerAdsManager","Destroy Ad");
            FAN_BANNER_MANAGER.adView.destroy();
        }
        super.onDestroy();
    }
}