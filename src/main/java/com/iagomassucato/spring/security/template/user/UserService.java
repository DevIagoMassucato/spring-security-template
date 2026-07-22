package com.iagomassucato.spring.security.template.user;

import com.iagomassucato.spring.security.template.accesscontrol.role.RoleEntity;
import com.iagomassucato.spring.security.template.accesscontrol.role.RoleFinder;
import com.iagomassucato.spring.security.template.security.credential.CredentialService;
import com.iagomassucato.spring.security.template.security.refreshtoken.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserFinder userFinder;
    private final RoleFinder roleFinder;
    private final RefreshTokenService refreshTokenService;
    private final CredentialService credentialService;

    @Transactional
    public UserResponse create(UserRequest userRequest) {
        UserEntity userEntity = toEntity(userRequest);
        UserEntity userEntitySaved = save(userEntity);
        credentialService.createLocalCredential(userEntitySaved, userRequest.getPassword());
        return UserResponse.fromEntity(userEntitySaved);
    }

    @Transactional
    public UserResponse update(Long id, UserPatchRequest userPatchRequest) {
        UserEntity userEntity = userFinder.findByIdOrThrow(id);
        if (userPatchRequest.getUsername() != null) {
            userEntity.updateUsername(userPatchRequest.getUsername());
        }
        if (userPatchRequest.getPassword() != null) {
            credentialService.updatePassword(id, userPatchRequest.getPassword());
        }
        if (userPatchRequest.getEmail() != null) {
            userEntity.updateEmail(userPatchRequest.getEmail());
        }
        if (userPatchRequest.getRoleIds() != null && !userPatchRequest.getRoleIds().isEmpty()) {
            Set<RoleEntity> roleEntitySet = roleFinder.findAllByIdsOrThrow(userPatchRequest.getRoleIds());
            userEntity.updateRoleEntitySet(roleEntitySet);
        }
        UserEntity userEntitySaved = save(userEntity);
        return UserResponse.fromEntity(userEntitySaved);
    }

    @Transactional
    public UserResponse replace(Long id, UserRequest userRequest) {
        UserEntity userEntity = userFinder.findByIdOrThrow(id);
        userEntity.updateUsername(userRequest.getUsername());
        userEntity.updateEmail(userRequest.getEmail());
        Set<RoleEntity> roleEntitySet = roleFinder.findAllByIdsOrThrow(userRequest.getRoleIds());
        userEntity.updateRoleEntitySet(roleEntitySet);
        credentialService.updatePassword(id, userRequest.getPassword());
        UserEntity userEntitySaved = save(userEntity);
        return UserResponse.fromEntity(userEntitySaved);
    }

    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public UserResponse findById(Long id) {
        UserEntity userEntity = userFinder.findByIdOrThrow(id);
        return UserResponse.fromEntity(userEntity);
    }

    @Transactional
    public void delete(Long id) {
        UserEntity userEntity = userFinder.findByIdOrThrow(id);
        refreshTokenService.deleteByUser(userEntity);
        credentialService.deleteByUser(userEntity);
        userRepository.delete(userEntity);
    }

    private UserEntity toEntity(UserRequest userRequest) {
        Set<RoleEntity> roleEntitySet = roleFinder.findAllByIdsOrThrow(userRequest.getRoleIds());
        return UserEntity.builder()
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .roleEntitySet(roleEntitySet)
                .build();
    }

    private UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }
}
