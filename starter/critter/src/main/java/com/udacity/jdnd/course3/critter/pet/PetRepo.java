package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface PetRepo extends JpaRepository<Pet, Long> {

    @Query("select p from Pet p where p.customer = :customer")
    List<Pet> findByCustomer(Customer customer);
}
