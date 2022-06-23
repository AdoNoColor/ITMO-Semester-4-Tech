package com.AdoNoColor.entity.model;

import com.AdoNoColor.entity.Owner;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class OwnerModel {
    private Integer id;
    private String name;
    private Date date_of_birth;
    private List<CatModel> cats;

    public OwnerModel() {
    }

    public static OwnerModel toModel(Owner owner){
        OwnerModel model = new OwnerModel();
        model.setId(owner.getId());
        model.setDate_of_birth(owner.getDate_of_birth());
        model.setName(owner.getName());
        model.setCats(owner.getCats().stream().map(CatModel::toModel).collect(Collectors.toList()));
        return model;
    }

    public List<CatModel> getCats() {
        return cats;
    }

    public void setCats(List<CatModel> cats) {
        this.cats = cats;
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

    public Date getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
    }
}
