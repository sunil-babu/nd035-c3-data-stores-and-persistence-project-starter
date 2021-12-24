package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.model.Schedule;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ScheduleService {

    private ScheduleRepository scheduleRepository;
    private EmployeeRepository employeeRepository;
    private PetRepository petRepository;

    public Schedule createSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        schedule.setActivities(scheduleDTO.getActivities());
        schedule.setDate(scheduleDTO.getDate());
        schedule.setEmployees(scheduleDTO.getEmployeeIds().stream().map((employeeId) -> employeeRepository.getOne(employeeId)).collect(Collectors.toList()));
        schedule.setPets(scheduleDTO.getPetIds().stream().map((petId) -> petRepository.getOne(petId)).collect(Collectors.toList()));
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getScheduleForPet(long petId) {
        return scheduleRepository.findAllByPets(petRepository.getOne(petId));
    }

    public List<Schedule> getScheduleForEmployee(long employeeId) {
        return scheduleRepository.findAllByEmployees(employeeRepository.getOne(employeeId));
    }
    public List<Schedule> getScheduleForCustomer(long customerId) {
        List<Pet> pets = petRepository.findPetByCustomerId(customerId);
        return scheduleRepository.findAllByPetsIn(pets);
    }


}
