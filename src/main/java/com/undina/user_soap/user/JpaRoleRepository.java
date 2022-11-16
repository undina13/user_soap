package com.undina.user_soap.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface JpaRoleRepository extends JpaRepository<Role, String> {
   List<Role> getAllByIdIn(List<Integer> roles);
}
