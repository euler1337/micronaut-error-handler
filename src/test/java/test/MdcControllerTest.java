package test;

import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.annotation.MicronautTest;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;
import static io.micronaut.http.HttpRequest.GET;
import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class MdcControllerTest {
    @Inject
    @Client("/")
    HttpClient client;

    // Works (unless run after the other test)
    @Test
    public void lowprio() {
        Set<String> correlationIds = new HashSet<>();
        int n = 20;
        for (int i = 0; i < n; i++) {
            correlationIds.add(client.toBlocking().retrieve(GET("lowprio"), String.class));
        }
        assertEquals(n, correlationIds.size());
    }

    // Doesnt work
    @Test
    public void hiprio() {
        Set<String> correlationIds = new HashSet<>();
        int n = 20;
        for (int i = 0; i < n; i++) {
            correlationIds.add(client.toBlocking().retrieve(GET("hiprio"), String.class));
        }
        assertEquals(n, correlationIds.size());
    }
}

