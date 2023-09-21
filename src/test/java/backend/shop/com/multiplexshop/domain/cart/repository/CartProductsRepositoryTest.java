package backend.shop.com.multiplexshop.domain.cart.repository;

import backend.shop.com.multiplexshop.domain.Products.entity.Categories;
import backend.shop.com.multiplexshop.domain.Products.entity.Products;
import backend.shop.com.multiplexshop.domain.Products.repository.ProductsRepository;
import backend.shop.com.multiplexshop.domain.cart.entity.Cart;
import backend.shop.com.multiplexshop.domain.cart.entity.CartProducts;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CartProductsRepositoryTest {
    
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    ProductsRepository productsRepository;
    @Autowired
    CartProductsRepository cartProductsRepository;

    
    
    @Test
    @DisplayName("장바구니를 입력받아 해당 장바구니 관련 모든 장바구니상품을 조회한다.")    
    public void test(){
        //given
        Member member = Member.builder()
                .memberEmailId("test")
                .password("1234")
                .memberName("테스트")
                .build();
        Member savedMember = memberRepository.save(member);

        Cart cart = Cart.createCart(savedMember);
        Cart savedCart = cartRepository.save(cart);

        Products products1 = Products.builder()
                .productName("향수")
                .productPrice(10000)
                .stockQuantity(100)
                .categories(Categories.STUFF)
                .orderQuantity(3)
                .build();
        Products savedStuff = productsRepository.save(products1);
        Products products2 = Products.builder()
                .productName("밀키트")
                .productPrice(5000)
                .stockQuantity(100)
                .categories(Categories.FOOD)
                .orderQuantity(4)
                .build();
        Products savedFood = productsRepository.save(products2);

        CartProducts createCartByStuff = CartProducts.builder()
                .products(savedStuff)
                .cart(savedCart)
                .count(3)
                .build();
        CartProducts createCartByFood = CartProducts.builder()
                .products(savedFood)
                .cart(savedCart)
                .count(3)
                .build();
        cartProductsRepository.saveAll(List.of(createCartByStuff,createCartByFood));
        //when
        List<CartProducts> result = cartProductsRepository.findAllByCart(savedCart);
        //then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getCart()).isEqualTo(savedCart);
    }
}