package com.sicklecare.api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "administrator")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Admin extends User {

    private String firstname;
    private String lastname;

}
