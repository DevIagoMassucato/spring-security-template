package com.iagomassucato.spring.security.template.accesscontrol;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Set;

@Getter
@AllArgsConstructor
public enum RoleEnum {
    ADMIN(Set.of(
            AnimePermissionEnum.ANIME_CREATE_ANIME,
            AnimePermissionEnum.ANIME_FIND_ALL_ANIME,
            AnimePermissionEnum.ANIME_FIND_BY_ID_ANIME,
            AnimePermissionEnum.ANIME_UPDATE_ANIME,
            AnimePermissionEnum.ANIME_DELETE_ANIME
    )),

    USER(Set.of(
            AnimePermissionEnum.ANIME_FIND_ALL_ANIME,
            AnimePermissionEnum.ANIME_FIND_BY_ID_ANIME,
            AnimePermissionEnum.ANIME_UPDATE_ANIME
    ));

    private final Set<Permission> permissionSet;

}
