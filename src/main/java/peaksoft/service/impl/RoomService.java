package peaksoft.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import peaksoft.model.Cinema;
import peaksoft.model.Room;
import peaksoft.service.Service;

import java.util.List;

@org.springframework.stereotype.Service
@Transactional
public class RoomService implements Service<Room> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Room findById(int id) {
        return entityManager.find(Room.class, id);
    }

    @Override
    public List<Room> findAll() {
        return (List<Room>) entityManager.createQuery("from Room ").getResultList();
    }

    public List<Room> findAllId(int id) {
        return (List<Room>) entityManager.createQuery("from Room r where r.cinema.id =:id", Room.class)
                .setParameter("id", id).getResultList();
    }

    @Override
    public Room save(Room room) {
        Cinema cinema = entityManager.find(Cinema.class, room.getCinemaId());
        room.setCinema(cinema);
        entityManager.persist(room);
        return room;
    }

    @Override
    public Room update(int id, Room room) {
        Room oldRoom = findById(id);
        oldRoom.setName(room.getName());
        oldRoom.setCinema(room.getCinema());
        oldRoom.setRating(room.getRating());
        entityManager.merge(oldRoom);
        return oldRoom;
    }

    @Override
    public void deleteById(int id) {
        entityManager.remove(entityManager.find(Room.class, id));
    }
}
