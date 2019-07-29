package it.ciro.backend.security;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class JwtTokenContent {

    private Long userId;
    private LocalDateTime expirationDateTime;
}
