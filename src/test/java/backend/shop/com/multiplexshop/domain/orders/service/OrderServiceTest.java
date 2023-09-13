package backend.shop.com.multiplexshop.domain.orders.service;

import backend.shop.com.multiplexshop.domain.Products.entity.Categories;
import backend.shop.com.multiplexshop.domain.Products.entity.Products;
import backend.shop.com.multiplexshop.domain.Products.repository.ProductsRepository;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.entity.Role;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import backend.shop.com.multiplexshop.domain.orders.OrderProductsDTOs;
import backend.shop.com.multiplexshop.domain.orders.dto.OrdersDTOs;
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

        OrderRequestDTO requestDTO = OrderRequestDTO.builder()
                .productId(List.of(1L,2L))
                .memberId(1L)
                .build();

        //when
        OrderResponseDTO save = orderService.save(requestDTO);

        //then
        List<Orders> orders = ordersRepository.findAll();
        assertThat(orders).hasSize(1);
    }
}