package com.iagomassucato.spring.security.template.shared;

import org.springframework.stereotype.Component;

@Component
public class PatchValidator {

    public void validate(PatchRequest patchRequest) {
        if (!patchRequest.hasFieldsToUpdate()) {
            throw new IllegalArgumentException("at least one field must be provided for update");
        }
    }
}
