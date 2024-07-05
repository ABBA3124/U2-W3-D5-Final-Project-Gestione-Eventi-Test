package davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.entities;


import davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.enums.Role;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@ToString
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "creator")
    private Set<Event> createdEvents;

    @ManyToMany
    @JoinTable(
            name = "user_event",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    private Set<Event> bookedEvents;
}
