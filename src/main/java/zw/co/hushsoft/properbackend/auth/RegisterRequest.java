package zw.co.hushsoft.properbackend.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.hushsoft.properbackend.user.Gender;
import zw.co.hushsoft.properbackend.user.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  private String firstname;
  private String lastname;
  private String email;
  private String dateOfBirth;
  private Gender gender;
  private String physicalAddress;
  private String password;
  private Role role;
  private boolean enabled;
}
