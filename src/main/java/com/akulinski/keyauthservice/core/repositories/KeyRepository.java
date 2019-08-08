package com.akulinski.keyauthservice.core.repositories;

import com.akulinski.keyauthservice.core.domain.Key;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyRepository extends CrudRepository<Key, Long> {
}
