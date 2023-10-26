package backend.shop.com.multiplexshop.domain.cart.service;


import backend.shop.com.multiplexshop.domain.IntegrationTestSupport;
import backend.shop.com.multiplexshop.domain.products.entity.Products;
import backend.shop.com.multiplexshop.domain.cart.entity.Cart;
import backend.shop.com.multiplexshop.domain.cart.entity.CartProducts;
import backend.shop.com.multiplexshop.domain.cart.repository.CartProductsRepository;
import backend.shop.com.multiplexshop.domain.cart.repository.CartRepository;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import backend.shop.com.multiplexshop.domain.products.entity.Categories;
import backend.shop.com.multiplexshop.domain.products.repository.ProductsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static backend.shop.com.multiplexshop.domain.cart.dto.CartDTOs.*;
import static backend.shop.com.multiplexshop.domain.cart.dto.CartProductsDTOs.*;
import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class CartServiceTest extends IntegrationTestSupport {


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
        assertThat(response.getMember().getMemberId()).isEqualTo(1L);
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
                .build();
        Products stuff = productsRepository.save(products1);
        Products products2 = Products.builder()
                .productName("밀키트")
                .productPrice(5000)
                .stockQuantity(100)
                .categories(Categories.FOOD)
                .build();
        Products food = productsRepository.save(products2);

        CartProducts cartWithStuff = CartProducts.builder()
                .products(stuff)
                .cart(createCart)
                .count(3)
                .build();
        CartProducts cartWithFood = CartProducts.builder()
                .products(food)
                .cart(createCart)
                .count(4)
                .build();
        cartProductsRepository.saveAll(List.of(cartWithStuff,cartWithFood));
        //when
        List<CartProductsResponseDTO> cartWithProducts = cartService.getCartWithProductsByMemberId(1L);
        //then
        assertThat(cartWithProducts).hasSize(2);
    }
    @Test
    @DisplayName("장바구니상품의 id를 입력 받아 해당 장바구니상품을 삭제한다.")
    public void deleteCartProductsById(){
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
                .build();
        Products stuff = productsRepository.save(products1);
        CartProducts cartWithStuff = CartProducts.builder()
                .products(stuff)
                .cart(createCart)
                .count(3)
                .build();
        cartProductsRepository.save(cartWithStuff);
        //when
        cartService.deleteCartProductsById(1L);
        //then
        List<CartProducts> result = cartProductsRepository.findAll();
        assertThat(result).isEmpty();
    }
    @Test
    @DisplayName("주문상품번호 리스트를 받아 해당 주문상품을 삭제한다.")
    public void deleteCartProductsByIds(){
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
                .build();
        Products stuff = productsRepository.save(products1);
        Products products2 = Products.builder()
                .productName("음식")
                .productPrice(10000)
                .stockQuantity(100)
                .categories(Categories.FOOD)
                .build();
        Products food = productsRepository.save(products2);
        CartProducts cartWithStuff = CartProducts.builder()
                .products(stuff)
                .cart(createCart)
                .count(3)
                .build();
        CartProducts cartWithFood = CartProducts.builder()
                .products(food)
                .cart(createCart)
                .count(3)
                .build();
        cartProductsRepository.saveAll(List.of(cartWithStuff,cartWithFood));
        List<Long> request = Arrays.asList(1L, 2L);
        //when
        cartService.deleteCartProductsByIds(request);
        //then
        List<CartProducts> result = cartProductsRepository.findAll();
        assertThat(result).isEmpty();
    }
    @Test
    @DisplayName("장바구니상품 id 리스트를 받아 리스트에 해당하는 장바구니상품을 조회한다.(지연로딩된 장바구니와 상품을 fetch join을 사용하여)")
    public void findOrderProductsOfListByCartProductsId(){
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
                .build();
        Products stuff = productsRepository.save(products1);
        Products products2 = Products.builder()
                .productName("밀키트")
                .productPrice(5000)
                .stockQuantity(100)
                .categories(Categories.FOOD)
                .build();
        Products food = productsRepository.save(products2);

        CartProducts cartWithStuff1 = CartProducts.builder()
                .products(stuff)
                .cart(createCart)
                .count(3)
                .build();
        CartProducts cartWithStuff2 = CartProducts.builder()
                .products(stuff)
                .cart(createCart)
                .count(6)
                .build();
        CartProducts cartWithFood1 = CartProducts.builder()
                .products(food)
                .cart(createCart)
                .count(4)
                .build();
        CartProducts cartWithFood2 = CartProducts.builder()
                .products(food)
                .cart(createCart)
                .count(8)
                .build();
        cartProductsRepository.saveAll(List.of(cartWithStuff1,cartWithStuff2,cartWithFood1,cartWithFood2));
        List<Long> ids = List.of(1L, 4L);
        //when
        List<CartProductsResponseDTO> result = cartService.findOrderProductsOfListByCartProductsId(ids);
        //then
        assertThat(result).hasSize(2);
    }
}