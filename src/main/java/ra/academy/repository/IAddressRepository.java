package ra.academy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ra.academy.model.entity.Address;

import java.util.List;
@Repository
public interface IAddressRepository extends JpaRepository<Address,Long> {
    List<Address> findAllByUserId(String userId);
}
