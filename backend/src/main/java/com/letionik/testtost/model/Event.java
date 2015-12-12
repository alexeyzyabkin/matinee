package com.letionik.testtost.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Iryna Guzenko on 12.12.2015.
 */
public class Event {
    private Long id;
    private String name;
    private LocalDateTime dateTime;
    private List<Participant> participants = new ArrayList<>();
}
