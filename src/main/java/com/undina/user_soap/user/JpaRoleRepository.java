package com.undina.user_soap.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface JpaRoleRepository extends JpaRepository<Role, String> {
   Set<Role> getAllByIdIn(Set<Integer> roles);
}
