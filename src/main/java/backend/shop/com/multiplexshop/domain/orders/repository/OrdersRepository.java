package backend.shop.com.multiplexshop.domain.orders.repository;

import backend.shop.com.multiplexshop.domain.orders.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository <Orders,Long> {
}
