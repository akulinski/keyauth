package com.akulinski.keyauthservice.core.domain;

import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "application")
@Getter
@Setter
public class Application {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "applicationGenerator")
    @SequenceGenerator(name = "applicationGenerator", allocationSize = 1)
    private Long id;

    @Column(unique = true)
    private String applicationName;


    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "applications")
    private Set<AppUser> appUsers = new HashSet<>();
}
