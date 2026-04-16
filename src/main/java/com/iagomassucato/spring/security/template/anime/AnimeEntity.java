package com.iagomassucato.spring.security.template.anime;

import lombok.*;
import javax.persistence.*;
import java.util.Objects;

@Table(name = "animes")
@Entity
@NoArgsConstructor
@Getter
public class AnimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @Builder
    public AnimeEntity(String title){
        this.title = Objects.requireNonNull(title);
    }

    public void updateTitle(String title){
        if (title == null || title.isBlank()){
            throw new IllegalArgumentException("title é is required");
        }
        this.title = title;
    }
}
