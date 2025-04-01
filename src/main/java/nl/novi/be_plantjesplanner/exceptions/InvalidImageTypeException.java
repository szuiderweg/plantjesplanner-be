package nl.novi.be_plantjesplanner.exceptions;

public class InvalidImageTypeException extends IllegalArgumentException {
    //exception without message
    public InvalidImageTypeException(){
        super();
    }

    public InvalidImageTypeException(String message){
        super(message);
    }
}
