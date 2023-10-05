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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

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
                .build();
        productsRepository.save(products1);

        Products products2 = Products.builder()
                .productName("밀키트")
                .productPrice(5000)
                .stockQuantity(100)
                .categories(Categories.FOOD)
                .build();
        productsRepository.save(products2);

        Member member = Member.builder()
                .memberEmailId("test")
                .password("1234")
                .memberName("테스트")
                .build();
        memberRepository.save(member);

        OrderProductsRequestDTO dto1 = OrderProductsRequestDTO.builder()
                .productId(1L)
                .count(4)
                .build();
        OrderProductsRequestDTO dto2 = OrderProductsRequestDTO.builder()
                .productId(2L)
                .count(4)
                .build();
        List<OrderProductsRequestDTO> productAndCount = List.of(dto1, dto2);
        OrderRequestDTO request = OrderRequestDTO.builder()
                .memberId(1L)
                .productWithCount(productAndCount)
                .build();
        //when
        OrderResponseDTO result = orderService.save(request);
        //then
        List<OrderProducts> orderProducts = orderProductsRepository.findAll();
        Products productResult = productsRepository.findById(1L).orElse(null);
        assertThat(result).isNotNull();
        assertThat(orderProducts).hasSize(2);
        assertThat(productResult.getStockQuantity()).isEqualTo(96);
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
                .build();
        Products savedStuff = productsRepository.save(products1);
        Products products2 = Products.builder()
                .productName("밀키트")
                .productPrice(5000)
                .stockQuantity(100)
                .categories(Categories.FOOD)
                .build();
        Products savedFood = productsRepository.save(products2);
        Member member = Member.builder()
                .memberEmailId("test")
                .password("1234")
                .memberName("테스트")
                .build();
        Member savedMember = memberRepository.save(member);

        Orders order = Orders.createOrder(savedMember);
        Orders savedOrder = ordersRepository.save(order);

        OrderProducts orderProducts = OrderProducts.createOrderProducts(savedOrder, savedStuff,3);
        OrderProducts orderProducts1 = OrderProducts.createOrderProducts(savedOrder, savedFood,4);
        orderProductsRepository.saveAll(List.of(orderProducts,orderProducts1));
        // when
        List<OrderProductsResponseDTO> result = orderService.findAllByOrderId(savedOrder.getId());
        //then
        assertThat(result.size()).isEqualTo(2);
    }
    @Test
    @DisplayName("주문번호를 입력하여 해당 주문의 상태를 취소로 변경에 성공한다.")
    public void deleteByOrdersId() throws Exception{
        //given
        Products products1 = Products.builder()
                .productName("향수")
                .productPrice(10000)
                .stockQuantity(96)
                .categories(Categories.STUFF)
                .build();
        Products savedStuff = productsRepository.save(products1);
        Products products2 = Products.builder()
                .productName("밀키트")
                .productPrice(5000)
                .stockQuantity(96)
                .categories(Categories.FOOD)
                .build();
        Products savedFood = productsRepository.save(products2);

        Member member = Member.builder()
                .memberEmailId("test")
                .password("1234")
                .memberName("테스트")
                .build();
        Member savedMember = memberRepository.save(member);

        Orders order = Orders.createOrder(savedMember);
        Orders savedOrder = ordersRepository.save(order);

        Delivery delivery = Delivery.builder()
                .order(savedOrder)
                .deliveryStatus(DeliveryStatus.READY)
                .build();
        deliveryRepository.save(delivery);

        OrderProducts orderProducts = OrderProducts.createOrderProducts(savedOrder, savedStuff,4);
        OrderProducts orderProducts1 = OrderProducts.createOrderProducts(savedOrder, savedFood,4);
        orderProductsRepository.saveAll(List.of(orderProducts,orderProducts1));


        //when
        orderService.deleteByOrdersIds(savedOrder.getId());
        // then
        Orders orders = ordersRepository.findById(savedOrder.getId()).get();
        Products productsAfterCancelOrder = productsRepository.findById(1L).orElse(null);
        assertThat(orders.getOrderStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(productsAfterCancelOrder.getStockQuantity()).isEqualTo(100);
    }

    @Test
    @DisplayName("주문번호를 입력하여 해당 주문의 상태를 취소로 변경하려고 하나, 이미 배송이 완료된 주문이라 예외가 발생 해야한다.")
    public void deleteByOrdersIdThrows() throws Exception{
        //given
        Orders order = Orders.builder()
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
    @Test
    @DisplayName("회원의 번호를 입력받아 회원을 조회한후 해당 회원을 입력하여 회원의 모든 주문을 조회한다.")
    public void findOrderWithProductsByMemberID(){
        //given
        Member member = Member.builder()
                .memberEmailId("test")
                .password("1234")
                .memberName("테스트")
                .build();
        Member savedMember = memberRepository.save(member);

        Products products1 = Products.builder()
                .productName("향수")
                .productPrice(10000)
                .stockQuantity(100)
                .categories(Categories.STUFF)
                .build();
        Products savedStuff = productsRepository.save(products1);

        Products products2 = Products.builder()
                .productName("밀키트")
                .productPrice(5000)
                .stockQuantity(100)
                .categories(Categories.FOOD)
                .build();
        Products savedFood = productsRepository.save(products2);

        Orders order = Orders.createOrder(savedMember);
        Orders savedOrder = ordersRepository.save(order);

        OrderProducts orderProductsByStuff = OrderProducts.createOrderProducts(savedOrder, savedStuff,3);
        OrderProducts orderProductsByFood = OrderProducts.createOrderProducts(savedOrder, savedFood,4);
        orderProductsRepository.saveAll(List.of(orderProductsByStuff,orderProductsByFood));
        //when
        List<OrderProductsResponseDTO> result = orderService.findOrderWithProductsByMemberId(1L);
        //then
        assertThat(result).hasSize(2);
    }
    @Test
    @DisplayName("회원의 번호를 입력받아 회원을 조회한 후 해당 회원의 주문들을 조회한다.")
    public void test(){
        //given
        Member member = Member.builder()
                .memberEmailId("test")
                .password("1234")
                .memberName("테스트")
                .build();
        Member savedMember = memberRepository.save(member);
        Orders firstOrder = Orders.createOrder(savedMember);
        Orders secondOrder = Orders.createOrder(savedMember);
        ordersRepository.saveAll(List.of(firstOrder,secondOrder));
        //when
        List<OrderResponseDTO> result = orderService.findAllByMemberId(savedMember.getMemberId());
        //then
        assertThat(result).hasSize(2);
    }
}