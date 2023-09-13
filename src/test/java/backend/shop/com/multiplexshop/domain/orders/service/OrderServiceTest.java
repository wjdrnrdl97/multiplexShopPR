package backend.shop.com.multiplexshop.domain.orders.service;

import backend.shop.com.multiplexshop.domain.Products.entity.Categories;
import backend.shop.com.multiplexshop.domain.Products.entity.Products;
import backend.shop.com.multiplexshop.domain.Products.repository.ProductsRepository;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.entity.Role;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import backend.shop.com.multiplexshop.domain.orders.OrderProductsDTOs;
import backend.shop.com.multiplexshop.domain.orders.dto.OrdersDTOs;
import backend.shop.com.multiplexshop.domain.orders.entity.OrderStatus;
import backend.shop.com.multiplexshop.domain.orders.entity.Orders;
import backend.shop.com.multiplexshop.domain.orders.repository.OrdersRepository;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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


    @Test
    @DisplayName("주문 요청을 받아 주문을 생성한다.")
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
        assertThat(orders).hasSize(1);
        assertThat(orders.get(0).getOrderPrice()).isEqualTo(50000);
    }
    @Test
    @DisplayName("주문번호를 받아 주문을 상세조회한다.")
    public void findbyOrdersId(){
        //given

        //when

        //then
    }
    @Test
    @DisplayName("취소할 주문의 번호를 입력받아 주문의 상태를 취소로 변경한다.(논리적 삭제)")
    public void deleteByOrdersId(){
        //given
        Member member = Member.builder()
                .memberEmailId("test")
                .password("1234")
                .memberName("테스트")
                .build();
        Member save = memberRepository.save(member);
        Orders order = Orders.builder()
                .orderPrice(10000)
                .orderStatus(OrderStatus.ORDER)
                .member(save)
                .build();
        ordersRepository.save(order);
        //when
        orderService.deleteByOrdersIds(1L);
        //then
        Orders orders = ordersRepository.findById(1L).get();
        assertThat(orders.getOrderStatus()).isEqualTo(OrderStatus.CANCEL);
    }
}