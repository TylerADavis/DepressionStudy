package edu.ucla.cs.er.depressionstudy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.aware.Aware;
import com.aware.Aware_Preferences;
import com.aware.ESM;
import com.aware.ui.esms.ESMFactory;
import com.aware.ui.esms.ESM_Radio;
import com.aware.utils.Scheduler;

import org.json.JSONArray;
import org.json.JSONException;

import edu.ucla.cs.er.depressionstudy.Util.Utils;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class LogoActivity extends AppCompatActivity {
    // Code from: https://www.youtube.com/watch?v=jXtof6OUtcE
    private static int SPLASH_TIME_OUT = 4000;
    private String esmString;
    public static final String PREF_USER_FIRST_TIME = "user_first_time";
    boolean isUserFirstTime;

    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check the first time launching the app
        isUserFirstTime = Boolean.valueOf(Utils.readSharedSetting(LogoActivity.this, PREF_USER_FIRST_TIME, "true"));
        Log.d("LogoActivity","first time? " + isUserFirstTime);
        Intent introIntent = new Intent(LogoActivity.this, OnboardingActivity.class);
        introIntent.putExtra(PREF_USER_FIRST_TIME, isUserFirstTime);

        setContentView(R.layout.activity_logo);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);


//         Set up the user interaction to manually show or hide the system UI.
//        mContentView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                toggle();
//            }
//        });

        if (isUserFirstTime) {
            startActivity(introIntent);
            finish();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent logoIntent = new Intent(LogoActivity.this, MainActivity.class);
                    startActivity(logoIntent);
                    finish();
                }
            }, SPLASH_TIME_OUT);
            weeklySurvey();
        }

    }

    private void weeklySurvey() {
        try {
            ESMFactory factory = new ESMFactory();
            Aware.setSetting(this, Aware_Preferences.STATUS_ESM, true);

            ESM_Radio esmRadio1 = new ESM_Radio();
            esmRadio1.addRadio("No difficulty")
                    .addRadio("A little difficulty")
                    .addRadio("Moderate difficulty")
                    .addRadio("Quite a bit of difficulty")
                    .addRadio("Extreme difficulty")
                    .setTitle("During the PAST WEEK, how much difficulty did you have")
                    .setInstructions("Managing your day-to-day life?")
                    .setExpirationThreshold(0)
                    .setSubmitButton("NEXT");

            ESM_Radio esmRadio2 = new ESM_Radio();
            esmRadio2.addRadio("No difficulty")
                    .addRadio("A little difficulty")
                    .addRadio("Moderate difficulty")
                    .addRadio("Quite a bit of difficulty")
                    .addRadio("Extreme difficulty")
                    .setTitle("During the PAST WEEK, how much difficulty did you have")
                    .setInstructions("Coping with problems in your life?")
                    .setExpirationThreshold(0)
                    .setSubmitButton("NEXT");

            ESM_Radio esmRadio3 = new ESM_Radio();
            esmRadio3.addRadio("No difficulty")
                    .addRadio("A little difficulty")
                    .addRadio("Moderate difficulty")
                    .addRadio("Quite a bit of difficulty")
                    .addRadio("Extreme difficulty")
                    .setTitle("During the PAST WEEK, how much difficulty did you have")
                    .setInstructions("Concentrating?")
                    .setExpirationThreshold(0)
                    .setSubmitButton("NEXT");

            ESM_Radio esmRadio4 = new ESM_Radio();
            esmRadio4.addRadio("None of the time")
                    .addRadio("A little of the time")
                    .addRadio("Half of the time")
                    .addRadio("Most of the time")
                    .addRadio("All of the time")
                    .setTitle("During the PAST WEEK, how much of the time did you")
                    .setInstructions("Get along with people in your family?")
                    .setExpirationThreshold(0)
                    .setSubmitButton("NEXT");

            ESM_Radio esmRadio5 = new ESM_Radio();
            esmRadio5.addRadio("None of the time")
                    .addRadio("A little of the time")
                    .addRadio("Half of the time")
                    .addRadio("Most of the time")
                    .addRadio("All of the time")
                    .setTitle("During the PAST WEEK, how much of the time did you")
                    .setInstructions("Get along with people outside your family?")
                    .setExpirationThreshold(0)
                    .setSubmitButton("NEXT");

            ESM_Radio esmRadio6 = new ESM_Radio();
            esmRadio6.addRadio("None of the time")
                    .addRadio("A little of the time")
                    .addRadio("Half of the time")
                    .addRadio("Most of the time")
                    .addRadio("All of the time")
                    .setTitle("During the PAST WEEK, how much of the time did you")
                    .setInstructions("Get along well in social situations?")
                    .setExpirationThreshold(0)
                    .setSubmitButton("NEXT");

            ESM_Radio esmRadio7 = new ESM_Radio();
            esmRadio7.addRadio("None of the time")
                    .addRadio("A little of the time")
                    .addRadio("Half of the time")
                    .addRadio("Most of the time")
                    .addRadio("All of the time")
                    .setTitle("During the PAST WEEK, how much of the time did you ")
                    .setInstructions("Feel close to another person?")
                    .setExpirationThreshold(0)
                    .setSubmitButton("NEXT");

            ESM_Radio esmRadio8 = new ESM_Radio();
            esmRadio8.addRadio("None of the time")
                    .addRadio("A little of the time")
                    .addRadio("Half of the time")
                    .addRadio("Most of the time")
                    .addRadio("All of the time")
                    .setTitle("During the PAST WEEK, how much of the time did you ")
                    .setInstructions("Feel like you had someone to turn to if you needed help?")
                    .setExpirationThreshold(0)
                    .setSubmitButton("NEXT");

            ESM_Radio esmRadio9 = new ESM_Radio();
            esmRadio9.addRadio("None of the time")
                    .addRadio("A little of the time")
                    .addRadio("Half of the time")
                    .addRadio("Most of the time")
                    .addRadio("All of the time")
                    .setTitle("During the PAST WEEK, how much of the time did you ")
                    .setInstructions("Feel confident in yourself?")
                    .setExpirationThreshold(0)
                    .setSubmitButton("NEXT");

            ESM_Radio esmRadio10 = new ESM_Radio();
            esmRadio10.addRadio("None of the time")
                    .addRadio("A little of the time")
                    .addRadio("Half of the time")
                    .addRadio("Most of the time")
                    .addRadio("All of the time")
                    .setTitle("During the PAST WEEK, how much of the time did you ")
                    .setInstructions("Feel sad or depressed?")
                    .setExpirationThreshold(0)
                    .setSubmitButton("NEXT");

            ESM_Radio esmRadio11 = new ESM_Radio();
            esmRadio11.addRadio("None of the time")
                    .addRadio("A little of the time")
                    .addRadio("Half of the time")
                    .addRadio("Most of the time")
                    .addRadio("All of the time")
                    .setTitle("During the PAST WEEK, how much of the time did you ")
                    .setInstructions("Think about ending your life?")
                    .setExpirationThreshold(0)
                    .setSubmitButton("NEXT");

            ESM_Radio esmRadio12 = new ESM_Radio();
            esmRadio12.addRadio("None of the time")
                    .addRadio("A little of the time")
                    .addRadio("Half of the time")
                    .addRadio("Most of the time")
                    .addRadio("All of the time")
                    .setTitle("During the PAST WEEK, how much of the time did you ")
                    .setInstructions("Feel nervous?")
                    .setExpirationThreshold(0)
                    .setSubmitButton("NEXT");

            ESM_Radio esmRadio13 = new ESM_Radio();
            esmRadio13.addRadio("Never")
                    .addRadio("Rarely")
                    .addRadio("Sometimes")
                    .addRadio("Often")
                    .addRadio("Always")
                    .setTitle("During the PAST WEEK, how often did you ")
                    .setInstructions("Have thoughts racing through your head?")
                    .setExpirationThreshold(0)
                    .setSubmitButton("NEXT");

            ESM_Radio esmRadio14 = new ESM_Radio();
            esmRadio14.addRadio("Never")
                    .addRadio("Rarely")
                    .addRadio("Sometimes")
                    .addRadio("Often")
                    .addRadio("Always")
                    .setTitle("During the PAST WEEK, how often did you ")
                    .setInstructions("Think you had special powers?")
                    .setExpirationThreshold(0)
                    .setSubmitButton("NEXT");

            ESM_Radio esmRadio15 = new ESM_Radio();
            esmRadio15.addRadio("Never")
                    .addRadio("Rarely")
                    .addRadio("Sometimes")
                    .addRadio("Often")
                    .addRadio("Always")
                    .setTitle("During the PAST WEEK, how often did you ")
                    .setInstructions("Hear voices or see things?")
                    .setExpirationThreshold(0)
                    .setSubmitButton("NEXT");

            ESM_Radio esmRadio16 = new ESM_Radio();
            esmRadio16.addRadio("Never")
                    .addRadio("Rarely")
                    .addRadio("Sometimes")
                    .addRadio("Often")
                    .addRadio("Always")
                    .setTitle("During the PAST WEEK, how often did you ")
                    .setInstructions("Think people were watching you?")
                    .setExpirationThreshold(0)
                    .setSubmitButton("NEXT");

            ESM_Radio esmRadio17 = new ESM_Radio();
            esmRadio17.addRadio("Never")
                    .addRadio("Rarely")
                    .addRadio("Sometimes")
                    .addRadio("Often")
                    .addRadio("Always")
                    .setTitle("During the PAST WEEK, how often did you ")
                    .setInstructions("Think people were against you?")
                    .setExpirationThreshold(0)
                    .setSubmitButton("NEXT");

            ESM_Radio esmRadio18 = new ESM_Radio();
            esmRadio18.addRadio("Never")
                    .addRadio("Rarely")
                    .addRadio("Sometimes")
                    .addRadio("Often")
                    .addRadio("Always")
                    .setTitle("During the PAST WEEK, how often did you ")
                    .setInstructions("Have mood swings?")
                    .setExpirationThreshold(0)
                    .setSubmitButton("NEXT");

            ESM_Radio esmRadio19 = new ESM_Radio();
            esmRadio19.addRadio("Never")
                    .addRadio("Rarely")
                    .addRadio("Sometimes")
                    .addRadio("Often")
                    .addRadio("Always")
                    .setTitle("During the PAST WEEK, how often did you ")
                    .setInstructions("Feel short-tempered?")
                    .setExpirationThreshold(0)
                    .setSubmitButton("NEXT");

            ESM_Radio esmRadio20 = new ESM_Radio();
            esmRadio20.addRadio("Never")
                    .addRadio("Rarely")
                    .addRadio("Sometimes")
                    .addRadio("Often")
                    .addRadio("Always")
                    .setTitle("During the PAST WEEK, how often did you ")
                    .setInstructions("Think about hurting yourself?")
                    .setExpirationThreshold(0)
                    .setSubmitButton("NEXT");

            ESM_Radio esmRadio21 = new ESM_Radio();
            esmRadio21.addRadio("Never")
                    .addRadio("Rarely")
                    .addRadio("Sometimes")
                    .addRadio("Often")
                    .addRadio("Always")
                    .setTitle("During the PAST WEEK, how often")
                    .setInstructions("Did you have an urge to drink alcohol or take street drugs?")
                    .setExpirationThreshold(0)
                    .setSubmitButton("NEXT");

            ESM_Radio esmRadio22 = new ESM_Radio();
            esmRadio22.addRadio("Never")
                    .addRadio("Rarely")
                    .addRadio("Sometimes")
                    .addRadio("Often")
                    .addRadio("Always")
                    .setTitle("During the PAST WEEK, how often")
                    .setInstructions("Did anyone talk to you about your drinking or drug use?")
                    .setExpirationThreshold(0)
                    .setSubmitButton("NEXT");

            ESM_Radio esmRadio23 = new ESM_Radio();
            esmRadio23.addRadio("Never")
                    .addRadio("Rarely")
                    .addRadio("Sometimes")
                    .addRadio("Often")
                    .addRadio("Always")
                    .setTitle("During the PAST WEEK, how often")
                    .setInstructions("Did you try to hide your drinking or drug use?")
                    .setExpirationThreshold(0)
                    .setSubmitButton("NEXT");

            ESM_Radio esmRadio24 = new ESM_Radio();
            esmRadio24.addRadio("Never")
                    .addRadio("Rarely")
                    .addRadio("Sometimes")
                    .addRadio("Often")
                    .addRadio("Always")
                    .setTitle("During the PAST WEEK, how often")
                    .setInstructions("Did you have problems from your drinking or drug use?")
                    .setExpirationThreshold(0)
                    .setSubmitButton("DONE");

            JSONArray array = new JSONArray();
            array.put(esmRadio1.build());
            array.put(esmRadio2.build());
            array.put(esmRadio3.build());
            array.put(esmRadio4.build());
            array.put(esmRadio5.build());
            array.put(esmRadio6.build());
            array.put(esmRadio7.build());
            array.put(esmRadio8.build());
            array.put(esmRadio9.build());
            array.put(esmRadio10.build());
            array.put(esmRadio11.build());
            array.put(esmRadio12.build());
            array.put(esmRadio13.build());
            array.put(esmRadio14.build());
            array.put(esmRadio15.build());
            array.put(esmRadio16.build());
            array.put(esmRadio17.build());
            array.put(esmRadio18.build());
            array.put(esmRadio19.build());
            array.put(esmRadio20.build());
            array.put(esmRadio21.build());
            array.put(esmRadio22.build());
            array.put(esmRadio23.build());
            array.put(esmRadio24.build());
            esmString = array.toString();
            Log.d("esmString", esmString);

            //add them to the factory
            factory.addESM(esmRadio1);
            factory.addESM(esmRadio2);
            factory.addESM(esmRadio3);
            factory.addESM(esmRadio4);
            factory.addESM(esmRadio5);
            factory.addESM(esmRadio6);
            factory.addESM(esmRadio7);
            factory.addESM(esmRadio8);
            factory.addESM(esmRadio9);
            factory.addESM(esmRadio10);
            factory.addESM(esmRadio11);
            factory.addESM(esmRadio12);
            factory.addESM(esmRadio13);
            factory.addESM(esmRadio14);
            factory.addESM(esmRadio15);
            factory.addESM(esmRadio16);
            factory.addESM(esmRadio17);
            factory.addESM(esmRadio18);
            factory.addESM(esmRadio19);
            factory.addESM(esmRadio20);
            factory.addESM(esmRadio21);
            factory.addESM(esmRadio22);
            factory.addESM(esmRadio23);
            factory.addESM(esmRadio24);

            Scheduler.Schedule schedule = new Scheduler.Schedule("schedule_id");
            schedule.addHour(9) //0-23
                    .addWeekday("Saturday") //Every Saturday
                    .setActionType(Scheduler.ACTION_TYPE_BROADCAST)
                    .setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM)
                    .addActionExtra(ESM.EXTRA_ESM, esmString);

            Scheduler.saveSchedule(getApplicationContext(), schedule);

            //Queue them
//            ESM.queueESM(this, factory.build());

            //Sample: Define the ESM to be displayed
//                    String esmString = "[{'esm':{'esm_type':"+ ESM.TYPE_ESM_TEXT+",'esm_title':'ESM Freetext','esm_instructions':'The user can answer an open ended question.','esm_submit':'Next','esm_expiration_threshold':60,'esm_trigger':'AWARE Tester'}}]";

            //Queue the ESM to be displayed when possible
//            Intent esm = new Intent(ESM.ACTION_AWARE_QUEUE_ESM);
//            esm.putExtra(ESM.EXTRA_ESM, esmString);
//            this.sendBroadcast(esm);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

}
