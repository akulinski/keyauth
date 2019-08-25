package com.akulinski.keyauthservice.core.repositories;

import com.akulinski.keyauthservice.core.domain.Role;
import com.akulinski.keyauthservice.core.domain.RoleType;
import com.akulinski.keyauthservice.core.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByUserAndRoleType(User user, RoleType roleType);
}
