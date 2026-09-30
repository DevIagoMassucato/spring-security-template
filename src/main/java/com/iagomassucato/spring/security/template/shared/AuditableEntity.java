package com.iagomassucato.spring.security.template.shared;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class AuditableEntity extends AbstractEntity {

    @Column(nullable = false, updatable = false)
    private Long createdBy;

    @Column(nullable = false)
    private Long updatedBy;

    protected AuditableEntity(Long createdBy) {
        super();
        this.createdBy = validateUserId(createdBy);
        this.updatedBy = this.createdBy;
    }

    public void updateUpdatedBy(Long updatedBy) {
        this.updatedBy = validateUserId(updatedBy);
    }

    private Long validateUserId(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId is required");
        }
        return userId;
    }
}
