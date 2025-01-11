package tart.app;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import static tart.app.Configuration.getErrorHandler;
import static tart.app.Configuration.getObjectMapper;
import static tart.app.Configuration.getUserService;
import tart.app.api.user.FooHandler;
import tart.app.api.user.HelloHandler;
import tart.app.api.user.RegistrationHandler;

public final class App {

    public static void main(String[] args) throws IOException {
        int serverPort = 8000;
        var server = HttpServer.create(new InetSocketAddress(serverPort), 0);

        var registrationHandler = new RegistrationHandler(getUserService(), getObjectMapper(),
                getErrorHandler());
        server.createContext("/api/users/register", registrationHandler::handle);

        var fooHandler = new FooHandler(getObjectMapper(),
                getErrorHandler());
        server.createContext("/api/foo", fooHandler::handle);

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
