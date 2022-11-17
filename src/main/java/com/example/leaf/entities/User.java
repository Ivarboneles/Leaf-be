package com.example.leaf.entities;

import com.example.leaf.entities.enums.Gender;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", length = 45)
    @NotNull(message = "Name is required")
    private String username;

    @Column(name = "name", length = 45)
    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "Password is required")
    private String password;

    @Column(nullable = false, unique = true)
    @Pattern(regexp = ("^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9\\+_-]+)*@"
            + "[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$"), message = "Invalid email")
    @Size(max = 30, min = 10, message = "Invalid mail size")
    private String email;

    @Column(unique = true)
    @Size(max = 12, min = 9, message = "Invalid phone size")
    @NotNull(message = "Phone is required")
    private String phone;

    @CreationTimestamp
    private Date birthDay;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address", referencedColumnName = "id")
    private Address address;

    @CreationTimestamp
    private Date createDate;

    @NotNull(message = "Enable is required")
    private Gender gender;

    private String avatar;
    private String nickName;
    private String bio;

    @ManyToOne(optional = true)
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(length = 64)
    private String verificationCode;

    @NotNull(message = "Enable is required")
    private Boolean enable;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities =new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        return authorities;
    }
    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enable;
    }

}
