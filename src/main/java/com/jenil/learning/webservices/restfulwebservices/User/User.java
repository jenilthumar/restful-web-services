package com.jenil.learning.webservices.restfulwebservices.User;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import org.springframework.hateoas.RepresentationModel;

public class User extends RepresentationModel<User> {
    
    private Integer id;

    @Size(min = 2)
    private String name;

    @Past
    @JsonIgnore
    private Date birthDate;

    public User(Integer id, String name, Date birthDate) {
        super();
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String toString() {
        return String.format("ID: %s, Name: %s, BirthDate: %s", id, name, birthDate);
    }
}
