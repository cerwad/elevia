package fr.baralecorp.elevia.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    @Test
    public void testUserCreation() {
        // When
        String name = "Barale";
        String firstName = "Cedric";
        LocalDate birthDate = LocalDate.of(1984, 9, 17);
        String handle = "dricou";
        String family = "BARALE";
        String email = "cedric@gmail.com";
        String password = "toto";

        User user = new User();
        user.setBirthDate(birthDate);
        user.setEmail(email);
        user.setFamily(family);
        user.setHandle(handle);
        user.setFirstName(firstName);
        user.setName(name);
        user.setPassword(password);

        // Then
        assertEquals(birthDate, user.getBirthDate());
        assertEquals(37, user.getAge());
        assertEquals(name, user.getName());
        assertEquals(firstName, user.getFirstName());
        assertEquals(family, user.getFamily());
        assertEquals(password, user.getPassword());
        assertEquals(email, user.getEmail());
        assertEquals(handle, user.getHandle());
    }
}
