package edu.ucla.cs.er.depressionstudy;


import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class StatusFragment extends Fragment {

    private Button createButton;
    private Activity context;
    private String esmString;

    public StatusFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_status, container, false);
        context = getActivity();

        //boolean backgroundLocationPermissionApproved = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS);
        //System.out.println("background location permission enabled:");
        //System.out.println(backgroundLocationPermissionApproved);

        return rootView;
    }

}
