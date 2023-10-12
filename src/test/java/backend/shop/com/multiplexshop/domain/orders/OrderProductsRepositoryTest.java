package backend.shop.com.multiplexshop.domain.orders;

import backend.shop.com.multiplexshop.domain.Products.entity.Categories;
import backend.shop.com.multiplexshop.domain.Products.entity.Products;
import backend.shop.com.multiplexshop.domain.Products.repository.ProductsRepository;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import backend.shop.com.multiplexshop.domain.orders.entity.OrderProducts;
import backend.shop.com.multiplexshop.domain.orders.entity.Orders;
import backend.shop.com.multiplexshop.domain.orders.repository.OrderProductsRepository;
import backend.shop.com.multiplexshop.domain.orders.repository.OrdersRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class OrderProductsRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ProductsRepository productsRepository;

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    OrderProductsRepository orderProductsRepository;


    @Test
    @DisplayName("회원을 입력하여 해당 회원의 모든 주문상품을 조회한다.")
    public void findAllByMember(){
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

        Member findMember = memberRepository.findById(1L).orElse(null);
        //when
        List<OrderProducts> result = orderProductsRepository.findAllByMember(findMember);
        //then
        assertThat(result).hasSize(2);
    }

}