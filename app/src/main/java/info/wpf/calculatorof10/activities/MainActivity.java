package info.wpf.calculatorof10.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;

import java.util.Arrays;
import java.util.List;

import info.wpf.calculatorof10.R;
import info.wpf.calculatorof10.adapters.CustomAdapter;
import info.wpf.calculatorof10.fan_admanager.FAN_AdManager;
import info.wpf.calculatorof10.fan_admanager.FAN_BANNER_MANAGER;
import info.wpf.calculatorof10.fan_admanager.FAN_CONSTANTS;
import info.wpf.calculatorof10.helpers.CONSTANTS;

public class MainActivity extends AppCompatActivity {

    public static AdView adView;
    public static AdListener facebookBannerAdListener;

    private ListView listView;
    private TextView calculator_result;
    static private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CONSTANTS.LogString("Main Activity Started");

        MainActivity.context = getApplicationContext();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.hideOverflowMenu();
        toolbar.setContentInsetsAbsolute(0, 0);
        // Set custom title for the app bar
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Hide the default menu icon
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        // Handle click on overflow menu icon
        findViewById(R.id.app_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAppMenu(v);
            }
        });

        // LOAD LIST VIEW CONTENTS
        loadListView();

        // ACTIVITY ADS MANAGER
        if(CONSTANTS.showADs){
            new FAN_BANNER_MANAGER(MainActivity.this, MainActivity.facebookBannerAdListener);
        }


    }

    public void showAppMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        getMenuInflater().inflate(R.menu.your_menu, popupMenu.getMenu());
        popupMenu.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.your_menu, menu);
        // Remove the default menu icon
        menu.removeItem(android.R.id.home);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;

        switch (item.getItemId()) {
            case R.id.menu_about:
                // Handle "About Us" menu option click
                CONSTANTS.LogString("About Us clicked");
                intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("url", "https://theneeyadevteam.000webhostapp.com/index.html");
                startActivity(intent);

                return true;
            case R.id.menu_privacy:
                // Handle "Privacy Setting" menu option click
                CONSTANTS.LogString( "Privacy Setting clicked");
                intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("url", "https://theneeyadevteam.000webhostapp.com/index.html");
                startActivity(intent);
                return true;
            case R.id.menu_close:
                // Handle "Close The App" menu option click
                CONSTANTS.LogString( "Close The App clicked");
                finish(); // Close the activity
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    public void loadListView(){
        //
        calculator_result = findViewById(R.id.calculator_result);
        // List View
        listView = findViewById(R.id.listview);

        // Sample data for the ListView
        List<String> items = Arrays.asList(new String[]{"1 + 9", "2 + 8", "3 + 7", "4 + 6", "5 + 5","6 + 4","7 + 3","8 + 2","9 + 1"});

        // Create an ArrayAdapter and set it as the ListView's adapter
        ListView listView = findViewById(R.id.listview);
        CustomAdapter adapter = new CustomAdapter(this, items);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Example: String selectedItem = (String) parent.getItemAtPosition(position);
                Log.e("REYDEV","ITEM CLICK["+(String) parent.getItemAtPosition(position)+"]");
                String[] item = ((String) parent.getItemAtPosition(position)).split("\\+");

                int item_1 = Integer.parseInt(item[0].trim());
                int item_2 = Integer.parseInt(item[1].trim());

                calculator_result.setText(((String) parent.getItemAtPosition(position))+" = ["+(item_1 + item_2)+"]");
            }
        });
        /* LIST VIEW BLOCK */
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