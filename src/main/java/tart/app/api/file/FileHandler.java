package tart.app.api.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import tart.app.api.*;
import tart.app.errors.*;
import tart.domain.file.FileService;

public class FileHandler extends Handler {

    private final FileService imageService;

    public FileHandler(
            FileService imageService,
            ObjectMapper objectMapper,
            GlobalExceptionHandler exceptionHandler
    ) {
        super(objectMapper, exceptionHandler);
        this.imageService = imageService;
    }

    @Override
    public String url() {
        // TODO is it better to store or return array?
        return URL_PREFIX + "file";
    }

    @Override
    protected void execute(HttpExchange exchange) throws IOException {
        byte[] response;

        if ("GET".equals(exchange.getRequestMethod())) {
            var e = doGet(exchange.getRequestURI());
//            response = super.writeResponse(e.getBody());
            response = e.getBody();
            exchange.getResponseHeaders().putAll(e.getHeaders());
            exchange.sendResponseHeaders(e.getStatusCode().getCode(), response.length);
        } else {
            throw ApplicationExceptions.methodNotAllowed(
                    "Method " + exchange.getRequestMethod() + " is not allowed for " + exchange.getRequestURI()).get();
        }

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response);
        }

        // TODO is close call redundant?
        exchange.close();
    }

    private ResponseEntity<byte[]> doGet(URI uri) throws IOException {
        var params = splitQuery(uri.getRawQuery());
        var dir = params.get("dir").stream().toList();
        var name = params.get("name").stream().findFirst().orElseThrow();

        var file = imageService.getFileData(dir, name);

        return new ResponseEntity<>(file.getData(),
                getHeaders(Constants.CONTENT_TYPE, Constants.IMAGE_JPEG), StatusCode.OK);
    }
}
