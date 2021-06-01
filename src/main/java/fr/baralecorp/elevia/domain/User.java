package fr.baralecorp.elevia.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idUser;

    @NotBlank(message = "First Name is mandatory")
    private String firstName;

    @NotBlank(message = "Name is mandatory")
    private String name;

    private String email;

    @NotBlank(message = "Age is mandatory")
    private int age;

    @NotBlank(message = "Handle is mandatory")
    private String handle;

    private String family;

    @OneToMany( targetEntity= Result.class, mappedBy="user" )
    private List<Result> results = new ArrayList<>();

    // standard constructors / setters / getters / toString

    public long getId() {
        return idUser;
    }

    public void setId(long id) {
        this.idUser = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public List<Result> getResults(){
        return results;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return idUser == user.idUser;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + idUser +
                ", firstName='" + firstName + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", handle='" + handle + '\'' +
                ", family='" + family + '\'' +
                '}';
    }

}