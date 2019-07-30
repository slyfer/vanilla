package it.ciro.backend.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityComponent {

    public UserPrincipal getLoggedUser() {
        UserPrincipal res;
        try {
            res = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (ClassCastException cce) {
            res = new UserPrincipal(null,
                    null,
                    SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString(),
                    true);
        }
        return res;
    }

    public Long getLoggedUserId() {
        return getLoggedUser().getId();
    }

    public void setContextAuthentication(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
