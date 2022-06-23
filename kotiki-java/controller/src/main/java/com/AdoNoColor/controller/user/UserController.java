package com.AdoNoColor.controller.user;

import com.AdoNoColor.domain.entity.model.UserModel;
import com.AdoNoColor.service.UserEntityService;
import com.AdoNoColor.service.tools.KafkaTemplateTool;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserEntityService userService;

    @Autowired
    private KafkaTemplateTool kafkaTemplateTool;

    @PostMapping("/create")
    public ResponseEntity<UserModel> create(@RequestBody UserModel user) {
        kafkaTemplateTool.kafkaUserTemplate.send("createUser", user);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @SneakyThrows
    public ResponseEntity<UserModel> getById(@RequestParam(value = "id") Integer id) {
        UserModel userModel = new UserModel();
        userModel.setId(id);
        kafkaTemplateTool.kafkaUserTemplate.send("getUserById", userModel);
        try
        {
            Thread.sleep(1000);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return ResponseEntity.ok().body(userService.getUserModel());
    }
}
