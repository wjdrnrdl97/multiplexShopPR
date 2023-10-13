package backend.shop.com.multiplexshop.domain.cart.service;

import backend.shop.com.multiplexshop.domain.products.entity.Products;
import backend.shop.com.multiplexshop.domain.products.repository.ProductsRepository;
import backend.shop.com.multiplexshop.domain.cart.entity.Cart;
import backend.shop.com.multiplexshop.domain.cart.entity.CartProducts;
import backend.shop.com.multiplexshop.domain.cart.repository.CartProductsRepository;
import backend.shop.com.multiplexshop.domain.cart.repository.CartRepository;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static backend.shop.com.multiplexshop.domain.cart.dto.CartDTOs.*;
import static backend.shop.com.multiplexshop.domain.cart.dto.CartProductsDTOs.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {

    private final CartRepository cartRepository;
    private final CartProductsRepository cartProductsRepository;
    private final MemberRepository memberRepository;
    private final ProductsRepository productsRepository;


    public CartResponseDTO createCartWithProductsByRequest(CartRequestDTO request) {
        Member findMember = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        
        Cart getCart = cartRepository.findByMember(findMember)
                .orElseGet(() -> cartRepository.save(Cart.createCart(findMember)));

        Products findProducts = productsRepository.findById(request.getProductsId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        CartProducts createCartProducts = CartProducts.builder()
                .cart(getCart)
                .products(findProducts)
                .count(request.getCount())
                .build();

        cartProductsRepository.save(createCartProducts);
        return CartResponseDTO.of(getCart);
    }

    public List<CartProductsResponseDTO> getCartWithProductsByMemberId(Long id){
        Member findMember = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        Cart getCart = cartRepository.findByMember(findMember)
                .orElseGet(() -> cartRepository.save(Cart.createCart(findMember)));

        List<CartProductsResponseDTO> findCartProdutsOfDTO = cartProductsRepository.findAllByCart(getCart)
                                                .stream().map(CartProductsResponseDTO::of).toList();

        return findCartProdutsOfDTO;
    }
    @Transactional
    public void deleteCartProductsById(Long id){
        cartProductsRepository.deleteById(id);
    }

    @Transactional
    public void deleteCartProductsAllByMemberId(Long id){
        Member findMember = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        Cart findCart = cartRepository.findByMember(findMember)
                        .orElseThrow(() -> new IllegalArgumentException("Cart not found"));
        cartProductsRepository.deleteAllByCart(findCart);
    }
        public List<CartProductsResponseDTO> findOrderProductsOfListByCartProductsId (List<Long> ids){
        List<CartProducts> cartProducts = new ArrayList<>();
        for (Long id : ids){
            cartProducts.add(cartProductsRepository.findWithCartAndProductById(id)
                                    .orElseThrow(()->new IllegalArgumentException("CartProduct not found")));
        }
        List<CartProductsResponseDTO> dto = cartProducts.stream().map(CartProductsResponseDTO::of).toList();
        return dto;
    }
}
