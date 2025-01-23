package tart.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import tart.app.errors.GlobalExceptionHandler;
import tart.data.file.LocalFileRepository;
import tart.data.user.InMemoryUserRepository;
import tart.domain.file.FileRepository;
import tart.domain.file.FileService;
import tart.domain.user.UserRepository;
import tart.domain.user.UserService;

class Configuration {

    public static int port() {
        // TODO read port from config
        return 8000;
    }

    enum RunMode {
        DEV, PROD
    };

    public static final RunMode runMode() {
        return RunMode.DEV;
    }

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final UserRepository USER_REPOSITORY = new InMemoryUserRepository();
    private static final UserService USER_SERVICE = new UserService(USER_REPOSITORY);
    private static final FileRepository IMAGE_REPOSITORY = new LocalFileRepository();
    private static final FileService IMAGE_SERVICE = new FileService(IMAGE_REPOSITORY);
    private static final GlobalExceptionHandler GLOBAL_ERROR_HANDLER = new GlobalExceptionHandler(OBJECT_MAPPER);

    static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

    static FileService getImageService() {
        return IMAGE_SERVICE;
    }

    static UserService getUserService() {
        return USER_SERVICE;
    }

    public static GlobalExceptionHandler getErrorHandler() {
        return GLOBAL_ERROR_HANDLER;
    }
}
