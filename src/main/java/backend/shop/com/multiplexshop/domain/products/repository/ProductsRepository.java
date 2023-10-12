package backend.shop.com.multiplexshop.domain.Products.repository;

import backend.shop.com.multiplexshop.domain.Products.entity.Categories;
import backend.shop.com.multiplexshop.domain.Products.entity.Products;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductsRepository extends JpaRepository<Products, Long> {

    Page<Products> findAllByCategories(Categories categories, PageRequest pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Products p where p.id = :id")
    Optional<Products> findAndPessimisticLockById(Long id);

}
