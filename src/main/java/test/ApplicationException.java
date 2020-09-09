package test;

import io.micronaut.http.HttpStatus;
import java.util.Optional;
import lombok.Getter;

public class ApplicationException extends RuntimeException {

    @Getter
    private final HttpStatus status;
    private final Object body;

    public ApplicationException(final HttpStatus status, final String message) {
        super(message);
        this.status = status;
        this.body = Optional.empty();
    }

    public Optional<Object> getBody() {
        return Optional.ofNullable(body);
    }

    public String getErrorType() {
        return status.name();
    }

    @Override
    public String toString() {
        return status.getCode() + " - " + getErrorType() + ". " + super.getMessage();
    }
}
