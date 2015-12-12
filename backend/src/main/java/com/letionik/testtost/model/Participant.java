package com.letionik.testtost.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Iryna Guzenko on 12.12.2015.
 */
public class Participant {
    private Long id;
    private User user;
    private Role role;
    private List<Task> tasks = new ArrayList<>();
    private Event event;
}
