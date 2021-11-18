package fr.baralecorp.elevia.domain;

import fr.baralecorp.elevia.service.util.DateUtil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "Player")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idUser;

    @NotBlank(message = "First Name is mandatory")
    private String firstName;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @Email
    @Column(unique = true)
    private String email;

    private LocalDate birthDate;

    @NotBlank(message = "Handle is mandatory")
    @Column(unique = true)
    private String handle;

    private String family;

    private String password;

    private Boolean accountNonLocked = true;

    @ManyToOne
    @JoinColumn(name = "parentId")
    private User parent;

    @OneToMany(targetEntity = Result.class, mappedBy = "user", fetch = FetchType.LAZY)
    private List<Result> results = new ArrayList<>();

    @OneToMany(targetEntity = User.class, mappedBy = "parent", fetch = FetchType.LAZY)
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
        return DateUtil.calcAge(birthDate);
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

    public List<Result> getResults() {
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "read");
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
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
                ", birthDate=" + birthDate +
                ", handle='" + handle + '\'' +
                ", family='" + family + '\'' +
                '}';
    }

}