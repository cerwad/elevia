package fr.baralecorp.elevia.controller.transferObj;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.Objects;

public class UserDisplay {

    private Long id;
    @NotBlank(message = "Prénom obligatoire")
    private String firstName;
    @NotBlank(message = "Nom obligatoire")
    private String name;

    @Email(message = "Le format doit correspondre à celui d'un email")
    private String email;

    @Past(message = "La date doit être dans le passé")
    @NotNull(message = "Date de naissance obligatoire")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDate;

    /*
    @NotNull
    private String birthDate;
    */
    @NotBlank
    private String handle;
    private String family;
    @NotBlank
    private String password;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isNew() {
        return id == null || id == -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDisplay that = (UserDisplay) o;
        return Objects.equals(id, that.id) && firstName.equals(that.firstName) && name.equals(that.name) && Objects.equals(email, that.email) && Objects.equals(birthDate, that.birthDate) && handle.equals(that.handle) && Objects.equals(family, that.family) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, name, email, birthDate, handle, family, password);
    }

    @Override
    public String toString() {
        return "UserDisplay{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", birthDate=" + birthDate +
                ", handle='" + handle + '\'' +
                ", family='" + family + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
