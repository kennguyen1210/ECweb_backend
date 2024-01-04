package ra.academy.validate.product;

import jakarta.validation.Constraint;
import ra.academy.validate.user.PhoneUniqueValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ProductNameUniqueValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ProductNameUnique {
    // khai bao cac thuoc tinh cua annotation
    String message() default "ProductName is exist";

    Class<?>[] groups() default {};

    Class<? extends jakarta.validation.Payload>[] payload() default {};
}
