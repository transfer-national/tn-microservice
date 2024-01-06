package ma.ensa.agentservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerResolver {



    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(RuntimeException.class)
    public String handleOtherException(Exception ex){
        return ex.getMessage();
    }

}
