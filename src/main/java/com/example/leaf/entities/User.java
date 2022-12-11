package com.example.leaf.entities;

import com.example.leaf.entities.enums.GenderEnum;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
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
    @Column(length = 45)
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
    private Date birthday;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address", referencedColumnName = "id")
    private Address address;

    @CreationTimestamp
    private Date createDate;

    private GenderEnum gender;

    private String avatar;
    private String nickname;
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
