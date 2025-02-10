package uz.learn.it.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.learn.it.enums.Role;

import java.util.Collection;
import java.util.Collections;

@Data
@Entity
@Table(name = "user_credentials")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {
    @Id
    @SequenceGenerator(name = "client_seq", sequenceName = "clients_id_seq", allocationSize = 1, initialValue = 2)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_seq")
    private long id;

    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
