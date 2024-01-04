package ra.academy.validate.user;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import ra.academy.repository.IUserRepository;
import ra.academy.validate.user.PhoneUnique;

@RequiredArgsConstructor
public class PhoneUniqueValidator implements ConstraintValidator<PhoneUnique,String> {
    private final IUserRepository userRepository;
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return !userRepository.existsByPhone(s);
    }
}
