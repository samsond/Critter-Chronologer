package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepo;
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

    @Autowired
    private PetRepo petRepo;

    public Schedule createSchedule(Schedule schedule) {
        return scheduleRepo.save(schedule);
    }

    public List<Schedule> findScheduleForEmployee(long id) {
        Employee employee = employeeRepo.getOne(id);

        if (employee == null) {
            throw new ScheduleNotFoundException("There is no schedule for employee id " + id);
        }

        List<Schedule> schedules = scheduleRepo.findByEmployees(employee);

        return schedules;
    }

    public List<Schedule> findAllSchedules() {
        return scheduleRepo.findAll();
    }

    public List<Schedule> findScheduleForPet(long id) {
        Pet pet = petRepo.getOne(id);

        if (pet == null) {
            throw new ScheduleNotFoundException("There is no schedule for pet id " + id);
        }
        List<Schedule> scheduleList = scheduleRepo.findScheduleByPets(pet);

        return scheduleList;
    }
}
