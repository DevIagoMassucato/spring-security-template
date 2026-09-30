package com.iagomassucato.spring.security.template.shared;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.Instant;

@MappedSuperclass
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    protected AbstractEntity(Instant createdAt) {
        this.createdAt = createdAt;
        this.updatedAt = createdAt;
    }

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            Instant now = Instant.now();
            this.createdAt = now;
            this.updatedAt = now;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }
}
