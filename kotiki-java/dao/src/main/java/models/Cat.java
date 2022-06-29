package models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "cats")
public class Cat {
    @Id
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "breed")
    @Enumerated(EnumType.STRING)
    private Breed breed;

    @Column(name = "color")
    @Enumerated(EnumType.STRING)
    private Color color;

    @Column(name = "birth_date")
    private LocalDate date_of_birth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private Owner owner;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "cats_friendships", joinColumns = {@JoinColumn(name = "cat1_id")}, inverseJoinColumns = {@JoinColumn(name = "cat2_id")})
    private List<Cat> friends;

    public Cat() {
    }

    public Cat(String name, Breed breed, Color color, LocalDate date_of_birth) {
        this.name = name;
        this.breed = breed;
        this.color = color;
        this.date_of_birth = date_of_birth;
        friends = new ArrayList<>();
        id = UUID.randomUUID().toString();
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

    public void setDate_of_birth(LocalDate date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getId() {
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

    public LocalDate getDate_of_birth() {
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
}
