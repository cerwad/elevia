package fr.baralecorp.elevia.dao;

import fr.baralecorp.elevia.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}