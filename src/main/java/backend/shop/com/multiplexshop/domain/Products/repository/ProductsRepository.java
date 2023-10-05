package backend.shop.com.multiplexshop.domain.Products.repository;

import backend.shop.com.multiplexshop.domain.Products.entity.Products;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Products> findAndLockById(Long id);
}
