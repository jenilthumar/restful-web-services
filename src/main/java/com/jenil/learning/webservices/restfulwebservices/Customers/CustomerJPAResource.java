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
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class CustomerJPAResource {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CustomerDAO service;

    // GET /users
    //retrieve all users
    @GetMapping(path = "jpa/users")
    public List<Customer> retrieveAllUsers() {
        return customerRepository.findAll();
    }
    
    //  GET /users/{id}
    //retrieveUser(int id)
    @GetMapping(path = "jpa/users/{id}")
    Customer one(@PathVariable Integer id) {

        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(String.valueOf(id)));

    }

    @GetMapping(path = "jpa/users/{id}/posts")
    public List<Post> retrieveAllUsers(@PathVariable Integer id) {

        Optional<Customer> optionalCustomer = customerRepository.findById(id);

        if(!optionalCustomer.isPresent()) {
            throw new CustomerNotFoundException("id: " + id);
        }
        return optionalCustomer.get().getPosts();
    }

    // Unfinished, Need revamp
    @PostMapping(path = "jpa/users")
    Customer newCustomer(@RequestBody Customer newCustomer) {
        return customerRepository.save(newCustomer);
    }

    @PostMapping(path = "/jpa/users/{id}/posts")
    public ResponseEntity<Object> createCustomer(@PathVariable int id, @RequestBody Post post) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if(!optionalCustomer.isPresent()) {
            throw new CustomerNotFoundException("id" + id);
        }

        Customer customer = optionalCustomer.get();

        post.setCustomer(customer);

        postRepository.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(post.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(path = "jpa/users/{id}")
    void deleteCustomer(@PathVariable Integer id) {
        customerRepository.deleteById(id);
    }
}
