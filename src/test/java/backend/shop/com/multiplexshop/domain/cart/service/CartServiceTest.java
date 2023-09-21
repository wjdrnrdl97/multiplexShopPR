package backend.shop.com.multiplexshop.domain.cart.service;

import backend.shop.com.multiplexshop.domain.Products.entity.Categories;
import backend.shop.com.multiplexshop.domain.Products.entity.Products;
import backend.shop.com.multiplexshop.domain.Products.repository.ProductsRepository;
import backend.shop.com.multiplexshop.domain.cart.entity.Cart;
import backend.shop.com.multiplexshop.domain.cart.entity.CartProducts;
import backend.shop.com.multiplexshop.domain.cart.repository.CartProductsRepository;
import backend.shop.com.multiplexshop.domain.cart.repository.CartRepository;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static backend.shop.com.multiplexshop.domain.cart.dto.CartDTOs.*;
import static backend.shop.com.multiplexshop.domain.cart.dto.CartProductsDTOs.*;
import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class CartServiceTest {

    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartProductsRepository cartProductsRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ProductsRepository productsRepository;
    @Autowired
    CartService cartService;

    @Test
    @DisplayName("요청에 따라 회원과 상품을 조회한 후 조회한 회원을 바탕으로 카트를 조회하여(없을 시 새로 생성)후에 카트상품에 카트와 조회한 상품을 담아 저장한다.")
    public void createCartWithProductsByRequest() {
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
        CartRequestDTO request = CartRequestDTO.builder()
                .memberId(1L)
                .productsId(1L)
                .count(3)
                .build();
        //when
        CartResponseDTO response = cartService.createCartWithProductsByRequest(request);
        //then
        assertThat(response.getMember().getId()).isEqualTo(1L);
    }
    @Test
    @DisplayName("회원 아이디를 입력하였을 때 해당 회원의 장바구니를 조회한 후 조회한 장바구니에 담겨진 장바구니상품을 조회한다.")
    public void getCartWithProductsByMemberId(){
        //given
        Member member = Member.builder()
                .memberEmailId("test")
                .password("1234")
                .memberName("테스트")
                .build();
        Member savedMember = memberRepository.save(member);

        Cart createCart = Cart.createCart(savedMember);
        Cart savedCart = cartRepository.save(createCart);

        Products products1 = Products.builder()
                .productName("향수")
                .productPrice(10000)
                .stockQuantity(100)
                .categories(Categories.STUFF)
                .orderQuantity(3)
                .build();
        Products stuff = productsRepository.save(products1);
        Products products2 = Products.builder()
                .productName("밀키트")
                .productPrice(5000)
                .stockQuantity(100)
                .categories(Categories.FOOD)
                .orderQuantity(4)
                .build();
        Products food = productsRepository.save(products2);

        CartProducts cartWithStuff = CartProducts.builder().products(stuff)
                .cart(createCart)
                .count(3)
                .build();
        CartProducts cartWithFood = CartProducts.builder().products(food)
                .cart(createCart)
                .count(4)
                .build();
        cartProductsRepository.saveAll(List.of(cartWithStuff,cartWithFood));
        //when
        List<CartProductsResponseDTO> cartWithProducts = cartService.getCartWithProductsByMemberId(1L);
        //then
        assertThat(cartWithProducts).hasSize(2);
    }
}