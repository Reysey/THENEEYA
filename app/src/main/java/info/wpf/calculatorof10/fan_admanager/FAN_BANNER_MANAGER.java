package info.wpf.calculatorof10.fan_admanager;

import android.app.Activity;
import android.widget.LinearLayout;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;

import info.wpf.calculatorof10.R;
import info.wpf.calculatorof10.activities.MainActivity;
import info.wpf.calculatorof10.helpers.CONSTANTS;

public class FAN_BANNER_MANAGER {

    public static AdView adView;
    public static boolean adShown = false;
    public static Activity activity;


    public FAN_BANNER_MANAGER(Activity activity, AdListener adListener) {
        FAN_BANNER_MANAGER.activity = activity;


        FAN_BANNER_MANAGER.activityBannerAdsManager(activity);
        FAN_BANNER_MANAGER.ShowFacebookBanner(activity, FAN_BANNER_MANAGER.initFacebookBannerAdListener(activity));
    }

    public static void activityBannerAdsManager(Activity activity){

        // CASE FACEBOOK
        FAN_AdManager.AdNetworkedInitializedEvent adNetworkedInitializedEvent = new FAN_AdManager.AdNetworkedInitializedEvent() {
            @Override
            public void onAdNetworkedInitialized() {
                // CHECK IF ADD LOADED...
                if(FAN_BANNER_MANAGER.adView != null){
                    if(FAN_BANNER_MANAGER.adShown || FAN_BANNER_MANAGER.adView.isAdInvalidated()){
                        FAN_BANNER_MANAGER.adView.destroy();
                    }
                }
            }
        };

        FAN_AdManager.initialize(activity.getApplicationContext(),adNetworkedInitializedEvent);

        if(AudienceNetworkAds.isInitialized(activity.getApplicationContext())){
            if(FAN_BANNER_MANAGER.adView != null){
                if(FAN_BANNER_MANAGER.adShown || FAN_BANNER_MANAGER.adView.isAdInvalidated()){
                    CONSTANTS.LogString("FAN_BANNER_MANAGER","activityBannerAdsManager","Destroy Ad");
                    FAN_BANNER_MANAGER.adView.destroy();
                }
            }
        }
    }

    public static void ShowFacebookBanner(Activity activity, AdListener adListener){


        CONSTANTS.LogString(activity.getLocalClassName(),"ShowFacebookBanner", "AD UNIT: "+"IMG_16_9_APP_INSTALL#"+ FAN_CONSTANTS.FACEBOOK_BANNER_UNIT_ID);

        FAN_BANNER_MANAGER.adView = new AdView(activity.getApplicationContext(), "IMG_16_9_APP_INSTALL#"+FAN_CONSTANTS.FACEBOOK_BANNER_UNIT_ID, AdSize.BANNER_HEIGHT_50);

        // Find the Ad Container
        LinearLayout adContainer = (LinearLayout) activity.findViewById(R.id.facebook_banner_container);
        // Add the ad view to your activity layout
        adContainer.addView(FAN_BANNER_MANAGER.adView);

        // Request an ad
        FAN_BANNER_MANAGER.adView.loadAd(FAN_BANNER_MANAGER.adView.buildLoadAdConfig().withAdListener(adListener).build());

        // AdManager__Banner.adShown = true;
    }

    public static AdListener initFacebookBannerAdListener(Activity activity){
        CONSTANTS.LogString("ACTIVITY: "+activity.getLocalClassName());
        switch (activity.getLocalClassName()){
            case "activities.MainActivity" : /* MainActivity */ return FAN_BANNER_MANAGER.getBannerAdListner(activity);
        }
        return null;
    }

    public static AdListener getBannerAdListner(Activity activity){
        return new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                CONSTANTS.LogString(activity.getLocalClassName(),"onError","AdError[ "+adError.getErrorCode()+" ]"+adError.getErrorMessage());
                FAN_BANNER_MANAGER.adShown = false;
            }

            @Override
            public void onAdLoaded(Ad ad) {
                CONSTANTS.LogString(activity.getLocalClassName(),"onAdLoaded","onAdLoaded[ "+ad.getPlacementId()+" ] Facebook Banner Ad Loaded.");
                FAN_BANNER_MANAGER.adShown = true;
            }

            @Override
            public void onAdClicked(Ad ad) {
                CONSTANTS.LogString(activity.getLocalClassName(),"onAdLoaded","onAdLoaded[ "+ad.getPlacementId()+" ] Facebook Banner Ad Clicked.");
                ad.destroy();
                FAN_BANNER_MANAGER.adShown = false;
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                CONSTANTS.LogString(activity.getLocalClassName(),"onAdLoaded","onAdLoaded[ "+ad.getPlacementId()+" ] Facebook Banner Ad Impression Logged.");
                FAN_BANNER_MANAGER.adShown = true;
                // TODO: ADD MORE VARIABLE TO TRACE TOTAL AD IMPRESSION PER APP USE
            }
        };
    }
}
