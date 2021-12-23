package com.udacity.jdnd.course3.critter.model;

import com.udacity.jdnd.course3.critter.constants.PetType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="pet")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PetType type;

    private String name;
    private LocalDate birthDate;
    private String notes;

    @ManyToOne(targetEntity = Customer.class)
    private Customer customer;


}
