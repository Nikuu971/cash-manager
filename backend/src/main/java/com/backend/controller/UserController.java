package com.backend.controller;

import com.backend.exception.UserException;
import com.backend.model.UserApplication;
import com.backend.model.UserResponse;
import com.backend.service.abstractions.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * Class Rest API User Controller
 * @author Baptiste Gellato
 * @version 0.0.1
 */
@RestController
@RequestMapping(UserController.BASE_URL)
public class UserController {
    public  static final String BASE_URL = "/api/v1/users";

    private final IUserService userService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * User Controller constructor
     * Init User controller service
     * @param userService  User Service
     */
    @Autowired
    public UserController(IUserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * Get user by user Id
     *
     * @param id  User Id
     * @return user if success or Bad Request if fail
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserById(@PathVariable long id) {
        try {
            UserApplication userApplication = userService.getUserById(id);
            return new ResponseEntity<>(userApplication, HttpStatus.OK);
        }
        catch (UserException err) {
            return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Create user
     *
     * @param userApplication  Request body how contain user informations
     * @return created user
     */
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public UserApplication createUser(@RequestBody UserApplication userApplication)
    {
        return userService.createUser(userApplication.getUsername());
    }

    @PostMapping(value = "/sign-up")
    public void signUp(@RequestBody UserApplication userApplication) {
        userApplication.setPassword(bCryptPasswordEncoder.encode(userApplication.getPassword()));
        userService.signUp(userApplication);
    }

}
