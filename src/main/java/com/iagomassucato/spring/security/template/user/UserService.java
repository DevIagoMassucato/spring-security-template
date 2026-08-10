package com.iagomassucato.spring.security.template.user;

import com.iagomassucato.spring.security.template.accesscontrol.role.RoleEntity;
import com.iagomassucato.spring.security.template.accesscontrol.role.RoleFinder;
import com.iagomassucato.spring.security.template.security.credential.*;
import com.iagomassucato.spring.security.template.security.refreshtoken.RefreshTokenDeleter;
import com.iagomassucato.spring.security.template.shared.PatchValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserFinder userFinder;
    private final RoleFinder roleFinder;
    private final CredentialFinder credentialFinder;
    private final CredentialCreator credentialCreator;
    private final CredentialUpdater credentialUpdater;
    private final CredentialDeleter credentialDeleter;
    private final RefreshTokenDeleter refreshTokenDeleter;
    private final PatchValidator patchValidator;


    @Transactional
    public UserResponse create(UserRequest userRequest) {
        UserEntity userEntity = UserEntity.create(
                userRequest.getUsername(),
                userRequest.getEmail(),
                findRolesByIds(userRequest.getRoleIds())
        );
        userRepository.save(userEntity);
        credentialCreator.createLocal(userEntity, userRequest.getPassword());
        return UserResponse.fromEntity(userEntity);
    }

    @Transactional
    public UserResponse update(Long id, UserPatchRequest userPatchRequest) {
        patchValidator.validate(userPatchRequest);
        UserEntity userEntity = userFinder.findByIdOrThrow(id);
        if (userPatchRequest.getUsername() != null) {
            userEntity.updateUsername(userPatchRequest.getUsername());
        }
        if (userPatchRequest.getEmail() != null) {
            userEntity.updateEmail(userPatchRequest.getEmail());
        }
        if (userPatchRequest.getRoleIds() != null && !userPatchRequest.getRoleIds().isEmpty()) {
            userEntity.updateRoleEntitySet(findRolesByIds(userPatchRequest.getRoleIds()));
        }
        if (userPatchRequest.getPassword() != null) {
            CredentialEntity credentialEntity = credentialFinder
                    .findByUserEntityAndCredentialProvideOrThrow(userEntity, CredentialProvider.LOCAL);
            credentialUpdater.updatePassword(credentialEntity, userPatchRequest.getPassword());
            refreshTokenDeleter.deleteByUser(userEntity);
        }
        return UserResponse.fromEntity(userEntity);
    }

    @Transactional
    public UserResponse replace(Long id, UserRequest userRequest) {
        UserEntity userEntity = userFinder.findByIdOrThrow(id);
        userEntity.updateUsername(userRequest.getUsername());
        userEntity.updateEmail(userRequest.getEmail());
        userEntity.updateRoleEntitySet(findRolesByIds(userRequest.getRoleIds()));
        CredentialEntity credentialEntity = credentialFinder
                .findByUserEntityAndCredentialProvideOrThrow(userEntity, CredentialProvider.LOCAL);
        credentialUpdater.updatePassword(credentialEntity, userRequest.getPassword());
        refreshTokenDeleter.deleteByUser(userEntity);
        return UserResponse.fromEntity(userEntity);
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
        refreshTokenDeleter.deleteByUser(userEntity);
        credentialDeleter.deleteByUser(userEntity);
        userRepository.delete(userEntity);
    }

    private Set<RoleEntity> findRolesByIds(Set<Long> rolesIds){
        return roleFinder.findAllByIdOrThrow(rolesIds);
    }
}
