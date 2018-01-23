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
                            .setExpirationThreshold(60)
                            .setSubmitButton("NEXT")
                            .setInstructions("WHAT IS YOUR EMOTION TODAY?");

                    ESM_Radio esmRadio1 = new ESM_Radio();
                    esmRadio1.addRadio("Insomnia")
                            .addRadio("Restless")
                            .addRadio("Neutral")
                            .addRadio("Good")
                            .addRadio("Very Good")
                            .setTitle("Daily Survey 2")
                            .setInstructions("HOW WAS YOUR SLEEP LAST NIGHT?")
                            .setExpirationThreshold(60)
                            .setSubmitButton("DONE");

                    JSONArray array = new JSONArray();
                    array.put(esmCheckbox1.build());
                    array.put(esmRadio1.build());
                    esmString = array.toString();
                    Log.d("esmString", esmString);

//                    //add them to the factory
//                    factory.addESM(esmCheckbox1);
//                    factory.addESM(esmRadio1);
//
//                    //Queue them
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
