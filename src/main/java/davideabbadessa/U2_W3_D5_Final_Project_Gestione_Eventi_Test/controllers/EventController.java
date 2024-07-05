package davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.controllers;


import davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.entities.Event;
import davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.entities.User;
import davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.exceptions.UnauthorizedException;
import davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.services.EventService;
import davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.findAll();
    }

    @PostMapping
    public Event createEvent(@RequestBody Event event) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        event.setCreator(currentUser);
        return eventService.saveEvent(event);
    }

    @PutMapping("/{id}")
    public Event updateEvent(@PathVariable UUID id, @RequestBody Event eventDetails) {
        Event event = eventService.findEventById(id);
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!event.getCreator().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("Non hai i permessi per modificare questo evento");
        }

        event.setTitle(eventDetails.getTitle());
        event.setDescription(eventDetails.getDescription());
        event.setDate(eventDetails.getDate());
        event.setLocation(eventDetails.getLocation());
        event.setAvailableSeats(eventDetails.getAvailableSeats());

        return eventService.saveEvent(event);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable UUID id) {
        Event event = eventService.findEventById(id);
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!event.getCreator().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("Non hai i permessi per eliminare questo evento");
        }

        eventService.deleteEvent(id);
        return ResponseEntity.ok().build();
    }
}
}
