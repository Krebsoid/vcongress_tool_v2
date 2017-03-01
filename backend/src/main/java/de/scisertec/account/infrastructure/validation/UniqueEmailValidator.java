package de.scisertec.account.infrastructure.validation;

import de.scisertec.account.domain.model.User;
import de.scisertec.account.infrastructure.repository.GroupRepository;
import de.scisertec.account.infrastructure.repository.UserRepository;
import de.scisertec.core.infrastructure.qualifier.Active;
import org.apache.commons.beanutils.BeanUtils;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

@RequestScoped
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, Object> {

    @Inject
    UserRepository userRepository;

    @Inject
    GroupRepository groupRepository;

    private String groupField;
    private String emailField;

    @Inject
    @Active
    Instance<User> user;

    @Inject
    @Active
    Instance<String> group;

    boolean checkForSelf = false;

    public void initialize(UniqueEmail annotation) {
        checkForSelf = annotation.omitSelf();
        groupField = annotation.group();
        emailField = annotation.email();
    }

    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            String groupValue;
            if(checkForSelf) {
                groupValue = group.get();
            } else {
                groupValue = BeanUtils.getProperty(value, groupField);
            }
            final String emailValue = BeanUtils.getProperty(value, emailField);
            boolean result;
            if (!checkForSelf) {
                result = !userRepository.findAll().stream()
                        .filter(user -> user.hasGroup(groupValue))
                        .anyMatch(user -> user.name().equals(emailValue));

            } else {
                Optional<User> foundUser = userRepository.findAll().stream()
                        .filter(user -> user.hasGroup(groupValue))
                        .filter(user -> user.name().equals(emailValue)).findFirst();
                result = !foundUser.isPresent() || user.get() != null && user.get().credential().username().equals(emailValue);
            }
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