package peaksoft.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import peaksoft.model.enums.MovieGenres;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private MovieGenres genres;

    private LocalDate creteDate;

    private String country;

    private String language;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, mappedBy = "movie")
    private List<Session> sessions;


}
