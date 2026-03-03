package zw.co.hushsoft.properbackend.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    LECTURER_READ("lecturer:read"),
    LECTURER_UPDATE("lecturer:update"),
    LECTURER_CREATE("lecturer:create"),
    LECTURER_DELETE("lecturer:delete")

    ;

    @Getter
    private final String permission;
}
