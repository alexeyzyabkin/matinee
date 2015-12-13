package com.invizorys.mobile.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bruce.pickerview.popwindow.DatePickerPopWin;
import com.invizorys.mobile.R;
import com.invizorys.mobile.activity.MainActivity;
import com.invizorys.mobile.api.MatineeService;
import com.invizorys.mobile.api.RetrofitCallback;
import com.invizorys.mobile.api.ServiceGenerator;
import com.invizorys.mobile.util.FragmentHelper;
import com.letionik.matinee.CreateEventRequestDto;
import com.letionik.matinee.EventDto;

import java.util.Date;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class FragmentCreateEvent extends Fragment implements View.OnClickListener {
    private TextView textViewDate;
    private MatineeService matineeService;
    private Date startDate;
    private EditText editTextEventName;
    private FragmentManager fragmentManager;

    public static FragmentCreateEvent newInstance() {
        return new FragmentCreateEvent();
    }

    public FragmentCreateEvent() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_event, container, false);
        textViewDate = (TextView) view.findViewById(R.id.textview_date);
        textViewDate.setOnClickListener(this);

        fragmentManager = getActivity().getFragmentManager();

        editTextEventName = (EditText) view.findViewById(R.id.edittext_event_name);
        view.findViewById(R.id.button_create_event).setOnClickListener(this);

        matineeService = ServiceGenerator.createService(MatineeService.class, MatineeService.BASE_URL);

        return view;
    }

    private void showDatePicker() {
        DatePickerPopWin pickerPopWin = new DatePickerPopWin(getActivity(), new DatePickerPopWin.OnDatePickedListener() {
            @Override
            public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
//                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
//                startDate = dateFormat.format(new Date(year, month, day));

                startDate = new Date(year, month, day);

                textViewDate.setText(dateDesc);
            }
        });
        pickerPopWin.showPopWin(getActivity());
    }

    private void createEvent() {
        String name = editTextEventName.getText().toString();
        matineeService.createEvent(new CreateEventRequestDto(name, startDate), new RetrofitCallback<EventDto>(getActivity()) {
            @Override
            public void success(EventDto eventDto, Response response) {
                if (eventDto.getId() == null) {
                    Toast.makeText(getActivity(), "eventDto null", Toast.LENGTH_SHORT).show();
                    return;
                }
                FragmentHelper.add(fragmentManager, FragmentEvent.newInstance(eventDto.getId()),
                        MainActivity.FRAME_CONTAINER);
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textview_date:
                showDatePicker();
                break;
            case R.id.button_create_event:
                createEvent();
                break;
        }
    }
}
