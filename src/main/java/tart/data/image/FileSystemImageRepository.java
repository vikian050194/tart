package tart.data.image;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import tart.app.core.wrapper.FileWrapper;
import tart.domain.image.ImageRepository;
import tart.domain.user.NewUser;
import tart.domain.user.User;

public class FileSystemImageRepository implements ImageRepository {

    private static final Map USERS_STORE = new ConcurrentHashMap();

    @Override
    public String create(NewUser newUser) {
        String id = UUID.randomUUID().toString();
        User user = User.builder()
            .id(id)
            .login(newUser.getLogin())
            .password(newUser.getPassword())
            .build();
        USERS_STORE.put(newUser.getLogin(), user);

        return id;
    }

    @Override
    public boolean setupRoot() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<FileWrapper> get10() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
