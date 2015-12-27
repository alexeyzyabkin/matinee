package com.invizorys.mobile.ui.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.invizorys.mobile.R;
import com.invizorys.mobile.ui.activity.MainActivity;
import com.invizorys.mobile.network.api.MatineeService;
import com.invizorys.mobile.network.api.RetrofitCallback;
import com.invizorys.mobile.network.api.ServiceGenerator;
import com.invizorys.mobile.ui.fragment.event.FragmentParticipants;
import com.invizorys.mobile.util.FragmentHelper;
import com.invizorys.mobile.util.Utils;
import com.letionik.matinee.EventDto;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class FragmentCreateFindEvent extends Fragment implements View.OnClickListener {
    private FragmentManager fragmentManager;
    private EditText enrollCodeText;
    private MatineeService matineeService;

    public static FragmentCreateFindEvent newInstance() {
        return new FragmentCreateFindEvent();
    }

    public FragmentCreateFindEvent() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_find_event, container, false);
        view.findViewById(R.id.button_create_event).setOnClickListener(this);
        view.findViewById(R.id.button_enroll_event).setOnClickListener(this);

        fragmentManager = getActivity().getFragmentManager();

        matineeService = ServiceGenerator.createService(MatineeService.class,
                MatineeService.BASE_URL, getActivity());
        enrollCodeText = (EditText) view.findViewById(R.id.enroll_code_text);
        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_create_event:
//                FragmentHelper.add(fragmentManager, FragmentCreateEvent.newInstance(),
//                        MainActivity.FRAME_CONTAINER);
                break;
            case R.id.button_enroll_event:
                Utils.hideKeyBoard(getActivity());
                String code = enrollCodeText.getText().toString();
                matineeService.enroll(code, new RetrofitCallback<EventDto>(getActivity()) {
                    @Override
                    public void success(EventDto eventDto, Response response) {
                        if (eventDto.getId() == null) {
                            Toast.makeText(getActivity(), "eventDto null", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        FragmentHelper.add(fragmentManager, FragmentParticipants.newInstance(eventDto.getId()),
                                MainActivity.FRAME_CONTAINER);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        super.failure(error);
                    }
                });
        }
    }
}
