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
@Table(name = "sectors")
public class Sector {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "raw", nullable = false)
    private String raw;

    @Column(name = "`column`", nullable = false)
    private String column;

    @Column(name = "name", nullable = false)
    private String name;

}