package com.AdoNoColor.controller.security;

import com.AdoNoColor.domain.entity.UserEntity;
import com.AdoNoColor.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserEntityRepository userRepo;

    @Autowired
    public UserDetailsServiceImpl(UserEntityRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity  user = userRepo.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User " + username  + " doesn't exist!");
        }

        return SecurityUser.fromUser(user);
    }
}
