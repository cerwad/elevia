package fr.baralecorp.elevia.service;

import fr.baralecorp.elevia.controller.transferObj.UserDisplay;
import fr.baralecorp.elevia.dao.UserRepository;
import fr.baralecorp.elevia.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordHasher;

    public void save(UserDisplay newUser) {

        User user;
        if (newUser.isNew()) {
            user = toUser(newUser);
        } else {
            user = userRepository.findById(newUser.getId()).orElse(null);
        }
        if (user != null) {
            userRepository.save(user);
        }
    }

    public UserDisplay getUserById(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("This id does not exist"));
        return convertUser(user);
    }


    public static UserDisplay convertUser(User user) {
        UserDisplay userDisplay = null;
        if (user != null) {
            userDisplay = new UserDisplay();
            userDisplay.setId(user.getId());
            userDisplay.setEmail(user.getEmail());
            userDisplay.setBirthDate(user.getBirthDate());
            userDisplay.setFirstName(user.getFirstName());
            userDisplay.setName(user.getName());
            userDisplay.setHandle(user.getHandle());
            userDisplay.setFamily(user.getFamily());
        }
        return userDisplay;
    }

    public User toUser(UserDisplay userDisplay) {
        User user = new User();
        user.setId(userDisplay.getId());
        user.setEmail(userDisplay.getEmail());
        user.setPassword(passwordHasher.encode(userDisplay.getPassword()));
        user.setBirthDate(userDisplay.getBirthDate());
        user.setName(userDisplay.getName());
        user.setFirstName(userDisplay.getFirstName());
        user.setFamily(userDisplay.getFamily());
        user.setHandle(userDisplay.getHandle());

        return user;
    }

    public Optional<UserDisplay> getUserByHandle(String handle) {
        Optional<User> user = userRepository.findUserByHandle(handle);
        return user.map(UserService::convertUser);
    }

    public Optional<UserDisplay> getUserByEmail(String email) {
        Optional<User> user = userRepository.findUserByEmail(email);
        return user.map(UserService::convertUser);
    }
}
