package test;

import io.micronaut.http.HttpStatus;
import io.reactivex.Single;

import io.micronaut.http.annotation.Get;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

@io.micronaut.http.annotation.Controller
public class Controller {
    Logger log = LoggerFactory.getLogger(Controller.class);

    @Get("/handler_last")
    public Single<String> badRequest() {
        return Single.error(new ApplicationException(HttpStatus.BAD_REQUEST, ""));
    }

    @Get("/handler_first")
    public Single<String> notFound() {
        return Single.error(new ApplicationExceptionWithEarlyHandler(HttpStatus.NOT_FOUND, ""));
    }

}
