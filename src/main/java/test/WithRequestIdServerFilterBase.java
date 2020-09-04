package test;

import io.micronaut.core.order.Ordered;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.HttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import io.reactivex.Flowable;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import static java.util.UUID.randomUUID;

public abstract class WithRequestIdServerFilterBase implements HttpServerFilter {
    Logger log = LoggerFactory.getLogger(WithRequestIdServerFilterBase.class);

    @Override
    public Publisher<MutableHttpResponse<?>> doFilter(io.micronaut.http.HttpRequest<?> request, ServerFilterChain chain) {
        log.info("In request id filter");
        String correlationId = MDC.get("correlationId");
        if (correlationId == null) {
            correlationId = request.getAttribute("correlationId", String.class).orElse(null);
        }
        if (correlationId == null) {
            correlationId = randomUUID().toString();
            log.info("Setting correlationId to " + correlationId + " on " + Thread.currentThread().getName());
            MDC.put("correlationId", correlationId);
            request.setAttribute("correlationId", correlationId);
        }
        return chain.proceed(request);
    }
}
