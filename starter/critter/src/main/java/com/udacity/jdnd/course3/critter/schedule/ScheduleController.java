package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PetService petService;

    private static ScheduleDTO convertEntityToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();

        BeanUtils.copyProperties(schedule, scheduleDTO);

        if (schedule.getEmployees() != null) {
            List<Long> employeeIds = new ArrayList<>();

            for (Employee employee: schedule.getEmployees()) {
                employeeIds.add(employee.getId());
            }

            if (!employeeIds.isEmpty()) {
                scheduleDTO.setEmployeeIds(employeeIds);
            }
        }

        if (schedule.getPets() != null) {
            List<Long> petIds = new ArrayList<>();

            for (Pet pet: schedule.getPets()) {
                petIds.add(pet.getId());
            }

            if (!petIds.isEmpty()) {
                scheduleDTO.setPetIds(petIds);
            }
        }

        return scheduleDTO;
    }

    private static Schedule convertScheduleDToEntity(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();

        BeanUtils.copyProperties(scheduleDTO, schedule);

        return schedule;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {

        List<Long> employeeIdList = scheduleDTO.getEmployeeIds();

        List<Employee> employeeList = new ArrayList<>();

        if (employeeIdList != null) {
            employeeList = employeeIdList.stream().map(id -> {
                Employee employee = employeeService.getEmployee(id);
                return employee;
            }).collect(Collectors.toList());
        }


        List<Long> petIdList = scheduleDTO.getPetIds();

        List<Pet> petList = new ArrayList<>();

        if (petIdList != null) {
            petList = petIdList.stream().map(id -> {
                Pet pet = petService.getPet(id);
                return pet;
            }).collect(Collectors.toList());
        }

        Schedule schedule = convertScheduleDToEntity(scheduleDTO);

        if (employeeIdList != null) {
            schedule.setEmployees(employeeList);
        }


        if (petList != null) {
            schedule.setPets(petList);
        }


        schedule = scheduleService.createSchedule(schedule);

        return convertEntityToScheduleDTO(schedule);

    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> scheduleList = scheduleService.findAllSchedules();
        List<ScheduleDTO> scheduleDTOList = scheduleList.stream().map(ScheduleController::convertEntityToScheduleDTO).collect(Collectors.toList());

        return scheduleDTOList;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> scheduleList = scheduleService.findScheduleForEmployee(employeeId);
        List<ScheduleDTO> scheduleDTOList = scheduleList.stream().map(ScheduleController::convertEntityToScheduleDTO).collect(Collectors.toList());

        return scheduleDTOList;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        throw new UnsupportedOperationException();
    }
}
