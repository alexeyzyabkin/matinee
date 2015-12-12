package com.letionik.matinee.converter;

import com.letionik.matinee.TaskDto;
import com.letionik.matinee.TaskProgressDto;
import com.letionik.matinee.model.Task;
import com.letionik.matinee.model.TaskProgress;
import org.springframework.stereotype.Component;

/**
 * Created by Alexey Zyabkin on 12.12.2015.
 */
@Component
public class TaskDtoConverter implements Converter<TaskProgressDto, TaskProgress> {

    @Override
    public TaskProgressDto convertTo(TaskProgress obj) {
        return null;
    }
}
