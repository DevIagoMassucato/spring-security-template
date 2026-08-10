package com.iagomassucato.spring.security.template.anime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "animes")
@NoArgsConstructor
@Getter
public class AnimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    public static AnimeEntity create(String title){
        return new AnimeEntity(title);
    }

    public void updateTitle(String title) {
        this.title = validateTitle(title);
    }

    private AnimeEntity(String title){
        this.title = validateTitle(title);
    }

    private String validateTitle(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("title is required");
        }
        return value;
    }
}
