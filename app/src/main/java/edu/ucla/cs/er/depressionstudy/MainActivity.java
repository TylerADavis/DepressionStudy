package edu.ucla.cs.er.depressionstudy;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.Settings;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.PermissionChecker;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.aware.Aware;
import com.aware.Applications;
import com.aware.Aware_Preferences;
import com.aware.ESM;
import com.aware.providers.ESM_Provider;
import com.aware.ui.PermissionsHandler;


import java.util.ArrayList;
import java.util.Arrays;
import edu.ucla.cs.er.depressionstudy.Util.Utils;

//New for aware
import com.google.firebase.messaging.FirebaseMessaging;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.distribute.Distribute;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static String STUDY_URL;
    private ArrayList<String> REQUIRED_PERMISSIONS = new ArrayList<>(Arrays.asList(
            //android.Manifest.permission.CAMERA,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.ACCESS_WIFI_STATE,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
            //android.Manifest.permission.BLUETOOTH,
            //android.Manifest.permission.BLUETOOTH_ADMIN,
            //android.Manifest.permission.READ_PHONE_STATE,
            //android.Manifest.permission.READ_CONTACTS,
            //android.Manifest.permission.READ_CALL_LOG,
            //android.Manifest.permission.READ_SMS,
            //android.Manifest.permission.RECORD_AUDIO
    ));

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;
    private AboutFragment about;
    private ContactFragment contact;
    private SurveyFragment survey;
    private StatusFragment status;
    private CalendarFragment calendar;

    public static final String PREF_USER_FIRST_TIME = "user_first_time";
    boolean isUserFirstTime;
    FragmentTransaction transaction_sec = getSupportFragmentManager().beginTransaction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            REQUIRED_PERMISSIONS.add(Manifest.permission.FOREGROUND_SERVICE);
        }

        AppCenter.start(getApplication(), "ff80713c-3956-4e3b-b3f1-7a8779a3ab4b",
                /*Analytics.class, Crashes.class, */Distribute.class);

        if (BuildConfig.FLAVOR.equals("dev")) {
            STUDY_URL = "https://gsnap.erlabdemo.com/index.php/1/4lph4num3ric";
        } else {
            STUDY_URL = "https://gsnap.erlabdemo.com/index.php/1/4lph4num3ric";
        }

        setContentView(R.layout.activity_main);

        //checkFirstTime();

        mActivityTitle = getTitle().toString();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.container);
        mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        if (findViewById(R.id.fragment_container) != null) {
            status = new StatusFragment();
            status.setArguments(getIntent().getExtras());
            transaction_sec.replace(R.id.fragment_container, status);
            transaction_sec.addToBackStack(null);
            transaction_sec.commit();
        }

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch (tab.getPosition()) {
                    case 0:
                        if (findViewById(R.id.fragment_container) != null) {
                            status = new StatusFragment();
                            status.setArguments(getIntent().getExtras());
                            transaction.replace(R.id.fragment_container, status);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                        return;
                    case 1:
                        if (findViewById(R.id.fragment_container) != null) {
                            survey = new SurveyFragment();

                            survey.setArguments(getIntent().getExtras());
                            transaction.replace(R.id.fragment_container, survey);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                        return;
                    /*case 1:
                        if (findViewById(R.id.fragment_container) != null) {
                            if (calendar == null) {
                                calendar = new CalendarFragment();
                            }

                            calendar.setArguments(getIntent().getExtras());
                            transaction.replace(R.id.fragment_container, calendar);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                        return;*/
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
//        initialFragment();
        //initializeAware();
//        scheduleNotifications();
    }

    public void openTab(int index) {
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.selectTab(tabLayout.getTabAt(index));
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (id) {
            case R.id.terminate:
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Closing eWellness")
                        .setMessage("Are you sure you want to close this app? It will stop the data collection.")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Aware.stopAWARE(getApplicationContext());
                                finish();
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            case R.id.info:
                if (findViewById(R.id.fragment_container) != null) {
                    about = new AboutFragment();
                    about.setArguments(getIntent().getExtras());
                    transaction.replace(R.id.fragment_container, about);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                return true;
            case R.id.survey:
                if (findViewById(R.id.fragment_container) != null) {
                    survey = new SurveyFragment();
                    survey.setArguments(getIntent().getExtras());
                    transaction.replace(R.id.fragment_container, survey);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                return true;

            case R.id.help:
                if (findViewById(R.id.fragment_container) != null) {
                    contact = new ContactFragment();
                    contact.setArguments(getIntent().getExtras());
                    transaction.replace(R.id.fragment_container, contact);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                return true;
            case R.id.status:
                if (findViewById(R.id.fragment_container) != null) {
                    status = new StatusFragment();
                    status.setArguments(getIntent().getExtras());
                    transaction.replace(R.id.fragment_container, status);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                return true;
            case R.id.calendar:
                if (findViewById(R.id.fragment_container) != null) {
                    calendar = new CalendarFragment();
                    calendar.setArguments(getIntent().getExtras());
                    transaction.replace(R.id.fragment_container, calendar);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
//        super.onBackPressed();
//        survey.onBackPressed();
//        onPause();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        initialFragment();
        initializeAware();
    }

    private void initializeAware() {
        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        if (!pm.isIgnoringBatteryOptimizations(getPackageName())) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        } else if (!hasRequiredPermissions()) {
            requestPermissions();
        } else {

//            if (!Aware.getSetting(getApplicationContext(), Aware_Preferences.WEBSERVICE_SERVER).equals(STUDY_URL)) {
            //Aware.joinStudy(getApplicationContext(), STUDY_URL);
//            }

            //Aware.setSetting(getApplicationContext(), Aware_Preferences.DEBUG_FLAG, "false");
            Aware.setSetting(getApplicationContext(), Aware_Preferences.DEBUG_FLAG, "true");
            //Aware.startAWARE(this);
            //Aware.startKeyboard(this);
            //Aware.startLocations(this);
            //Aware.startESM(this);

            //Aware.isBatteryOptimizationIgnored(getApplicationContext(), getPackageName());

            boolean isAccessibilityActive = Applications.isAccessibilityServiceActive(this);
            System.out.println("Accessibility active:");
            System.out.println(isAccessibilityActive);

            // This needs to happen after we have joined the study, so add some delay to make
            // sure it happens after all the web requests are finished
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    saveSubjectID();
                }
            }, 10000);

            subscribeToNotifications();


            Intent aware = new Intent(this, Aware.class);
            startService(aware);
            Aware.joinStudy(getApplicationContext(), STUDY_URL);

            //Aware.isBatteryOptimizationIgnored(this, getApplicationContext().getPackageName());
        }
    }

    private void saveSubjectID() {
        String subjectID = Utils.readSharedSetting(getApplicationContext(), LogoActivity.PREF_SUBJECT_ID, "0");
        if (!subjectID.equals("0")) {
            System.out.println("Saving subject id");
            System.out.println(subjectID);
            //Aware.setSetting(getApplicationContext(), Aware_Preferences.STATUS_ESM, true);

            Context context = getApplicationContext();

            String json = "{\"esm_type\":1, \"esm_title\":\"Subject ID\"}";

            ContentValues rowData = new ContentValues();
            rowData.put(ESM_Provider.ESM_Data.TIMESTAMP, System.currentTimeMillis());
            rowData.put(ESM_Provider.ESM_Data.ANSWER_TIMESTAMP, System.currentTimeMillis());
            rowData.put(ESM_Provider.ESM_Data.DEVICE_ID, Aware.getSetting(context, Aware_Preferences.DEVICE_ID));
            rowData.put(ESM_Provider.ESM_Data.JSON, json);
            rowData.put(ESM_Provider.ESM_Data.STATUS, ESM.STATUS_ANSWERED);
            rowData.put(ESM_Provider.ESM_Data.ANSWER, subjectID);

            context.getContentResolver().insert(ESM_Provider.ESM_Data.CONTENT_URI, rowData);
        }
    }

    private boolean hasRequiredPermissions() {
        for (String p : REQUIRED_PERMISSIONS) {
            if (PermissionChecker.checkSelfPermission(this, p) != PermissionChecker.PERMISSION_GRANTED) {
                return false;
            }
        }

        return true;
    }

    private void requestPermissions() {
        Intent permissionsHandler = new Intent(this, PermissionsHandler.class);
        permissionsHandler.putStringArrayListExtra(PermissionsHandler.EXTRA_REQUIRED_PERMISSIONS, REQUIRED_PERMISSIONS);
        permissionsHandler.putExtra(PermissionsHandler.EXTRA_REDIRECT_ACTIVITY, getPackageName() + "/" + getClass().getName());
        permissionsHandler.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(permissionsHandler);
    }

    public void checkFirstTime() {
        // Check the first time launching the app
        isUserFirstTime = Boolean.valueOf(Utils.readSharedSetting(MainActivity.this, PREF_USER_FIRST_TIME, "true"));
        Log.d("LogoActivity","first time? " + isUserFirstTime);
        Intent introIntent = new Intent(MainActivity.this, OnboardingActivity.class);
        introIntent.putExtra(PREF_USER_FIRST_TIME, isUserFirstTime);

        if (isUserFirstTime) {
            startActivity(introIntent);
            finish();
        }
    }

    public void subscribeToNotifications() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "eWellness";
            String description = "eWellness notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("eWellness-high", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        // Subscribe to notification channel with device id
        String device_id = Aware.getSetting(this, Aware_Preferences.DEVICE_ID);
        FirebaseMessaging.getInstance().subscribeToTopic(device_id)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    String msg = "Subscribed to notifications " + device_id;
                    if (!task.isSuccessful()) {
                        msg = "Failed to subscribe to notifications " + device_id;
                    }
                    Log.d(TAG, msg);
                }
            });
    }
}
