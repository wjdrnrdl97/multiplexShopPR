package backend.shop.com.multiplexshop.domain.delivery.repository;

import backend.shop.com.multiplexshop.domain.delivery.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery,Long> {
}
