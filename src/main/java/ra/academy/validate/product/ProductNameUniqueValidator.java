package ra.academy.validate.product;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import ra.academy.repository.IProductRepository;

@RequiredArgsConstructor
public class ProductNameUniqueValidator implements ConstraintValidator<ProductNameUnique,String> {
    private final IProductRepository productRepository;
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return !productRepository.existsByProductName(s);
    }
}
