package tart.app.api.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.List;
import static tart.app.api.ApiUtils.splitQuery;
import tart.app.api.Constants;
import tart.app.api.Handler;
import tart.app.api.ResponseEntity;
import tart.app.api.StatusCode;
import tart.app.errors.ApplicationExceptions;
import tart.app.errors.GlobalExceptionHandler;

public class HelloHandler extends Handler {

    public HelloHandler(ObjectMapper objectMapper,
            GlobalExceptionHandler exceptionHandler) {
        super(objectMapper, exceptionHandler);
    }

    @Override
    protected void execute(HttpExchange exchange) throws IOException {
        byte[] response;

        if ("GET".equals(exchange.getRequestMethod())) {
            ResponseEntity e = doGet(exchange.getRequestURI());
//            response = super.writeResponse(e.getBody());
            response = e.getBody().toString().getBytes();
            exchange.getResponseHeaders().putAll(e.getHeaders());
            exchange.sendResponseHeaders(e.getStatusCode().getCode(), response.length);
        } else {
            throw ApplicationExceptions.methodNotAllowed(
                    "Method " + exchange.getRequestMethod() + " is not allowed for " + exchange.getRequestURI()).get();
        }

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response);
        }

//        exchange.close();
    }

    private ResponseEntity<String> doGet(URI uri) {
        var params = splitQuery(uri.getRawQuery());
        var noNameText = "Anonymous";
        var name = params.getOrDefault("name", List.of(noNameText)).stream().findFirst().orElse(noNameText);
        var response = String.format("Hello, %s!", name);

        return new ResponseEntity<>(response,
                getHeaders(Constants.CONTENT_TYPE, Constants.TEXT_HTML), StatusCode.OK);
    }
}
