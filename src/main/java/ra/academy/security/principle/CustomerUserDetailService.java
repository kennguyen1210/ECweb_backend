package ra.academy.security.principle;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ra.academy.model.entity.User;
import ra.academy.repository.IUserRepository;

import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class CustomerUserDetailService implements UserDetailsService {
    private final IUserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("Username not found!"));
        List<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(e-> new SimpleGrantedAuthority(e.getRoleName().name()))
                .collect(Collectors.toList());
        return CustomUserDetail.builder()
                .authorities(authorities)
                .userId(user.getUserId())
                .username(user.getUsername())
                .enable(user.getStatus())
                .password(user.getPassword())
                .fullName(user.getFullName())
                .build();
    }
}
