package group.aist.cinema.model;

import group.aist.cinema.enums.SessionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "movie-sessions")
public class MovieSession {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_sessions_seq_gen")
    @SequenceGenerator(name = "movie_sessions_seq_gen", sequenceName = "movie_sessions_seq", allocationSize = 1)
    private Long id;

    @Column(name = "time", nullable = false)
    private LocalDate time;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private SessionType type;

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