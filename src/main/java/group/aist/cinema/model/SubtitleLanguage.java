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
@Table(name = "subtitle_languages")
public class SubtitleLanguage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subtitle_languages_seq_gen")
    @SequenceGenerator(name = "subtitle_languages_seq_gen", sequenceName = "subtitle_languages_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "iso_code", nullable = false)
    private String isoCode;

}