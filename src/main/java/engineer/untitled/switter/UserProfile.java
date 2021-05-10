package engineer.untitled.switter;

import java.util.Collection;
import java.util.Collections;

public class UserProfile {
    private final String username;
    private final String avatar;
    private final Collection<String> authorities;

    public UserProfile(
        String username,
        String avatar,
        Collection<String> authorities
    ) {
        this.username = username;
        this.avatar = avatar;
        this.authorities = Collections.unmodifiableCollection(authorities);
    }

    public String getUsername() {
        return username;
    }

    public String getAvatar() {
        return avatar;
    }

    public Collection<String> getAuthorities() {
        return authorities;
    }
}
