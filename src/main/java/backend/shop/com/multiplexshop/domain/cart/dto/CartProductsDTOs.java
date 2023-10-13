package backend.shop.com.multiplexshop.domain.cart.dto;

import backend.shop.com.multiplexshop.domain.products.entity.Products;
import backend.shop.com.multiplexshop.domain.cart.entity.Cart;
import backend.shop.com.multiplexshop.domain.cart.entity.CartProducts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CartProductsDTOs {

    @Getter
    @NoArgsConstructor
    public static class CartProductsResponseDTO{
        private Long id;
        private Products products;
        private Cart cart;
        private Integer count;

        @Builder
        public CartProductsResponseDTO(Long id, Products products, Cart cart, Integer count) {
            this.id = id;
            this.products = products;
            this.cart = cart;
            this.count = count;
        }
        public static CartProductsResponseDTO of(CartProducts cartProducts){
            return CartProductsResponseDTO.builder()
                    .id(cartProducts.getId())
                    .products(cartProducts.getProducts())
                    .cart(cartProducts.getCart())
                    .count(cartProducts.getCount())
                    .build();
        }
    }
}
