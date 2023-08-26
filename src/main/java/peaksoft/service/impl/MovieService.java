package peaksoft.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import peaksoft.model.Movie;
import peaksoft.service.Service;

import java.time.LocalDate;
import java.util.List;

@org.springframework.stereotype.Service
@Transactional
public class MovieService implements Service<Movie> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Movie findById(int id) {
        return entityManager.find(Movie.class,id);
    }

    @Override
    public List<Movie> findAll() {
        return (List<Movie>) entityManager.createQuery("from Movie").getResultList();
    }

    @Override
    public Movie save(Movie movie) {
        movie.setCreteDate(LocalDate.now());
        entityManager.persist(movie);
        return movie;
    }

    @Override
    public Movie update(int id, Movie movie) {
//        Movie oldMovie = new Movie();
        Movie oldMovie = findById(id);
        oldMovie.setName(movie.getName());
        oldMovie.setGenres(movie.getGenres());
        oldMovie.setCountry(movie.getCountry());
        entityManager.persist(oldMovie);
        return oldMovie;

    }

    @Override
    public void deleteById(int id) {
        entityManager.remove(entityManager.find(Movie.class,id));
    }
//    public List<MovieGenres> movieGenres(){
//        List<MovieGenres> genres = MovieGenres.values();
//    }
}
