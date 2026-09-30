package com.iagomassucato.spring.security.template.anime;

import com.iagomassucato.spring.security.template.shared.AuditableEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "animes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AnimeEntity extends AuditableEntity {

    @Column(nullable = false)
    private String title;

    public static AnimeEntity create(String title, Long createdBy){
        return new AnimeEntity(title, createdBy);
    }

    public void updateTitle(String title) {
        this.title = validateTitle(title);
    }

    private AnimeEntity(String title, Long createdBy){
        super(createdBy);
        this.title = validateTitle(title);
    }

    private String validateTitle(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("title is required");
        }
        return value;
    }
}
