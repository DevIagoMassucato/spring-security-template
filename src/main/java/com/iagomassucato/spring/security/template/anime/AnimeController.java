package com.iagomassucato.spring.security.template.anime;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/anime")
@RequiredArgsConstructor
public class AnimeController {
    private final AnimeService animeService;

    @PostMapping
    public ResponseEntity<AnimeResponse> create(@RequestBody AnimeRequest animeRequest){
        AnimeResponse animeResponse = animeService.create(animeRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(animeResponse);
    }

    @GetMapping
    public ResponseEntity<List<AnimeResponse>> findAll(){
        List<AnimeResponse> animeResponseList = animeService.findAll();
        return ResponseEntity.ok(animeResponseList);
    }


    @GetMapping("/public")
    public String testPublic(){
        return "endpoint publico";
    }
}
