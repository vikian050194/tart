package tart.app.api.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import tart.app.api.Constants;
import tart.app.api.Handler;
import tart.app.api.ResponseEntity;
import tart.app.api.StatusCode;
import tart.app.errors.ApplicationExceptions;
import tart.app.errors.GlobalExceptionHandler;

public class ImageHandler extends Handler {

    public ImageHandler(ObjectMapper objectMapper,
            GlobalExceptionHandler exceptionHandler) {
        super(objectMapper, exceptionHandler);
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
