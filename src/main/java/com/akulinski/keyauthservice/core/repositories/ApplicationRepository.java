package com.akulinski.keyauthservice.core.repositories;

import com.akulinski.keyauthservice.core.domain.Application;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationRepository extends CrudRepository<Application, Long> {

    Optional<Application> findByApplicationName(String applicationName);
}
