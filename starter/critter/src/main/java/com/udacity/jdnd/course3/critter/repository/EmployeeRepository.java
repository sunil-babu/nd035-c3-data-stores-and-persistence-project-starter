package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.constants.EmployeeSkill;
import com.udacity.jdnd.course3.critter.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findAllByDaysAvailable(DayOfWeek dayOfWeek);
    List<Employee> findAllBySkillsIn(Set<EmployeeSkill> skills);
}
