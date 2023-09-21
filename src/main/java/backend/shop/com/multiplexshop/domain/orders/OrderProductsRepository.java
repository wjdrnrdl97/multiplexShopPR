package backend.shop.com.multiplexshop.domain.orders;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductsRepository extends JpaRepository<OrderProducts,Long> {
}
