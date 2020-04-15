package com.lethanh98.controller;

import com.lethanh98.entity.User;
import com.lethanh98.repo.UserRepo;
import com.lethanh98.rp.BaseReponse;
import com.lethanh98.rp.RpFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    @Autowired
    UserRepo userRepo;

    @GetMapping()
    public BaseReponse<List<User>> get() {
        List<User> users = (List<User>) userRepo.findAll();
        return RpFactory.getReponse(users);
    }

    @GetMapping("/{id}")
    public BaseReponse<User> getById(@PathVariable("id") Integer id) {
        List<User> users = (List<User>) userRepo.findAll();
        AtomicReference<User> userRp = new AtomicReference<>();
        users.stream().filter(user -> user.getId() == id).findFirst().ifPresent(userRp::set);
        return RpFactory.getReponse(userRp.get());
    }

    @PostMapping()
    @Transactional
    public BaseReponse<User> post(String firstName, String lastName) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return RpFactory.getReponse(userRepo.save(user));
    }

    @PostMapping("/new")
    @Transactional
    public BaseReponse<User> postNew(@RequestBody User user) {
        return RpFactory.getReponse(userRepo.save(user));
    }
}
