package edu.ucla.cs.er.depressionstudy;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.aware.Aware;
import com.aware.Aware_Preferences;
import com.aware.ESM;
import com.aware.ui.esms.ESM_Checkbox;
import com.aware.ui.esms.ESM_Radio;

import org.json.JSONArray;
import org.json.JSONException;


/**
 * A simple {@link Fragment} subclass.
 */
public class SurveyFragment extends Fragment {

    private Button startButton;
    private Activity context;
    private String esmString;

    public SurveyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_survey, container, false);
        context = getActivity();

        startButton =  (Button) rootView.findViewById(R.id.bt_start);
//        setState(STATE_DEFAULT);
        startButton.setOnClickListener(new Button.OnClickListener(){
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
                            .setTitle("During the past day, about how often did you feel ...")
                            .setInstructions("... tired out for no good reason?")
                            .setExpirationThreshold(300)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio2 = new ESM_Radio();
                    esmRadio2.addRadio("None of the time")
                            .addRadio("A little of the time")
                            .addRadio("Some of the time")
                            .addRadio("Most of the time")
                            .addRadio("All of the time")
                            .setTitle("During the past day, about how often did you feel ...")
                            .setInstructions("... nervous?")
                            .setExpirationThreshold(300)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio3 = new ESM_Radio();
                    esmRadio3.addRadio("None of the time")
                            .addRadio("A little of the time")
                            .addRadio("Some of the time")
                            .addRadio("Most of the time")
                            .addRadio("All of the time")
                            .setTitle("During the past day, about how often did you feel ...")
                            .setInstructions("... so nervous that nothing could calm you down?")
                            .setExpirationThreshold(300)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio4 = new ESM_Radio();
                    esmRadio4.addRadio("None of the time")
                            .addRadio("A little of the time")
                            .addRadio("Some of the time")
                            .addRadio("Most of the time")
                            .addRadio("All of the time")
                            .setTitle("During the past day, about how often did you feel ...")
                            .setInstructions("... hopeless?")
                            .setExpirationThreshold(60)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio5 = new ESM_Radio();
                    esmRadio5.addRadio("None of the time")
                            .addRadio("A little of the time")
                            .addRadio("Some of the time")
                            .addRadio("Most of the time")
                            .addRadio("All of the time")
                            .setTitle("During the past day, about how often did you feel ...")
                            .setInstructions("... restless or fidgety?")
                            .setExpirationThreshold(300)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio6 = new ESM_Radio();
                    esmRadio6.addRadio("None of the time")
                            .addRadio("A little of the time")
                            .addRadio("Some of the time")
                            .addRadio("Most of the time")
                            .addRadio("All of the time")
                            .setTitle("During the past day, about how often did you feel ...")
                            .setInstructions("... so restless that you could not sit still?")
                            .setExpirationThreshold(300)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio7 = new ESM_Radio();
                    esmRadio7.addRadio("None of the time")
                            .addRadio("A little of the time")
                            .addRadio("Some of the time")
                            .addRadio("Most of the time")
                            .addRadio("All of the time")
                            .setTitle("During the past day, about how often did you feel ...")
                            .setInstructions("... depressed?")
                            .setExpirationThreshold(300)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio8 = new ESM_Radio();
                    esmRadio8.addRadio("None of the time")
                            .addRadio("A little of the time")
                            .addRadio("Some of the time")
                            .addRadio("Most of the time")
                            .addRadio("All of the time")
                            .setTitle("During the past day, about how often did you feel ...")
                            .setInstructions("... so depressed that nothing could cheer you up?")
                            .setExpirationThreshold(300)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio9 = new ESM_Radio();
                    esmRadio9.addRadio("None of the time")
                            .addRadio("A little of the time")
                            .addRadio("Some of the time")
                            .addRadio("Most of the time")
                            .addRadio("All of the time")
                            .setTitle("During the past day, about how often did you feel ...")
                            .setInstructions("... that everything was an effort?")
                            .setExpirationThreshold(300)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio10 = new ESM_Radio();
                    esmRadio10.addRadio("None of the time")
                            .addRadio("A little of the time")
                            .addRadio("Some of the time")
                            .addRadio("Most of the time")
                            .addRadio("All of the time")
                            .setTitle("During the past day, about how often did you feel ...")
                            .setInstructions("... worthless?")
                            .setExpirationThreshold(300)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio11 = new ESM_Radio();
                    esmRadio11.addRadio("A lot less often than usual")
                            .addRadio("Some less often than usual")
                            .addRadio("A little less often than usual")
                            .addRadio("About the same as usual")
                            .addRadio("A little more often than usual")
                            .addRadio("Some more often than usual")
                            .addRadio("A lot more often than usual")
                            .setTitle("The last ten questions asked about feelings that might have occurred during the past day. Taking them altogether, did these feelings occur less often in the past day than is usual for you, about the same as usual, or more often than usual? (If you never have any of these feelings, select response option 4.)")
                            .setExpirationThreshold(300)
                            .setSubmitButton("NEXT");

                    ESM_Radio esmRadio12 = new ESM_Radio();
                    esmRadio12.addRadio("None of the time")
                            .addRadio("A little of the time")
                            .addRadio("Some of the time")
                            .addRadio("Most of the time")
                            .addRadio("All of the time")
                            .setTitle("During the past day, how often have physical health problems been the main cause of these feelings?")
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
                    array.put(esmRadio11.build());
                    array.put(esmRadio12.build());
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

        return rootView;
    }

}
