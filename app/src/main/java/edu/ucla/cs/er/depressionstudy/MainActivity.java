package edu.ucla.cs.er.depressionstudy;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.PermissionChecker;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.aware.Aware;
import com.aware.Aware_Preferences;
import com.aware.ESM;
import com.aware.providers.ESM_Provider;
import com.aware.ui.PermissionsHandler;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import edu.ucla.cs.er.depressionstudy.Util.Utils;

import static android.app.Notification.DEFAULT_ALL;

//New for aware
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static String STUDY_URL;
    private static final ArrayList<String> REQUIRED_PERMISSIONS = new ArrayList<>(Arrays.asList(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.ACCESS_WIFI_STATE,
            android.Manifest.permission.BLUETOOTH,
            android.Manifest.permission.BLUETOOTH_ADMIN,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.READ_CONTACTS,
            android.Manifest.permission.READ_CALL_LOG,
            android.Manifest.permission.READ_SMS,
            android.Manifest.permission.RECORD_AUDIO
    ));

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;
    private AboutFragment about;
    private ContactFragment contact;
    private SurveyFragment survey;
    private LogoFragment logo;
    private Bundle bundle;
    private Window window;
    private StatusFragment status;

    private static int SPLASH_TIME_OUT = 1000;
    public static final String PREF_USER_FIRST_TIME = "user_first_time";
    boolean isUserFirstTime;
    private Handler handler;
    private Runnable runnable;
    FragmentTransaction transaction_init = getSupportFragmentManager().beginTransaction();
    FragmentTransaction transaction_sec = getSupportFragmentManager().beginTransaction();

    private AlarmManager alarmMgr;
    private PendingIntent notificationIntent;

    private NavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new NavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_status_new:
                    if (findViewById(R.id.fragment_container) != null) {
                        status = new StatusFragment();
                        status.setArguments(getIntent().getExtras());
                        transaction.replace(R.id.fragment_container, status);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                    mActivityTitle = getResources().getString(R.string.title_status);
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorStatus)));
                    window.setStatusBarColor(getResources().getColor(R.color.colorStatus));
                    return true;


                case R.id.navigation_questionnaires:
                    if (findViewById(R.id.fragment_container) != null) {
                        survey = new SurveyFragment();
                        survey.setArguments(getIntent().getExtras());
                        transaction.replace(R.id.fragment_container, survey);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                    mActivityTitle = getResources().getString(R.string.title_questionnaires);
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorSurvey)));
                    window.setStatusBarColor(getResources().getColor(R.color.colorSurvey));
                    return true;

                case R.id.navigation_contactus:
                    if (findViewById(R.id.fragment_container) != null) {
                        contact = new ContactFragment();
                        contact.setArguments(getIntent().getExtras());
                        transaction.replace(R.id.fragment_container, contact);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                    mActivityTitle = getResources().getString(R.string.title_contactus);
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorContact)));
                    window.setStatusBarColor(getResources().getColor(R.color.colorContact));
                    return true;

                case R.id.navigation_about:
                    // Check that the activity is using the layout version with
                    // the fragment_container FrameLayout
                    if (findViewById(R.id.fragment_container) != null) {
                        // Create a new Fragment to be placed in the activity layout
                        about = new AboutFragment();

                        // In case this activity was started with special instructions from an
                        // Intent, pass the Intent's extras to the fragment as arguments
                        about.setArguments(getIntent().getExtras());

                        // Add the fragment to the 'fragment_container' FrameLayout
                        transaction.replace(R.id.fragment_container, about);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                    mActivityTitle = getResources().getString(R.string.title_about);
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAbout)));
                    window.setStatusBarColor(getResources().getColor(R.color.colorAbout));
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppCenter.start(getApplication(), "272a2395-8aa0-4c7c-8f0e-8c394edf22cf",
                Analytics.class, Crashes.class);

        if (BuildConfig.FLAVOR.equals("dev")) {
            STUDY_URL = "https://api.awareframework.com/index.php/webservice/index/1534/BqnhriI8YsQg";
        } else {
            STUDY_URL = "https://api.awareframework.com/index.php/webservice/index/1534/BqnhriI8YsQg";
        }

        setContentView(R.layout.activity_main);

        checkFirstTime();

        window = this.getWindow();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);

        mActivityTitle = getTitle().toString();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.container);
        mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            /** Called when a drawer has settled in a completely open state. */
//            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened(drawerView);
//                getSupportActionBar().setTitle("Navigation");
//                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
//            }
//
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
//        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
//        navigationView.setNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

// Here's where we set the initial homepage. I've set it to be the status screen.
//        if (findViewById(R.id.fragment_container) != null) {
//            survey = new SurveyFragment();
//            survey.setArguments(getIntent().getExtras());
//            transaction_sec.replace(R.id.fragment_container, survey);
//            transaction_sec.addToBackStack(null);
//            transaction_sec.commit();
//        }

        if (findViewById(R.id.fragment_container) != null) {
            status = new StatusFragment();
            status.setArguments(getIntent().getExtras());
            transaction_sec.replace(R.id.fragment_container, status);
            transaction_sec.addToBackStack(null);
            transaction_sec.commit();
        }
