package tart.app.api.hello;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tart.app.App;

class HelloHandlerTest {

    static App app;
    static final int PORT = 9090;
    static String baseAddress = "http://localhost:%d/api".formatted(PORT);

    @BeforeAll
    public static void initializeApp() throws IOException {
        app = new App(PORT);
        app.init();
        app.start();
    }

    @AfterAll
    public static void stopApp() {
        app.stop(0);
        app = null;
    }

    @Test
    void testAnonymousCall() throws IOException, InterruptedException, URISyntaxException {
        // Arrange
        var expectedStatus = 200;
        var expectedBody = "Hello, Anonymous!";
        var client = HttpClient.newHttpClient();
        var uri = new URI("%s/%s".formatted(baseAddress, "hello"));

        // Act
        var response = client.send(
                HttpRequest.newBuilder().GET().uri(uri).build(),
                BodyHandlers.ofString());

        // Assert
        assertEquals(expectedStatus, response.statusCode());
        assertEquals(expectedBody, response.body());
    }

    @Test
    void testUserCall() throws IOException, InterruptedException, URISyntaxException {
        // Arrange
        var expectedStatus = 200;
        var expectedBody = "Hello, V!";
        var client = HttpClient.newHttpClient();
        var uri = new URI("%s/%s?name=V".formatted(baseAddress, "hello"));

        // Act
        var response = client.send(
                HttpRequest.newBuilder().GET().uri(uri).build(),
                BodyHandlers.ofString());

        // Assert
        assertEquals(expectedStatus, response.statusCode());
        assertEquals(expectedBody, response.body());
    }
}
