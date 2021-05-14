package com.ramy.swapme.role;

import com.ramy.swapme.users.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Table(name = "roles")
public class RoleEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String nameOfRole;

    @ManyToMany(mappedBy = "roles")
    private Collection<UserEntity> userEntities;


}
