package group.aist.cinema.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "movie-streams")
public class MovieStream {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "subtitle", nullable = false)
    private Boolean subtitle = false;

    @Column(name = "dubbing_type", nullable = false)
    private String dubbingType;

}