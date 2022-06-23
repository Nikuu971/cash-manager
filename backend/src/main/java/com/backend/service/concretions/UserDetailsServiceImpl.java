package com.backend.service.concretions;

import com.backend.model.UserApplication;
import com.backend.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository applicationUserRepository) {
        this.userRepository = applicationUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserApplication userApplication = userRepository.findByUsername(username);
        if (userApplication == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(userApplication.getUsername(), userApplication.getPassword(), emptyList());
    }
}