package backend.shop.com.multiplexshop.domain.orders.service;

import backend.shop.com.multiplexshop.domain.Products.entity.Categories;
import backend.shop.com.multiplexshop.domain.Products.entity.Products;
import backend.shop.com.multiplexshop.domain.Products.repository.ProductsRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static backend.shop.com.multiplexshop.domain.orders.OrderProductsDTOs.*;
import static backend.shop.com.multiplexshop.domain.orders.dto.OrdersDTOs.*;
import static org.assertj.core.api.Assertions.*;

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

    @BeforeEach
    public void delete(){
        memberRepository.deleteAll();
        productsRepository.deleteAll();
        orderProductsRepository.deleteAll();
    }

    @Test
    @DisplayName("주문 요청을 받아 주문을 생성한다.")
    public void save(){
        //given
        createStuff();
        createFood();
        createMember();
        OrderRequestDTO requestDTO = OrderRequestDTO.builder()
                .productId(List.of(1L,2L))
                .memberId(1L)
                .build();

        //when
        OrderResponseDTO save = orderService.save(requestDTO);
        //then
        List<Orders> orders = ordersRepository.findAll();
        List<OrderProducts> orderProducts = ordersRepository.findByOrdersIdAll(1L)
                        .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        assertThat(orders.get(0).getOrderPrice()).isEqualTo(50000);
        assertThat(orderProducts.size()).isEqualTo(2);
    }
    @Test
    @DisplayName("주문번호를 받아 주문을 상세조회 한다.")
    public void findbyOrdersIdAll(){
        //given
        Member member = Member.builder()
                .memberEmailId("test")
                .password("1234")
                .memberName("테스트")
                .build();
        memberRepository.save(member);
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
        OrderRequestDTO requestDTO = OrderRequestDTO.builder()
                .productId(List.of(1L,2L))
                .memberId(1L)
                .build();
        OrderResponseDTO save = orderService.save(requestDTO);
        //when
        List<OrderProductsResponseDTO> list = orderService.findByOrdersIdAll(1L);
        Products products = productsRepository.findById(1L).get();
        //then
        assertThat(list.size()).isEqualTo(2);
        assertThat(list.get(0).getProducts()).isEqualTo(products);
    }
    @Test
    @DisplayName("취소할 주문의 번호를 입력받아 주문의 상태를 취소로 변경한다.(논리적 삭제)")
    public void deleteByOrdersId(){
        //given
        Orders order = Orders.builder()
                .orderPrice(10000)
                .build();
        ordersRepository.save(order);
        //when
        orderService.deleteByOrdersIds(1L);
        //then
        Orders orders = ordersRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        assertThat(orders.getOrderStatus()).isEqualTo(OrderStatus.CANCEL);
    }



    private void createMember() {
        Member member = Member.builder()
                .memberEmailId("test")
                .password("1234")
                .memberName("테스트")
                .build();
        memberRepository.save(member);
    }

    private void createFood() {
        Products products2 = Products.builder()
                .productName("밀키트")
                .productPrice(5000)
                .stockQuantity(100)
                .categories(Categories.FOOD)
                .orderQuantity(4)
                .build();
        productsRepository.save(products2);
    }

    private void createStuff() {
        Products products1 = Products.builder()
                .productName("향수")
                .productPrice(10000)
                .stockQuantity(100)
                .categories(Categories.STUFF)
                .orderQuantity(3)
                .build();
        productsRepository.save(products1);
    }
}