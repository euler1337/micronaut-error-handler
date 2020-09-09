package test;

import com.fasterxml.jackson.core.JsonParseException;
import io.micronaut.core.convert.exceptions.ConversionErrorException;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Error;
import java.util.Map;
import javax.validation.ConstraintViolationException;
import static io.micronaut.http.HttpStatus.BAD_REQUEST;
import static io.micronaut.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Controller("/defaultresponses")
public class DefaultResponses {

    protected static final Map<Class, HttpStatus> EXCEPTION_TO_STATUS_MAPPING = Map.of(
        ConstraintViolationException.class, BAD_REQUEST,
        JsonParseException.class, BAD_REQUEST,
        ConversionErrorException.class, BAD_REQUEST,
        Throwable.class, INTERNAL_SERVER_ERROR
    );

    public static HttpStatus getStatus(Class clazz) {
        return EXCEPTION_TO_STATUS_MAPPING.getOrDefault(clazz, INTERNAL_SERVER_ERROR);
    }

    @Error(global = true)
    public HttpResponse<?> ApplicationExceptionWithEarlyHandler(
        HttpRequest request,
        ApplicationExceptionWithEarlyHandler e
    ) {
        final HttpStatus status = e.getStatus();
        final Object body = e.getBody().orElseGet(() -> buildError(request, e)
                                                            .error(e.getErrorType())
                                                            .build());
        return HttpResponse.status(status)
                   .body(body)
                   .contentType(MediaType.APPLICATION_JSON_TYPE);
    }

    @Error(global = true)
    public HttpResponse<ErrorResponse> error(HttpRequest request, Throwable e) {
        final ErrorResponse body = buildError(request, e).build();
        HttpStatus status = getStatus(e.getClass());
        return createResponse(status, body);
    }

    @Error(global = true)
    public HttpResponse<?> applicationException(HttpRequest request, ApplicationException e) {
        final HttpStatus status = e.getStatus();
        final Object body = e.getBody().orElseGet(() -> buildError(request, e)
                                                            .error(e.getErrorType())
                                                            .build());
        return HttpResponse.status(status)
                   .body(body)
                   .contentType(MediaType.APPLICATION_JSON_TYPE);
    }

    public HttpResponse<ErrorResponse> createResponse(HttpStatus status, ErrorResponse body) {
        return HttpResponse.<ErrorResponse>status(status)
                   .body(body)
                   .contentType(MediaType.APPLICATION_JSON_TYPE);
    }

    protected ErrorResponse.ErrorResponseBuilder buildError(HttpRequest request, Throwable e) {
        return ErrorResponse.builder()
                   .path(request.getUri().getPath())
                   .error(e.getMessage());
    }
}
