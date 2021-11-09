package fr.baralecorp.elevia.batch;

import fr.baralecorp.elevia.dao.ResultRepository;
import fr.baralecorp.elevia.dao.UserRepository;
import fr.baralecorp.elevia.domain.ExerciseType;
import fr.baralecorp.elevia.domain.Result;
import fr.baralecorp.elevia.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class InitH2DB {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private BCryptPasswordEncoder passwordHasher;

    public void addUsers() {
        List<User> users = new ArrayList<>();
        // Init de la BDD H2
        User user1 = new User();
        user1.setAge(18);
        user1.setEmail("amanda@gmail.com");
        user1.setFirstName("Amanda");
        user1.setName("LECUYOT");
        user1.setHandle("Amanda");
        user1.setPassword(passwordHasher.encode("amanda"));
        users.add(user1);

        User userP1 = new User();
        userP1.setAge(18);
        userP1.setEmail("audrey@gmail.com");
        userP1.setFamily("BARALE");
        userP1.setFirstName("Audrey");
        userP1.setName("BARALE");
        userP1.setHandle("didy");
        userP1.setPassword(passwordHasher.encode("audrey"));
        users.add(userP1);

        User user2 = new User();
        user2.setFirstName("Elea");
        user2.setName("Carriere");
        user2.setHandle("Elea");
        user2.setAge(7);
        user2.setFamily("BARALE");
        user2.setParent(userP1);
        user2.setPassword(passwordHasher.encode("elea"));
        users.add(user2);

        User userP2 = new User();
        userP2.setAge(33);
        userP2.setEmail("laura@gmail.com");
        userP2.setFamily("BARALE");
        userP2.setFirstName("Laura");
        userP2.setName("LECUYOT");
        userP2.setHandle("lolo");
        userP2.setPassword(passwordHasher.encode("laura"));
        users.add(userP2);

        User user3 = new User();
        user3.setFirstName("Livia");
        user3.setName("Lecuyot");
        user3.setHandle("Livia");
        user3.setAge(4);
        user3.setFamily("BARALE");
        user3.setParent(userP2);
        user3.setPassword(passwordHasher.encode("livia"));
        users.add(user3);

        User ced = new User();
        ced.setAge(37);
        ced.setEmail("cedric@gmail.com");
        ced.setFirstName("Cedric");
        ced.setName("BARALE");
        ced.setHandle("dricou");
        ced.setFamily("BARALE");
        ced.setPassword(passwordHasher.encode("cedric"));
        users.add(ced);

        userRepository.saveAll(users);
    }

    public void addResults() {
        List<Result> results = new ArrayList<>();
        Optional<User> elea = userRepository.findById(3L);
        if (elea.isPresent()) {
            Result res1 = new Result();
            res1.setDay(LocalDateTime.of(2021, 6, 1, 15, 0));
            res1.setExercise(ExerciseType.MULTIPLICATION);
            res1.setNbErrors((short) 0);
            res1.setTime(Duration.ofMillis(35500));
            res1.setUser(elea.get());
            results.add(res1);

            Result res2 = new Result();
            res2.setDay(LocalDateTime.of(2021, 6, 1, 14, 30));
            res2.setExercise(ExerciseType.MULTIPLICATION);
            res2.setNbErrors((short) 0);
            res2.setTime(Duration.ofSeconds(40));
            res2.setUser(elea.get());
            results.add(res2);

            Result res3 = new Result();
            res3.setDay(LocalDateTime.of(2021, 5, 30, 14, 30));
            res3.setExercise(ExerciseType.MULTIPLICATION);
            res3.setNbErrors((short) 1);
            res3.setTime(Duration.ofSeconds(42));
            res3.setUser(elea.get());
            results.add(res3);
        }

        Optional<User> livia = userRepository.findById(5L);
        if (livia.isPresent()) {
            Result res1 = new Result();
            res1.setDay(LocalDateTime.of(2021, 6, 1, 15, 5));
            res1.setExercise(ExerciseType.MULTIPLICATION);
            res1.setNbErrors((short) 3);
            res1.setTime(Duration.ofSeconds(70));
            res1.setUser(livia.get());
            results.add(res1);

            Result res2 = new Result();
            res2.setDay(LocalDateTime.of(2021, 6, 1, 15, 10));
            res2.setExercise(ExerciseType.MULTIPLICATION);
            res2.setNbErrors((short) 0);
            res2.setTime(Duration.ofSeconds(65));
            res2.setUser(livia.get());
            results.add(res2);
        }

        Optional<User> ced = userRepository.findById(6L);
        if (ced.isPresent()) {
            Result res1 = new Result();
            res1.setDay(LocalDateTime.of(2021, 5, 28, 15, 0));
            res1.setExercise(ExerciseType.MULTIPLICATION);
            res1.setNbErrors((short) 0);
            res1.setTime(Duration.ofMillis(35800));
            res1.setUser(ced.get());
            results.add(res1);

            Result res2 = new Result();
            res2.setDay(LocalDateTime.of(2021, 6, 1, 14, 30));
            res2.setExercise(ExerciseType.MULTIPLICATION);
            res2.setNbErrors((short) 0);
            res2.setTime(Duration.ofSeconds(41));
            res2.setUser(ced.get());
            results.add(res2);
        }
        resultRepository.saveAll(results);

    }
}
