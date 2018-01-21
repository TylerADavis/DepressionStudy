package edu.ucla.cs.er.depressionstudy;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.PermissionChecker;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.aware.Applications;
import com.aware.Aware;
import com.aware.Aware_Preferences;
import com.aware.ui.PermissionsHandler;

import java.util.ArrayList;
import java.util.Arrays;

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
            android.Manifest.permission.READ_PHONE_STATE
    ));

    private TextView mTextMessage;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;
    private AboutFragment about;
    private Bundle bundle;
    private Window window;

    private NavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new NavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_questionnaires:
                    mTextMessage.setVisibility(View.INVISIBLE);
                    mActivityTitle = getResources().getString(R.string.title_questionnaires);
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorSurvey)));
                    window.setStatusBarColor(getResources().getColor(R.color.colorSurvey));
                    return true;
                case R.id.navigation_contactus:
                    mTextMessage.setVisibility(View.INVISIBLE);
                    mActivityTitle = getResources().getString(R.string.title_contactus);
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorContact)));
                    window.setStatusBarColor(getResources().getColor(R.color.colorContact));
                    return true;
                case R.id.navigation_about:
                    mTextMessage.setVisibility(View.INVISIBLE);
                    // Check that the activity is using the layout version with
                    // the fragment_container FrameLayout
                    if (findViewById(R.id.fragment_container) != null) {
                        // Create a new Fragment to be placed in the activity layout
                        about = new AboutFragment();

                        // In case this activity was started with special instructions from an
                        // Intent, pass the Intent's extras to the fragment as arguments
                        about.setArguments(getIntent().getExtras());

                        // Add the fragment to the 'fragment_container' FrameLayout
                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.fragment_container, about).commit();
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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        window = this.getWindow();
        mTextMessage = (TextView) findViewById(R.id.message);
        mActivityTitle = getTitle().toString();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.container);
        mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        initializeAware();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return false;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initializeAware() {
        if (hasRequiredPermissions()) {
            if (Aware.getSetting(getApplicationContext(), Aware_Preferences.WEBSERVICE_SERVER).length() == 0) {
                Aware.joinStudy(getApplicationContext(), STUDY_URL);
                Toast.makeText(getApplicationContext(), "Joining study", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Already in a study", Toast.LENGTH_SHORT).show();
            }

            Intent aware = new Intent(this, Aware.class);
            startService(aware);

            Aware.setSetting(getApplicationContext(), Aware_Preferences.DEBUG_FLAG, "true");
            Aware.startAWARE(this);

            Applications.isAccessibilityServiceActive(this);
            Aware.isBatteryOptimizationIgnored(getApplicationContext(), getPackageName());
        } else {
            requestPermissions();
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
}
