package it.ciro.backend.web.rest;

import it.ciro.backend.security.JwtAuthenticationResponse;
import it.ciro.backend.security.JwtTokenProvider;
import it.ciro.backend.security.LoginRequest;
import it.ciro.backend.security.SecurityComponent;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(
    path = "/api/auth",
    produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
    consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
public class LoginController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final SecurityComponent securityComponent;

    public LoginController(AuthenticationManager authenticationManager,
                           JwtTokenProvider jwtTokenProvider,
                           SecurityComponent securityComponent) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.securityComponent = securityComponent;
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@RequestBody @Valid final LoginRequest loginRequest) {
        final Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername().toLowerCase(), loginRequest.getPassword()));

        securityComponent.setContextAuthentication(authentication);
        final String jwt = jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }
}
