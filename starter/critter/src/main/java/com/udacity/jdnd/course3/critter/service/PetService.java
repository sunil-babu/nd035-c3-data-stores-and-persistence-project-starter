package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.exception.NoDataFoundException;
import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class PetService {

    private CustomerRepository customerRepository;
    private PetRepository petRepository;

    public Pet savePet(PetDTO petDTO) {
        Customer customer = customerRepository.findById(petDTO.getOwnerId()).get();
        List<Pet> petList = new ArrayList<>();
        Pet pet = new Pet();
        pet.setType(petDTO.getType());
        pet.setName(petDTO.getName());
        pet.setCustomer(customer);
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setNotes(petDTO.getNotes());
        pet = petRepository.save(pet);
        petList.add(pet);
        customer.setPets(petList);
        customerRepository.save(customer);
        return pet;
    }

    public Pet getPet(long petId){
        return petRepository.findById(petId).orElseThrow(() -> new NoDataFoundException(petId));
    }

    public List<Pet> getPets(){
      return petRepository.findAll();
    }

    public List<Pet> getPetsByOwner(long ownerId){
        return petRepository.findPetByCustomerId(ownerId);
    }


}
