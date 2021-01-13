package edu.ucla.cs.er.depressionstudy;


import android.app.Activity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {

    private Button createButton;
    private Activity context;
    private String esmString;

    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_contact, container, false);
        context = getActivity();

//        createButton =  (Button) rootView.findViewById(R.id.bt_create);
////        setState(STATE_DEFAULT);
//        createButton.setOnClickListener(new Button.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                try {
////                    ESMFactory factory = new ESMFactory();
//                    Aware.setSetting(context, Aware_Preferences.STATUS_ESM, true);
//
//                    //define ESM question
//                    ESM_Freetext esmFreetext = new ESM_Freetext();
//                    esmFreetext.setTitle("Contact Us")
//                            .setTrigger("AWARE Tester")
//                            .setExpirationThreshold(60)
//                            .setSubmitButton("SEND")
//                            .setInstructions("We appreciate your comments.");
//
//                    JSONArray array = new JSONArray();
//                    array.put(esmFreetext.build());
//                    esmString = array.toString();
//                    Log.d("esmString", esmString);
//
////                    //add them to the factory
////                    factory.addESM(esmFreetext);
////
////                    //Queue them
////                    ESM.queueESM(getContext(), factory.build());
//
//                    Intent esm = new Intent(ESM.ACTION_AWARE_QUEUE_ESM);
//                    esm.putExtra(ESM.EXTRA_ESM, esmString);
//                    context.sendBroadcast(esm);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });

        return rootView;
    }

}
