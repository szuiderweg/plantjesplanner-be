package nl.novi.be_plantjesplanner.exceptions;

import java.io.IOException;

public class UnreadableFileException  extends IOException {
    //exception without message
    public UnreadableFileException(){
        super();
    }

    public UnreadableFileException(String message){
        super(message);
    }

}
