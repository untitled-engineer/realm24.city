package engineer.untitled.switter.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_GUEST, ROLE_USER, ROLE_ADMIN, ROLE_PUBLISHER,
    // ? roles
    OWNER, AGENT, AGENCY;

    @Override
    public String getAuthority() {
        return name();
    }
}
