package backend.shop.com.multiplexshop.domain.orders.service;

import backend.shop.com.multiplexshop.domain.Products.entity.Categories;
import backend.shop.com.multiplexshop.domain.Products.entity.Products;
import backend.shop.com.multiplexshop.domain.Products.repository.ProductsRepository;
import backend.shop.com.multiplexshop.domain.delivery.entity.Delivery;
import backend.shop.com.multiplexshop.domain.delivery.entity.DeliveryStatus;
import backend.shop.com.multiplexshop.domain.delivery.repository.DeliveryRepository;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import backend.shop.com.multiplexshop.domain.orders.OrderProducts;
import backend.shop.com.multiplexshop.domain.orders.OrderProductsRepository;
import backend.shop.com.multiplexshop.domain.orders.entity.OrderStatus;
import backend.shop.com.multiplexshop.domain.orders.entity.Orders;
import backend.shop.com.multiplexshop.domain.orders.repository.OrdersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static backend.shop.com.multiplexshop.domain.orders.OrderProductsDTOs.*;
import static backend.shop.com.multiplexshop.domain.orders.dto.OrdersDTOs.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    OrderService orderService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ProductsRepository productsRepository;
    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    OrderProductsRepository orderProductsRepository;
    @Autowired
    DeliveryRepository deliveryRepository;

    @Test
    @DisplayName("주문 요청을 받아 주문을 생성에 성공한다.")
    public void save(){
        //given
        Products products1 = Products.builder()
                .productName("향수")
                .productPrice(10000)
                .stockQuantity(100)
                .categories(Categories.STUFF)
                .orderQuantity(3)
                .build();
        productsRepository.save(products1);
        Products products2 = Products.builder()
                .productName("밀키트")
                .productPrice(5000)
                .stockQuantity(100)
                .categories(Categories.FOOD)
                .orderQuantity(4)
                .build();
        productsRepository.save(products2);
        Member member = Member.builder()
                .memberEmailId("test")
                .password("1234")
                .memberName("테스트")
                .build();
        memberRepository.save(member);
        OrderRequestDTO requestDTO = OrderRequestDTO.builder()
                .productId(List.of(1L,2L))
                .memberId(1L)
                .build();

        //when
        OrderResponseDTO save = orderService.save(requestDTO);
        //then
        List<Orders> orders = ordersRepository.findAll();
        Delivery delivery = deliveryRepository.findByOrderId(1L)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        assertThat(orders.get(0).getOrderPrice()).isEqualTo(50000);
        assertThat(delivery.getDeliveryStatus()).isEqualTo(DeliveryStatus.READY);
    }

    @Test
    @DisplayName("주문번호를 받아 주문을 상세조회에 성공한다.")
    public void findbyOrdersIdAll(){
        //given
        Products products1 = Products.builder()
                .productName("향수")
                .productPrice(10000)
                .stockQuantity(100)
                .categories(Categories.STUFF)
                .orderQuantity(3)
                .build();
    productsRepository.save(products1);
        Products products2 = Products.builder()
                .productName("밀키트")
                .productPrice(5000)
                .stockQuantity(100)
                .categories(Categories.FOOD)
                .orderQuantity(4)
                .build();
        productsRepository.save(products2);
        Member member = Member.builder()
                .memberEmailId("test")
                .password("1234")
                .memberName("테스트")
                .build();
        memberRepository.save(member);
        Orders order = Orders.createOrder(member, List.of(products1, products2));
        ordersRepository.save(order);
        OrderProducts orderProducts = OrderProducts.createOrderProducts(order, products1);
        OrderProducts orderProducts1 = OrderProducts.createOrderProducts(order, products2);
        orderProductsRepository.saveAll(List.of(orderProducts,orderProducts1));
        // when
        List<OrderProductsResponseDTO> list = orderService.findAllByOrderId(1L);
        //then
        assertThat(list.size()).isEqualTo(2);
        assertThat(list.get(0).getProducts().getProductName()).isEqualTo("향수");
    }
    @Test
    @DisplayName("주문번호를 입력하여 해당 주문의 상태를 취소로 변경에 성공한다.")
    public void deleteByOrdersId() throws Exception{
        //given
        Orders order = Orders.builder()
                .orderPrice(10000)
                .orderStatus(OrderStatus.ORDER)
                .build();
        Orders savedOrder = ordersRepository.save(order);
        Delivery delivery = Delivery.builder()
                .order(savedOrder)
                .deliveryStatus(DeliveryStatus.READY)
                .build();
        deliveryRepository.save(delivery);
        //when
        orderService.deleteByOrdersIds(savedOrder.getId());
        // then
        Orders orders = ordersRepository.findById(savedOrder.getId()).get();
        assertThat(orders.getOrderStatus()).isEqualTo(OrderStatus.CANCEL);
    }

    @Test
    @DisplayName("주문번호를 입력하여 해당 주문의 상태를 취소로 변경하려고 하나, 이미 배송이 완료된 주문이라 예외가 발생 해야한다.")
    public void deleteByOrdersIdThrows() throws Exception{
        //given
        Orders order = Orders.builder()
                .orderPrice(10000)
                .orderStatus(OrderStatus.ORDER)
                .build();
        Orders savedOrder = ordersRepository.save(order);
        Delivery delivery = Delivery.builder()
                .order(savedOrder)
                .deliveryStatus(DeliveryStatus.COMPLETE)
                .build();
        deliveryRepository.save(delivery);
        //when //then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            orderService.deleteByOrdersIds(savedOrder.getId());
        });
        // 예상한 예외 메시지 확인 (원하는 예외 메시지로 수정)
        String expectedMessage = "This order has already been completed.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}