package com.AbnerTest.ecommerce_test.core;

import com.AbnerTest.ecommerce_test.elements.AppConstants;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Table(name = "users")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "userId")
@SQLDelete(sql = "UPDATE users SET active = false, deleted_at = NOW() WHERE id = ?")
@SQLRestriction("active = true")
public class Users implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long userId;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false, unique = true)
    private String login;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private boolean active = true;
    @Column
    private LocalDateTime deletedAt;

    @OneToOne(mappedBy = "user")
    private Carts cart;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Orders> orders = new ArrayList<>();

    public Users(String username, String login, String password, UserRole role) {
        this.username = username;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    @PrePersist
    public void prePersist(){
        createdAt = LocalDateTime.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role == UserRole.ADMIN) {return List.of(new SimpleGrantedAuthority(AppConstants.ROLE_ADMIN),new SimpleGrantedAuthority(AppConstants.ROLE_USER));}
        return List.of(new SimpleGrantedAuthority(AppConstants.ROLE_USER));
    }

    @Override
    public String getUsername() {
        return login;
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
        return this.active;
    }
}
