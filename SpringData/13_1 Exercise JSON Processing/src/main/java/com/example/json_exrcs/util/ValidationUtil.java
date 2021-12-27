package com.example.json_exrcs.util;

//import javax.validation.ConstraintViolation;

import javax.validation.ConstraintViolation;
import java.util.Set;

public interface ValidationUtil {

    <E> boolean isValid(E entity);

    <E> Set<ConstraintViolation<E>> getViolation(E entity);

}
