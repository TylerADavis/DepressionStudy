//package edu.ucla.cs.er.depressionstudy;
//
//
//import android.app.Activity;
//import android.content.Intent;
//import android.database.Cursor;
//import android.os.Bundle;
//import androidx.fragment.app.Fragment;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.aware.Aware;
//import com.aware.Aware_Preferences;
//import com.aware.ESM;
//import com.aware.providers.ESM_Provider;
//import com.aware.ui.ESM_Queue;
//import com.aware.ui.esms.ESMFactory;
//import com.aware.ui.esms.ESM_Checkbox;
//import com.aware.ui.esms.ESM_Number;
//import com.aware.ui.esms.ESM_PAM;
//import com.aware.ui.esms.ESM_QuickAnswer;
//import com.aware.ui.esms.ESM_Radio;
//import com.quickbirdstudios.surveykit.steps.InstructionStep;
//import com.quickbirdstudios.surveykit.survey.SurveyView;
//
//
//import org.json.JSONArray;
//import org.json.JSONException;
//
//import java.util.Calendar;
//
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class SurveyFragment extends Fragment {
//
//    private Button startButtonSurvey;
//    private Activity context;
//    private String esmString;
//
//    public SurveyFragment() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public void onResume() {
//        super.onResume();
//
//        //updateCompletedTextVisibility(this.getView());
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View rootView = inflater.inflate(R.layout.fragment_survey, container, false);
//        context = getActivity();
//
//        SurveyView surveyView = rootView.findViewById(R.id.survey_view);
//
//
////        startButtonSurvey = (Button) rootView.findViewById(R.id.bt_start_survey);
////        startButtonSurvey.setOnClickListener(new Button.OnClickListener() {
////            public void onClick(View v) {
////                try {
////                    ESMFactory factory = new ESMFactory();
////
////                    ESM_QuickAnswer thank_you = new ESM_QuickAnswer();
////                    thank_you.addQuickAnswer("Close")
////                            .setInstructions("Thank you for filling the survey!")
////                            .setTitle("Survey");
////
////                    ESM_QuickAnswer sbq4 = new ESM_QuickAnswer();
////                    sbq4.addQuickAnswer("0-4 times")
////                            .addQuickAnswer("≥5 times")
////                            .setInstructions("In the last 3 months, how many times did you have insertive anal sex (you were the top) with a man who is HIV-positive when you did not use a condom?")
////                            .addFlow("0-4 times", thank_you.build())
////                            .addFlow("≥5 times", thank_you.build())
////                            .setTitle("Survey");
////
////                    ESM_QuickAnswer sbq3 = new ESM_QuickAnswer();
////                    sbq3.addQuickAnswer(">1 time")
////                            .addQuickAnswer("1 time")
////                            .addQuickAnswer("0 times")
////                            .addQuickAnswer("Don't know")
////                            .setInstructions("In the last 3 months, how many of your male sex partners were HIV-positive")
////                            .addFlow(">1 time", sbq4.build())
////                            .addFlow("1 time", sbq4.build())
////                            .setTitle("Survey");
////
////                    ESM_QuickAnswer sbq2 = new ESM_QuickAnswer();
////                    sbq2.addQuickAnswer("≥1 time")
////                            .addQuickAnswer("0 times")
////                            .setInstructions("In the last 3 months, how many times did you have receptive anal sex (you were the bottom) with a man when he did not use a condom?")
////                            .addFlow("≥1 time", sbq3.build())
////                            .addFlow("0 time", sbq3.build())
////                            .setTitle("Survey");
////
////                    ESM_QuickAnswer sbq1 = new ESM_QuickAnswer();
////                    sbq1.addQuickAnswer(">10 male partners")
////                            .addQuickAnswer("6-10 male partners")
////                            .addQuickAnswer("1-5 male partners")
////                            .addQuickAnswer("0 male partners")
////                            .setInstructions("In the last 3 months, how many men have you had sex with?")
////                            .addFlow(">10 male partners", sbq2.build())
////                            .addFlow("6-10 male partners", sbq2.build())
////                            .addFlow("1-5 male partners", sbq2.build())
////                            .setTitle("Survey");
////
////                    ESM_QuickAnswer pwid6 = new ESM_QuickAnswer();
////                    pwid6.addQuickAnswer("Yes")
////                            .addQuickAnswer("No")
////                            .setInstructions("In the past 3 months, did you visit a shooting gallery?")
////                            .addFlow("Yes", sbq1.build())
////                            .addFlow("No", sbq1.build())
////                            .setTitle("Survey");
////
////                    ESM_QuickAnswer pwid5 = new ESM_QuickAnswer();
////                    pwid5.addQuickAnswer("Yes")
////                            .addQuickAnswer("No")
////                            .setInstructions("In the past 3 months, did you share a cooker?")
////                            .addFlow("Yes", pwid6.build())
////                            .addFlow("No", pwid6.build())
////                            .setTitle("Survey");
////
////                    ESM_QuickAnswer pwid4 = new ESM_QuickAnswer();
////                    pwid4.addQuickAnswer("Yes")
////                            .addQuickAnswer("No")
////                            .setInstructions("In the past 3 months, did you use needles?")
////                            .addFlow("Yes", pwid5.build())
////                            .addFlow("No", pwid5.build())
////                            .setTitle("Survey");
////
////                    ESM_QuickAnswer pwid3 = new ESM_QuickAnswer();
////                    pwid3.addQuickAnswer("Yes")
////                            .addQuickAnswer("No")
////                            .setInstructions("In the past 3 months, did you inject heroin?")
////                            .addFlow("Yes", pwid4.build())
////                            .addFlow("No", pwid4.build())
////                            .setTitle("Survey");
////
////                    ESM_QuickAnswer pwid2 = new ESM_QuickAnswer();
////                    pwid2.addQuickAnswer("Yes")
////                            .addQuickAnswer("No")
////                            .setInstructions("In the past 3 months, did you inject cocaine?")
////                            .addFlow("Yes", pwid3.build())
////                            .addFlow("No", pwid3.build())
////                            .setTitle("Survey");
////
////                    ESM_QuickAnswer pwid1 = new ESM_QuickAnswer();
////                    pwid1.addQuickAnswer("Yes")
////                            .addQuickAnswer("No")
////                            .setInstructions("In the past 3 months, were you in a methadone maintenance program?")
////                            .addFlow("Yes", pwid2.build())
////                            .addFlow("No", pwid2.build())
////                            .setTitle("Survey");
////
////                    ESM_QuickAnswer su1 = new ESM_QuickAnswer();
////                    su1.addQuickAnswer("Yes, methamphetamines")
////                            .addQuickAnswer("Yes, injectable drugs not prescribed")
////                            .addQuickAnswer("Yes, used both")
////                            .addQuickAnswer("Neither")
////                            .setInstructions("In the past 3 months, have you used substances such as crystal meth or injectable drugs not prescribed to you by a physician?")
////                            .addFlow("Yes, methamphetamines", sbq1.build())
////                            .addFlow("Neither", sbq1.build())
////                            .addFlow("Yes, injectable drugs not prescribed", pwid1.build())
////                            .addFlow("Yes, used both", pwid1.build())
////                            .setTitle("Survey");
////
////                    ESM_Number ageq = new ESM_Number();
////                    ageq.setTitle("Survey")
////                            .setInstructions("How old are you today?")
////                            .setSubmitButton("Next")
////                            .setExpirationThreshold(3600)
////                            .setNotificationTimeout(0)
////                            .setReplaceQueue(true);
////
////                    factory.addESM(ageq);
////                    factory.addESM(su1);
////                    //factory.addESM(sbq1);
////
////                    //Queue them
////                    //ESM.queueESM(context, factory.build());
////                    /*Intent esm = new Intent(ESM.ACTION_AWARE_QUEUE_ESM);
////                    esm.putExtra(ESM.EXTRA_ESM, factory.build());
////                    context.sendBroadcast(esm);*/
////                    ESM.queueESM(getContext(), factory.build());
////
////                    /*Intent intent_ESM = new Intent(context, ESM_Queue.class);
////                    intent_ESM.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                    context.startActivity(intent_ESM);*/
////                } catch (JSONException e) {
////                    e.printStackTrace();
////                }
////            }
////        });
//
//        //updateCompletedTextVisibility(rootView);
//
//        return rootView;
//    }
//
//    private boolean hasFilledSurveyToday() {
//        Aware.setSetting(context, Aware_Preferences.STATUS_ESM, true);
//        Cursor survey_data = context.getContentResolver().query(ESM_Provider.ESM_Data.CONTENT_URI, null, null, null, "timestamp DESC");
//        boolean isEqual = false;
//
//        // Doing it this way to make sure we dont only check the subject id, which is an ESM also
//        if (survey_data.getCount() > 1) {
//            survey_data.moveToFirst();
//            survey_data.moveToNext();
//            float latest_timestamp = survey_data.getFloat(survey_data.getColumnIndex("timestamp"));
//            if (!survey_data.isClosed()) survey_data.close();
//
//            Calendar cal_today = Calendar.getInstance();
//            cal_today.setTimeInMillis(System.currentTimeMillis());
//            Calendar cal_survey = Calendar.getInstance();
//            cal_survey.setTimeInMillis((long)latest_timestamp);
//
//            isEqual = (cal_today.get(Calendar.YEAR) == cal_survey.get(Calendar.YEAR))
//                    && (cal_today.get(Calendar.MONTH) == cal_survey.get(Calendar.MONTH))
//                    && (cal_today.get(Calendar.DAY_OF_MONTH) == cal_survey.get(Calendar.DAY_OF_MONTH));
//        }
//
//        return isEqual;
//    }
//
//    private void updateCompletedTextVisibility(View rootView) {
//        /*TextView alreadyCompletedText = rootView.findViewById(R.id.text_completed);
//        ImageView alreadyCompletedImg = rootView.findViewById(R.id.img_completed);
//        if (hasFilledSurveyToday()) {
//            alreadyCompletedText.setVisibility(View.VISIBLE);
//            alreadyCompletedImg.setVisibility(View.VISIBLE);
//        } else {
//            alreadyCompletedText.setVisibility(View.GONE);
//            alreadyCompletedImg.setVisibility(View.GONE);
//        }*/
//    }
//
//}
