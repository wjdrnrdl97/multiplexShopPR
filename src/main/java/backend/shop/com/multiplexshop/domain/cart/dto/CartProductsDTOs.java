package backend.shop.com.multiplexshop.domain.cart.dto;

import backend.shop.com.multiplexshop.domain.Products.entity.Products;
import backend.shop.com.multiplexshop.domain.cart.entity.Cart;
import backend.shop.com.multiplexshop.domain.cart.entity.CartProducts;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class CartProductsDTOs {

    @Getter
    @NoArgsConstructor
    public static class CartProductsResponseDTO{
        private Products products;
        private Cart cart;
        private Integer count;

        @Builder
        public CartProductsResponseDTO(Products products, Cart cart, Integer count) {
            this.products = products;
            this.cart = cart;
            this.count = count;
        }
        public static CartProductsResponseDTO of(CartProducts cartProducts){
            return CartProductsResponseDTO.builder()
                    .products(cartProducts.getProducts())
                    .cart(cartProducts.getCart())
                    .count(cartProducts.getCount())
                    .build();
        }
    }
}
