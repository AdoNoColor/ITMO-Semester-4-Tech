package com.AdoNoColor.service;

import com.AdoNoColor.domain.entity.UserEntity;
import com.AdoNoColor.domain.entity.model.UserModel;
import com.AdoNoColor.repository.OwnerRepository;
import com.AdoNoColor.repository.UserEntityRepository;
import com.AdoNoColor.service.exceptions.UserAlreadyExistsException;
import com.AdoNoColor.service.exceptions.UserNotFoundException;
import com.AdoNoColor.service.tools.KafkaTemplateTool;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserEntityService implements UserDetailsService {

    private UserModel userModel;
    private List<UserModel> usersModel;

    @Autowired
    private KafkaTemplateTool kafkaTemplate;

    public UserEntityService() {
    }

    @Override
    @SneakyThrows
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = new UserModel();
        kafkaTemplate.KafkaStringTemplate.send("loadUserByUsername", username);
        try
        {
            Thread.sleep(1000);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        user = userModel;
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(user == null){
            throw new UsernameNotFoundException("user not found:"+username);
        }
        UserDetails userDetails = User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().toString())
                .build();
        return userDetails;
    }

    @KafkaListener(topics = "sendUser")
    public void getUser(UserModel userModel) {
        this.userModel = userModel;
    }

    @KafkaListener(topics = "sendUsers")
    public void getUsers(List<UserModel> usersModel) {
        this.usersModel = usersModel;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public List<UserModel> getUsersModel() {
        return usersModel;
    }

    public void setUsersModel(List<UserModel> usersDto) {
        this.usersModel = usersModel;
    }
}
