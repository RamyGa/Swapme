package com.ramy.swapme.users;

import com.ramy.swapme.giftcards.GiftCardEntity;
import com.ramy.swapme.role.RoleEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.*;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Component //added this because we are using it in GiftCardService
//@PasswordsMatch
public class UserEntity implements UserDetails {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @Column(nullable = false, unique = true)//cannot be null, every email needs to be unique
    @NonNull
    @Email
    private String email;
    @NonNull
    private String password;

    @Transient
    @NotEmpty(message = "please enter password confirmation ")
    private String confirmPassword;

    @ManyToMany(fetch = FetchType.EAGER)//EAGER (grab them all) instead of Lazy loading (we know a user will not have
    @JoinTable(                         //a ton of roles  (just one or two roles)
            name="user_roles",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")

            //JointTable in a normal application you'd have a users Table, a Roles Table, and a UsersRoles table that would
            //contain two reference columns like - UserId and a RoleId
    )
    private Set<RoleEntity> roles = new HashSet<>();

    public void addRole(RoleEntity role){
        roles.add(role);
    }

    @OneToMany
    @JoinColumn(name = "userId")//this is the column that joins a user with a card
    private List<GiftCardEntity> cardsOwned;



    public void addRoles(Set<RoleEntity> roleEntities){
        roles.forEach(this::addRole);
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (RoleEntity role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getNameOfRole()));
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
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
        return true;
    }



}
