package group.aist.cinema.repository;

import group.aist.cinema.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findUserByEmail(String email);

    @EntityGraph(value = "User.balance", type = EntityGraph.EntityGraphType.LOAD)
    Page<User> findAll(Pageable pageable);

    @EntityGraph(value = "User.balance", type = EntityGraph.EntityGraphType.LOAD)
    Optional<User> findById(Long id);
}
