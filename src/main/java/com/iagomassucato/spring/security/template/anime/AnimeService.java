package com.iagomassucato.spring.security.template.anime;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
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

    public AnimeResponse replace(Long id, AnimeRequest animeRequest) {
        AnimeEntity animeEntity = findByIdOrThrow(id);
        animeEntity.updateTitle(animeRequest.getTitle());
        AnimeEntity animeEntitySaved = save(animeEntity);
        return AnimeResponse.fromEntity(animeEntitySaved);
    }

    public List<AnimeResponse> findAll() {
        return animeRepository.findAll()
                .stream()
                .map(AnimeResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public AnimeResponse findById(Long id) {
        AnimeEntity animeEntity = findByIdOrThrow(id);
        return AnimeResponse.fromEntity(animeEntity);
    }

    public void delete(Long id){
        AnimeEntity animeEntity = findByIdOrThrow(id);
        animeRepository.delete(animeEntity);
    }

    private AnimeEntity toEntity(AnimeRequest animeRequest){
        return AnimeEntity.builder()
                .title(animeRequest.getTitle())
                .build();
    }

    private AnimeEntity save(AnimeEntity animeEntity){
        return animeRepository.save(animeEntity);
    }

    private AnimeEntity findByIdOrThrow(Long id) {
        return animeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("anime not found with id: " + id));
    }
}