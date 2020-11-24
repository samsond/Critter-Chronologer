package com.udacity.jdnd.course3.critter.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PetService {

    @Autowired
    private PetRepo petRepo;

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
