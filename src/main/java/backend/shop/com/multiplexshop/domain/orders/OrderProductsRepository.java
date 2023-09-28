package backend.shop.com.multiplexshop.domain.orders;

import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.orders.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderProductsRepository extends JpaRepository<OrderProducts,Long> {

    @Query("SELECT op FROM OrderProducts op JOIN FETCH op.products JOIN FETCH op.orders WHERE op.orders.id = :orderId")
    List<OrderProducts> findByOrdersIdAll(@Param("orderId") Long orderId);
    @Query("SELECT op FROM OrderProducts op JOIN FETCH op.orders JOIN FETCH op.products WHERE op.orders.member = :member")
    List<OrderProducts> findAllByMember(@Param("member")Member member);
}
