package ra.academy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.academy.model.entity.User;
@Repository
public interface IAccountRepository extends JpaRepository<User,String> {
}
