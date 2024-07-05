package davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.exceptions;

import lombok.Getter;
import org.springframework.validation.ObjectError;

import java.util.List;

@Getter
public class BadRequestException extends RuntimeException {
    private List<ObjectError> errorsList;

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(List<ObjectError> errorsList) {
        super("Ci sono stati errori di validazione del payload");
        this.errorsList = errorsList;
    }

}
