package com.ramy.swapme.role;


import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByNameOfRole(String name);
}
