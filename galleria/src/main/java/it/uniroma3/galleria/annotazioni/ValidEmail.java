package it.uniroma3.galleria.annotazioni;

import java.lang.annotation.*;

import javax.validation.Constraint;
import javax.validation.Payload;


@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE}) 
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Documented
public @interface ValidEmail {   
    String message() default "Formato email sbagliato! (Es: mario.rossi@email.com)";
    Class<?>[] groups() default {}; 
    Class<? extends Payload>[] payload() default {};
}