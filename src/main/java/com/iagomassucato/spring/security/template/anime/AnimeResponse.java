package com.iagomassucato.spring.security.template.anime;

public record AnimeResponse(
        Long id,
        String title
) {
    public static AnimeResponse fromEntity(AnimeEntity animeEntity) {
        return new AnimeResponse(
                animeEntity.getId(),
                animeEntity.getTitle()
        );
    }
}
