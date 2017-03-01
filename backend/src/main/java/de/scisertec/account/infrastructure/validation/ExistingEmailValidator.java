package de.scisertec.account.infrastructure.validation;

import de.scisertec.account.domain.model.Group;
import de.scisertec.account.infrastructure.repository.GroupRepository;
import de.scisertec.account.infrastructure.repository.UserRepository;
import org.apache.commons.beanutils.BeanUtils;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;

public class ExistingEmailValidator implements ConstraintValidator<ExistingEmail, Object> {

    @Inject
    UserRepository userRepository;

    @Inject
    GroupRepository groupRepository;

    private String groupField;
    private String emailField;

    public void initialize(ExistingEmail annotation) {
        groupField = annotation.group();
        emailField = annotation.email();
    }

    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext)
    {
        try {
            final String groupValue = BeanUtils.getProperty(value, groupField);
            final String emailValue = BeanUtils.getProperty(value, emailField);
            Group group = groupRepository.findByIdentifier(groupValue);
            boolean result = userRepository.findByGroupAndMailAddress(group, emailValue) != null;
            if(!result) {
                String message = constraintValidatorContext.getDefaultConstraintMessageTemplate();

                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate(message).addPropertyNode(emailField).addConstraintViolation();
            }
            return result;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return false;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return false;
        }
    }
}