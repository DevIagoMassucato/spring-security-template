package com.iagomassucato.spring.security.template.anime;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/animes")
@RequiredArgsConstructor
public class AnimeController {

    private final AnimeService animeService;

    @GetMapping("/public")
    public String testPublic(){
        return "endpoint public";
    }

    @PostMapping
    public ResponseEntity<AnimeResponse> create(@Valid @RequestBody AnimeRequest animeRequest){
        AnimeResponse animeResponse = animeService.create(animeRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(animeResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnimeResponse> replace(@PathVariable Long id, @Valid @RequestBody AnimeRequest animeRequest){
        AnimeResponse animeResponse = animeService.replace(id, animeRequest);
        return ResponseEntity.ok(animeResponse);
    }

    @GetMapping
    public ResponseEntity<List<AnimeResponse>> findAll(){
        List<AnimeResponse> animeResponseList = animeService.findAll();
        return ResponseEntity.ok(animeResponseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimeResponse> findById(@Valid @PathVariable Long id){
        AnimeResponse animeResponse = animeService.findById(id);
        return ResponseEntity.ok(animeResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        animeService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
