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

    @Column(name = "row")
    private String row;

    @Column(name = "number")
    private Integer number;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private SeatType type;

}