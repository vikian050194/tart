package tart.app.api.image;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.List;
import tart.app.api.*;
import static tart.app.api.ApiUtils.splitQuery;
import tart.app.errors.*;
import tart.domain.file.FileService;

public class ImageHandler extends Handler {

    private final FileService imageService;

    public ImageHandler(
            FileService imageService,
            ObjectMapper objectMapper,
            GlobalExceptionHandler exceptionHandler
    ) {
        super(objectMapper, exceptionHandler);
        this.imageService = imageService;
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

//        exchange.close();
    }

    private ResponseEntity<byte[]> doGet(URI uri) throws IOException {
        var params = splitQuery(uri.getRawQuery());
        var defaultName = "2024-11-17 09-01-03.JPG";
        var name = params.getOrDefault("name", List.of(defaultName)).stream().findFirst().orElse(defaultName);
        
        
        var fileDir = List.of("home", "kirill", "Phot");
        var file = imageService.getFileData(fileDir, name);

        return new ResponseEntity<>(file.getData(),
                getHeaders(Constants.CONTENT_TYPE, Constants.IMAGE_JPEG), StatusCode.OK);
    }
}
