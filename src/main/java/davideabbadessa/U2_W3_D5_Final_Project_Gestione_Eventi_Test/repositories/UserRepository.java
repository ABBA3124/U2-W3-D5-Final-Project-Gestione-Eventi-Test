package davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.repositories;

import davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
}
