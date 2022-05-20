package com.AdoNoColor.service;

import com.AdoNoColor.domain.entity.UserEntity;
import com.AdoNoColor.domain.entity.model.UserModel;
import com.AdoNoColor.repository.OwnerRepository;
import com.AdoNoColor.repository.UserEntityRepository;
import com.AdoNoColor.service.exceptions.OwnerNotFoundException;
import com.AdoNoColor.service.exceptions.UserAlreadyExistsException;
import com.AdoNoColor.service.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserEntityRepository userRepo;

    @Autowired
    private OwnerRepository ownerRepo;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserEntity myUser= userRepo.findByUsername(userName);
        if (myUser == null) {
            throw new UsernameNotFoundException("Unknown user: " + userName);
        }
        UserDetails user = User.builder()
                .username(myUser.getUsername())
                .password(myUser.getPassword())
                .roles(myUser.getRole())
                .build();
        return user;
    }

    public UserEntity createUser(UserEntity user) throws UserAlreadyExistsException {
        if (userRepo.findByUsername(user.getUsername()) != null) {
            throw new UserAlreadyExistsException("User has already been registered!");
        }

        String password = user.getPassword();
        ownerRepo.save(user.getOwner());
        return userRepo.save(user);
    }

    public Integer deleteUser(Integer id) throws UserNotFoundException {
        UserEntity user = userRepo.findById(id).get();

        if (user == null) {
            throw new UserNotFoundException("User was not found!");
        }

        userRepo.delete(user);
        return id;
    }

    public UserModel getUserById(Integer id) throws UserNotFoundException {
        UserEntity user = userRepo.findById(id).get();

        if (user == null) {
            throw new UserNotFoundException("User was not found!");
        }

        return UserModel.toModel(user);
    }


    public UserEntity updateUserName(Integer id, String name) throws UserNotFoundException, UserAlreadyExistsException {
        UserEntity user = userRepo.findById(id).get();

        if (user == null) {
            throw new UserNotFoundException("User was not found!");
        }

        if (name == userRepo.findByUsername(name).getUsername()){
            throw new UserAlreadyExistsException("User with that username already exists!");
        }

        user.setUsername(name);

        return userRepo.save(user);
    }

}
