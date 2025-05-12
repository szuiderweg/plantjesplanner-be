package nl.novi.be_plantjesplanner.exceptions;

public class DuplicateResourceException extends RuntimeException{
    public DuplicateResourceException(String message){
        super(message);
    }
}