//        initialFragment();
        checkForUpdates();
        initializeAware();
//        scheduleNotifications();
    }

    //to schedule notification
 /*   private void scheduleNotifications() {
        System.out.println("Scheduling notifications..");

        Context context = getApplicationContext();

        Notification.Builder builder = new Notification.Builder(context);
        builder.setCategory(Notification.CATEGORY_REMINDER);
        builder.setContentTitle("eWellness Reminder");
        builder.setContentText("Please fill your daily survey");
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setVisibility(Notification.VISIBILITY_PUBLIC);
        builder.setDefaults(DEFAULT_ALL);
        builder.setPriority(Notification.PRIORITY_HIGH);
        builder.setTicker("Please fill your daily survey");
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(contentIntent);
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra(NotificationReceiver.NOTIFICATION_ID, 42);
        intent.putExtra(NotificationReceiver.NOTIFICATION, notification);
        notificationIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        // Set the alarm to start at 9:00 a.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 0);
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        // Repeat daily
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 60 * 24, notificationIntent);

        System.out.println("Scheduled notifications");
    }*/

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
        checkForCrashes();
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterManagers();
    }

    @Override
    public void onDestroy() {
        unregisterManagers();
//        handler.removeCallbacks(runnable);
        super.onDestroy();
    }

    private void initializeAware() {
        if (hasRequiredPermissions()) {
            if (!Aware.getSetting(getApplicationContext(), Aware_Preferences.WEBSERVICE_SERVER).equals(STUDY_URL)) {
                Aware.joinStudy(getApplicationContext(), STUDY_URL);

                // This needs to happen after we have joined the study, so add some delay to make
                // sure it happens after all the web requests are finished
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        saveSubjectID();
                    }
                }, 10000);
            }

            Intent aware = new Intent(this, Aware.class);
            startService(aware);

            Aware.setSetting(getApplicationContext(), Aware_Preferences.DEBUG_FLAG, "false");
            //Aware.setSetting(getApplicationContext(), Aware_Preferences.DEBUG_FLAG, "true");
            Aware.startAWARE(this);

            //Applications.isAccessibilityServiceActive(this);
            //Aware.isBatteryOptimizationIgnored(getApplicationContext(), getPackageName());

            saveSubjectID();
        } else {
            requestPermissions();
        }
    }

    private void saveSubjectID() {
        String subjectID = Utils.readSharedSetting(getApplicationContext(), LogoActivity.PREF_SUBJECT_ID, "0");
        if (!subjectID.equals("0")) {
            System.out.println("Saving subject id");
            System.out.println(subjectID);
            Aware.setSetting(getApplicationContext(), Aware_Preferences.STATUS_ESM, true);

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

    private void requestESM() {
        //Make sure ESMs are active within the framework
        Aware.setSetting(getApplicationContext(), Aware_Preferences.STATUS_ESM, true);

        //Define the ESM to be displayed
        String esmString = "[{'esm':{'esm_type':"+ ESM.TYPE_ESM_TEXT+",'esm_title':'ESM Freetext','esm_instructions':'The user can answer an open ended question.','esm_submit':'Next','esm_expiration_threshold':60,'esm_trigger':'AWARE Tester'}}]";

        //Queue the ESM to be displayed when possible
        Intent esm = new Intent(ESM.ACTION_AWARE_QUEUE_ESM);
        esm.putExtra(ESM.EXTRA_ESM, esmString);
        sendBroadcast(esm);
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

    // HockeyApp integration

    private void checkForCrashes() {
        CrashManager.register(this);
    }

    private void checkForUpdates() {
        // Remove this for store builds!
        UpdateManager.register(this);
    }

    private void unregisterManagers() {
        UpdateManager.unregister();
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

    public void initialFragment() {

        // Hide Action bar
//        getSupportActionBar().hide();

        // LogoFragment first
        if (findViewById(R.id.fragment_container) != null) {
            logo = new LogoFragment();
            logo.setArguments(getIntent().getExtras());
            transaction_init.replace(R.id.fragment_container, logo);
            transaction_init.addToBackStack(null);
            transaction_init.commit();
        }

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
//                transaction_init.remove(logo).commit();

                // Initial Fragment
//                getSupportActionBar().show();
//                if (findViewById(R.id.fragment_container) != null) {
//                    survey = new SurveyFragment();
//                    survey.setArguments(getIntent().getExtras());
//                    transaction_sec.replace(R.id.fragment_container, survey);
//                    transaction_sec.addToBackStack(null);
//                    transaction_sec.commit();
//                }
                if (findViewById(R.id.fragment_container) != null) {
                    status = new StatusFragment();
                    status.setArguments(getIntent().getExtras());
                    transaction_sec.replace(R.id.fragment_container, status);
                    transaction_sec.addToBackStack(null);
                    transaction_sec.commit();
                }
            }
        };

        handler.postDelayed(runnable, SPLASH_TIME_OUT);

    }
}
