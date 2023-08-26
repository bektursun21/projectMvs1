package peaksoft.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import peaksoft.model.Movie;
import peaksoft.model.Session;
import peaksoft.service.Service;

import java.util.List;

@org.springframework.stereotype.Service
@Transactional
public class SessionService implements Service<Session> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Session findById(int id) {
        return entityManager.find(Session.class, id);
    }

    @Override
    public List<Session> findAll() {
        return (List<Session>) entityManager.createQuery("from Session " , Session.class).getResultList();
    }

    @Override
    public Session save(Session session) {
//        Session session1 = entityManager.createQuery("from Session where id = :id", Session.class).setParameter("id", session.)
        Movie movie = entityManager.createQuery("from Movie where id = :id", Movie.class).setParameter("id", session.getMovieId()).getSingleResult();
        session.setMovie(movie);
        System.out.println(movie.getName());
        entityManager.persist(session);
        return session;
    }

    @Override
    public Session update(int id, Session session) {
        Session oldSession = findById(id);
        oldSession.setName(session.getName());
        oldSession.setStart(session.getStart());
        oldSession.setFinish(session.getFinish());
        entityManager.merge(oldSession);
        return oldSession;
    }

    @Override
    public void deleteById(int id) {
        entityManager.remove(entityManager.find(Session.class, id));
    }
}
