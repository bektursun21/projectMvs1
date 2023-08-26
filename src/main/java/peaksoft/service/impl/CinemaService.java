package peaksoft.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import peaksoft.model.Cinema;
import peaksoft.service.Service;

import java.util.List;

@org.springframework.stereotype.Service
@Transactional
public class CinemaService implements Service<Cinema> {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public Cinema findById(int id) {
        return entityManager.find(Cinema.class,id);
    }


    @Override
    public List<Cinema> findAll() {
        return (List<Cinema>) entityManager.createQuery("from Cinema ").getResultList();
    }

    @Override
    public Cinema save(Cinema cinema) {
       entityManager.persist(cinema);
        return cinema;
    }

    @Override
    public Cinema update(int id, Cinema cinema) {
        Cinema oldCinema = findById(id);
        oldCinema.setName(cinema.getName());
        oldCinema.setAddress(cinema.getAddress());
        entityManager.persist(oldCinema);
        return oldCinema;
    }

    @Override
    public void deleteById(int id) {
        entityManager.remove(entityManager.find(Cinema.class,id));
    }
}
