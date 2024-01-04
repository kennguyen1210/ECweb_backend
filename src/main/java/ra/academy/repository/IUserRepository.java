package ra.academy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.academy.model.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User,String> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String str);
    boolean existsByPhone(String str);
    Page<User> findAll(Pageable pageable);
    List<User> findAllByFullNameContainingIgnoreCase(String search);
}
