package group.aist.cinema.repository;

import group.aist.cinema.model.MovieSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieSessionRepository extends JpaRepository<MovieSession, Long> { }
