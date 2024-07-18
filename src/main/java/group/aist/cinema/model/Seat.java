package group.aist.cinema.model;

import group.aist.cinema.enums.SeatType;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "seats")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seats_seq_gen")
    @SequenceGenerator(name = "seats_seq_gen", sequenceName = "seats_seq", allocationSize = 1)
    private Long id;

    @Column(name = "horizontal", nullable = false)
    private String horizontal;

    @Column(name = "vertical", nullable = false)
    private String vertical;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private SeatType type;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sector_id", nullable = false)
    private Sector sector;
}