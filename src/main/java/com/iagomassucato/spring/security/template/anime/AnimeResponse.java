package com.iagomassucato.spring.security.template.anime;

import java.time.Instant;

public record AnimeResponse(
        Long id,
        String title,
        Instant createdAt,
        Instant updatedAt,
        Long createdBy,
        Long updatedBy
) {
    public static AnimeResponse fromEntity(AnimeEntity animeEntity) {
        return new AnimeResponse(
                animeEntity.getId(),
                animeEntity.getTitle(),
                animeEntity.getCreatedAt(),
                animeEntity.getUpdatedAt(),
                animeEntity.getCreatedBy(),
                animeEntity.getUpdatedBy()
        );
    }
}
