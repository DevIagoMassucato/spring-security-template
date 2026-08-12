package com.iagomassucato.spring.security.template.anime;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeRepository animeRepository;

    public AnimeResponse create(AnimeRequest animeRequest){
        AnimeEntity animeEntity = AnimeEntity.create(animeRequest.title());
        animeRepository.save(animeEntity);
        return AnimeResponse.fromEntity(animeEntity);
    }

    public AnimeResponse replace(Long id, AnimeRequest animeRequest) {
        AnimeEntity animeEntity = findByIdOrThrow(id);
        animeEntity.updateTitle(animeRequest.title());
        animeRepository.save(animeEntity);
        return AnimeResponse.fromEntity(animeEntity);
    }

    public List<AnimeResponse> findAll() {
        return animeRepository.findAll()
                .stream()
                .map(AnimeResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "animes", key = "#id")
    public AnimeResponse findById(Long id) {
        AnimeEntity animeEntity = findByIdOrThrow(id);
        return AnimeResponse.fromEntity(animeEntity);
    }

    @CacheEvict(value = "animes", key = "#id")
    public void delete(Long id){
        AnimeEntity animeEntity = findByIdOrThrow(id);
        animeRepository.delete(animeEntity);
    }

    private AnimeEntity findByIdOrThrow(Long id) {
        return animeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("anime not found with id: " + id));
    }
}
