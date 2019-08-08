package com.akulinski.keyauthservice.core.repositories;

import com.akulinski.keyauthservice.core.domain.Key;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KeyRepository extends CrudRepository<Key, Long> {
    Optional<Key> findByKeyValue(String keyValue);
}
