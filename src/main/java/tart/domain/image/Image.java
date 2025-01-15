package tart.domain.image;

import tart.domain.user.*;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Image {

    String id;
    String login;
    String password;
}
