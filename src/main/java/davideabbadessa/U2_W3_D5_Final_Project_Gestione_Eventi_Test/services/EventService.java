package davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.services;


import davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.entities.Event;
import davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.exceptions.NotFoundException;
import davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.repositories.EventRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    public void deleteEvent(UUID id) {
        eventRepository.deleteById(id);
    }

    public Event findEventById(UUID id) {
        return eventRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Event findEventByIdWithBookedUsers(UUID id) {
        TypedQuery<Event> query = entityManager.createQuery(
                "SELECT e FROM Event e LEFT JOIN FETCH e.bookedUsers WHERE e.id = :id", Event.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }
}
