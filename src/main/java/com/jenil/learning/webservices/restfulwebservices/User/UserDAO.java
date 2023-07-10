package com.jenil.learning.webservices.restfulwebservices.User;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import org.springframework.stereotype.Component;

@Component
public class UserDAO {
    
    private static List<User> users = new ArrayList<>();

    private static int userCount = 3;

    static {
        users.add(new User(1, "Adam", new Date(0)));
        users.add(new User(2, "Eve", new Date(0)));
        users.add(new User(3, "Jack", new Date(0)));
    }

    public List<User> findAll() {
        return users;
    }

    public User saveUser(User user) {
        if(user.getId()==null) {
            user.setId(++userCount);
        }
        users.add(user);
        return user;
    }

    public User findUser(int id) {
        for(User user: users) {
            if(user.getId()==id) {
                return user;
            }
        }
        return null;
    }

    public User deleteUserById(int id) {
        
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if(user.getId()==id) {
                iterator.remove();
                return user;
            }
        }
        return null;
    }
}
