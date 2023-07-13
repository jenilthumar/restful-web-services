package com.jenil.learning.webservices.restfulwebservices.Customers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class CustomerResource {
    
    @Autowired
    private CustomerDAO service;

    // GET /users
    //retrieve all users
    @GetMapping(path = "/users")
    public List<Customer> retrieveAllUsers() {
        return service.findAll();
    }
    
    //  GET /users/{id}
    //retrieveUser(int id)
    @GetMapping(path = "/users/{id}")
    public EntityModel<Customer> retrieveUser(@PathVariable int id) {
        Customer customer = service.findUser(id);
        
        if(customer ==null) {
            throw new CustomerNotFoundException("id-" + id);
        }

        EntityModel<Customer> entityModel = EntityModel.of(customer);

        WebMvcLinkBuilder link =  linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(link.withRel("all-users"));

        return entityModel;
    }

    //post - create a new kenil
    @PostMapping(path = "/users")
    public ResponseEntity createUser(@Valid @RequestBody Customer customer, BindingResult bindingResult) {
        Customer savedCustomer = service.saveUser(customer);

        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(savedCustomer.getId())
            .toUri();


        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(path = "/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        Customer customer = service.deleteUserById(id);

        if(customer ==null) {
            //throw new UserNotFoundException("id-" + id);

            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}
