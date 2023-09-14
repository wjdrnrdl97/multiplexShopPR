package backend.shop.com.multiplexshop.domain.delivery.repository;

import backend.shop.com.multiplexshop.domain.delivery.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery,Long> {

    @Query("SELECT d from Delivery d where d.order = :order")
    Optional<Delivery> findByOrdersId(@Param("order") Long id);
}
