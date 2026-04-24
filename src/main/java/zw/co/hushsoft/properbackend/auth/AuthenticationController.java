package zw.co.hushsoft.properbackend.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.hushsoft.properbackend.user.User;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
  private final AuthenticationService service;

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(
      @RequestBody RegisterRequest request
  ) {
    return ResponseEntity.ok(service.register(request));
  }
  @PostMapping("/generate-verification-code-by-/{email}")
  public String generateVerificationCode(@PathVariable String email){
    return service.generateVerificationCode(email);
  }
  @PutMapping("/validate-verification-code-by-/{email}/{code}")
  public ResponseEntity<String> validateVerificationCode(@PathVariable String email, @PathVariable String code){
    return service.verifyCode(email, code);
  }
  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody AuthenticationRequest request
  ) {
    return ResponseEntity.ok(service.authenticate(request));
  }
  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest){
    return ResponseEntity.ok(service.authenticateWithVerification(authenticationRequest));
  }

  @PostMapping("/refresh-token")
  public void refreshToken(
      HttpServletRequest request,
      HttpServletResponse response
  ) throws IOException {
    service.refreshToken(request, response);
  }

  @GetMapping("/get-user-by-/{email}")
  public User getUserByEmail(@PathVariable String email){
    return service.getUserByEmail(email);
  }

  @PutMapping("/update-is-first-time-by-/{email}")
  public ResponseEntity<String> updateIsFirstTime(@PathVariable String email){
    return service.updateIsFirstTime(email);
  }

}
