package backend.shop.com.multiplexshop.domain.delivery.repository;

import backend.shop.com.multiplexshop.domain.delivery.entity.Delivery;
import backend.shop.com.multiplexshop.domain.delivery.entity.DeliveryStatus;
import backend.shop.com.multiplexshop.domain.orders.entity.OrderStatus;
import backend.shop.com.multiplexshop.domain.orders.entity.Orders;
import backend.shop.com.multiplexshop.domain.orders.repository.OrdersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class DeliveryRepositoryTest {

    @Autowired
    DeliveryRepository deliveryRepository;

    @Autowired
    OrdersRepository ordersRepository;

    @Test
    @DisplayName("주문번호를 입력받았을 때 해당 주문번호의 배송정보를 조회한다.")
    public void test(){
        //given
        Orders orders = Orders.builder()
                .orderStatus(OrderStatus.ORDER)
                .build();
        Orders savedOrder = ordersRepository.save(orders);

        Delivery delivery = Delivery.createDelivery(savedOrder);
        deliveryRepository.save(delivery);
        //when
        Delivery result = deliveryRepository.findByOrderId(savedOrder.getId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        //then
        assertThat(result.getDeliveryStatus()).isEqualTo(DeliveryStatus.READY);
    }
}