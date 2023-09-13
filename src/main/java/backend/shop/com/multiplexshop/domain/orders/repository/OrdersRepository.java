package backend.shop.com.multiplexshop.domain.orders.repository;

import backend.shop.com.multiplexshop.domain.orders.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrdersRepository extends JpaRepository <Orders,Long> {

    @Modifying
    @Query("UPDATE Orders o SET o.orderStatus = 'CANCEL' WHERE o.id = :id")
    void changeOrderStatus(@Param("id") Long id);
}
