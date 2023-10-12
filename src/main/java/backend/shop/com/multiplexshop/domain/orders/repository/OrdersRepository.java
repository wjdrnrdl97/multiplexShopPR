package backend.shop.com.multiplexshop.domain.orders.repository;

import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.orders.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersRepository extends JpaRepository <Orders,Long> {
    List<Orders> findAllByMember(Member member);
}
