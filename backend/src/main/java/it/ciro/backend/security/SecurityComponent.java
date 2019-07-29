package it.ciro.backend.security;

import com.pkb.business.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SecurityComponent {

    public UserPrincipal getLoggedUser() {
        UserPrincipal res;
        try {
            res = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (ClassCastException cce) {
            res = new UserPrincipal(null,
                    SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString(),
                    null,
                    null);
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

    public UserPrincipal toUserPrincipal(User user) {
        List<GrantedAuthority> authorities = null;
        if (!CollectionUtils.isEmpty(user.getRoles())) {
            authorities = user.getRoles().stream().map(role ->
                    new SimpleGrantedAuthority(role.getName().name())
            ).collect(Collectors.toList());
        }

        return new UserPrincipal(
                user.getTechnicalId(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }


}
