package backend.shop.com.multiplexshop.domain.orders.service;

import backend.shop.com.multiplexshop.domain.Products.entity.Categories;
import backend.shop.com.multiplexshop.domain.Products.entity.Products;
import backend.shop.com.multiplexshop.domain.Products.repository.ProductsRepository;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.entity.Role;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import backend.shop.com.multiplexshop.domain.orders.OrderProductsDTOs;
import backend.shop.com.multiplexshop.domain.orders.entity.Orders;
import backend.shop.com.multiplexshop.domain.orders.repository.OrdersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static backend.shop.com.multiplexshop.domain.orders.OrderProductsDTOs.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OrderServiceTest {

    @Autowired
    OrderService orderService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ProductsRepository productsRepository;
    @Autowired
    OrdersRepository ordersRepository;

//    @Test
//    @DisplayName("OrderService의 save()로 주문 생성에 성공 해야한다.")
//    public void test1() throws Exception{
//        //given
//        Member member = Member.builder()
//                .memberEmailId("Admin")
//                .password("1234")
//                .memberName("관리자")
//                .role(Role.ADMIN)
//                .build();
//        Member newMember = memberRepository.save(member);
//        Products product = Products.builder()
//                .productName("상품1")
//                .productPrice(10000)
//                .stockQuantity(1000)
//                .categories(Categories.STUFF)
//                .build();
//        Products newProduct = productsRepository.save(product);
//        OrderProductsRequestDTO dto = OrderProductsRequestDTO.builder()
//                .memberId(newMember.getId())
//                .productId(newProduct.getId())
//                .orderCount(2)
//                .build();
//        //when
//        Orders order = orderService.save(dto);
//        //then
//        Orders result = ordersRepository.findById(order.getId())
//                .orElseThrow(()-> new IllegalArgumentException("Order not found"));
//        assertThat(result).isNotNull();
//        assertThat(result.getMember().getId()).isEqualTo(newMember.getId());
//        assertThat(result.getOrderProducts().get(0).getProducts().getId()).isEqualTo(newProduct.getId());
//
//    }
}