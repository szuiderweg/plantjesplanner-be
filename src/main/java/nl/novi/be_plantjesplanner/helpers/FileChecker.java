package nl.novi.be_plantjesplanner.helpers;

import nl.novi.be_plantjesplanner.exceptions.InvalidImageTypeException;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class FileChecker {
    private static final List<String> ALLOWED_TYPES = List.of("image/png", "image/jpeg", "image/jpg","image/webp", "image/svg+xml" );//allowed file types for uploaded files
    private FileChecker(){}//private constructor to prevent accidental instantiation

    //helper to check if an uploaded file is indeed an image in a supported format
    public static void checkUploadedImage(MultipartFile file){
        if(file.isEmpty()){
            throw new IllegalArgumentException("Het bestand is leeg! probeer het opnieuw");
        }
        String contentType = file.getContentType();
        if (!ALLOWED_TYPES.contains(contentType.toLowerCase())) {
            String errorMessage = "Ongeldig bestandstype: Alleen .png, .jpeg, jpg, .webp en .svg zijn toegestaan.";
            throw new InvalidImageTypeException(errorMessage);
        }
    }
    //checks if the mediatype is in ALLOWED_TYPES
    public static boolean checkMediaType(MediaType mediaType){
        return ALLOWED_TYPES.contains(mediaType.toString());
    }

}
