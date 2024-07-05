package davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.controllers;


import davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.dto.EventDTO;
import davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.entities.Event;
import davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.entities.User;
import davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.exceptions.UnauthorizedException;
import davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public List<EventDTO> getAllEvents() {
        return eventService.findAll().stream().map(event -> new EventDTO(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getDate(),
                event.getLocation(),
                event.getAvailableSeats(),
                event.getCreator().getId()
        )).collect(Collectors.toList());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ORGANIZER')")
    @ResponseStatus(HttpStatus.CREATED)
    public EventDTO createEvent(@RequestBody Event event) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        event.setCreator(currentUser);
        Event createdEvent = eventService.saveEvent(event);
        return new EventDTO(
                createdEvent.getId(),
                createdEvent.getTitle(),
                createdEvent.getDescription(),
                createdEvent.getDate(),
                createdEvent.getLocation(),
                createdEvent.getAvailableSeats(),
                createdEvent.getCreator().getId()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ORGANIZER')")
    public EventDTO updateEvent(@PathVariable UUID id, @RequestBody Event eventDetails) {
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

        Event updatedEvent = eventService.saveEvent(event);
        return new EventDTO(
                updatedEvent.getId(),
                updatedEvent.getTitle(),
                updatedEvent.getDescription(),
                updatedEvent.getDate(),
                updatedEvent.getLocation(),
                updatedEvent.getAvailableSeats(),
                updatedEvent.getCreator().getId()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ORGANIZER')")
    public ResponseEntity<?> deleteEvent(@PathVariable UUID id) {
        Event event = eventService.findEventById(id);
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!event.getCreator().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("Non hai i permessi per eliminare questo evento");
        }

        eventService.deleteEvent(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/book")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> bookEvent(@PathVariable UUID id) {
        Event event = eventService.findEventByIdWithBookedUsers(id);
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (event.getAvailableSeats() > 0) {
            event.getBookedUsers().add(currentUser);
            event.setAvailableSeats(event.getAvailableSeats() - 1);
            eventService.saveEvent(event);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Non ci sono posti disponibili");
        }
    }
}

