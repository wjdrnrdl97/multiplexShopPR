package backend.shop.com.multiplexshop.domain.orders.controller;

import backend.shop.com.multiplexshop.domain.Products.entity.Categories;
import backend.shop.com.multiplexshop.domain.Products.entity.Products;
import backend.shop.com.multiplexshop.domain.Products.repository.ProductsRepository;
import backend.shop.com.multiplexshop.domain.delivery.entity.Delivery;
import backend.shop.com.multiplexshop.domain.delivery.repository.DeliveryRepository;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import backend.shop.com.multiplexshop.domain.orders.OrderProducts;
import backend.shop.com.multiplexshop.domain.orders.OrderProductsRepository;
import backend.shop.com.multiplexshop.domain.orders.dto.OrdersDTOs;
import backend.shop.com.multiplexshop.domain.orders.dto.OrdersDTOs.OrderRequestDTO;
import backend.shop.com.multiplexshop.domain.orders.entity.OrderStatus;
import backend.shop.com.multiplexshop.domain.orders.entity.Orders;
import backend.shop.com.multiplexshop.domain.orders.repository.OrdersRepository;
import backend.shop.com.multiplexshop.domain.orders.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class OrdersAPIControllerTest {

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

    @Autowired
    MockMvc mockMvc;
    @Autowired
    WebApplicationContext context;
    @Autowired
    ObjectMapper objectMapper;
    @BeforeEach
    public void setMockMvc(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilter(new CharacterEncodingFilter("UTF-8",true))
                .build();
    }
    @Test
    @DisplayName("사용자의 요청에 따라 서비스를 통해 주문이 생성되고 Http 201 코드와 body에 생성된 주문이 응답해야 한다.")
    public void postOrderByRequest() throws Exception{
        //given
        String url = "/api/order";
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
        OrderRequestDTO request = OrderRequestDTO.builder()
                .productId(List.of(1L,2L))
                .memberId(1L)
                .build();
        //when
        String requestBody = objectMapper.writeValueAsString(request);
        ResultActions perform = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(requestBody))
        //then
                .andDo(print())
                .andExpect(status().isCreated());
        }

@Test
@DisplayName("주문의 번호를 통해 해당  주문과 주문한 상품을 조회하여 HTTP 200와 조회 목록을 응답에 성공해야 한다.")
@Transactional
public void getOrderWithProductByOrderId() throws Exception{
    //given
    String url = "/api/order/{id}";
    Products products1 = Products.builder()
            .productName("향수")
            .productPrice(10000)
            .stockQuantity(100)
            .categories(Categories.STUFF)
            .orderQuantity(3)
            .build();
    Products savedProduct1 = productsRepository.save(products1);
    Products products2 = Products.builder()
            .productName("밀키트")
            .productPrice(5000)
            .stockQuantity(100)
            .categories(Categories.FOOD)
            .orderQuantity(4)
            .build();
    Products savedProduct2 = productsRepository.save(products2);
    Member member = Member.builder()
            .memberEmailId("test")
            .password("1234")
            .memberName("테스트")
            .build();
    Member savedMember = memberRepository.save(member);
    Orders orders = Orders.builder()
            .member(savedMember)
            .orderPrice(50000)
            .orderStatus(OrderStatus.ORDER)
            .build();
    Orders savedOrder = ordersRepository.save(orders);
    OrderProducts stuff = OrderProducts.builder()
            .orders(savedOrder)
            .products(savedProduct1)
            .build();
    OrderProducts food = OrderProducts.builder()
            .orders(savedOrder)
            .products(savedProduct2)
            .build();
    orderProductsRepository.saveAll(List.of(stuff,food));
    //when
    ResultActions perform = mockMvc.perform(get(url, 1))
    //then
            .andExpect(status().isOk());

}
    @Test
    @DisplayName("주문의 번호를 통해 해당 주문이 배송되었는지 확인 후에 주문의 상태를 CANCEL로 변경 후 HTTP 204 응답에 성공한다.")
    public void deleteOrderByOrderId() throws Exception{
        //given
        String url = "/api/order/{id}";

        Orders order = Orders.builder()
                .orderStatus(OrderStatus.ORDER)
                .orderPrice(50000)
                .build();
        ordersRepository.save(order);
        Delivery delivery = Delivery.createDelivery(order);
        deliveryRepository.save(delivery);

        //when
        ResultActions perform = mockMvc.perform(delete(url, 1))
        //then
                .andExpect(status().isNoContent());
    }
}