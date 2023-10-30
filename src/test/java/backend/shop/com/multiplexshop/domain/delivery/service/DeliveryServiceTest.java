package backend.shop.com.multiplexshop.domain.delivery.service;

import backend.shop.com.multiplexshop.domain.IntegrationTestSupport;
import backend.shop.com.multiplexshop.domain.delivery.dto.DeliveryDTOs;
import backend.shop.com.multiplexshop.domain.delivery.entity.Delivery;
import backend.shop.com.multiplexshop.domain.delivery.entity.DeliveryStatus;
import backend.shop.com.multiplexshop.domain.delivery.repository.DeliveryRepository;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import backend.shop.com.multiplexshop.domain.orders.entity.OrderStatus;
import backend.shop.com.multiplexshop.domain.orders.entity.Orders;
import backend.shop.com.multiplexshop.domain.orders.repository.OrdersRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static backend.shop.com.multiplexshop.domain.delivery.dto.DeliveryDTOs.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DeliveryServiceTest extends IntegrationTestSupport {
    @Test
    @DisplayName("회원번호를 입력받아 회원을 조회 한후 해당 회원의 주문들을 조회한 다음 해당 주문들의 배송정보를 조회한다.")
    public void findAllByMemberId(){
        //given
        Member member = Member.builder()
                .memberEmailId("test")
                .password("1234")
                .memberName("테스트")
                .build();
        Member savedMember = memberRepository.save(member);

        Orders firstOrder = Orders.createOrder(savedMember);
        Orders savedFirstOrder = ordersRepository.save(firstOrder);
        Orders secondOrder = Orders.createOrder(savedMember);
        Orders savedSecondOrder = ordersRepository.save(secondOrder);

        Delivery firstDelivery = Delivery.createDelivery(savedFirstOrder);
        Delivery secondDelivery = Delivery.createDelivery(savedSecondOrder);
        deliveryRepository.saveAll(List.of(firstDelivery,secondDelivery));
        //when
        List<DeliveryResponseDTO> result = deliveryService.findAllByMemberId(savedMember.getMemberId());
        //then
        assertThat(result).hasSize(2);
    }
    @Test
    @DisplayName("주문번호를 입력하여 해당 주문의 배송정보를 조회한다.")
    public void findDeliveryByOrderId(){
        //given
        Member member = Member.builder()
                .memberEmailId("test")
                .password("1234")
                .memberName("테스트")
                .build();
        Member savedMember = memberRepository.save(member);

        Orders firstOrder = Orders.createOrder(savedMember);
        Orders savedFirstOrder = ordersRepository.save(firstOrder);

        Delivery delivery = Delivery.createDelivery(savedFirstOrder);
        deliveryRepository.save(delivery);
        //when
        DeliveryResponseDTO result = deliveryService.findDeliveryByOrderId(savedFirstOrder.getId());
        //then
        assertThat(result).isNotNull();
        assertThat(result.getDeliveryStatus()).isEqualTo(DeliveryStatus.READY);
    }
}