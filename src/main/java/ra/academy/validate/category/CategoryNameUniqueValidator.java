package ra.academy.validate.category;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import ra.academy.repository.ICategoryRepository;
import ra.academy.repository.IProductRepository;

@RequiredArgsConstructor
public class CategoryNameUniqueValidator implements ConstraintValidator<CategoryNameUnique,String> {
    private final ICategoryRepository categoryRepository;
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return !categoryRepository.existsByCategoryName(s);
    }
}
