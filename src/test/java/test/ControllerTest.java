package test;

import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.annotation.MicronautTest;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;
import static io.micronaut.http.HttpRequest.GET;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@MicronautTest
public class ControllerTest {
    @Inject
    @Client("/")
    HttpClient client;

    @Test
    public void testHandlerLast() {
        try {
            String result = client.toBlocking().retrieve(GET("handler_last"), String.class);
        } catch (HttpClientResponseException e) {
            assertEquals(404, e.getStatus().getCode());
            return;
        }
        fail("we should not get here");
    }

    @Test
    public void testHandlerFirst() {
        try {
            String result = client.toBlocking().retrieve(GET("handler_first"), String.class);
        } catch (HttpClientResponseException e) {
            assertEquals(404, e.getStatus().getCode());
            return;
        }
        fail("we should not get here");
    }

}

