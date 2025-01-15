package tart.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import tart.app.errors.GlobalExceptionHandler;
import tart.data.image.FileSystemImageRepository;
import tart.data.user.InMemoryUserRepository;
import tart.domain.image.ImageRepository;
import tart.domain.image.ImageService;
import tart.domain.user.UserRepository;
import tart.domain.user.UserService;

class Configuration {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final UserRepository USER_REPOSITORY = new InMemoryUserRepository();
    private static final UserService USER_SERVICE = new UserService(USER_REPOSITORY);
    private static final ImageRepository IMAGE_REPOSITORY = new FileSystemImageRepository();
    private static final ImageService IMAGE_SERVICE = new ImageService(IMAGE_REPOSITORY);
    private static final GlobalExceptionHandler GLOBAL_ERROR_HANDLER = new GlobalExceptionHandler(OBJECT_MAPPER);

    static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

    static ImageService getImageService() {
        return IMAGE_SERVICE;
    }
    
    static UserService getUserService() {
        return USER_SERVICE;
    }

    public static GlobalExceptionHandler getErrorHandler() {
        return GLOBAL_ERROR_HANDLER;
    }
}
