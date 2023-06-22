package info.wpf.calculatorof10.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.agrawalsuneet.dotsloader.loaders.LightsLoader;

import info.wpf.calculatorof10.R;

import info.wpf.calculatorof10.fan_admanager.FAN_AdManager;
import info.wpf.calculatorof10.fan_admanager.FAN_CONSTANTS;

import info.wpf.calculatorof10.helpers.CONSTANTS;
import info.wpf.calculatorof10.helpers.DevMonitor;
import info.wpf.calculatorof10.helpers.RemoteConfig;

public class SplashActivity extends AppCompatActivity {

    private LightsLoader lightsLoader;
    private ImageView imageView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Splash Screen Animation
        lightsLoader = findViewById(R.id.lightsLoader);

        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);

        startZoomInAnimation();
        startTextAnimation();
        startLoaderAnimation();

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

    private void startZoomInAnimation() {
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(imageView, "scaleX", 0.5f, 1);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(imageView, "scaleY", 0.5f, 1);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
        animatorSet.setDuration(CONSTANTS.SPLASH_ANIMATION_DURATION);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                imageView.setVisibility(View.VISIBLE);
            }
        });
        animatorSet.start();
    }

    private void startTextAnimation() {
        textView.setScaleX(0.5f);
        textView.setScaleY(0.5f);
        textView.animate()
                .scaleX(1)
                .scaleY(1)
                .setDuration(CONSTANTS.SPLASH_ANIMATION_DURATION)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setStartDelay(0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        textView.setVisibility(android.view.View.VISIBLE);
                    }
                })
                .start();
    }

    private void startLoaderAnimation() {
        // Start the loader animation
        lightsLoader.startLayoutAnimation();
    }
}