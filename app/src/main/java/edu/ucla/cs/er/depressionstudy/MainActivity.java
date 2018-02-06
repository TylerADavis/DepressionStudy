package edu.ucla.cs.er.depressionstudy;

import android.*;
import android.Manifest;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.aware.Applications;
import com.aware.Aware;
import com.aware.Aware_Preferences;
import com.aware.ESM;
import com.aware.ui.PermissionsHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;

import static android.app.Notification.BADGE_ICON_LARGE;
import static android.app.Notification.DEFAULT_ALL;

public class MainActivity extends AppCompatActivity {;
    private static final String STUDY_URL = "https://api.awareframework.com/index.php/webservice/index/1534/BqnhriI8YsQg";
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
    private Bundle bundle;
    private Window window;

    private AlarmManager alarmMgr;
    private PendingIntent notificationIntent;

    private NavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new NavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
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
        setContentView(R.layout.activity_main);

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


        // Initial Fragment
        FragmentTransaction transaction_init = getSupportFragmentManager().beginTransaction();
        if (findViewById(R.id.fragment_container) != null) {
            survey = new SurveyFragment();
            survey.setArguments(getIntent().getExtras());
            transaction_init.replace(R.id.fragment_container, survey);
            transaction_init.addToBackStack(null);
            transaction_init.commit();
        }

        //

        checkForUpdates();
    }

    private void scheduleNotifications() {
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
        }

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        survey.onBackPressed();
//        onPause();
//    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onResume() {
        super.onResume();

        checkForCrashes();
        initializeAware();
        this.scheduleNotifications();
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterManagers();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterManagers();
    }

    private void initializeAware() {
        if (hasRequiredPermissions()) {
            Aware.joinStudy(getApplicationContext(), STUDY_URL);
            /*if (Aware.getSetting(getApplicationContext(), Aware_Preferences.WEBSERVICE_SERVER).length() == 0) {
                Aware.joinStudy(getApplicationContext(), STUDY_URL);
                Toast.makeText(getApplicationContext(), "Joining study", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Already in a study", Toast.LENGTH_SHORT).show();
            }*/

            Intent aware = new Intent(this, Aware.class);
            startService(aware);

            Aware.setSetting(getApplicationContext(), Aware_Preferences.DEBUG_FLAG, "false");
            Aware.startAWARE(this);

            Applications.isAccessibilityServiceActive(this);
            Aware.isBatteryOptimizationIgnored(getApplicationContext(), getPackageName());
        } else {
            requestPermissions();
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
}
