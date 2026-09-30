package com.iagomassucato.springsecuritytemplate.security.resetpassword;

import com.iagomassucato.springsecuritytemplate.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class ResetPasswordFinder {

    private final ResetPasswordRepository resetPasswordRepository;

    public ResetPasswordEntity findByUserEntityAndCode(UserEntity userEntity, String code){
        return resetPasswordRepository.findByUserEntityAndCode(userEntity, code)
                .orElseThrow(() -> new NoSuchElementException("code is invalid"));
    }
}
