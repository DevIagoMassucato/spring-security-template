package com.iagomassucato.spring.security.template.anime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

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

    @Builder
    public AnimeEntity(String title){
        this.title = validateTitle(title);
    }

    public void updateTitle(String title) {
        this.title = validateTitle(title);
    }

    private String validateTitle(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("title is required");
        }
        return value;
    }

}
