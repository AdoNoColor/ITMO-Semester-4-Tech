package com.AdoNoColor.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "cats")
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "breed")
    @Enumerated(EnumType.STRING)
    private Breed breed;

    @Column(name = "color")
    @Enumerated(EnumType.STRING)
    private Color color;

    @Column(name = "date_of_birth")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date date_of_birth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private Owner owner;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "cats_friendships", joinColumns = {@JoinColumn(name = "cat1_id")}, inverseJoinColumns = {@JoinColumn(name = "cat2_id")})
    private List<Cat> friends;

    public Cat() {
    }

    public Cat(String name, Breed breed, Color color, Date date_of_birth) {
        this.name = name;
        this.breed = breed;
        this.color = color;
        this.date_of_birth = date_of_birth;
        friends = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBreed(Breed breed) {
        this.breed = breed;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Breed getBreed() {
        return breed;
    }

    public Color getColor() {
        return color;
    }

    public Date getDate_of_birth() {
        return date_of_birth;
    }

    public Owner getOwner() {
        return owner;
    }

    public List<Cat> getFriends() {
        return friends;
    }

    public void setFriends(List<Cat> friends) {
        this.friends = friends;
    }

    public void AddFriend(Cat cat) {
        this.friends.add(cat);
    }

    public void DeleteFriend(Cat cat) {
        this.friends.remove(cat);
    }
}
