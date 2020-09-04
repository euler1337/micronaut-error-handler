package test;

import io.reactivex.Single;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

@Controller
public class MdcController {
    Logger log = LoggerFactory.getLogger(MdcController.class);

    @Get("/lowprio")
    public Single<String> lowprio() {
        log.info("Got request");
        return Single.just(MDC.get("correlationId"));
    }

    @Get("/hiprio")
    public Single<String> hiprio() {
        log.info("Got request");
        return Single.just(MDC.get("correlationId"));
    }
}
