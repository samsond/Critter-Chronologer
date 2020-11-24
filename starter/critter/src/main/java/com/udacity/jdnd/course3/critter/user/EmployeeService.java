package com.udacity.jdnd.course3.critter.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
}
