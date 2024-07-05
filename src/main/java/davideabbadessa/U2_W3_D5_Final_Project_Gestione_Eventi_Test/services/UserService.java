package davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.services;


import davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.dto.UserDTO;
import davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.entities.User;
import davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.exceptions.BadRequestException;
import davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.exceptions.NotFoundException;
import davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public User saveUser(UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.username()).isEmpty()) {
            User user = new User(userDTO.username(), passwordEncoder.encode(userDTO.password()));
            return userRepository.save(user);
        } else {
            throw new BadRequestException(userDTO.username() + " Già in uso!");
        }
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Username non trovato"));
    }

    public User findById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }
}
