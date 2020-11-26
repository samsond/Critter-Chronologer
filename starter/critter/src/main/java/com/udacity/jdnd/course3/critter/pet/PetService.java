package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepo;
import com.udacity.jdnd.course3.critter.user.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PetService {

    @Autowired
    private PetRepo petRepo;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private CustomerService customerService;

    public Pet getPet(long id) {

        Pet pet;

        Optional<Pet> optionalPet = petRepo.findById(id);

        if (optionalPet.isPresent()) {
            pet = optionalPet.get();
        } else {
            throw new PetNotFoundException("Pet Not found with id " + id);
        }

        return pet;
    }

    public List<Pet> getPets() {
        List<Pet> pets = petRepo.findAll();

        if (pets.isEmpty()) {
            throw new PetNotFoundException("There is no pets to return");
        }

        return pets;
    }

    public List<Pet> getPetsByOwner(long ownerId) {
        Customer customer = customerRepo.getOne(ownerId);
        List<Pet> pets = petRepo.findByCustomer(customer);

        if (pets.isEmpty()) {
            throw new PetNotFoundException("There is no pets with owner id " +ownerId);
        }

        return pets;
    }

    public Pet savePet(Pet pet) {
//        if (pet.getCustomer() != null) {
//            System.out.println("############################### customer id " + pet.getCustomer().getId());
//            return petRepo.findById(pet.getId())
//                    .map(petToBeUpdated -> {
//                        petToBeUpdated.setType(pet.getType());
//                        petToBeUpdated.setName(pet.getName());
//                        petToBeUpdated.setNotes(pet.getNotes());
//                        if (pet.getCustomer() != null) {
//                            petToBeUpdated.setCustomer(pet.getCustomer());
//                        }
//
//                        return petRepo.save(petToBeUpdated);
//                    }).orElseThrow(PetNotFoundException::new);
//        }

        pet = petRepo.save(pet);

        customerService.addPetToCustomer(pet, pet.getCustomer());
        return pet;


    }
}
