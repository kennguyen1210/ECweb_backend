package ra.academy.validate.product;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
@RequiredArgsConstructor
public class ImageUrlRequiredValidator implements ConstraintValidator<ImageUrlRequired, MultipartFile> {
    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        if(multipartFile.getSize()==0){
            return false;
        }
        return true;
    }
}
