package by.tutin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleServiceException(Exception exception) {
        return new ResponseEntity<>(exception.getLocalizedMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<String> handleServiceException(ServiceException exception) {
        return new ResponseEntity<>(exception.getLocalizedMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(DaoException.class)
    public ResponseEntity<String> handleServiceException(DaoException exception) {
        return new ResponseEntity<>(exception.getLocalizedMessage(), HttpStatus.FORBIDDEN);
    }


}
