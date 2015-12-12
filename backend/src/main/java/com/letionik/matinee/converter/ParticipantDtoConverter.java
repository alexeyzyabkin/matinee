package com.letionik.matinee.converter;

import com.letionik.matinee.ParticipantDto;
import com.letionik.matinee.model.Participant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Alexey Zyabkin on 12.12.2015.
 */
@Component
public class ParticipantDtoConverter implements Converter<ParticipantDto, Participant> {

    @Autowired
    private UserDtoConverter userDtoConverter;

    @Autowired
    private RoleDtoConverter roleDtoConverter;

    @Autowired
    private TaskDtoConverter taskDtoConverter;

    @Override
    public ParticipantDto convertTo(Participant obj) {
        ParticipantDto participantDto = new ParticipantDto();
        participantDto.setId(obj.getId());
        participantDto.setUserDto(userDtoConverter.convertTo(obj.getUser()));
        participantDto.setRole(roleDtoConverter.convertTo(obj.getRole()));
        participantDto.setTasks(taskDtoConverter.convertCollection(obj.getProgressTasks()));
        return participantDto;
    }
}
