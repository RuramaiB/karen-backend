package zw.co.hushsoft.properbackend.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static zw.co.hushsoft.properbackend.user.Permission.*;

@RequiredArgsConstructor
public enum Role {

  LECTURER(
          Set.of(
                  LECTURER_READ,
                  LECTURER_UPDATE,
                  LECTURER_DELETE,
                  LECTURER_CREATE
          )
  ),
  STUDENT( Set.of(
          LECTURER_READ,
          LECTURER_CREATE
  )),

  ;

  @Getter
  private final Set<Permission> permissions;

  public List<SimpleGrantedAuthority> getAuthorities() {
    var authorities = getPermissions()
            .stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
            .collect(Collectors.toList());
    authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
    return authorities;
  }
}
