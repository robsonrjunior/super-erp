package com.github.robsonrjunior.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class SoftDeletableEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "deleted_at")
    private Instant deletedAt;
}
