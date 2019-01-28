package jrc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EndpointNotActiveException extends RuntimeException {

    public EndpointNotActiveException(String message) {
        super(message);
    }
}
