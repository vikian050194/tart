package tart.app;

import tart.app.api.hello.HelloHandler;
import tart.app.api.image.ImageHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import static tart.app.Configuration.*;
import tart.app.api.user.*;

public final class App {

    public static void main(String[] args) throws IOException {
        // TODO read port from config
        int serverPort = 8000;

        var server = HttpServer.create(new InetSocketAddress(serverPort), 0);

        var registrationHandler = new RegistrationHandler(getUserService(), getObjectMapper(),
                getErrorHandler());
        server.createContext("/api/users/register", registrationHandler::handle);

        var imageHandler = new ImageHandler(getImageService(), getObjectMapper(),
                getErrorHandler());
        server.createContext("/api/image", imageHandler::handle);

        var helloHandler = new HelloHandler(getObjectMapper(),
                getErrorHandler());
        server.createContext("/api/hello", helloHandler::handle);

//        context.setAuthenticator(new BasicAuthenticator("myrealm") {
//            @Override
//            public boolean checkCredentials(String user, String pwd) {
//                return user.equals("admin") && pwd.equals("admin");
//            }
//        });
        server.setExecutor(null); // creates a default executor
        server.start();
    }
}
