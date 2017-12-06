package edu.ucla.cs.er.depressionstudy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.aware.Applications;
import com.aware.Aware;
import com.aware.Aware_Preferences;
import com.aware.syncadapters.Aware_Sync;
import com.aware.ui.PermissionsHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        initializeAware();
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
