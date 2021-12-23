package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private PetRepository petRepository;
    private CustomerRepository customerRepository;
    private EmployeeRepository employeeRepository;

    public CustomerDTO saveCustomer(CustomerDTO customerDTO){
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setNotes(customerDTO.getNotes());
        if(customerDTO.getPetIds()!=null) {
            customer.setPets(customerDTO.getPetIds().stream().map((petId) -> petRepository.getOne(petId)).collect(Collectors.toList()));
        }
        customer = customerRepository.save(customer);
        return changeCustomerToCustomerDTO(customer);
    }

    public List<CustomerDTO> getAllCustomers(){
        return customerRepository.findAll().stream().map(this::changeCustomerToCustomerDTO).collect(Collectors.toList());

    }

    public CustomerDTO getOwnerByPet(long petId){
       return changeCustomerToCustomerDTO(petRepository.getOne(petId).getCustomer());
    }


    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setSkills(employeeDTO.getSkills());
        employee.setDaysAvailable(employeeDTO.getDaysAvailable());
        employee = employeeRepository.save(employee);
        return changetEmployeeToEmployeeDTO(employee);
    }

    public EmployeeDTO getEmployee(long employeeId) {
        return changetEmployeeToEmployeeDTO(employeeRepository.getOne(employeeId));
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        Employee employee = employeeRepository.getOne(employeeId);
        employee.setDaysAvailable(daysAvailable);
    }

    public List<EmployeeDTO> findEmployeesForService(EmployeeRequestDTO employeeDTO) {
        List<Employee> employees = employeeRepository.findAllByDaysAvailable(employeeDTO.getDate().getDayOfWeek());
        employees.removeIf(employee -> !employee.getSkills().containsAll(employeeDTO.getSkills()));
        return employees.stream().map(this::changetEmployeeToEmployeeDTO).collect(Collectors.toList());
    }


    private CustomerDTO changeCustomerToCustomerDTO(Customer customer) {
        if(customer.getPets() != null)
            return new CustomerDTO(customer.getId(),  customer.getName(), customer.getPhoneNumber(), customer.getNotes(),customer.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
        else
            return new CustomerDTO(customer.getId(),  customer.getName(), customer.getPhoneNumber(), customer.getNotes(),null);

    }

    private EmployeeDTO changetEmployeeToEmployeeDTO(Employee employee) {
        return new EmployeeDTO(employee.getId(),  employee.getName(),employee.getSkills(),employee.getDaysAvailable());
    }
}
