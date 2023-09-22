package backend.shop.com.multiplexshop.domain.cart.controller;

import backend.shop.com.multiplexshop.domain.Products.entity.Categories;
import backend.shop.com.multiplexshop.domain.Products.entity.Products;
import backend.shop.com.multiplexshop.domain.Products.repository.ProductsRepository;
import backend.shop.com.multiplexshop.domain.cart.entity.Cart;
import backend.shop.com.multiplexshop.domain.cart.entity.CartProducts;
import backend.shop.com.multiplexshop.domain.cart.repository.CartProductsRepository;
import backend.shop.com.multiplexshop.domain.cart.repository.CartRepository;
import backend.shop.com.multiplexshop.domain.cart.service.CartService;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

import java.util.List;

import static backend.shop.com.multiplexshop.domain.cart.dto.CartDTOs.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class CartAPIControllerTest {

    @Autowired
    CartService cartService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ProductsRepository productsRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartProductsRepository cartProductsRepository;
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
    @DisplayName("요청(회원PK,상품PK,수량)을 입력받아 장바구니-상품을 생성하고 Http 201 코드와 함께 응답에 성공한다.")
    public void postCartByRequest() throws Exception{
        //given
        String uri = "/api/cart";
        Member member = Member.builder()
                .memberEmailId("test")
                .password("1234")
                .memberName("테스트")
                .build();
        memberRepository.save(member);
        Products stuff = Products.builder()
                .productName("향수")
                .productPrice(10000)
                .stockQuantity(100)
                .categories(Categories.STUFF)
                .orderQuantity(3)
                .build();
        productsRepository.save(stuff);
        CartRequestDTO request = CartRequestDTO.builder()
                .memberId(1L)
                .productsId(1L)
                .count(3)
                .build();
        //when
        String requestBody = objectMapper.writeValueAsString(request);
        ResultActions perform = mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON).content(requestBody))
        //then
                .andDo(print())
                .andExpect(status().isCreated());
    }
    @Test
    @DisplayName("장바구니를 접속하였을 때 회원별 장바구니-상품을 조회한후 Http 200코드와 함께 응답에 성공한다.")
    public void getCartWithProductsByMember() throws Exception{
        //given
        String uri = "/api/cart/{memberId}";
        Member member1 = Member.builder()
                .memberEmailId("사용자1")
                .password("1234")
                .memberName("테스트")
                .build();
        Member savedMember1 = memberRepository.save(member1);
        Cart member1Cart = Cart.createCart(savedMember1);
        Cart savedMember1Cart = cartRepository.save(member1Cart);

        Member member2 = Member.builder()
                .memberEmailId("사용자2")
                .password("1234")
                .memberName("테스트")
                .build();
        Member savedMember2 = memberRepository.save(member2);
        Cart member2Cart = Cart.createCart(savedMember2);
        Cart savedMember2Cart = cartRepository.save(member2Cart);

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

        CartProducts member1CartWithStuff = CartProducts.builder()
                .cart(savedMember1Cart)
                .products(stuff)
                .count(3)
                .build();
        CartProducts member1CartWithFood = CartProducts.builder()
                .cart(savedMember1Cart)
                .products(food)
                .count(4)
                .build();

        CartProducts member2CartWithStuff = CartProducts.builder()
                .cart(savedMember2Cart)
                .products(stuff)
                .count(3)
                .build();
        CartProducts member2CartWithFood = CartProducts.builder()
                .cart(savedMember2Cart)
                .products(food)
                .count(4)
                .build();
        cartProductsRepository.saveAll(List.of(member1CartWithStuff,member1CartWithFood,member2CartWithStuff,member2CartWithFood));
        //when
        ResultActions perform = mockMvc.perform(get(uri, 1));
        //then
        perform.andExpect(status().isOk());
        perform.andDo(print());
    }
    @Test
    @DisplayName("장바구니-상품 번호를 입력받아 해당 장바구니-상품을 단건 삭제하고 Http 204코드를 응답에 성공한다.")
    public void deleteCartProductsById() throws Exception{
        //given
        String uri = "/api/cart/{id}";

        Member member1 = Member.builder()
                .memberEmailId("사용자1")
                .password("1234")
                .memberName("테스트")
                .build();
        Member savedMember1 = memberRepository.save(member1);
        Cart member1Cart = Cart.createCart(savedMember1);
        Cart savedMember1Cart = cartRepository.save(member1Cart);

        Products products1 = Products.builder()
                .productName("향수")
                .productPrice(10000)
                .stockQuantity(100)
                .categories(Categories.STUFF)
                .orderQuantity(3)
                .build();
        Products stuff = productsRepository.save(products1);

        CartProducts member1CartWithStuff = CartProducts.builder()
                .products(stuff)
                .cart(savedMember1Cart)
                .count(3)
                .build();
        cartProductsRepository.save(member1CartWithStuff);
        //when
        ResultActions perform = mockMvc.perform(delete(uri, 1))
        //then
        .andExpect(status().isNoContent());
    }
    @Test
    @DisplayName("회원의 아이디를 조회한후 조회한 회원의 장바구니에 담겨진 장바구니-상품을 전체 삭제하고 Http 204코드 응답에 성공한다.")
    public void deleteCartProductsAllByMember() throws Exception{
        //given
        String uri = "/api/cart/all/{memberId}";
        Member member1 = Member.builder()
                .memberEmailId("사용자1")
                .password("1234")
                .memberName("테스트")
                .build();
        Member savedMember1 = memberRepository.save(member1);
        Cart member1Cart = Cart.createCart(savedMember1);
        Cart savedMember1Cart = cartRepository.save(member1Cart);

        Member member2 = Member.builder()
                .memberEmailId("사용자2")
                .password("1234")
                .memberName("테스트")
                .build();
        Member savedMember2 = memberRepository.save(member2);
        Cart member2Cart = Cart.createCart(savedMember2);
        Cart savedMember2Cart = cartRepository.save(member2Cart);

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

        CartProducts member1CartWithStuff = CartProducts.builder()
                .cart(savedMember1Cart)
                .products(stuff)
                .count(3)
                .build();
        CartProducts member1CartWithFood = CartProducts.builder()
                .cart(savedMember1Cart)
                .products(food)
                .count(4)
                .build();

        CartProducts member2CartWithStuff = CartProducts.builder()
                .cart(savedMember2Cart)
                .products(stuff)
                .count(3)
                .build();
        CartProducts member2CartWithFood = CartProducts.builder()
                .cart(savedMember2Cart)
                .products(food)
                .count(4)
                .build();
        cartProductsRepository.saveAll(List.of(member1CartWithStuff,member1CartWithFood,member2CartWithStuff,member2CartWithFood));
        //when
        mockMvc.perform(delete(uri,1))
        //then
        .andExpect(status().isNoContent());
    }
}