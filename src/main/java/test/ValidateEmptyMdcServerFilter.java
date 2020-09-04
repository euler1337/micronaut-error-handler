package test;

import io.micronaut.core.order.Ordered;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.HttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import io.reactivex.Flowable;
import java.util.Map;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

@Filter("/**")
public class ValidateEmptyMdcServerFilter implements HttpServerFilter {
    Logger log = LoggerFactory.getLogger(ValidateEmptyMdcServerFilter.class);

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    public Publisher<MutableHttpResponse<?>> doFilter(io.micronaut.http.HttpRequest<?> request, ServerFilterChain chain) {
        log.info("enter validation filter");
        Map<String, String> mdc = MDC.getCopyOfContextMap();
        if (mdc != null && mdc.size() > 0) {
            throw new RuntimeException("MDC is " + mdc);
        }
        return chain.proceed(request);
    }
}
