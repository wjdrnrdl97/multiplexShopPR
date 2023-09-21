package backend.shop.com.multiplexshop.domain.cart.service;

import backend.shop.com.multiplexshop.domain.Products.entity.Categories;
import backend.shop.com.multiplexshop.domain.Products.entity.Products;
import backend.shop.com.multiplexshop.domain.Products.repository.ProductsRepository;
import backend.shop.com.multiplexshop.domain.cart.dto.CartDTOs;
import backend.shop.com.multiplexshop.domain.cart.dto.CartProductsDTOs;
import backend.shop.com.multiplexshop.domain.cart.entity.CartProducts;
import backend.shop.com.multiplexshop.domain.cart.repository.CartProductsRepository;
import backend.shop.com.multiplexshop.domain.cart.repository.CartRepository;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static backend.shop.com.multiplexshop.domain.cart.dto.CartDTOs.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


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
        assertThat(response.getId()).isEqualTo(1L);

    }
}