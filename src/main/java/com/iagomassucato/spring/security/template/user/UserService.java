package com.iagomassucato.spring.security.template.user;

import com.iagomassucato.spring.security.template.accesscontrol.role.RoleEntity;
import com.iagomassucato.spring.security.template.accesscontrol.role.RoleFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleFinder roleFinder;
    private final PasswordEncoder passwordEncoder;

    public UserResponse create(UserRequest userRequest) {
        UserEntity userEntity = toEntity(userRequest);
        UserEntity userEntitySaved = save(userEntity);
        return UserResponse.fromEntity(userEntitySaved);
    }

    public UserResponse update(Long id, UserPatchRequest userPatchRequest){
        UserEntity userEntity = findByIdOrThrow(id);
        if (userPatchRequest.getUsername() != null) {
            userEntity.updateUsername(userPatchRequest.getUsername());
        }
        if (userPatchRequest.getPassword() != null) {
            userEntity.updatePassword(encodePassword(userPatchRequest.getPassword()));
        }
        if (userPatchRequest.getRoleId() != null) {
            RoleEntity roleEntity = roleFinder.findByIdOrThrow(userPatchRequest.getRoleId());
            userEntity.updateRoleEntity(roleEntity);
        }
        UserEntity userEntitySaved = save(userEntity);
        return UserResponse.fromEntity(userEntitySaved);
    }

    public UserResponse replace(Long id, UserRequest userRequest){
        UserEntity userEntity = findByIdOrThrow(id);
        userEntity.updateUsername(userRequest.getUsername());
        userEntity.updatePassword(encodePassword(userRequest.getPassword()));
        RoleEntity roleEntity = roleFinder.findByIdOrThrow(userRequest.getRoleId());
        userEntity.updateRoleEntity(roleEntity);
        UserEntity userEntitySaved = save(userEntity);
        return UserResponse.fromEntity(userEntitySaved);
    }

    public List<UserResponse> findAll(){
        return userRepository.findAll()
                .stream()
                .map(UserResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public UserResponse findById(Long id){
        UserEntity userEntity = findByIdOrThrow(id);
        return UserResponse.fromEntity(userEntity);
    }

    public void delete(Long id){
        UserEntity userEntity = findByIdOrThrow(id);
        userRepository.delete(userEntity);
    }

    private UserEntity toEntity(UserRequest userRequest) {
        RoleEntity roleEntity = roleFinder.findByIdOrThrow(userRequest.getRoleId());
        return UserEntity.builder()
                .username(userRequest.getUsername())
                .password(encodePassword(userRequest.getPassword()))
                .roleEntity(roleEntity)
                .build();
    }

    private UserEntity save(UserEntity userEntity){
        return userRepository.save(userEntity);
    }

    private UserEntity findByIdOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("user not found with id: " + id));
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
