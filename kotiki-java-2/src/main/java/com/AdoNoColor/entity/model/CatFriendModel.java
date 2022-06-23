package com.AdoNoColor.entity.model;

import com.AdoNoColor.entity.Cat;

public class CatFriendModel {
    private Integer id;
    private String name;

    public static CatFriendModel toModel(Cat catModel){
        CatFriendModel model = new CatFriendModel();
        model.setId(catModel.getId());
        model.setName(catModel.getName());
        return model;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CatFriendModel() {
    }
}
