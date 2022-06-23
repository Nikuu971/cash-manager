package com.backend.service.abstractions;

import com.backend.model.UserApplication;

public interface IUserService {
    UserApplication getUserById(Long id);

    UserApplication createUser(String username);

    UserApplication signUp(UserApplication userApplication);
}
