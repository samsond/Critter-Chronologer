package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.List;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Nationalized
    private String name;
    private String phoneNumber;
    private String notes;

//    @OneToMany(fetch = FetchType.LAZY,mappedBy = "customer", cascade = CascadeType.ALL)
//    private List<Long> petIds;

    @OneToMany(mappedBy = "customer", targetEntity = Pet.class)
//    @JoinTable(
//            name = "customer_pet",
//            joinColumns = { @JoinColumn(name = "customer_id") },
//            inverseJoinColumns = { @JoinColumn(name = "pet_id") }
//    )
//    @JsonIgnore
    private List<Pet> pets;

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

//    public List<Pet> getPetIds() {
//        return petIds;
//    }
//
//    public void setPetIds(List<Pet> petIds) {
//        this.petIds = petIds;
//    }

    public Customer() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

//    public List<Long> getPetIds() {
//        return petIds;
//    }

//    public void setPetIds(List<Long> petIds) {
//        this.petIds = petIds;
//    }
}
