package com.akulinski.keyauthservice.core.domain;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "auth_key")
@Data
public class Key implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column
    private String ident;

    @Column(unique = true, columnDefinition = "TEXT")
    private String keyValue;

    @Column
    private Boolean isUsed;

    @Column
    private Instant useDate;
}
