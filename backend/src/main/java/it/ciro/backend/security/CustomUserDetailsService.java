package it.ciro.backend.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {

    private final Map<String, UserDetails> userDetailsByNameMap = new HashMap<>();
    private final Map<Long, UserDetails> userDetailsByIdMap = new HashMap<>();

    public CustomUserDetailsService(PasswordEncoder passwordEncoder) {

        final UserPrincipal userPrincipal = new UserPrincipal(1L, passwordEncoder.encode("admin"), "admin", true);
        userPrincipal.addAuthority(new SimpleGrantedAuthority("ADMIN"));

        userDetailsByNameMap.put(userPrincipal.getUsername(), userPrincipal);
        userDetailsByIdMap.put(userPrincipal.getId(), userPrincipal);
    }

    @Override
    public UserDetails loadUserByUsername(final String username) {
        return userDetailsByNameMap.get(username);
    }

    public UserDetails loadUserById(final Long id) {
        return userDetailsByIdMap.get(id);
    }
}
