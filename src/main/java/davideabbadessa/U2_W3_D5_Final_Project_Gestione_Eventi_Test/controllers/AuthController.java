package davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.controllers;


import davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.dto.UserDTO;
import davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.dto.UserLoginDTO;
import davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.dto.UserLoginResponseDTO;
import davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.dto.UserResponseDTO;
import davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.exceptions.BadRequestException;
import davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.services.AuthService;
import davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public UserLoginResponseDTO login(@RequestBody UserLoginDTO payload) {
        return new UserLoginResponseDTO(authService.authUserAndGenerateToken(payload));
    }


    @PostMapping("/registrati")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO createUser(@Validated @RequestBody UserDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            System.out.println(validationResult.getAllErrors());
            throw new BadRequestException(validationResult.getAllErrors());
        }
        return new UserResponseDTO(this.userService.saveUser(body).getId());
    }
}