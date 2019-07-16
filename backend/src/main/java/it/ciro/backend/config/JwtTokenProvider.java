package it.ciro.backend.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.ciro.backend.AppProperties;
import it.ciro.backend.security.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.InvalidSignatureException;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.jwt.crypto.sign.SignerVerifier;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
@Slf4j
public class JwtTokenProvider {

    private static final String HMAC_SHA_512 = "HMACSHA512";
    private final AppProperties appProperties;
    private final SignerVerifier signerVerifier;
    private final ObjectMapper objectMapper;

    public JwtTokenProvider(final AppProperties pAppProperties, ObjectMapper objectMapper) {
        appProperties = pAppProperties;
        this.objectMapper = objectMapper;
        signerVerifier =
                new MacSigner(HMAC_SHA_512, new SecretKeySpec(appProperties.getJwtSecret().getBytes(), HMAC_SHA_512));
    }

    public String generateToken(final Authentication authentication) {
        final UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        JwtTokenContent tokenContent = new JwtTokenContent();
        tokenContent.setUserId(userPrincipal.getId());
        tokenContent.setExpirationDateTime(
                LocalDateTime.now().plus(appProperties.getJwtExpirationInMs(), ChronoUnit.MILLIS));

        try {
            Jwt jwt = JwtHelper.encode(objectMapper.writeValueAsString(tokenContent), signerVerifier);
            return jwt.getEncoded();
        } catch (JsonProcessingException jpe) {
            throw new IllegalStateException(jpe);
        }
    }

    Long getUserIdFromJWT(final String token) {

        try {
            JwtTokenContent tokenContent = jwtTokenContent(token);
            return tokenContent.getUserId();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    boolean isValid(final String authToken) {
        boolean result = false;
        try {
            JwtTokenContent tokenContent = jwtTokenContent(authToken);
            result = LocalDateTime.now().isBefore(tokenContent.getExpirationDateTime());
        } catch (InvalidSignatureException | IOException ex) {
            log.error(ex.getMessage(), ex);
        }
        return result;
    }

    private JwtTokenContent jwtTokenContent(String token) throws IOException {
        Jwt jwt = JwtHelper.decodeAndVerify(token, signerVerifier);
        return objectMapper.readValue(jwt.getClaims(), JwtTokenContent.class);
    }
}
