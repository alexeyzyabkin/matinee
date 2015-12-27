package com.invizorys.mobile.ui.fragment.event;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.invizorys.mobile.R;
import com.invizorys.mobile.network.api.MatineeService;
import com.invizorys.mobile.network.api.ServiceGenerator;
import com.invizorys.mobile.ui.activity.MainActivity;

public class FragmentEventSettings extends Fragment implements View.OnClickListener {
    private EditText editTextEmail;
    private MatineeService matineeService;
    private RecyclerView recyclerView;

    public static FragmentEventSettings newInstance() {
        return new FragmentEventSettings();
    }

    public FragmentEventSettings() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.settings);
        ((MainActivity)getActivity()).showBackArrow();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        matineeService = ServiceGenerator.createService(MatineeService.class,
                MatineeService.BASE_URL, getActivity());

        View view = inflater.inflate(R.layout.fragment_event_settings, container, false);
        view.findViewById(R.id.button_add_email).setOnClickListener(this);
        editTextEmail = (EditText) view.findViewById(R.id.edittext_email);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_emails);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        return view;
    }

    private void addEmail() {
        editTextEmail.getText().toString();
    }

    private void getEmails() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_add_email:
                addEmail();
                break;
        }
    }
}
