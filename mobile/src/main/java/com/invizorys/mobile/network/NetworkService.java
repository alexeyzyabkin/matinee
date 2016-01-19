package com.invizorys.mobile.network;

import android.app.IntentService;
import android.content.Intent;

import com.invizorys.mobile.data.EventDataSource;
import com.invizorys.mobile.model.EventUpdated;
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
    public static final String EVENT_ID = "eventId";
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
                syncEvents();
                break;
            case GET_EVENT:
                long eventId = intent.getLongExtra(EVENT_ID, 0);
                syncEvent(eventId);
                break;
        }
    }

    private void syncEvents() {
        matineeService.getEvents(new RetrofitCallback<List<EventDto>>(this) {
            @Override
            public void success(List<EventDto> eventDtos, Response response) {
                saveEvents(eventDtos);
                EventBus.getDefault().post(new EventsUpdated(true));
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                EventBus.getDefault().post(new EventsUpdated(false));
            }
        });
    }

    private void syncEvent(final long eventId) {
        matineeService.getEvent(eventId, new RetrofitCallback<EventDto>(this) {
            @Override
            public void success(EventDto eventDto, Response response) {
                saveEvent(eventDto);
                EventBus.getDefault().post(new EventUpdated(eventId, true));
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                EventBus.getDefault().post(new EventUpdated(eventId, false));
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

    private void saveEvent(EventDto eventDto) {
        EventDataSource eventDataSource = new EventDataSource(this);
        eventDataSource.beginTransaction();
        eventDataSource.saveEvent(eventDto);
        eventDataSource.endTransaction();
    }

    public enum NetworkRequest implements Serializable {
        GET_EVENTS, GET_EVENT
    }
}
