package edu.ucla.cs.er.depressionstudy;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import edu.ucla.cs.er.depressionstudy.Util.Utils;

public class OnboardingActivity extends AppCompatActivity {
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private ImageButton mNextBtn;
    private Button mFinishBtn, mSkipBtn;
    private Window window;
    private RelativeLayout relativeLayout2;
    private EditText subIDTxt;
    int subID = 0;

    ImageView zero, one, two, three, four;
    ImageView[] indicators;
    int page = 0;   //  to track page position

    int lastLeftValue = 0;
    CoordinatorLayout mCoordinator;

    static final String TAG = "OnboardingActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        window = this.getWindow();
        mNextBtn = (ImageButton) findViewById(R.id.intro_btn_next);
        mFinishBtn = (Button) findViewById(R.id.intro_btn_finish);
        mSkipBtn = (Button) findViewById(R.id.intro_btn_skip);

        zero = (ImageView) findViewById(R.id.intro_indicator_0);
        one = (ImageView) findViewById(R.id.intro_indicator_1);
        two = (ImageView) findViewById(R.id.intro_indicator_2);
        three = (ImageView) findViewById(R.id.intro_indicator_3);
        four = (ImageView) findViewById(R.id.intro_indicator_4);

        mCoordinator = (CoordinatorLayout) findViewById(R.id.main_content);
        indicators = new ImageView[]{zero, one, two, three, four};

        relativeLayout2 = (RelativeLayout) findViewById(R.id.register_subID);
        subIDTxt = (EditText) findViewById(R.id.subjectID);
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.showSoftInput(subIDTxt, InputMethodManager.SHOW_IMPLICIT);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setCurrentItem(page);
        updateIndicators(page);

        final int color1 = getResources().getColor(R.color.colorSurvey);
        final int color2 = getResources().getColor(R.color.colorContact);
        final int color3 = getResources().getColor(R.color.colorAbout);
        final int color4 = getResources().getColor(R.color.colorActivity);
        final int color5 = getResources().getColor(R.color.colorOnboarding);

        final int[] colorList = new int[]{color1, /*color2, color3,*/ color4, color5};

        final ArgbEvaluator evaluator = new ArgbEvaluator();

        window.setStatusBarColor(color1);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // color update
                int colorUpdate = (Integer) evaluator.evaluate(positionOffset, colorList[position], colorList[position == 2 ? position : position + 1]);
                mViewPager.setBackgroundColor(colorUpdate);
            }

            @Override
            public void onPageSelected(int position) {
                page = position;

                updateIndicators(page);

                switch (position) {
                    case 0:
                        mViewPager.setBackgroundColor(color1);
                        window.setStatusBarColor(color1);
                        relativeLayout2.setVisibility(View.GONE);
                        break;
                    /*case 1:
                        mViewPager.setBackgroundColor(color2);
                        window.setStatusBarColor(color2);
                        relativeLayout2.setVisibility(View.GONE);
                        break;
                    case 2:
                        mViewPager.setBackgroundColor(color3);
                        window.setStatusBarColor(color3);
                        relativeLayout2.setVisibility(View.GONE);
                        break;*/
                    case 1:
                        mViewPager.setBackgroundColor(color4);
                        window.setStatusBarColor(color4);
                        relativeLayout2.setVisibility(View.GONE);
                        break;
                    case 2:
                        mViewPager.setBackgroundColor(color5);
                        window.setStatusBarColor(color5);
                        relativeLayout2.setVisibility(View.VISIBLE);

                        break;
                }

                mNextBtn.setVisibility(position == 2 ? View.GONE : View.VISIBLE);
                mFinishBtn.setVisibility(position == 2 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page += 1;
                mViewPager.setCurrentItem(page, true);
            }
        });

        mSkipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Terminate the app
                finish();
            }
        });

//        if (!subIDTxt.getText().toString().isEmpty()) {
//            mFinishBtn.setEnabled(true);
//        } else {
//            mFinishBtn.setEnabled(false);
//        }

        mFinishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!subIDTxt.getText().toString().isEmpty()) {
                    subID = Integer.valueOf(subIDTxt.getText().toString());
                    Log.d(TAG, "SubID = " + subID);

                    Utils.saveSharedSetting(OnboardingActivity.this, LogoActivity.PREF_SUBJECT_ID, String.valueOf(subID));

                    // OnboardingActivity -> LogoActivity
                    Utils.saveSharedSetting(OnboardingActivity.this, LogoActivity.PREF_USER_FIRST_TIME, "false");
                    Intent logoIntent = new Intent(OnboardingActivity.this, LogoActivity.class);
                    startActivity(logoIntent);
                    finish();
                }

                // OnboardingActivity -> MainActivity
//                Utils.saveSharedSetting(OnboardingActivity.this, MainActivity.PREF_USER_FIRST_TIME, "false");
//                Intent mainIntent = new Intent(OnboardingActivity.this, MainActivity.class);
//                startActivity(mainIntent);

            }
        });
    }

    public void updateIndicators(int position) {
        for (int i = 0; i < indicators.length; i++) {
            indicators[i].setBackgroundResource(
                    i == position ? R.drawable.indicator_selected : R.drawable.indicator_unselected
            );
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_onboarding, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_onboarding, container, false);

            LinearLayout linearLayout1 = (LinearLayout) rootView.findViewById(R.id.consent);
            RelativeLayout relativeLayout = (RelativeLayout) rootView.findViewById(R.id.welcome);
            LinearLayout linearLayout2 = (LinearLayout) rootView.findViewById(R.id.mentalhealth_notice);
            LinearLayout linearLayout3 = (LinearLayout) rootView.findViewById(R.id.data_collection);

            int section = getArguments().getInt(ARG_SECTION_NUMBER);
            switch (section) {
                case 1:
                    linearLayout1.setVisibility(View.GONE);
                    relativeLayout.setVisibility(View.VISIBLE);
                    linearLayout2.setVisibility(View.GONE);
                    linearLayout3.setVisibility(View.GONE);

                    return rootView;
                /*case 2:
                    linearLayout1.setVisibility(View.VISIBLE);
                    relativeLayout.setVisibility(View.GONE);
                    linearLayout2.setVisibility(View.GONE);
                    linearLayout3.setVisibility(View.GONE);

                    return rootView;
                case 3:
                    linearLayout1.setVisibility(View.GONE);
                    relativeLayout.setVisibility(View.GONE);
                    linearLayout2.setVisibility(View.VISIBLE);
                    linearLayout3.setVisibility(View.GONE);

                    return rootView;*/
                case 2:
                    linearLayout1.setVisibility(View.GONE);
                    relativeLayout.setVisibility(View.GONE);
                    linearLayout2.setVisibility(View.GONE);
                    linearLayout3.setVisibility(View.VISIBLE);

                    return rootView;
                case 3:
                    linearLayout1.setVisibility(View.GONE);
                    relativeLayout.setVisibility(View.GONE);
                    linearLayout2.setVisibility(View.GONE);
                    linearLayout3.setVisibility(View.GONE);

                    return rootView;
            }
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 5 total pages.
            return 3;
        }

    }
}
