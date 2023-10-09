package backend.shop.com.multiplexshop.domain.orders.repository;

import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.orders.OrderProducts;
import backend.shop.com.multiplexshop.domain.orders.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrdersRepository extends JpaRepository <Orders,Long> {
    List<Orders> findAllByMember(Member member);
}
