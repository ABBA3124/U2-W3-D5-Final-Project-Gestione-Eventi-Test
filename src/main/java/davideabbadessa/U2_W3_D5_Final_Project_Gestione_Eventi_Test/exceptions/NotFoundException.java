package davideabbadessa.U2_W3_D5_Final_Project_Gestione_Eventi_Test.exceptions;


import java.util.UUID;


public class NotFoundException extends RuntimeException {
    public NotFoundException(UUID id) {
        super("ID: --> " + id + " non trovato!");
    }

    public NotFoundException(String messaggio) {
        super(messaggio);
    }
}
