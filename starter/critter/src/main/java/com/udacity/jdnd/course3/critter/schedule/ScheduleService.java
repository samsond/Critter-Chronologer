package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepo scheduleRepo;

    @Autowired
    private EmployeeRepo employeeRepo;

    public Schedule createSchedule(Schedule schedule) {
        return scheduleRepo.save(schedule);
    }

    public List<Schedule> findScheduleForEmployee(long id) {
        Optional<Employee> optionalEmployee = employeeRepo.findById(id);
        Employee employee = employeeRepo.getOne(id);

        List<Schedule> schedules = scheduleRepo.findByEmployees(employee);
//        if (optionalEmployee.isPresent()) {
//            schedules = scheduleRepo.findAllByEmployees(optionalEmployee.get());
//        } else {
//            throw new ScheduleNotFoundException("There is no schedule for employee id " + id);
//        }


        return schedules;
    }
}
