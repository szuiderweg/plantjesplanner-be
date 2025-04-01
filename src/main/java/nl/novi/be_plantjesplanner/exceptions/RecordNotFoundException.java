package nl.novi.be_plantjesplanner.exceptions;

public class RecordNotFoundException extends RuntimeException{
    //exception without message
    public RecordNotFoundException(){
        super();
    }

    public RecordNotFoundException(String message){
        super(message);
    }


}
