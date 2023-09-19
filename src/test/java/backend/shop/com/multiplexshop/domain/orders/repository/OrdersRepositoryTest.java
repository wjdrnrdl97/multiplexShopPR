package backend.shop.com.multiplexshop.domain.orders.repository;

import backend.shop.com.multiplexshop.domain.Products.entity.Categories;
import backend.shop.com.multiplexshop.domain.Products.entity.Products;
import backend.shop.com.multiplexshop.domain.Products.repository.ProductsRepository;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import backend.shop.com.multiplexshop.domain.orders.OrderProducts;
import backend.shop.com.multiplexshop.domain.orders.OrderProductsRepository;
import backend.shop.com.multiplexshop.domain.orders.entity.Orders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class OrdersRepositoryTest {

    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ProductsRepository productsRepository;
    @Autowired
    OrderProductsRepository orderProductsRepository;

    @BeforeEach
    public void delete(){
        memberRepository.deleteAll();
        productsRepository.deleteAll();
        orderProductsRepository.deleteAll();
    }

    @Test
    @DisplayName("findAllByOrderId()를 사용하여 주문번호를 입력하였을 때 해당되는 주문상품을 조회한다.")
    public void test() throws Exception{
        //given
        createStuff();
        Products stuff = productsRepository.findById(1L).get();
        createFood();
        Products food = productsRepository.findById(2L).get();
        createOrder();
        Orders order = ordersRepository.findById(1L).get();
        OrderProducts orderProducts1 = OrderProducts.createOrderProducts(order,stuff);
        orderProductsRepository.save(orderProducts1);
        OrderProducts orderProducts2 = OrderProducts.createOrderProducts(order,food);
        orderProductsRepository.save(orderProducts2);

        //when
        Orders orders = ordersRepository.findById(1L).get();
        List<OrderProducts> orderProducts = ordersRepository.findByOrdersIdAll(orders.getId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        //then
        assertThat(orderProducts.size()).isEqualTo(2);
        assertThat(orderProducts.get(0).getOrders().getId()).isEqualTo(1L);
    }

    private void createOrder() {
        Orders saveOrder = Orders.builder()
                .orderPrice(50000)
                .build();
        ordersRepository.save(saveOrder);
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