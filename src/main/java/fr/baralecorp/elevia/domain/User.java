package fr.baralecorp.elevia.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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

    private int age;

    @NotBlank(message = "Handle is mandatory")
    private String handle;

    private String family;

    private String password;

    @ManyToOne
    @JoinColumn(name="parentId")
    private User parent;

    @OneToMany( targetEntity= Result.class, mappedBy="user", fetch=FetchType.LAZY )
    private List<Result> results = new ArrayList<>();

    @OneToMany(targetEntity= User.class, mappedBy="parent", fetch=FetchType.LAZY)
    protected Set<User> children;

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

    public User getParent() {
        return parent;
    }

    public void setParent(User parent) {
        this.parent = parent;
    }

    public Set<User> getChildren() {
        return children;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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