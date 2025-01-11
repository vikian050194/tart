package tart.app.data.user;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import tart.app.domain.user.NewUser;
import tart.app.domain.user.User;
import tart.app.domain.user.UserRepository;

public class InMemoryUserRepository implements UserRepository {

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
}
