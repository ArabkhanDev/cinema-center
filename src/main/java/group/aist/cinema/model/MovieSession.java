package group.aist.cinema.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "movie_sessions")
public class MovieSession {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_sessions_seq_gen")
    @SequenceGenerator(name = "movie_sessions_seq_gen", sequenceName = "movie_sessions_seq", allocationSize = 1)
    private Long id;

    @Column(name = "time")
    @ElementCollection
    @CollectionTable(name = "movie_session_times", joinColumns = @JoinColumn(name = "movie_session_id"))
    private Set<LocalDateTime> time;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hall_id", nullable = false)
    private Hall hall;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "movie_stream_id", nullable = false)
    private MovieStream movieStream;

}