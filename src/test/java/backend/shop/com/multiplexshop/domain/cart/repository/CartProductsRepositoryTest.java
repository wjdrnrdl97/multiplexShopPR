package backend.shop.com.multiplexshop.domain.cart.repository;

import backend.shop.com.multiplexshop.domain.products.entity.Categories;
import backend.shop.com.multiplexshop.domain.products.entity.Products;
import backend.shop.com.multiplexshop.domain.products.repository.ProductsRepository;
import backend.shop.com.multiplexshop.domain.cart.entity.Cart;
import backend.shop.com.multiplexshop.domain.cart.entity.CartProducts;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
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
    public void findAllByCart(){
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
                .build();
        Products savedStuff = productsRepository.save(products1);
        Products products2 = Products.builder()
                .productName("밀키트")
                .productPrice(5000)
                .stockQuantity(100)
                .categories(Categories.FOOD)
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
    
    @Test
    @DisplayName("장바구니를 입력받아 관련된 장바구니-상품을 전부 삭제한다.")
    public void deleteAllByCart(){
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
                .build();
        Products savedStuff = productsRepository.save(products1);
        Products products2 = Products.builder()
                .productName("밀키트")
                .productPrice(5000)
                .stockQuantity(100)
                .categories(Categories.FOOD)
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
        cartProductsRepository.deleteAllByCart(savedCart);
        //then
        List<CartProducts> result = cartProductsRepository.findAll();
        assertThat(result).isEmpty();
    }
    @Test
    @DisplayName("장바구니상품 번호를 입력받아 관련된 장바구니상품을 조회한다(지연로딩된 카트와 상품을 fetch join을 이용하여 즉시로딩으로)")
    public void findWithCartAndProductById(){
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
                .build();
        Products savedStuff = productsRepository.save(products1);

        Products products2 = Products.builder()
                .productName("밀키트")
                .productPrice(5000)
                .stockQuantity(100)
                .categories(Categories.FOOD)
                .build();
        Products savedFood = productsRepository.save(products2);
        
        CartProducts createCartByStuff = CartProducts.builder()
                .products(savedStuff)
                .cart(savedCart)
                .count(3)
                .build();
        cartProductsRepository.save(createCartByStuff);
        //when
        CartProducts result = cartProductsRepository.findWithCartAndProductById(1L).orElse(null);
        //then
        assertThat(result.getCart()).isEqualTo(savedCart);
    }
    @Test
    @DisplayName("회원과 상품을 매개변수로 하여 회원의 장바구니와 상품이 일치한 장바구니-상품을 삭제한다.")
    public void deleteByCartMemberAndProducts(){
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
                .build();
        Products savedStuff = productsRepository.save(products1);

        Products products2 = Products.builder()
                .productName("밀키트")
                .productPrice(5000)
                .stockQuantity(100)
                .categories(Categories.FOOD)
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
         cartProductsRepository.deleteByCartMemberAndProducts(savedMember,savedStuff);
        //then
        List<CartProducts> result = cartProductsRepository.findAll();
        assertThat(result).hasSize(1);
    }
}