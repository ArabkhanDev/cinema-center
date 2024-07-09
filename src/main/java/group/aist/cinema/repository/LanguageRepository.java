package group.aist.cinema.repository;

import group.aist.cinema.model.Language;
import group.aist.cinema.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LanguageRepository extends JpaRepository<Language, Long> {

    List<Movie> findByName(String language);

}
