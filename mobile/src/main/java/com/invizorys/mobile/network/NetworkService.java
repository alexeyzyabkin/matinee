package com.invizorys.mobile.network;

import android.app.IntentService;
import android.content.Intent;

import com.invizorys.mobile.data.EventDataSource;
import com.invizorys.mobile.model.EventsUpdated;
import com.invizorys.mobile.network.api.MatineeService;
import com.invizorys.mobile.network.api.RetrofitCallback;
import com.invizorys.mobile.network.api.ServiceGenerator;
import com.letionik.matinee.EventDto;

import java.io.Serializable;
import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Paryshkura Roman on 27.12.2015.
 */
public class NetworkService extends IntentService {
    public static final String NETWORK_REQUEST = "networkRequest";
    private static final String name = "NetworkService";
    private MatineeService matineeService;

    public NetworkService() {
        super(name);
        matineeService = ServiceGenerator.createService(MatineeService.class,
                MatineeService.BASE_URL, this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        switch ((NetworkRequest) intent.getSerializableExtra(NETWORK_REQUEST)) {
            case GET_EVENTS:
                getEvents();
                break;
        }

    }

    private void getEvents() {
        matineeService.getEvents(new RetrofitCallback<List<EventDto>>(this) {
            @Override
            public void success(List<EventDto> eventDtos, Response response) {
                saveEvents(eventDtos);
                EventBus.getDefault().post(new EventsUpdated());
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });
    }

    private void saveEvents(List<EventDto> eventDtos) {
        EventDataSource eventDataSource = new EventDataSource(this);
        eventDataSource.beginTransaction();
//        eventDataSource.clearTable();
        for (EventDto eventDto : eventDtos) {
            eventDataSource.saveEvent(eventDto);
        }
        eventDataSource.endTransaction();
    }

    public enum NetworkRequest implements Serializable {
        GET_EVENTS
    }
}
