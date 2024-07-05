package davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.repositories;

import davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {
}
