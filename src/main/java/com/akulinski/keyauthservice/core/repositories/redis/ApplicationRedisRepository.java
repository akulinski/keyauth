package com.akulinski.keyauthservice.core.repositories.redis;

import com.akulinski.keyauthservice.core.domain.Application;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationRedisRepository extends CrudRepository<Application, String> {

    Optional<Application> findByApplicationName(String applicationName);
}

