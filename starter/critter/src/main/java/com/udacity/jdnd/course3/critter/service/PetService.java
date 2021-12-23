package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PetService {

    private CustomerRepository customerRepository;
    private PetRepository petRepository;

    public PetDTO savePet(PetDTO petDTO) {
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
        return changetPetToPetDTO(pet);
    }

    public PetDTO getPet(long petId){
            return changetPetToPetDTO(petRepository.getOne(petId));
    }

    public List<PetDTO> getPets(){
      return petRepository.findAll().stream().map(this::changetPetToPetDTO).collect(Collectors.toList());
    }

    public List<PetDTO> getPetsByOwner(long ownerId){
        return petRepository.findPetByCustomerId(ownerId).stream().map(this::changetPetToPetDTO).collect(Collectors.toList());
    }

    private PetDTO changetPetToPetDTO(Pet pet) {
        return new PetDTO(pet.getId(), pet.getType(), pet.getName(), pet.getCustomer().getId(), pet.getBirthDate(), pet.getNotes());
    }
}
