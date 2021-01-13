package edu.ucla.cs.er.depressionstudy;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aware.Aware;
import com.aware.Aware_Preferences;
import com.aware.ESM;
import com.aware.providers.ESM_Provider;
import com.google.gson.Gson;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import edu.ucla.cs.er.depressionstudy.Util.Utils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment implements OnDateSelectedListener {

    public static final String PREF_CALENDAR_DATES = "pref_calendar_dates";

    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);
        MaterialCalendarView mcv = (MaterialCalendarView)rootView.findViewById(R.id.calendarView);
        mcv.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);

        mcv.setOnDateChangedListener(this);

        try {
            List<Double> selectedDatesInt = Utils.readSharedSettingObj(this.getContext(), PREF_CALENDAR_DATES, new ArrayList<Double>(), ArrayList.class);
            for (int i = 0; i < selectedDatesInt.size(); i++) {
                Double dateHash = selectedDatesInt.get(i);
                int year = (int)(dateHash/10000);
                int month = (int)((dateHash-year*10000)/100);
                int day = (int)(dateHash-year*10000-month*100);

                mcv.setDateSelected(CalendarDay.from(year, month, day), true);
                System.out.println("Adding date" + CalendarDay.from(year, month, day).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        // TODO: Store and load data to local store
        // TODO: Save latest data to ESM

        List<CalendarDay> selectedDates = widget.getSelectedDates();
        List<Integer> selectedDatesInt = new ArrayList<Integer>();
        for (int i = 0; i < selectedDates.size(); i++) {
            selectedDatesInt.add(new Integer(selectedDates.get(i).hashCode()));
        }

        Utils.saveSharedSettingObj(this.getContext(), PREF_CALENDAR_DATES, selectedDatesInt);

        for (int i = 0; i < selectedDates.size(); i++) {
            selectedDatesInt.set(i, 100+selectedDatesInt.get(i));
        }
        Gson gson = new Gson();
        String jsonDates = gson.toJson(selectedDatesInt);

        System.out.println(jsonDates);

        String json = "{\"esm_type\":1, \"esm_title\":\"Hookups\"}";

        ContentValues rowData = new ContentValues();
        rowData.put(ESM_Provider.ESM_Data.TIMESTAMP, System.currentTimeMillis());
        rowData.put(ESM_Provider.ESM_Data.ANSWER_TIMESTAMP, System.currentTimeMillis());
        rowData.put(ESM_Provider.ESM_Data.DEVICE_ID, Aware.getSetting(this.getContext(), Aware_Preferences.DEVICE_ID));
        rowData.put(ESM_Provider.ESM_Data.JSON, json);
        rowData.put(ESM_Provider.ESM_Data.STATUS, ESM.STATUS_ANSWERED);
        rowData.put(ESM_Provider.ESM_Data.ANSWER, jsonDates);

        this.getContext().getContentResolver().insert(ESM_Provider.ESM_Data.CONTENT_URI, rowData);
    }
}