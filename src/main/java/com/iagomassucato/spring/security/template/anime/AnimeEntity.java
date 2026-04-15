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
}
