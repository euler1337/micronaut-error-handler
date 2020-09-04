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

@Filter("/lowprio")
public class WithRequestIdServerFilterLoPrio extends WithRequestIdServerFilterBase {
    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
