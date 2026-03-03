package zw.co.hushsoft.properbackend.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.hushsoft.properbackend.user.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

  @JsonProperty("access_token")
  private String accessToken;
  @JsonProperty("refresh_token")
  private String refreshToken;
  private Role role;
  private String email;
  private String message;
  private Boolean isFirstTime = true;
  private Boolean isVerified = true;
  private Boolean hasTwoFactor = true;
}
