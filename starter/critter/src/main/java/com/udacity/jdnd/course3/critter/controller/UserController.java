package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        return changeCustomerToCustomerDTO(userService.saveCustomer(customerDTO));
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        return userService.getAllCustomers().stream().map(this::changeCustomerToCustomerDTO).collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        return changeCustomerToCustomerDTO(userService.getOwnerByPet(petId));
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return changetEmployeeToEmployeeDTO(userService.saveEmployee(employeeDTO));
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return changetEmployeeToEmployeeDTO(userService.getEmployee(employeeId));
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
         userService.setAvailability(daysAvailable,employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        return userService.findEmployeesForService(employeeDTO).stream().map(this::changetEmployeeToEmployeeDTO).collect(Collectors.toList());
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
