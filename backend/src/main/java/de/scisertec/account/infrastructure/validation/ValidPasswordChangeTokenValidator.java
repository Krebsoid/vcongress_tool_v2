package de.scisertec.account.infrastructure.validation;

import de.scisertec.account.domain.model.PasswordRecoveryToken;
import de.scisertec.account.infrastructure.repository.PasswordRecoveryTokenRepository;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidPasswordChangeTokenValidator implements ConstraintValidator<ValidPasswordChangeToken, String> {

    @Inject
    PasswordRecoveryTokenRepository passwordRecoveryTokenRepository;

    public void initialize(ValidPasswordChangeToken annotation) {

    }

    public boolean isValid(String valueForValidation, ConstraintValidatorContext constraintValidatorContext)
    {
        PasswordRecoveryToken token = passwordRecoveryTokenRepository.findByValue(valueForValidation);
        return token != null && !token.used();
    }
}