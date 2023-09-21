package backend.shop.com.multiplexshop.domain.Products.repository;

import backend.shop.com.multiplexshop.domain.Products.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Long> {
}
