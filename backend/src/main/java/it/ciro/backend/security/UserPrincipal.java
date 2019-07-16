package it.ciro.backend.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class UserPrincipal implements UserDetails {

    private Long id;
    private String password;
    private String username;
    private ArrayList<GrantedAuthority> authorities;
    private boolean enabled;

    public UserPrincipal(Long id, String password, String username, boolean enabled) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.enabled = enabled;

        authorities = new ArrayList<>();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    public void addAuthority(GrantedAuthority grantedAuthority) {
        authorities.add(grantedAuthority);
    }
}
