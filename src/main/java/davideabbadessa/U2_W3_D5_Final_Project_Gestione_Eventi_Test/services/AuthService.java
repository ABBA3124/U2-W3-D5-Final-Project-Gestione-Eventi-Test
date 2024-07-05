package davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.services;


import davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.dto.UserLoginDTO;
import davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.entities.User;
import davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.exceptions.UnauthorizedException;
import davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String authUserAndGenerateToken(UserLoginDTO payload) {
        User user = this.userService.findByUsername(payload.username());

        if (passwordEncoder.matches(payload.password(), user.getPassword())) {
            return jwtTools.creaToken(user);
        } else {
            throw new UnauthorizedException("Credenziali non corrette!");
        }
    }
}
