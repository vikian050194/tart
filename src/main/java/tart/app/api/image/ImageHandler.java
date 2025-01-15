package tart.app.api.image;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import tart.app.api.*;
import tart.app.errors.*;
import tart.domain.image.ImageService;

public class ImageHandler extends Handler {

    private final ImageService imageService;
    
    public ImageHandler(
            ImageService imageService,
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
            var e = doGet();
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

    private ResponseEntity<byte[]> doGet() throws IOException {
        var filepath = "/home/kirill/Phot/20230901_170650.jpg";
        byte[] bytes;

        try (RandomAccessFile f = new RandomAccessFile(filepath, "r")) {
            bytes = new byte[(int) f.length()];
            f.read(bytes);
        }

        return new ResponseEntity<>(bytes,
                getHeaders(Constants.CONTENT_TYPE, Constants.IMAGE_JPEG), StatusCode.OK);
    }
}
