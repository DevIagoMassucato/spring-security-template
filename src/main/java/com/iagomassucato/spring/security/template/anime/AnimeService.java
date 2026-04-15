package com.iagomassucato.spring.security.template.anime;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnimeService {
    private final AnimeRepository animeRepository;

    public AnimeResponse create(AnimeRequest animeRequest){
        AnimeEntity animeEntity = toEntity(animeRequest);
        AnimeEntity animeEntitySaved = save(animeEntity);
        return AnimeResponse.fromEntity(animeEntitySaved);
    }

    public List<AnimeResponse> findAll() {
        return animeRepository.findAll()
                .stream()
                .map(AnimeResponse::fromEntity)
                .collect(Collectors.toList());
    }

    private AnimeEntity save(AnimeEntity animeEntity){
        return animeRepository.save(animeEntity);
    }

    private AnimeEntity toEntity(AnimeRequest animeRequest){
        return AnimeEntity.builder()
                .title(animeRequest.getTitle())
                .build();
    }
}
