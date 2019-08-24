package com.akulinski.keyauthservice.core.repositories.redis;

import com.akulinski.keyauthservice.core.domain.AppUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRedisRepository extends CrudRepository<AppUser, String> {

    Optional<AppUser> findByUsername(String username);
}
