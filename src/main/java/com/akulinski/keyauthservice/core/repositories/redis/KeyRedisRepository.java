package com.akulinski.keyauthservice.core.repositories.redis;

import com.akulinski.keyauthservice.core.domain.Key;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KeyRedisRepository extends CrudRepository<Key, String> {

    Optional<Key> findByKeyValue(String keyValue);
}
