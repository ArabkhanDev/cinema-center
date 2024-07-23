package group.aist.cinema.model;

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

    @Column(name = "poster_image")
    private String posterImage;

    @Column(name = "background_image")
    private String backgroundImage;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "movie_dubbing_languages",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "dubbing_language_id")
    )
    private Set<DubbingLanguage> dubbingLanguages = new HashSet<>();
}