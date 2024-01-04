package ra.academy.service;

import org.springframework.data.domain.Pageable;
import ra.academy.exception.UsernameAndPasswordIncorrectException;
import ra.academy.model.dto.request.auth.SignInRequest;
import ra.academy.model.dto.request.auth.SignUpRequest;
import ra.academy.model.dto.request.user.AccountRequest;
import ra.academy.model.dto.request.user.PasswordChangeRequest;
import ra.academy.model.dto.response.*;
import ra.academy.model.entity.Role;
import ra.academy.model.entity.User;

import java.util.List;

public interface IUserService extends IGenericService<User,String>{
        void signUp(SignUpRequest signUpRequest);
        PageDataResponse<User> findAllHavePageable(Pageable pageable);
        JwtResponse singIn(SignInRequest signInRequest) throws UsernameAndPasswordIncorrectException;

        void addRole(String userId, Long roleId);
        void deleteRole(String userId, Long roleId);
        void changeStatus(String userId);
        DataResponse<Role> getListRole();
        DataResponse<User> findUserBySearch(String search);
        AccountResponse getAccount(String userId);
        void changePass(PasswordChangeRequest p, String userId);
        AccountResponse saveChangeInfo(AccountRequest acc, String userId);
}
