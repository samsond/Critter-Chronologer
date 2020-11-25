package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.pet.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PetService petService;

    @Autowired
    private EmployeeService employeeService;

    private static CustomerDTO convertEntityToCustomerDTO(Customer customer) {

        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        if (customer != null && customer.getPets() != null) {
            List<Long> petId = new ArrayList<>();
            for (Pet pet: customer.getPets()) {
                petId.add(pet.getId());
            }

            if (!petId.isEmpty()) {
                customerDTO.setPetIds(petId);
            }

        }
        return customerDTO;
    }

    private static Customer convertCustomerDTOToEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }

    private static EmployeeDTO convertEntityToEmployeeDTO(Employee employee) {
        List<Long> employeeId = new ArrayList<>();
        EmployeeDTO employeeDTO = new EmployeeDTO();

        BeanUtils.copyProperties(employee, employeeDTO);

        return employeeDTO;
    }

    private static Employee convertEmployeeDTOToEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);

        return employee;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = convertCustomerDTOToEntity(customerDTO);
        List<Pet> pets = new ArrayList<>();
        if (customerDTO.getPetIds() != null) {


            for (Long petId: customerDTO.getPetIds()) {

                Pet pet = petService.getPet(petId);

                if (pet != null) {
                    pets.add(pet);
                }
                // check whether a pet is associated with a customer
                // before saving
//                if (pet !=null  && pet.getCustomer() != null) {
//
//                }
//                if (pet != null) {
//                    pets.add(pet);
//                }

            }

            if (!pets.isEmpty()) {
                customer.setPets(pets);
            }
        }

        customer = customerService.saveCustomer(customer);

        for (Pet pet: pets) {

            if (pet.getCustomer() == null) {
                pet.setCustomer(customer);
                petService.savePet(pet);
            }

            pet.setCustomer(customer);
            petService.savePet(pet);

        }

        return convertEntityToCustomerDTO(customer);

    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){

        List<Customer> customers = customerService.findAllCustomers();
        List<CustomerDTO> customerDTOS = customers.stream().map(UserController::convertEntityToCustomerDTO).collect(Collectors.toList());

//        for (Customer customer: customers) {
//            customerDTOS.add(convertEntityToCustomerDTO(customer));
//        }

        return customerDTOS;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){

        Pet pet = petService.getPet(petId);

        if (pet.getCustomer() == null) {
            throw new CustomerNotFoundException("There is no owner for ID: " + petId);
        }

        Customer customer = pet.getCustomer();

        return convertEntityToCustomerDTO(customer);

    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {

        Employee employee = convertEmployeeDTOToEntity(employeeDTO);

        employee = employeeService.saveEmployee(employee);

        return convertEntityToEmployeeDTO(employee);
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {

        Employee employee = employeeService.getEmployee(employeeId);

        return convertEntityToEmployeeDTO(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {

        List<Employee> employeeList = employeeService.checkAvailability(employeeDTO.getDate().getDayOfWeek(), employeeDTO.getSkills());

        List<EmployeeDTO> employeeDTOList = employeeList.stream().map(UserController::convertEntityToEmployeeDTO).collect(Collectors.toList());

        return employeeDTOList;
    }

}
