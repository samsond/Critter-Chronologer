package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ScheduleRepo extends JpaRepository<Schedule, Long> {


//    @Query("select s from Schedule s where s.employees = :employee")
    List<Schedule> findByEmployees(@Param("employees") Employee employee);

    List<Schedule> findScheduleByPets(@Param("pets") Pet pet);

}
