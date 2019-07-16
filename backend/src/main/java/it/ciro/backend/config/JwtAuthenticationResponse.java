package it.ciro.backend.config;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class JwtAuthenticationResponse {

    private String accessToken;
    private String tokenType = "Bearer";
    private String email;
    private LocalDate subscriptionEndDate;

    public JwtAuthenticationResponse() {
        //used for the json (de)serialization
    }

    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
