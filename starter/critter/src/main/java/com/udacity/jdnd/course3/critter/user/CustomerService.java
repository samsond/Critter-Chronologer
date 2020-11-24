package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private PetService petService;

    @Transactional
    public Customer saveCustomer(Customer customer) {

//        if (customer.getPetIds() != null) {
//
//
//            for (Pet petId: customer.getPetIds()) {
//                Pet pet = petService.getPet(petId.getId());
//                if (pet != null) {
//                    petService.savePet(pet);
//                }
//
//            }
//
//        }



        return customerRepo.save(customer);
    }

    public List<Customer> findAllCustomers() {
        return customerRepo.findAll();
    }
}
