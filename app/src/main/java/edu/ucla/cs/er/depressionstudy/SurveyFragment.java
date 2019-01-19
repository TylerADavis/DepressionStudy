package edu.ucla.cs.er.depressionstudy;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aware.Aware;
import com.aware.Aware_Preferences;
import com.aware.ESM;
import com.aware.providers.ESM_Provider;
import com.aware.ui.esms.ESM_Checkbox;
import com.aware.ui.esms.ESM_Radio;
import com.aware.ui.esms.ESM_Freetext;
import com.aware.ui.esms.ESMFactory;


import org.json.JSONArray;
import org.json.JSONException;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class SurveyFragment extends Fragment {

    private Button startButtonK10;
    private Button startButtonLubben;
    private Button startButtonSFS;
    private Button startButtonInsomnia;
    private Button startButtonOFSC;
    private Button startButtonPQSI;
    private Button startButtonSSRS;
    private Button startButtonPhysical;
    private Activity context;
    private String esmString;

    public SurveyFragment() {
        // Required empty public constructor
    }


    @Override
    public void onResume() {
        super.onResume();

        updateCompletedTextVisibility(this.getView());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_survey, container, false);
        context = getActivity();

        startButtonK10 =  (Button) rootView.findViewById(R.id.bt_start);
//        setState(STATE_DEFAULT);
        startButtonK10.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                //*** http://www.awareframework.com/esm/ ***/
                try {
//                    ESMFactory factory = new ESMFactory();
                    Aware.setSetting(context, Aware_Preferences.STATUS_ESM, true);

                    //define ESM question
                    ESM_Checkbox esmCheckbox1 = new ESM_Checkbox();
                    esmCheckbox1.addCheck("HAPPY")
                            .addCheck("SAD")
                            .addCheck("DEPRESSED")
                            .addCheck("EXCITED")
                            .addCheck("ANXIOUS")
                            .addCheck("SATISFIED")
                            .setTrigger("AWARE Tester")
                            .setTitle("Daily Survey 1")
                            .setExpirationThreshold(300)
                            .setSubmitButton("NEXT")
                            .setInstructions("WHAT IS YOUR EMOTION TODAY?");

                    ESM_Radio esmRadio1 = new ESM_Radio();
                    esmRadio1.addRadio("None of the time")
                            .addRadio("A little of the time")
                            .addRadio("Some of the time")
                            .addRadio("Most of the time")
                            .addRadio("All of the time")
                            .setTitle("[1 of 10] During the past day, about how often did you feel ...")
                            .setInstructions("... tired out for no good reason?")
                            .setExpirationThreshold(300)
                            .setReplaceQueue(true)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio2 = new ESM_Radio();
                    esmRadio2.addRadio("None of the time")
                            .addRadio("A little of the time")
                            .addRadio("Some of the time")
                            .addRadio("Most of the time")
                            .addRadio("All of the time")
                            .setTitle("[2 of 10] During the past day, about how often did you feel ...")
                            .setInstructions("... nervous?")
                            .setExpirationThreshold(300)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio3 = new ESM_Radio();
                    esmRadio3.addRadio("None of the time")
                            .addRadio("A little of the time")
                            .addRadio("Some of the time")
                            .addRadio("Most of the time")
                            .addRadio("All of the time")
                            .setTitle("[3 of 10] During the past day, about how often did you feel ...")
                            .setInstructions("... so nervous that nothing could calm you down?")
                            .setExpirationThreshold(300)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio4 = new ESM_Radio();
                    esmRadio4.addRadio("None of the time")
                            .addRadio("A little of the time")
                            .addRadio("Some of the time")
                            .addRadio("Most of the time")
                            .addRadio("All of the time")
                            .setTitle("[4 of 10] During the past day, about how often did you feel ...")
                            .setInstructions("... hopeless?")
                            .setExpirationThreshold(60)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio5 = new ESM_Radio();
                    esmRadio5.addRadio("None of the time")
                            .addRadio("A little of the time")
                            .addRadio("Some of the time")
                            .addRadio("Most of the time")
                            .addRadio("All of the time")
                            .setTitle("[5 of 10] During the past day, about how often did you feel ...")
                            .setInstructions("... restless or fidgety?")
                            .setExpirationThreshold(300)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio6 = new ESM_Radio();
                    esmRadio6.addRadio("None of the time")
                            .addRadio("A little of the time")
                            .addRadio("Some of the time")
                            .addRadio("Most of the time")
                            .addRadio("All of the time")
                            .setTitle("[6 of 10] During the past day, about how often did you feel ...")
                            .setInstructions("... so restless that you could not sit still?")
                            .setExpirationThreshold(300)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio7 = new ESM_Radio();
                    esmRadio7.addRadio("None of the time")
                            .addRadio("A little of the time")
                            .addRadio("Some of the time")
                            .addRadio("Most of the time")
                            .addRadio("All of the time")
                            .setTitle("[7 of 10] During the past day, about how often did you feel ...")
                            .setInstructions("... depressed?")
                            .setExpirationThreshold(300)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio8 = new ESM_Radio();
                    esmRadio8.addRadio("None of the time")
                            .addRadio("A little of the time")
                            .addRadio("Some of the time")
                            .addRadio("Most of the time")
                            .addRadio("All of the time")
                            .setTitle("[8 of 10] During the past day, about how often did you feel ...")
                            .setInstructions("... so depressed that nothing could cheer you up?")
                            .setExpirationThreshold(300)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio9 = new ESM_Radio();
                    esmRadio9.addRadio("None of the time")
                            .addRadio("A little of the time")
                            .addRadio("Some of the time")
                            .addRadio("Most of the time")
                            .addRadio("All of the time")
                            .setTitle("[9 of 10] During the past day, about how often did you feel ...")
                            .setInstructions("... that everything was an effort?")
                            .setExpirationThreshold(300)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio10 = new ESM_Radio();
                    esmRadio10.addRadio("None of the time")
                            .addRadio("A little of the time")
                            .addRadio("Some of the time")
                            .addRadio("Most of the time")
                            .addRadio("All of the time")
                            .setTitle("[10 of 10] During the past day, about how often did you feel ...")
                            .setInstructions("... worthless?")
                            .setExpirationThreshold(300)
                            .setSubmitButton("NEXT");

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
                    esmString = array.toString();
                    Log.d("esmString", esmString);

                    //Queue them
//                    ESM.queueESM(getContext(), factory.build());

                    //Sample: Define the ESM to be displayed
//                    String esmString = "[{'esm':{'esm_type':"+ ESM.TYPE_ESM_TEXT+",'esm_title':'ESM Freetext','esm_instructions':'The user can answer an open ended question.','esm_submit':'Next','esm_expiration_threshold':60,'esm_trigger':'AWARE Tester'}}]";

                    //Queue the ESM to be displayed when possible
                    Intent esm = new Intent(ESM.ACTION_AWARE_QUEUE_ESM);
                    esm.putExtra(ESM.EXTRA_ESM, esmString);
                    context.sendBroadcast(esm);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        startButtonLubben =  (Button) rootView.findViewById(R.id.bt_start3);

        startButtonLubben.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                //*** http://www.awareframework.com/esm/ ***/
                try {
//                    ESMFactory factory = new ESMFactory();
                    Aware.setSetting(context, Aware_Preferences.STATUS_ESM, true);

                    //define ESM question
                    ESM_Checkbox esmCheckbox1 = new ESM_Checkbox();
                    esmCheckbox1.addCheck("HAPPY")
                            .addCheck("SAD")
                            .addCheck("DEPRESSED")
                            .addCheck("EXCITED")
                            .addCheck("ANXIOUS")
                            .addCheck("SATISFIED")
                            .setTrigger("AWARE Tester")
                            .setTitle("Daily Survey 1")
                            .setExpirationThreshold(300)
                            .setSubmitButton("NEXT")
                            .setInstructions("WHAT IS YOUR EMOTION TODAY?");

                    ESM_Radio esmRadio1 = new ESM_Radio();
                    esmRadio1.addRadio("None")
                            .addRadio("One")
                            .addRadio("Two")
                            .addRadio("Three or Four")
                            .addRadio("Five through Eight")
                            .addRadio("Nine or more")
                            .setTitle("[1 of 6] FAMILY: Considering the people to whom you are related by birth, marriage, adoption, etc...")
                            .setInstructions("... How many relatives do you see or hear from at least once a month?")
                            .setExpirationThreshold(300)
                            .setReplaceQueue(true)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio2 = new ESM_Radio();
                    esmRadio2.addRadio("None")
                            .addRadio("One")
                            .addRadio("Two")
                            .addRadio("Three or Four")
                            .addRadio("Five through Eight")
                            .addRadio("Nine or more")
                            .setTitle("[2 of 6] FAMILY: Considering the people to whom you are related by birth, marriage, adoption, etc...")
                            .setInstructions("... How many relatives do you feel at ease with that you can talk about private matters?")
                            .setExpirationThreshold(300)
                            .setReplaceQueue(true)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio3 = new ESM_Radio();
                    esmRadio3.addRadio("None")
                            .addRadio("One")
                            .addRadio("Two")
                            .addRadio("Three or Four")
                            .addRadio("Five through Eight")
                            .addRadio("Nine or more")
                            .setTitle("[3 of 6] FAMILY: Considering the people to whom you are related by birth, marriage, adoption, etc...")
                            .setInstructions("... How many relatives do you feel close to such that you could call on them for help?")
                            .setExpirationThreshold(300)
                            .setReplaceQueue(true)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio4 = new ESM_Radio();
                    esmRadio4.addRadio("None")
                            .addRadio("One")
                            .addRadio("Two")
                            .addRadio("Three or Four")
                            .addRadio("Five through Eight")
                            .addRadio("Nine or more")
                            .setTitle("[4 of 6] FRIENDSHIPS: Considering all of your friends including those who live in your neighborhood...")
                            .setInstructions("... How many of your friends do you see or hear from at least once a month?")
                            .setExpirationThreshold(300)
                            .setReplaceQueue(true)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio5 = new ESM_Radio();
                    esmRadio5.addRadio("None")
                            .addRadio("One")
                            .addRadio("Two")
                            .addRadio("Three or Four")
                            .addRadio("Five through Eight")
                            .addRadio("Nine or more")
                            .setTitle("[5 of 6] FRIENDSHIPS: Considering all of your friends including those who live in your neighborhood...")
                            .setInstructions("... How many friends do you feel at ease with that you can talk about private matters?")
                            .setExpirationThreshold(300)
                            .setReplaceQueue(true)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio6 = new ESM_Radio();
                    esmRadio6.addRadio("None")
                            .addRadio("One")
                            .addRadio("Two")
                            .addRadio("Three or Four")
                            .addRadio("Five through Eight")
                            .addRadio("Nine or more")
                            .setTitle("[6 of 6] FRIENDSHIPS: Considering all of your friends including those who live in your neighborhood...")
                            .setInstructions("... How many friends do you feel close to such that you could call on them for help?")
                            .setExpirationThreshold(300)
                            .setReplaceQueue(true)
                            .setSubmitButton("NEXT");


                    JSONArray array = new JSONArray();
                    array.put(esmRadio1.build());
                    array.put(esmRadio2.build());
                    array.put(esmRadio3.build());
                    array.put(esmRadio4.build());
                    array.put(esmRadio5.build());
                    array.put(esmRadio6.build());
                    esmString = array.toString();
                    Log.d("esmString", esmString);

                    //Queue them
//                    ESM.queueESM(getContext(), factory.build());

                    //Sample: Define the ESM to be displayed
//                    String esmString = "[{'esm':{'esm_type':"+ ESM.TYPE_ESM_TEXT+",'esm_title':'ESM Freetext','esm_instructions':'The user can answer an open ended question.','esm_submit':'Next','esm_expiration_threshold':60,'esm_trigger':'AWARE Tester'}}]";

                    //Queue the ESM to be displayed when possible
                    Intent esm = new Intent(ESM.ACTION_AWARE_QUEUE_ESM);
                    esm.putExtra(ESM.EXTRA_ESM, esmString);
                    context.sendBroadcast(esm);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        startButtonSFS =  (Button) rootView.findViewById(R.id.bt_start4);

        startButtonSFS.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                //*** http://www.awareframework.com/esm/ ***/
                try {
//                    ESMFactory factory = new ESMFactory();
                    Aware.setSetting(context, Aware_Preferences.STATUS_ESM, true);

                    //define ESM question
                    ESM_Checkbox esmCheckbox1 = new ESM_Checkbox();
                    esmCheckbox1.addCheck("HAPPY")
                            .addCheck("SAD")
                            .addCheck("DEPRESSED")
                            .addCheck("EXCITED")
                            .addCheck("ANXIOUS")
                            .addCheck("SATISFIED")
                            .setTrigger("AWARE Tester")
                            .setTitle("Daily Survey 1")
                            .setExpirationThreshold(300)
                            .setSubmitButton("NEXT")
                            .setInstructions("WHAT IS YOUR EMOTION TODAY?");

                    ESM_Radio esmRadio1 = new ESM_Radio();
                    esmRadio1.addRadio("Almost Never")
                            .addRadio("Rarely")
                            .addRadio("Sometimes")
                            .addRadio("Often")
                            .setTitle("[1 of 15] How often do you leave the house (for any reason)?")
                            .setExpirationThreshold(300)
                            .setReplaceQueue(true)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio2 = new ESM_Radio();
                    esmRadio2.addRadio("Avoid them")
                            .addRadio("Feel nervous")
                            .addRadio("Accept them")
                            .addRadio("Like them")
                            .setTitle("[2 of 15] How do you react to the presence of strangers?")
                            .setExpirationThreshold(300)
                            .setReplaceQueue(true)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio3 = new ESM_Radio();
                    esmRadio3.addRadio("Very easy")
                            .addRadio("Quite easy")
                            .addRadio("Average")
                            .addRadio("Quite difficult")
                            .addRadio("Very difficult")
                            .setTitle("[3 of 15] How easy or difficult do you find it talking to people?")
                            .setExpirationThreshold(300)
                            .setReplaceQueue(true)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio4 = new ESM_Radio();
                    esmRadio4.addRadio("Never")
                            .addRadio("Rarely (<=1/week)")
                            .addRadio("Sometimes (> 1/week)")
                            .addRadio("Often (Daily)")
                            .setTitle("[4 of 15] Activities: How often you have done the following...")
                            .setInstructions("... Washing pots, tidying up etc.?")
                            .setExpirationThreshold(300)
                            .setReplaceQueue(true)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio5 = new ESM_Radio();
                    esmRadio5.addRadio("Never")
                            .addRadio("Rarely (<=1/month)")
                            .addRadio("Sometimes (1/ 2 weeks)")
                            .addRadio("Often (1/week)")
                            .setTitle("[5 of 15] Activities: How often you have done the following...")
                            .setInstructions("... Washing own clothes?")
                            .setExpirationThreshold(300)
                            .setReplaceQueue(true)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio6 = new ESM_Radio();
                    esmRadio6.addRadio("Never")
                            .addRadio("Rarely (<=1/week)")
                            .addRadio("Sometimes (> 1/week)")
                            .addRadio("Often (Daily)")
                            .setTitle("[6 of 15] Activities: How often you have done the following...")
                            .setInstructions("... Looking for a job/working?")
                            .setExpirationThreshold(300)
                            .setReplaceQueue(true)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio7 = new ESM_Radio();
                    esmRadio7.addRadio("Never")
                            .addRadio("Rarely (<=1/week)")
                            .addRadio("Sometimes (> 1/week)")
                            .addRadio("Often (Daily)")
                            .setTitle("[7 of 15] Activities: How often you have done the following...")
                            .setInstructions("... Preparing and cooking a meal?")
                            .setExpirationThreshold(300)
                            .setReplaceQueue(true)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio8 = new ESM_Radio();
                    esmRadio8.addRadio("Never")
                            .addRadio("Rarely (<=1/month)")
                            .addRadio("Sometimes (> 1/month)")
                            .addRadio("Often (1/week)")
                            .setTitle("[8 of 15] Activities: How often you have done the following...")
                            .setInstructions("... Using money to purchase something?")
                            .setExpirationThreshold(300)
                            .setReplaceQueue(true)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio9 = new ESM_Radio();
                    esmRadio9.addRadio("Never")
                            .addRadio("Rarely (<=1/week)")
                            .addRadio("Sometimes (> 1/week)")
                            .addRadio("Often (Daily)")
                            .setTitle("[9 of 15] Activities: How often you have done the following...")
                            .setInstructions("... Budgeting and planning expenses (e.g.,\n" +
                                    "do you budget your daily expenses?)?")
                            .setExpirationThreshold(300)
                            .setReplaceQueue(true)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio10 = new ESM_Radio();
                    esmRadio10.addRadio("Never")
                            .addRadio("Rarely (<=1/month)")
                            .addRadio("Sometimes (> 1/month)")
                            .addRadio("Often (1/week)")
                            .setTitle("[10 of 15] Activities: How often you have done the following...")
                            .setInstructions("... Fixing things (car, bike, household etc.)?")
                            .setExpirationThreshold(300)
                            .setReplaceQueue(true)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio11 = new ESM_Radio();
                    esmRadio11.addRadio("Never")
                            .addRadio("Rarely (1 / 3 months)")
                            .addRadio("Sometimes (> 1 / 3 months)")
                            .addRadio("Often (1 / month)")
                            .setTitle("[11 of 15] Activities: How often you have done the following...")
                            .setInstructions("... Visiting an exhibition / fair?")
                            .setExpirationThreshold(300)
                            .setReplaceQueue(true)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio12 = new ESM_Radio();
                    esmRadio12.addRadio("Never")
                            .addRadio("Rarely (1 / 3 months)")
                            .addRadio("Sometimes (> 1 / 3 months)")
                            .addRadio("Often (1 / month)")
                            .setTitle("[12 of 15] Activities: How often you have done the following...")
                            .setInstructions("... Going to parties?")
                            .setExpirationThreshold(300)
                            .setReplaceQueue(true)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio13 = new ESM_Radio();
                    esmRadio13.addRadio("Never")
                            .addRadio("Rarely (1 / 3 months)")
                            .addRadio("Sometimes (> 1 / 3 months)")
                            .addRadio("Often (1 / month)")
                            .setTitle("[13 of 15] Activities: How often you have done the following...")
                            .setInstructions("... Going to a dance / night club?")
                            .setExpirationThreshold(300)
                            .setReplaceQueue(true)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio14 = new ESM_Radio();
                    esmRadio14.addRadio("Never")
                            .addRadio("Rarely (> 1 / 3 months)")
                            .addRadio("Sometimes (> 1 / month)")
                            .addRadio("Often (> 1 / week)")
                            .setTitle("[14 of 15] Activities: How often you have done the following...")
                            .setInstructions("... Participating in church /temple activity")
                            .setExpirationThreshold(300)
                            .setReplaceQueue(true)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio15 = new ESM_Radio();
                    esmRadio15.addRadio("Yes")
                            .addRadio("No")
                            .setTitle("[15 of 15] Have you been determined to be disabled?")
                            .setExpirationThreshold(300)
                            .setReplaceQueue(true)
                            .setSubmitButton("NEXT");

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
                    esmString = array.toString();
                    Log.d("esmString", esmString);

                    //Queue them
//                    ESM.queueESM(getContext(), factory.build());

                    //Sample: Define the ESM to be displayed
//                    String esmString = "[{'esm':{'esm_type':"+ ESM.TYPE_ESM_TEXT+",'esm_title':'ESM Freetext','esm_instructions':'The user can answer an open ended question.','esm_submit':'Next','esm_expiration_threshold':60,'esm_trigger':'AWARE Tester'}}]";

                    //Queue the ESM to be displayed when possible
                    Intent esm = new Intent(ESM.ACTION_AWARE_QUEUE_ESM);
                    esm.putExtra(ESM.EXTRA_ESM, esmString);
                    context.sendBroadcast(esm);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        startButtonInsomnia =  (Button) rootView.findViewById(R.id.bt_start7);

        startButtonInsomnia.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                //*** http://www.awareframework.com/esm/ ***/
                try {
//                    ESMFactory factory = new ESMFactory();
                    Aware.setSetting(context, Aware_Preferences.STATUS_ESM, true);

                    //define ESM question

                    ESM_Radio esmRadio1 = new ESM_Radio();
                    esmRadio1.addRadio("None")
                            .addRadio("Mild")
                            .addRadio("Moderate")
                            .addRadio("Severe")
                            .addRadio("Very Severe")
                            .setTitle("[1 of 3] Please rate the CURRENT (i.e., Last Week) severity of these insomnia problems ...")
                            .setInstructions("... Difficulty falling asleep?")
                            .setExpirationThreshold(300)
                            .setReplaceQueue(true)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio2 = new ESM_Radio();
                    esmRadio2.addRadio("None")
                            .addRadio("Mild")
                            .addRadio("Moderate")
                            .addRadio("Severe")
                            .addRadio("Very Severe")
                            .setTitle("[2 of 3] Please rate the CURRENT (i.e., Last Week) severity of these insomnia problems ...")
                            .setInstructions("... Difficulty staying asleep?")
                            .setExpirationThreshold(300)
                            .setReplaceQueue(true)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio3 = new ESM_Radio();
                    esmRadio3.addRadio("None")
                            .addRadio("Mild")
                            .addRadio("Moderate")
                            .addRadio("Severe")
                            .addRadio("Very Severe")
                            .setTitle("[1 of 3] Please rate the CURRENT (i.e., Last Week) severity of these insomnia problems ...")
                            .setInstructions("... Problems waking up too early?")
                            .setExpirationThreshold(300)
                            .setReplaceQueue(true)
                            .setSubmitButton("NEXT");

                    JSONArray array = new JSONArray();
                    array.put(esmRadio1.build());
                    array.put(esmRadio2.build());
                    array.put(esmRadio3.build());

                    esmString = array.toString();
                    Log.d("esmString", esmString);

                    //Queue them
//                    ESM.queueESM(getContext(), factory.build());

                    //Sample: Define the ESM to be displayed
//                    String esmString = "[{'esm':{'esm_type':"+ ESM.TYPE_ESM_TEXT+",'esm_title':'ESM Freetext','esm_instructions':'The user can answer an open ended question.','esm_submit':'Next','esm_expiration_threshold':60,'esm_trigger':'AWARE Tester'}}]";

                    //Queue the ESM to be displayed when possible
                    Intent esm = new Intent(ESM.ACTION_AWARE_QUEUE_ESM);
                    esm.putExtra(ESM.EXTRA_ESM, esmString);
                    context.sendBroadcast(esm);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        startButtonOFSC =  (Button) rootView.findViewById(R.id.bt_start6);

        startButtonOFSC.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                //*** http://www.awareframework.com/esm/ ***/
                try {
//                    ESMFactory factory = new ESMFactory();
                    Aware.setSetting(context, Aware_Preferences.STATUS_ESM, true);

                    //define ESM question

                    ESM_Radio esmRadio1 = new ESM_Radio();
                    esmRadio1.addRadio("At least once a day")
                            .addRadio("At least once a week")
                            .addRadio("At least once a month")
                            .addRadio("Less than once a month")
                            .addRadio("Not at all")
                            .setTitle("[1 of 4] How often do you do the following ...")
                            .setInstructions("... Visit with someone who does not live with you?")
                            .setExpirationThreshold(300)
                            .setReplaceQueue(true)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio2 = new ESM_Radio();
                    esmRadio2.addRadio("At least once a day")
                            .addRadio("At least once a week")
                            .addRadio("At least once a month")
                            .addRadio("Less than once a month")
                            .addRadio("Not at all")
                            .setTitle("[2of 4] How often do you do the following ...")
                            .setInstructions("... Telephone someone who does not live with you?")
                            .setExpirationThreshold(300)
                            .setReplaceQueue(true)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio3 = new ESM_Radio();
                    esmRadio3.addRadio("At least once a day")
                            .addRadio("At least once a week")
                            .addRadio("At least once a month")
                            .addRadio("Less than once a month")
                            .addRadio("Not at all")
                            .setTitle("[3 of 4] How often do you do the following ...")
                            .setInstructions("... Do something with another person that you planned ahead of time?")
                            .setExpirationThreshold(300)
                            .setReplaceQueue(true)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio4 = new ESM_Radio();
                    esmRadio4.addRadio("At least once a day")
                            .addRadio("At least once a week")
                            .addRadio("At least once a month")
                            .addRadio("Less than once a month")
                            .addRadio("Not at all")
                            .setTitle("[4 of 4] How often do you do the following ...")
                            .setInstructions("... Spend time with someone you consider more than a friend, like a spouse, a boyfriend, or a girlfriend?")
                            .setExpirationThreshold(300)
                            .setReplaceQueue(true)
                            .setSubmitButton("NEXT");

                    JSONArray array = new JSONArray();
                    array.put(esmRadio1.build());
                    array.put(esmRadio2.build());
                    array.put(esmRadio3.build());
                    array.put(esmRadio4.build());


                    esmString = array.toString();
                    Log.d("esmString", esmString);

                    //Queue them
//                    ESM.queueESM(getContext(), factory.build());

                    //Sample: Define the ESM to be displayed
//                    String esmString = "[{'esm':{'esm_type':"+ ESM.TYPE_ESM_TEXT+",'esm_title':'ESM Freetext','esm_instructions':'The user can answer an open ended question.','esm_submit':'Next','esm_expiration_threshold':60,'esm_trigger':'AWARE Tester'}}]";

                    //Queue the ESM to be displayed when possible
                    Intent esm = new Intent(ESM.ACTION_AWARE_QUEUE_ESM);
                    esm.putExtra(ESM.EXTRA_ESM, esmString);
                    context.sendBroadcast(esm);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        startButtonPQSI =  (Button) rootView.findViewById(R.id.bt_start5);

        startButtonPQSI.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                //*** http://www.awareframework.com/esm/ ***/
                try {
//                    ESMFactory factory = new ESMFactory();
                    Aware.setSetting(context, Aware_Preferences.STATUS_ESM, true);

                    //define ESM question

                    ESM_Radio esmRadio1 = new ESM_Radio();
                    esmRadio1.addRadio("Less than 15 minutes")
                            .addRadio("16 to 30 minutes")
                            .addRadio("31 to 60 minutes")
                            .addRadio("More than 60 minutes")
                            .setTitle("[1 of 5] During the past week ...")
                            .setInstructions("... How long (in minutes) does it take you to fall asleep?")
                            .setExpirationThreshold(300)
                            .setReplaceQueue(true)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio2 = new ESM_Radio();
                    esmRadio2.addRadio("More than 7 hours")
                            .addRadio("6 to 7 hours")
                            .addRadio("5 to 6 hours")
                            .addRadio("less than 5 hours")
                            .setTitle("[2of 5] During the past week ...")
                            .setInstructions("... How many hours of actual sleep did you get at night?")
                            .setExpirationThreshold(300)
                            .setReplaceQueue(true)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio3 = new ESM_Radio();
                    esmRadio3.addRadio("Not during the last month")
                            .addRadio("Less than once a week")
                            .addRadio("Once or twice a week")
                            .addRadio("Less than once a month")
                            .addRadio("Three or more times a week")
                            .setTitle("[3 of 5] During the past month, how often have you had trouble sleeping because you cannot get to sleep within 30 minutes?")
                            .setExpirationThreshold(300)
                            .setReplaceQueue(true)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio4 = new ESM_Radio();
                    esmRadio4.addRadio("Not during the last month")
                            .addRadio("Less than once a week")
                            .addRadio("Once or twice a week")
                            .addRadio("Less than once a month")
                            .addRadio("Three or more times a week")
                            .setTitle("[4 of 5] During the past month, how often have you taken medicine (prescribed or 'over the counter') to help you sleep?")
                            .setExpirationThreshold(300)
                            .setReplaceQueue(true)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio5 = new ESM_Radio();
                    esmRadio5.addRadio("Very Good")
                            .addRadio("Fairly Good")
                            .addRadio("Fairly Bad")
                            .addRadio("Very Bad")
                            .setTitle("[5 of 5] During the past month, how would you rate your sleep quality overall?")
                            .setExpirationThreshold(300)
                            .setReplaceQueue(true)
                            .setSubmitButton("NEXT");

                    JSONArray array = new JSONArray();
                    array.put(esmRadio1.build());
                    array.put(esmRadio2.build());
                    array.put(esmRadio3.build());
                    array.put(esmRadio4.build());
                    array.put(esmRadio5.build());



                    esmString = array.toString();
                    Log.d("esmString", esmString);

                    //Queue them
//                    ESM.queueESM(getContext(), factory.build());

                    //Sample: Define the ESM to be displayed
//                    String esmString = "[{'esm':{'esm_type':"+ ESM.TYPE_ESM_TEXT+",'esm_title':'ESM Freetext','esm_instructions':'The user can answer an open ended question.','esm_submit':'Next','esm_expiration_threshold':60,'esm_trigger':'AWARE Tester'}}]";

                    //Queue the ESM to be displayed when possible
                    Intent esm = new Intent(ESM.ACTION_AWARE_QUEUE_ESM);
                    esm.putExtra(ESM.EXTRA_ESM, esmString);
                    context.sendBroadcast(esm);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        startButtonSSRS =  (Button) rootView.findViewById(R.id.bt_start9);

        startButtonSSRS.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                //*** http://www.awareframework.com/esm/ ***/
                try {
//                    ESMFactory factory = new ESMFactory();
                    Aware.setSetting(context, Aware_Preferences.STATUS_ESM, true);

                    //define ESM question

                    ESM_Radio esmRadio1 = new ESM_Radio();
                    esmRadio1.addRadio("Yes")
                            .addRadio("No")
                            .setTitle("[1 of 6] Have you wished you were dead or wished you could go to sleep and not wake up?")
                            .setExpirationThreshold(300)
                            .setReplaceQueue(true)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio2 = new ESM_Radio();
                    esmRadio2.addRadio("Yes")
                            .addRadio("No")
                            .setTitle("[2 of 6] Have you actually had any thoughts of killing yourself?")
                            .setExpirationThreshold(300)
                            .setReplaceQueue(true)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio3 = new ESM_Radio();
                    esmRadio3.addRadio("Yes")
                            .addRadio("No")
                            .setTitle("[3 of 6] If you answered YES to 2, answer this, if not just press NEXT and proceed to Question 6...")
                            .setInstructions("Have you been thinking about how you might do this?")
                            .setExpirationThreshold(300)
                            .setReplaceQueue(true)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio4 = new ESM_Radio();
                    esmRadio4.addRadio("Yes")
                            .addRadio("No")
                            .setTitle("[4 of 6] If you answered YES to 2, answer this, if not just press NEXT and proceed to Question 6...")
                            .setInstructions("Have you had these thoughts and had some intention of acting on them?")
                            .setExpirationThreshold(300)
                            .setReplaceQueue(true)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio5 = new ESM_Radio();
                    esmRadio5.addRadio("Yes")
                            .addRadio("No")
                            .setTitle("[5 of 6] If you answered YES to 2, answer this, if not just press NEXT and proceed to Question 6...")
                            .setInstructions("Have you started to work out, or worked out the details of how to kill yourself? Do you intend to carry out this plan?")
                            .setExpirationThreshold(300)
                            .setReplaceQueue(true)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio6 = new ESM_Radio();
                    esmRadio6.addRadio("Yes")
                            .addRadio("No")
                            .setTitle("[6 of 6] Have you done anything, started to do anything, or prepared to do anything to end your life?")
                            .setExpirationThreshold(300)
                            .setReplaceQueue(true)
                            .setSubmitButton("NEXT");

                    JSONArray array = new JSONArray();
                    array.put(esmRadio1.build());
                    array.put(esmRadio2.build());
                    array.put(esmRadio3.build());
                    array.put(esmRadio4.build());
                    array.put(esmRadio5.build());
                    array.put(esmRadio6.build());




                    esmString = array.toString();
                    Log.d("esmString", esmString);

                    //Queue them
//                    ESM.queueESM(getContext(), factory.build());

                    //Sample: Define the ESM to be displayed
//                    String esmString = "[{'esm':{'esm_type':"+ ESM.TYPE_ESM_TEXT+",'esm_title':'ESM Freetext','esm_instructions':'The user can answer an open ended question.','esm_submit':'Next','esm_expiration_threshold':60,'esm_trigger':'AWARE Tester'}}]";

                    //Queue the ESM to be displayed when possible
                    Intent esm = new Intent(ESM.ACTION_AWARE_QUEUE_ESM);
                    esm.putExtra(ESM.EXTRA_ESM, esmString);
                    context.sendBroadcast(esm);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        startButtonPhysical =  (Button) rootView.findViewById(R.id.bt_start8);

        startButtonPhysical.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                //*** http://www.awareframework.com/esm/ ***/
                try {
//                    ESMFactory factory = new ESMFactory();
                    Aware.setSetting(context, Aware_Preferences.STATUS_ESM, true);

                    //define ESM question

                    ESM_Radio esmRadio1 = new ESM_Radio();
                    esmRadio1.addRadio("1")
                            .addRadio("2")
                            .addRadio("3")
                            .addRadio("4")
                            .addRadio("5")
                            .addRadio("6")
                            .addRadio("7")
                            .setTitle("[1 of 7] During the last 7 DAYS, how many days did you do VIGOROUS physical activities like heavy lifting, aerobics, or fast bicycling?")
                            .setExpirationThreshold(300)
                            .setReplaceQueue(true)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio2 = new ESM_Radio();
                    esmRadio2.addRadio("Less than 15")
                            .addRadio("Between 15 and 30")
                            .addRadio("Between 30 and 45")
                            .addRadio("Between 45 and 60")
                            .addRadio("More than 1 hour")
                            .setTitle("[2 of 7] On days of intense physical activity how many MINUTES do you spend on them?")
                            .setExpirationThreshold(300)
                            .setReplaceQueue(true)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio3 = new ESM_Radio();
                    esmRadio3.addRadio("1")
                            .addRadio("2")
                            .addRadio("3")
                            .addRadio("4")
                            .addRadio("5")
                            .addRadio("6")
                            .addRadio("7")
                            .setTitle("[3 of 7] During the last 7 DAYS, how many days did you do MODERATE physical activities like carrying light loads, Doubles Tennis, or regular bicycling?")
                            .setExpirationThreshold(300)
                            .setReplaceQueue(true)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio4 = new ESM_Radio();
                    esmRadio4.addRadio("Less than 15")
                            .addRadio("Between 15 and 30")
                            .addRadio("Between 30 and 45")
                            .addRadio("Between 45 and 60")
                            .addRadio("More than 1 hour")
                            .setTitle("[4 of 7] On days of moderate physical activity how many MINUTES do you spend on them?")
                            .setExpirationThreshold(300)
                            .setReplaceQueue(true)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio5 = new ESM_Radio();
                    esmRadio5.addRadio("1")
                            .addRadio("2")
                            .addRadio("3")
                            .addRadio("4")
                            .addRadio("5")
                            .addRadio("6")
                            .addRadio("7")
                            .setTitle("[5 of 7] During the last 7 DAYS, how many days did you walk for at LEAST 10 MINUTES?")
                            .setExpirationThreshold(300)
                            .setReplaceQueue(true)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio6 = new ESM_Radio();
                    esmRadio6.addRadio("Less than 30 minutes")
                            .addRadio("Between 30 minutes and 1 hour")
                            .addRadio("Between 1 hour and 2 hours")
                            .addRadio("More than 2 hours")
                            .setTitle("[6 of 7] How much time do you spend walking a day?")
                            .setExpirationThreshold(300)
                            .setReplaceQueue(true)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio7 = new ESM_Radio();
                    esmRadio7.addRadio("Less than 1 hour")
                            .addRadio("Between 1 and 3 hours")
                            .addRadio("Between 3 hours and 5 hours")
                            .addRadio("More than 5 hours")
                            .setTitle("[7 of 7] How much time do you spend SITTING a day?")
                            .setExpirationThreshold(300)
                            .setReplaceQueue(true)
                            .setSubmitButton("NEXT");

                    JSONArray array = new JSONArray();
                    array.put(esmRadio1.build());
                    array.put(esmRadio2.build());
                    array.put(esmRadio3.build());
                    array.put(esmRadio4.build());
                    array.put(esmRadio5.build());
                    array.put(esmRadio6.build());
                    array.put(esmRadio7.build());
                    

                    esmString = array.toString();
                    Log.d("esmString", esmString);

                    //Queue them
//                    ESM.queueESM(getContext(), factory.build());

                    //Sample: Define the ESM to be displayed
//                    String esmString = "[{'esm':{'esm_type':"+ ESM.TYPE_ESM_TEXT+",'esm_title':'ESM Freetext','esm_instructions':'The user can answer an open ended question.','esm_submit':'Next','esm_expiration_threshold':60,'esm_trigger':'AWARE Tester'}}]";

                    //Queue the ESM to be displayed when possible
                    Intent esm = new Intent(ESM.ACTION_AWARE_QUEUE_ESM);
                    esm.putExtra(ESM.EXTRA_ESM, esmString);
                    context.sendBroadcast(esm);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        updateCompletedTextVisibility(rootView);

        return rootView;
    }


    public void onBackPressed() {
        onPause();
        context.onBackPressed();
    }

    private boolean hasFilledSurveyToday() {
        Aware.setSetting(context, Aware_Preferences.STATUS_ESM, true);
        Cursor survey_data = context.getContentResolver().query(ESM_Provider.ESM_Data.CONTENT_URI, null, null, null, "timestamp DESC");
        boolean isEqual = false;

        // Doing it this way to make sure we dont only check the subject id, which is an ESM also
        if (survey_data.getCount() > 1) {
            survey_data.moveToFirst();
            survey_data.moveToNext();
            float latest_timestamp = survey_data.getFloat(survey_data.getColumnIndex("timestamp"));
            String device_ID = survey_data.getString(survey_data.getColumnIndex("device_id"));
            if (!survey_data.isClosed()) survey_data.close();

            Calendar cal_today = Calendar.getInstance();
            cal_today.setTimeInMillis(System.currentTimeMillis());
//            if (device_ID.equals(Aware_Preferences.DEVICE_ID)) {
                Calendar cal_survey = Calendar.getInstance();
                cal_survey.setTimeInMillis((long)latest_timestamp);

                isEqual = (cal_today.get(Calendar.YEAR) == cal_survey.get(Calendar.YEAR))
                        && (cal_today.get(Calendar.MONTH) == cal_survey.get(Calendar.MONTH))
                        && (cal_today.get(Calendar.DAY_OF_MONTH) == cal_survey.get(Calendar.DAY_OF_MONTH));
//            } else {
//                isEqual = false;
//            }



        } else {
            //Toast.makeText(context,"Survey data is not obtained yet.", Toast.LENGTH_LONG).show();
        }

//        survey_data.close();
        return isEqual;
    }

    private void updateCompletedTextVisibility(View rootView) {
        TextView alreadyCompletedText = rootView.findViewById(R.id.text_completed);
        ImageView alreadyCompletedImg = rootView.findViewById(R.id.img_completed);
        if (hasFilledSurveyToday()) {
            alreadyCompletedText.setVisibility(View.VISIBLE);
            alreadyCompletedImg.setVisibility(View.VISIBLE);
        } else {
            alreadyCompletedText.setVisibility(View.GONE);
            alreadyCompletedImg.setVisibility(View.GONE);
        }
    }

}
