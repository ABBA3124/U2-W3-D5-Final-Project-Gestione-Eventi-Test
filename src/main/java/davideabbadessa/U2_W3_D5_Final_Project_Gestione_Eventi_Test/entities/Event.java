package davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.entities;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@ToString
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String title;
    private String description;
    private LocalDateTime date;
    private String location;
    private int availableSeats;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;
}
