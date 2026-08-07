package com.iagomassucato.spring.security.template.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserFinder {

    private final UserRepository userRepository;

    public UserEntity findByIdOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("user not found with id: " + id));
    }

    public UserEntity findByEmailOrThrow(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("user not found with email: " + email));
    }

    public UserEntity findByUsernameOrThrow(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("user not found with username: " + username));
    }

    public Optional<UserEntity> findByUsernameOptional(String username) {
        return userRepository.findByUsername(username);
    }
}
