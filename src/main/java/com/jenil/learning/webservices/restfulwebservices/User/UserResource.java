package com.jenil.learning.webservices.restfulwebservices.User;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserResource {
    
    @Autowired
    private UserDAO service;

    // GET /users
    //retrieve all users
    @GetMapping(path = "/users")
    public List<User> retrieveAllUsers() {
        return service.findAll();
    }
    
    //  GET /users/{id}
    //retrieveUser(int id)
    @GetMapping(path = "/users/{id}")
    public User retrieveUser(@PathVariable int id) {
        User user = service.findUser(id);
        
        if(user==null) {
            throw new UserNotFoundException("id-" + id);
        }

        user.add(linkTo(methodOn(UserResource.class).retrieveAllUsers()).withRel("All users"));

        return user;
    }

    //post - create a new user
    @PostMapping(path = "/users")
    public ResponseEntity createUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        User savedUser = service.saveUser(user);

        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(savedUser.getId())
            .toUri();


        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(path = "/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        User user = service.deleteUserById(id);

        if(user==null) {
            //throw new UserNotFoundException("id-" + id);

            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}
