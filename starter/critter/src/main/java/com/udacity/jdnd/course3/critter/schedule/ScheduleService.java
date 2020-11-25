package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepo scheduleRepo;

    public Schedule createSchedule(Schedule schedule) {
        return scheduleRepo.save(schedule);
    }
}
