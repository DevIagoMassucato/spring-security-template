package com.iagomassucato.spring.security.template.accesscontrol;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AnimePermissionEnum implements Permission {

    ANIME_CREATE_ANIME("anime:create"),
    ANIME_FIND_ALL_ANIME("anime:find-all"),
    ANIME_FIND_BY_ID_ANIME("anime:find-by-id"),
    ANIME_UPDATE_ANIME("anime:update"),
    ANIME_DELETE_ANIME("anime:delete");

    private final String permission;
}
