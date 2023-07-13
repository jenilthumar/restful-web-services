package com.jenil.learning.webservices.restfulwebservices.Customers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import org.springframework.stereotype.Component;

@Component
public class CustomerDAO {
    
    private static List<Customer> customers = new ArrayList<>();

    private static int userCount = 3;

    static {
        customers.add(new Customer(1, "Adam", new Date(0).toLocalDate()));
        customers.add(new Customer(2, "Eve", new Date(0).toLocalDate()));
        customers.add(new Customer(3, "Jack", new Date(0).toLocalDate()));
    }

    public List<Customer> findAll() {
        return customers;
    }

    public Customer saveUser(Customer customer) {
        if(customer.getId()==null) {
            customer.setId(++userCount);
        }
        customers.add(customer);
        return customer;
    }

    public Customer findUser(int id) {
        for(Customer customer : customers) {
            if(customer.getId()==id) {
                return customer;
            }
        }
        return null;
    }

    public Customer deleteUserById(int id) {
        
        Iterator<Customer> iterator = customers.iterator();
        while (iterator.hasNext()) {
            Customer customer = iterator.next();
            if(customer.getId()==id) {
                iterator.remove();
                return customer;
            }
        }
        return null;
    }
}
