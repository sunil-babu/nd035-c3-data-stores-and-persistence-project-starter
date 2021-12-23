package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.model.Schedule;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ScheduleService {

    private ScheduleRepository scheduleRepository;
    private EmployeeRepository employeeRepository;
    private PetRepository petRepository;

    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        schedule.setActivities(scheduleDTO.getActivities());
        schedule.setDate(scheduleDTO.getDate());
        schedule.setEmployees(scheduleDTO.getEmployeeIds().stream().map((employeeId) -> employeeRepository.getOne(employeeId)).collect(Collectors.toList()));
        schedule.setPets(scheduleDTO.getPetIds().stream().map((petId) -> petRepository.getOne(petId)).collect(Collectors.toList()));
        return changeScheduleToScheduleDTO(scheduleRepository.save(schedule));
    }

    public List<ScheduleDTO> getAllSchedules() {
        return scheduleRepository.findAll().stream().map(this::changeScheduleToScheduleDTO).collect(Collectors.toList());
    }

    public List<ScheduleDTO> getScheduleForPet(long petId) {
        return scheduleRepository.findAllByPets(petRepository.getOne(petId)).stream().map(this::changeScheduleToScheduleDTO).collect(Collectors.toList());
    }

    public List<ScheduleDTO> getScheduleForEmployee(long employeeId) {
        return scheduleRepository.findAllByEmployees(employeeRepository.getOne(employeeId)).stream().map(this::changeScheduleToScheduleDTO).collect(Collectors.toList());
    }
    public List<ScheduleDTO> getScheduleForCustomer(long customerId) {
        List<Pet> pets = petRepository.findPetByCustomerId(customerId);
        return scheduleRepository.findAllByPetsIn(pets).stream().map(this::changeScheduleToScheduleDTO).collect(Collectors.toList());
    }

    private ScheduleDTO changeScheduleToScheduleDTO(Schedule schedule) {
        return new ScheduleDTO(schedule.getId(),  schedule.getEmployees().stream().map(employee -> employee.getId()).collect(Collectors.toList()), schedule.getPets().stream().map(Pet::getId).collect(Collectors.toList()),schedule.getDate(),schedule.getActivities());
    }
}
