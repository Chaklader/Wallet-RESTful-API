package mobi.puut.entities;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Chaklader on 6/7/17.
 */

@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Documented
//@Constraint(validatedBy = .ValidEmailImpl.class)
@Constraint(validatedBy = ValidEmailImpl.class)
public @interface ValidEmail {

    String message() default "This does not appear to be a valid email address";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int min() default 5;
}

