package de.scisertec.event.infrastructure.validation;

import de.scisertec.event.domain.model.Event;
import de.scisertec.event.infrastructure.repository.EventRepository;
import org.apache.commons.beanutils.BeanUtils;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;

@RequestScoped
public class EventUniqueIdentifierValidator implements ConstraintValidator<EventUniqueIdentifier, Object> {

    @Inject
    EventRepository eventRepository;

    private String eventField;
    private String identifierField;

    boolean checkForSelf = false;

    public void initialize(EventUniqueIdentifier annotation) {
        checkForSelf = annotation.omitSelf();
        eventField = annotation.event();
        identifierField = annotation.identifier();
    }

    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            final String identifierValue = BeanUtils.getProperty(value, identifierField);
            boolean result;
            if (!checkForSelf) {
                result = !eventRepository.findAll().stream().anyMatch(event -> event.identifier().equals(identifierValue));
            } else {
                final Long eventValue = Long.parseLong(BeanUtils.getProperty(value, eventField));
                Event selfCheckEvent = eventRepository.findBy(eventValue);
                result = !eventRepository.findAll().stream().anyMatch(event -> event.identifier().equals(identifierValue)) || selfCheckEvent.identifier().equals(identifierValue);
            }
            if(!result) {
                String message = constraintValidatorContext.getDefaultConstraintMessageTemplate();
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate(message).addPropertyNode(identifierField).addConstraintViolation();
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