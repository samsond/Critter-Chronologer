package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    @Autowired
    private PetRepo petRepo;

    @Autowired
    private CustomerRepo customerRepo;

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
        if (pet.getCustomer() != null) {
            return petRepo.findById(pet.getId())
                    .map(petToBeUpdated -> {
                        petToBeUpdated.setType(pet.getType());
                        petToBeUpdated.setName(pet.getName());
                        petToBeUpdated.setNotes(pet.getNotes());
                        if (pet.getCustomer() != null) {
                            petToBeUpdated.setCustomer(pet.getCustomer());
                        }

                        return petRepo.save(petToBeUpdated);
                    }).orElseThrow(PetNotFoundException::new);
        }

        return petRepo.save(pet);
    }
}
