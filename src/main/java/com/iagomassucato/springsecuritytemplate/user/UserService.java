package com.iagomassucato.springsecuritytemplate.user;

import com.iagomassucato.springsecuritytemplate.accesscontrol.role.RoleEntity;
import com.iagomassucato.springsecuritytemplate.accesscontrol.role.RoleFinder;
import com.iagomassucato.springsecuritytemplate.security.auth.AuthUser;
import com.iagomassucato.springsecuritytemplate.security.credential.*;
import com.iagomassucato.springsecuritytemplate.security.credential.*;
import com.iagomassucato.springsecuritytemplate.shared.PatchValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserFinder userFinder;
    private final UserSessionRevoker userSessionRevoker;
    private final RoleFinder roleFinder;
    private final CredentialFinder credentialFinder;
    private final CredentialCreator credentialCreator;
    private final CredentialUpdater credentialUpdater;
    private final CredentialDeleter credentialDeleter;
    private final PatchValidator patchValidator;
    private final AuthUser authUser;

    @Transactional
    public UserResponse create(UserRequest userRequest) {
        UserEntity userEntity = UserEntity.create(
                userRequest.username(),
                userRequest.email(),
                findRolesByIds(userRequest.roleIds()),
                authUser.getId()
        );
        userRepository.save(userEntity);
        credentialCreator.createLocal(userEntity, userRequest.password());
        return UserResponse.fromEntity(userEntity);
    }

    @Transactional
    public UserResponse update(Long id, UserPatchRequest userPatchRequest) {
        patchValidator.validate(userPatchRequest);
        UserEntity userEntity = userFinder.findByIdOrThrow(id);
        if (userPatchRequest.username() != null) {
            userEntity.updateUsername(userPatchRequest.username());
        }
        if (userPatchRequest.email() != null) {
            userEntity.updateEmail(userPatchRequest.email());
        }
        if (userPatchRequest.roleIds() != null && !userPatchRequest.roleIds().isEmpty()) {
            userEntity.updateRoleEntitySet(findRolesByIds(userPatchRequest.roleIds()));
        }
        if (userPatchRequest.password() != null) {
            CredentialEntity credentialEntity = credentialFinder
                    .findByUserEntityAndCredentialProviderOrThrow(userEntity, CredentialProvider.LOCAL);
            credentialUpdater.updatePassword(credentialEntity, userPatchRequest.password());
            userSessionRevoker.revokeAll(userEntity);
        }
        userEntity.updateUpdatedBy(authUser.getId());
        return UserResponse.fromEntity(userEntity);
    }

    @Transactional
    public UserResponse replace(Long id, UserRequest userRequest) {
        UserEntity userEntity = userFinder.findByIdOrThrow(id);
        userEntity.updateUsername(userRequest.username());
        userEntity.updateEmail(userRequest.email());
        userEntity.updateRoleEntitySet(findRolesByIds(userRequest.roleIds()));
        CredentialEntity credentialEntity = credentialFinder
                .findByUserEntityAndCredentialProviderOrThrow(userEntity, CredentialProvider.LOCAL);
        credentialUpdater.updatePassword(credentialEntity, userRequest.password());
        userSessionRevoker.revokeAll(userEntity);
        userEntity.updateUpdatedBy(authUser.getId());
        return UserResponse.fromEntity(userEntity);
    }

    public List<UserResponse> findAll() {
        return userFinder.findAll()
                .stream()
                .map(UserResponse::fromEntity)
                .toList();
    }

    public UserResponse findById(Long id) {
        UserEntity userEntity = userFinder.findByIdOrThrow(id);
        return UserResponse.fromEntity(userEntity);
    }

    @Transactional
    public void delete(Long id) {
        UserEntity userEntity = userFinder.findByIdOrThrow(id);
        credentialDeleter.deleteByUser(userEntity);
        userRepository.delete(userEntity);
    }

    private Set<RoleEntity> findRolesByIds(Set<Long> rolesIds){
        return roleFinder.findAllByIdOrThrow(rolesIds);
    }
}
