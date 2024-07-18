package group.aist.cinema.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "halls")
public class Hall {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "halls_seq_gen")
    @SequenceGenerator(name = "halls_seq_gen", sequenceName = "halls_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "seat_count", nullable = false)
    private Integer seatCount;

    @Column(name = "available_seat", nullable = false)
    private Integer availableSeat;

}