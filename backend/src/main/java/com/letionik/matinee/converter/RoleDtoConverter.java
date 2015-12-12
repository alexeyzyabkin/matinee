package com.letionik.matinee.converter;

import com.letionik.matinee.RoleDto;
import com.letionik.matinee.model.Role;
import org.springframework.stereotype.Component;

/**
 * Created by Alexey Zyabkin on 12.12.2015.
 */
@Component
public class RoleDtoConverter implements Converter<RoleDto, Role>{

    @Override
    public RoleDto convertTo(Role obj) {
        return null;
    }
}
