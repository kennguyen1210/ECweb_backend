package ra.academy.validate.user;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanWrapperImpl;
import ra.academy.validate.user.PasswordMatching;

@RequiredArgsConstructor
public class PasswordMatchingValidator implements ConstraintValidator<PasswordMatching,Object> {
    private String passwordField;
    private String confirmPasswordField;
    @Override
    public boolean isValid(Object userDto, ConstraintValidatorContext context) {
        Object passwordValue = new BeanWrapperImpl(userDto).getPropertyValue(passwordField);
        Object confirmPasswordValue = new BeanWrapperImpl(userDto).getPropertyValue(confirmPasswordField);
        if(passwordValue == null || !passwordValue.equals(confirmPasswordValue)){
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode(confirmPasswordField)
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

    @Override
    public void initialize(PasswordMatching passwordMatching) {
        passwordField = passwordMatching.password();
        confirmPasswordField = passwordMatching.confirmPassword();
    }
}
