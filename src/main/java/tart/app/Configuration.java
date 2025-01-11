package tart.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import tart.app.data.user.InMemoryUserRepository;
import tart.app.domain.user.UserRepository;
import tart.app.domain.user.UserService;
import tart.app.errors.GlobalExceptionHandler;

class Configuration {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final UserRepository USER_REPOSITORY = new InMemoryUserRepository();
    private static final UserService USER_SERVICE = new UserService(USER_REPOSITORY);
    private static final GlobalExceptionHandler GLOBAL_ERROR_HANDLER = new GlobalExceptionHandler(OBJECT_MAPPER);

    static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

    static UserService getUserService() {
        return USER_SERVICE;
    }

    static UserRepository getUserRepository() {
        return USER_REPOSITORY;
    }

    public static GlobalExceptionHandler getErrorHandler() {
        return GLOBAL_ERROR_HANDLER;
    }
}
