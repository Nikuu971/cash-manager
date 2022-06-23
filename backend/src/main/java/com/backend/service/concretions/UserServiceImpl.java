package com.backend.service.concretions;

import com.backend.exception.UserException;
import com.backend.model.UserApplication;
import com.backend.repository.UserRepository;
import com.backend.service.abstractions.IUserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Class User Service
 * @author Baptiste Gellato
 * @version 0.0.1
 */
@Service
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;

    /**
     * User service constructor
     * Init user service repository
     *
     * @param userRepository User repository
     */
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Get user by user Id
     *
     * @param id User Id
     * @return user
     * @throws UserException if user Id is invalid
     */
    @Override
    public UserApplication getUserById(Long id) {
        Optional<UserApplication> user = this.userRepository.findById(id);

        if (!user.isPresent()) {
            throw new UserException("Invalid UserId");
        }
        return user.get();
    }

    /**
     * Create user
     *
     * @param username Contain all user information
     * @return created user
     */
    @Override
    public UserApplication createUser(String username) {
        UserApplication userApplication = new UserApplication(username);

        this.userRepository.save(userApplication);
        return userApplication;
    }

    @Override
    public UserApplication signUp(UserApplication userApplication) {
        this.userRepository.save(userApplication);
        return userApplication;
    }
}
