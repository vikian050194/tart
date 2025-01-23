package tart.app.api.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.List;
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
    public boolean auth() {
        return true;
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

        // TODO is it needed?
        exchange.close();
    }

    private ResponseEntity<byte[]> doGet(URI uri) throws IOException {
        var path = uri.getRawPath();
        var foo = path.substring(url().length());

        switch (foo) {
            case "": {
                var params = splitQuery(uri.getRawQuery());
                var dir = params.getOrDefault("dir", List.of()).stream().toList();
                var name = params.get("name").stream().findFirst().orElseThrow();
//                var name = "";

                if (dir.isEmpty()) {
                    var dirs = imageService.getDirectories();

                    return new ResponseEntity<>(dirs.toString().getBytes(),
                            getHeaders(Constants.CONTENT_TYPE, Constants.TEXT_HTML), StatusCode.OK);
                }

                // TODO name is optional
                var file = imageService.getFileData(dir, name);

                return new ResponseEntity<>(file.getData(),
                        getHeaders(Constants.CONTENT_TYPE, Constants.IMAGE_JPEG), StatusCode.OK);
            }
            default:
                throw new AssertionError();
        }
    }
}
