package com.iagomassucato.spring.security.template.anime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AnimeResponse {
    private Long id;
    private String title;

    public static AnimeResponse fromEntity(AnimeEntity animeEntity){
        return new AnimeResponse(
                animeEntity.getId(),
                animeEntity.getTitle()
        );
    }
}
