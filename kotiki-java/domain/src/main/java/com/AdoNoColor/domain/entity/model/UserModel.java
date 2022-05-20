package com.AdoNoColor.domain.entity.model;

import com.AdoNoColor.domain.entity.UserEntity;

public class UserModel {
    private Integer id;
    private String username;
    private String password;
    private OwnerModel ownerModel;
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UserModel() {
    }

    public static UserModel toModel(UserEntity user){
        UserModel model = new UserModel();
        model.setId(user.getId());
        model.setPassword(user.getPassword());
        model.setUsername(user.getUsername());
        model.setRole(user.getRole());
        model.setOwnerModel(OwnerModel.toModel(user.getOwner()));
        return model;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public OwnerModel getOwnerModel() {
        return ownerModel;
    }

    public void setOwnerModel(OwnerModel ownerModel) {
        this.ownerModel = ownerModel;
    }
}

