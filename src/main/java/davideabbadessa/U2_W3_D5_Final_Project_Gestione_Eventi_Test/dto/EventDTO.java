package davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
public class EventDTO {
    private UUID id;
    private String title;
    private String description;
    private LocalDateTime date;
    private String location;
    private int availableSeats;
    private UUID creatorId;

    public EventDTO(UUID id, String title, String description, LocalDateTime date, String location, int availableSeats, UUID creatorId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.location = location;
        this.availableSeats = availableSeats;
        this.creatorId = creatorId;
    }

}
