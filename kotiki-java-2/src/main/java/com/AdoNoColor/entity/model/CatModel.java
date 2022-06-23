package com.AdoNoColor.entity.model;

import com.AdoNoColor.entity.Breed;
import com.AdoNoColor.entity.Cat;
import com.AdoNoColor.entity.Color;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CatModel {
    private Integer id;
    private String name;
    private Breed breed;
    private Color color;
    private Date date_of_birth;
    private Integer owner;
    private List<CatFriendModel> friends;

    public CatModel() {
    }

    public static CatModel toModel(Cat cat){
        CatModel model = new CatModel();
        model.setId(cat.getId());
        model.setBreed(cat.getBreed());
        model.setColor(cat.getColor());
        model.setOwner(cat.getOwner().getId());
        model.setDate_of_birth(cat.getDate_of_birth());
        model.setName(cat.getName());
        model.setFriends(cat.getFriends().stream().map(CatFriendModel::toModel).collect(Collectors.toList()));
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

    public Breed getBreed() {
        return breed;
    }

    public void setBreed(Breed breed) {
        this.breed = breed;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Date getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public List<CatFriendModel> getFriends() {
        return friends;
    }

    public void setFriends(List<CatFriendModel> friends) {
        this.friends = friends;
    }
}
