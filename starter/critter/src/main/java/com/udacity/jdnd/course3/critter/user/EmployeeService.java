package com.udacity.jdnd.course3.critter.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    public Employee saveEmployee(Employee employee) {

        return employeeRepo.save(employee);
    }

    public Employee getEmployee(long id) {
        Employee employee;

        Optional<Employee> optionalEmployee = employeeRepo.findById(id);

        if (optionalEmployee.isPresent()) {
            employee = optionalEmployee.get();
        } else {
            throw new EmployeeNotFoundException("Employee not found with id " + id);
        }

        return employee;
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, long id) {

        Employee employee = this.getEmployee(id);

        if (employee != null) {
            employee.setDaysAvailable(daysAvailable);
            employeeRepo.save(employee);
        }

    }

    public List<Employee> checkAvailability(DayOfWeek dayOfWeek, Set<EmployeeSkill> employeeSkills) {

        List<Employee> employeeList = employeeRepo.findByDaysAvailable(dayOfWeek);

        if (employeeList.isEmpty()) {
            throw new EmployeeNotFoundException("There is no Employee available with " + dayOfWeek + "and with skills " + employeeSkills);
        }

        List<Employee> employeesAvailable = new ArrayList<>();

        for (Employee employee: employeeList) {
            if (employee.getSkills().containsAll(employeeSkills)) {
                employeesAvailable.add(employee);
            }
        }

        if (employeesAvailable.isEmpty()) {
            throw new EmployeeNotFoundException("There is no Employee available with " + dayOfWeek + "and with skills " + employeeSkills);
        }

        return employeesAvailable;
    }
}
