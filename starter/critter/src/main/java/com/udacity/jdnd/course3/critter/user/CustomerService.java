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
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private PetService petService;

    @Transactional
    public Customer saveCustomer(Customer customer) {

//        if (customer.getPets() != null) {
//
//
//            for (Pet petId: customer.getPets()) {
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

    public void addPetToCustomer(Pet pet, Customer customer) {
        List<Pet> petList = customer.getPets();

        if (petList == null) {
            petList = new ArrayList<>();

        }
        petList.add(pet);

        customer.setPets(petList);

        customerRepo.save(customer);
    }

    public List<Customer> findAllCustomers() {
        return customerRepo.findAll();
    }

    public Customer findCustomer(Long id) {
        return customerRepo.getOne(id);
    }
}
