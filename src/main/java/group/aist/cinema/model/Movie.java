package group.aist.cinema.model;

import group.aist.cinema.model.embeddable.MovieLanguage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movies_seq_gen")
    @SequenceGenerator(name = "movies_seq_gen", sequenceName = "movies_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "genre", nullable = false)
    private String genre;

    @Column(name = "release_date", nullable = false)
    private LocalDate releaseDate;

    @Column(name = "age_restriction", nullable = false)
    private Short ageRestriction;

    @Column(name = "duration", nullable = false)
    private String duration;

    @Column(name = "image")
    private String backgroundImage;

    @Column(name = "poster_image")
    private String posterImage;

    @Column(name = "arrival_of_cinema")
    private LocalDate arrivalOfCinema;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "movie_languages", joinColumns = @JoinColumn(name = "movie_id"))
    private Set<MovieLanguage> languages = new HashSet<>();
}