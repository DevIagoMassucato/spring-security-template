package com.iagomassucato.spring.security.template.accesscontrol;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AnimePermissionEnum implements Permission {

    ANIME_CREATE_ANIME("ANIME_CREATE_ANIME"),
    ANIME_FIND_ALL_ANIME("ANIME_FIND_ALL_ANIME"),
    ANIME_FIND_BY_ID_ANIME("ANIME_FIND_BY_ID_ANIME"),
    ANIME_REPLACE_ANIME("ANIME_REPLACE_ANIME"),
    ANIME_DELETE_ANIME("ANIME_DELETE_ANIME");

    private final String permission;
}