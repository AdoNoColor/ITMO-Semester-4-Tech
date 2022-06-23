package com.AdoNoColor.service;

import com.AdoNoColor.entity.UserEntity;
import com.AdoNoColor.entity.model.UserModel;
import com.AdoNoColor.repository.UserEntityRepository;
import com.AdoNoColor.tools.KafkaTemplateTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserEntityService implements UserDetailsService {
    @Autowired
    private UserEntityRepository userRepo;

    @Autowired
    private KafkaTemplateTool kafkaTemplateTool;

    @Override
    @KafkaListener(topics = "loadUserByUsername")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepo.findByUsername(username);
        UserModel userModel = UserModel.toModel(user);
        kafkaTemplateTool.kafkaUserTemplate.send("sendUser", userModel);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(user == null) {
            throw new UsernameNotFoundException("user not found:"+username);
        }
        UserDetails userDetails = User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().toString())
                .build();
        return userDetails;
    }

    @KafkaListener(topics = "getUserById")
    public UserEntity getUsersById(UserModel userModel) {
        UserEntity user = userRepo.findById(userModel.getId()).get();
        UserModel userModelRes = UserModel.toModel(user);
        kafkaTemplateTool.kafkaUserTemplate.send("sendUser", userModelRes);
        return userRepo.findByUsername(user.getUsername());
    }

    @KafkaListener(topics = "createUser")
    public UserEntity create(UserModel user) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String oldPass = user.getPassword();
        user.setPassword(passwordEncoder.encode(oldPass));
        UserEntity user1 = userRepo.findByUsername(user.getUsername());
        return userRepo.save(user1);
    }

}
