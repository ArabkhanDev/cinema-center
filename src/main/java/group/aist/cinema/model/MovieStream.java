package group.aist.cinema.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "movie_streams")
public class MovieStream {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_streams_seq_gen")
    @SequenceGenerator(name = "movie_streams_seq_gen", sequenceName = "movie_streams_seq", allocationSize = 1)
    private Long id;

    private Boolean hasSubtitle;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "movie_stream_dubbing_languages",
            joinColumns = @JoinColumn(name = "movie_stream_id"),
            inverseJoinColumns = @JoinColumn(name = "dubbing_language_id")
    )
    private Set<DubbingLanguage> dubbingLanguages = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "movie_stream_subtitle_languages",
            joinColumns = @JoinColumn(name = "movie_stream_id"),
            inverseJoinColumns = @JoinColumn(name = "subtitle_language_id")
    )
    private Set<SubtitleLanguage> subtitleLanguages = new HashSet<>();

}