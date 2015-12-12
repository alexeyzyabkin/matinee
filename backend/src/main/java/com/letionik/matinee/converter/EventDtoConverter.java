package com.letionik.matinee.converter;

import com.letionik.matinee.EventDto;
import com.letionik.matinee.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Alexey Zyabkin on 12.12.2015.
 */
@Component
public class EventDtoConverter implements Converter<EventDto, Event> {
    @Autowired
    private UserDtoConverter userDtoConverter;
    @Autowired
    private ParticipantDtoConverter participantDtoConverter;

    public EventDto convertTo(Event event) {
        EventDto eventDto = new EventDto();
        eventDto.setId(event.getId());
        eventDto.setCode(event.getCode());
        eventDto.setAdmin(userDtoConverter.convertTo(event.getAdmin()));
        eventDto.setParticipants(participantDtoConverter.convertCollection(event.getParticipants()));
        eventDto.setName(event.getName());
        eventDto.setEventStatus(event.getStatus());
        return eventDto;
    }
}
