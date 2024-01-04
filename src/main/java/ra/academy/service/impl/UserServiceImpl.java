package ra.academy.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ra.academy.exception.UsernameAndPasswordIncorrectException;
import ra.academy.model.dto.request.auth.SignInRequest;
import ra.academy.model.dto.request.auth.SignUpRequest;
import ra.academy.model.dto.request.user.AccountRequest;
import ra.academy.model.dto.request.user.PasswordChangeRequest;
import ra.academy.model.dto.response.*;
import ra.academy.model.entity.Role;
import ra.academy.model.entity.RoleName;
import ra.academy.model.entity.User;
import ra.academy.repository.IRoleRepository;
import ra.academy.repository.IUserRepository;
import ra.academy.security.jwt.JwtProvider;
import ra.academy.security.principle.CustomUserDetail;
import ra.academy.service.IUserService;
import ra.academy.service.UploadFileService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UploadFileService uploadFileService;
    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User findById(String id) {
        return userRepository.findById(id).orElseThrow(()->new NoSuchElementException("User not found!"));
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public void detele(String id) {

    }

    @Override
    public void signUp(SignUpRequest signUpRequest) {
        Set<Role> setRole = new HashSet<>();
        if(signUpRequest.getRoleList() == null || signUpRequest.getRoleList().isEmpty()){
            setRole.add(roleRepository.findRoleByRoleName(RoleName.USER).orElseThrow(()-> new RuntimeException("Role not found")));
        } else {
            signUpRequest.getRoleList().forEach(e->{
                switch (e){
                    case "admin":
                        setRole.add(roleRepository.findRoleByRoleName(RoleName.ADMIN).orElseThrow(()-> new RuntimeException("Role not found")));
                        break;
                    case "user":
                    default:
                        setRole.add(roleRepository.findRoleByRoleName(RoleName.USER).orElseThrow(()-> new RuntimeException("Role not found")));
                }
            });
        }
        User user = modelMapper.map(signUpRequest, User.class);
        user.setRoles(setRole);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreate_at(new Date());
        user.setStatus(true);
        if(signUpRequest.getAvatarImg() == null){
            user.setAvatar(null);
        } else {
            String avatar = uploadFileService.uploadFileToServer(signUpRequest.getAvatarImg());
            user.setAvatar(avatar);
        }
        userRepository.save(user);
    }

    @Override
    public JwtResponse singIn(SignInRequest signInRequest) throws UsernameAndPasswordIncorrectException {
        Authentication authentication = null;
        String str = passwordEncoder.encode("321321");
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getUsername(),signInRequest.getPassword()));
        }catch (Exception e){
            throw new UsernameAndPasswordIncorrectException("Username or password incorrect!");
        }
        CustomUserDetail userDetail = (CustomUserDetail) authentication.getPrincipal();
        String accessToken = jwtProvider.generateAccessToken(userDetail);
        String refreshToken = jwtProvider.generateRefreshToken(userDetail);
        return JwtResponse.builder()
                .id(userDetail.getUserId())
                .fullName(userDetail.getFullName())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public PageDataResponse<User> findAllHavePageable(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return new PageDataResponse<>(users.getContent(),users.getTotalPages(),users.getSize(),users.getNumber(),users.getSort());
    }

    @Override
    public void addRole(String userId, Long roleId) {
        User u = userRepository.findById(userId).orElseThrow(()->new NoSuchElementException("User not found!"));
        Role r = roleRepository.findById(roleId).orElseThrow(()-> new NoSuchElementException("Role not found!"));
        if(u.getRoles().stream().anyMatch(e->e.getRoleName()==r.getRoleName())){
            throw new RuntimeException("User had this role!");
        }
        u.getRoles().add(r);
        userRepository.save(u);
    }

    @Override
    public void deleteRole(String userId, Long roleId) {
        User u = userRepository.findById(userId).orElseThrow(()->new NoSuchElementException("User not found!"));
        Role r = roleRepository.findById(roleId).orElseThrow(()-> new NoSuchElementException("Role not found!"));
        if(u.getRoles().stream().noneMatch(e->e.getRoleName()==r.getRoleName())){
            throw new RuntimeException("User dont have this role!");
        }
        u.getRoles().remove(r);
        userRepository.save(u);
    }

    @Override
    public void changeStatus(String userId) {
        User u = userRepository.findById(userId).orElseThrow(()->new NoSuchElementException("User not found!"));
        u.setStatus(!u.getStatus());
        userRepository.save(u);
    }

    @Override
    public DataResponse<Role> getListRole() {
        return new DataResponse<>(roleRepository.findAll());
    }

    @Override
    public DataResponse<User> findUserBySearch(String search) {
        try {
            List<User> users = userRepository.findAllByFullNameContainingIgnoreCase(search);
            return new DataResponse<>(users);
        }catch (Exception e){
            throw new RuntimeException("Not found!");
        }
    }

    @Override
    public AccountResponse getAccount(String userId) {
        User u = userRepository.findById(userId).orElseThrow(()->new NoSuchElementException("User not found!"));
        return AccountResponse.builder()
                .username(u.getUsername())
                .email(u.getEmail())
                .fullName(u.getFullName())
                .address(u.getAddress())
                .avatarUrl(u.getAvatar())
                .phone(u.getPhone())
                .build();
    }

    @Override
    public void changePass(PasswordChangeRequest p, String userId) {
        User u = userRepository.findById(userId).orElseThrow(()->new NoSuchElementException("User not found!"));
        if(!passwordEncoder.matches(p.getOldPassword(),u.getPassword())){
            throw new RuntimeException("not authorize");
        }
        u.setPassword(passwordEncoder.encode(p.getNewPass()));
        userRepository.save(u);
    }

    @Override
    public AccountResponse saveChangeInfo(AccountRequest acc,String userId) {
        User u = userRepository.findById(userId).orElseThrow(()->new NoSuchElementException("User not found!"));
        if(!(acc.getAvatar().getSize()==0)){
            // thay doi avata
            String newAvatar = uploadFileService.uploadFileToServer(acc.getAvatar());
            u.setAvatar(newAvatar);
        }
        // khong thay doi avatar
        u.setPhone(acc.getPhone());
        u.setAddress(acc.getAddress());
        u.setFullName(acc.getFullName());
        u.setEmail(acc.getEmail());
        u.setUpdate_at(new Date());
        try {
            userRepository.save(u);
            return AccountResponse.builder()
                    .username(u.getUsername())
                    .email(u.getEmail())
                    .fullName(u.getFullName())
                    .address(u.getAddress())
                    .avatarUrl(u.getAvatar())
                    .phone(u.getPhone())
                    .build();
        }catch (Exception e){
            throw new RuntimeException("Phone is exist!");
        }


    }
}
