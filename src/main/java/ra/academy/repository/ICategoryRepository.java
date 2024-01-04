package ra.academy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.academy.model.entity.Category;

import java.util.List;

@Repository
public interface ICategoryRepository extends JpaRepository<Category,Long> {
    boolean existsByCategoryId(Long id);
    boolean existsByCategoryName(String name);
    List<Category> findAllByStatus(Boolean status);
    Page<Category> findAll(Pageable pageable);
}
