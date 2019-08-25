package com.akulinski.keyauthservice.config;

import com.akulinski.keyauthservice.core.domain.Key;
import com.akulinski.keyauthservice.core.domain.Role;
import com.akulinski.keyauthservice.core.domain.RoleType;
import com.akulinski.keyauthservice.core.domain.User;
import com.akulinski.keyauthservice.core.repositories.AppUserRepository;
import com.akulinski.keyauthservice.core.repositories.KeyRepository;
import com.akulinski.keyauthservice.core.repositories.RoleRepository;
import com.akulinski.keyauthservice.core.repositories.UserRepository;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.stream.Stream;

@Configuration
@Profile("dev")
@Slf4j
public class DataMockConfig {
    private Faker faker;


    private final KeyRepository keyRepository;


    private final AppUserRepository appUserRepository;


    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    public DataMockConfig(KeyRepository keyRepository, AppUserRepository appUserRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.keyRepository = keyRepository;
        faker = new Faker();
        this.appUserRepository = appUserRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }


    @EventListener(ApplicationReadyEvent.class)
    public void mockData() {

        long diff = 10L - keyRepository.count();

        Stream.generate(() -> {
            Key key = new Key();
            key.setKeyValue(faker.shakespeare().asYouLikeItQuote() + faker.random().hex(100));
            key.setIdent(faker.idNumber().valid());
            key.setUseDate(new Date().toInstant());
            return key;
        }).limit(diff).forEach(keyRepository::save);

        diff = 10L - appUserRepository.count();
/*
        Stream.generate(() -> {
            User user = new User();
            user.setPassword(passwordEncoder.encode(faker.dog().name()));
            user.setUsername(faker.funnyName().name());
            user.setName(faker.funnyName().name());
            user.setEmail(faker.pokemon().name());
            userRepository.save(user);

            Role role = new Role();
            role.setUser(user);
            role.setRoleType(RoleType.ROLE_USER);
            roleRepository.save(role);

            AppUser appUser = new AppUser();
            appUser.setUsername(faker.funnyName().name());
            appUser.setUserType(UserType.BASIC_USER);
            appUser.setUser(user);

            final Set<Application> apps = Stream.generate(() -> {
                Application application = new Application();
                application.setApplicationName(faker.harryPotter().quote());
                application.setAppUsers(Set.of(appUser));
                return application;
            }).limit(faker.random().nextInt(5)).collect(Collectors.toSet());

            appUser.setApplications(apps);
            return appUser;
        }).limit(diff).forEach(appUserRepository::save);*/

        if (userRepository.findByUsernameOrEmail("admin", "admin").isEmpty()) {
            User user = new User();
            user.setEmail("admin@admin.com");
            user.setName("admin");
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("admin"));
            userRepository.save(user);

            Role role1 = new Role();
            role1.setUser(user);
            role1.setRoleType(RoleType.ROLE_USER);

            Role role2 = new Role();
            role2.setUser(user);
            role2.setRoleType(RoleType.ROLE_USER);

            roleRepository.save(role1);
            roleRepository.save(role2);
        }
    }
}
