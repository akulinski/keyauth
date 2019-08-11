package com.akulinski.keyauthservice.core.domain;


import lombok.Data;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "auth_key")
@Data
@RedisHash("key")
public class Key implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column
    @Indexed
    private String ident;

    @Column(unique = true, columnDefinition = "TEXT")
    private String keyValue;

    @Column
    private Boolean isUsed;

    @Column
    private Instant useDate;
}
