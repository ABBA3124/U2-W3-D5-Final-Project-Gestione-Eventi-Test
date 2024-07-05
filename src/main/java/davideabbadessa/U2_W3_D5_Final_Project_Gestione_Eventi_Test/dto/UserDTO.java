package davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserDTO(
        @NotEmpty(message = "Username è richiesto!")
        String username,

        @NotEmpty(message = "La password è un dato obbligatorio!")
        @Size(min = 6, message = "La password deve avere almeno 6 caratteri!")
        String password
) {
}




