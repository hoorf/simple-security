package org.github.ruifengho.simplesecurity.demo;


import com.github.ruifengho.simplesecurity.exception.SimpleSecurityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class SimpleSecurityExceptionHandler {

    @ExceptionHandler(value = {SimpleSecurityException.class})
    @ResponseBody
    public ResponseEntity<String> error(SimpleSecurityException exception) {
        return new ResponseEntity(exception.getLocalizedMessage(), HttpStatus.UNAUTHORIZED);
    }
}
