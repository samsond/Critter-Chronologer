package com.udacity.jdnd.course3.critter.pet;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;

    private static PetDTO convertEntityToPetDTO(Pet pet) {

        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        if (pet.getCustomer() != null) {
            petDTO.setOwnerId(pet.getCustomer().getId());
        }
        return petDTO;
    }

    private static Pet convertPetDTOToEntity(PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        return pet;
    }



    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {

        Pet pet = convertPetDTOToEntity(petDTO);

        pet = petService.savePet(pet);

        return convertEntityToPetDTO(pet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {

        Pet pet = petService.getPet(petId);

        return convertEntityToPetDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets = petService.getPets();

        List<PetDTO> petDTOList = pets.stream().map(PetController::convertEntityToPetDTO).collect(Collectors.toList());

        return petDTOList;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> pets = petService.getPetsByOwner(ownerId);

        List<PetDTO> petDTOList = pets.stream().map(PetController::convertEntityToPetDTO).collect(Collectors.toList());

        return petDTOList;
    }
}
