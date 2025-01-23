package tart.app;

import com.sun.net.httpserver.BasicAuthenticator;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.LinkedList;
import static tart.app.Configuration.*;
import tart.app.Configuration.RunMode;
import tart.app.api.Handler;
import tart.app.api.file.*;
import tart.app.api.hello.*;
import tart.app.api.user.*;

public final class App {

    private final HttpServer server;

    public App(int httpPort) throws IOException {
        if (Configuration.runMode() == RunMode.PROD) {
            server = HttpServer.create(new InetSocketAddress("0.0.0.0", httpPort), 0);
        } else {
            server = HttpServer.create(new InetSocketAddress("127.0.0.1", httpPort), 0);
        }
//        HttpContextHelper.initializeDummyEndpoints(server::createContext);
    }

    private void setAuthenticator(HttpContext c) {
        // TODO use Handler.auth()
        if (Configuration.runMode() == RunMode.DEV) {
            return;
        }

        c.setAuthenticator(new BasicAuthenticator("myrealm") {
            @Override
            public boolean checkCredentials(String user, String pwd) {
                return user.equals("admin") && pwd.equals("admin");
            }
        });
    }

    public void init() {
        var handlers = new LinkedList<Handler>();
        handlers.add(new RegistrationHandler(getUserService(), getObjectMapper(),
                getErrorHandler()));
        handlers.add(new FileHandler(getImageService(), getObjectMapper(),
                getErrorHandler()));
        handlers.add(new HelloHandler(getObjectMapper(),
                getErrorHandler()));

        var contexts = handlers.stream().map(h -> server.createContext(h.url(), h::handle)).toList();

        contexts.forEach(c ->  setAuthenticator(c));

    }

    public InetSocketAddress start() {
        // TODO is it needed?
        server.setExecutor(null);
        server.start();
        // TODO use logger
        System.out.println("Server started on %s".formatted(server.getAddress()));
        return server.getAddress();
    }

    public void stop(int delay) {
        server.stop(delay);
        // TODO use logger
        System.out.println("Server stopped with code %s".formatted(delay));
    }

    public static void main(String[] args) throws IOException {
        var httpPort = Configuration.port();

        var app = new App(httpPort);
        app.init();
        app.start();
    }
}
