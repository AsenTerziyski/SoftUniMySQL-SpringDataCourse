package softuni.exam.instagraphlite.util;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;

public interface ValidationUtil {
    <T> boolean isValid(T entity);
    <T>Set<ConstraintViolation<T>> getViolations(T entity);
    <T> Set<String> getViolationMessages(T entity);
}
