package com.AdoNoColor.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "owners")
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;


    @Column(name = "date_of_birth")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date date_of_birth;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cat> cats;

    public Owner() {
    }

    public Owner(String name, Date date_of_birth) {
        this.name = name;
        this.date_of_birth = date_of_birth;
        cats = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCats(List<Cat> cats) {
        this.cats = cats;
    }

    public void addCat(Cat cat) { this.cats.add(cat); }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDate_of_birth() {
        return date_of_birth;
    }

    public List<Cat> getCats() {
        return cats;
    }
}
