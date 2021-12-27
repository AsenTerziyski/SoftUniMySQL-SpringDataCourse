package softuni.exam.instagraphlite.util.impl;

import org.springframework.stereotype.Component;
import softuni.exam.instagraphlite.util.ValidationUtil;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ValidationUtilImpl implements ValidationUtil {

    private final Validator valdator;

    public ValidationUtilImpl() {
        this.valdator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();
    }

    @Override
    public <T> boolean isValid(T entity) {
        return this.valdator.validate(entity).isEmpty();
    }

    @Override
    public <T> Set<ConstraintViolation<T>> getViolations(T entity) {
        return this.valdator.validate(entity);
    }

    @Override
    public <T> Set<String> getViolationMessages(T entity) {
        Set<ConstraintViolation<T>> violationSet = getViolations(entity);
//        Set<ConstraintViolation<T>> violationSet = this.valdator.validate(entity);
        Set<String> messages = new HashSet<>();
        for (ConstraintViolation<T> v : violationSet) {
            messages.add(v.getMessage());
        }
        return messages;
    }

}
