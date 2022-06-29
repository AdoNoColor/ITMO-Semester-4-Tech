package models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "owners")
public class Owner {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "date_of_birth")
    private LocalDate date_of_birth;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cat> cats;

    public Owner() {
    }

    public Owner(String name, LocalDate date_of_birth) {
        this.name = name;
        this.date_of_birth = date_of_birth;
        cats = new ArrayList<>();
        id = UUID.randomUUID().toString();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCats(List<Cat> cats) {
        this.cats = cats;
    }

    public void addCat(Cat cat) { this.cats.add(cat); }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Cat> getCats() {
        return cats;
    }
}
