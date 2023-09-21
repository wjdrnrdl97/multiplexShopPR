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
        public CartProductsResponseDTO(CartProducts cartProducts){
            this.cart = cartProducts.getCart();
            this.products = cartProducts.getProducts();
            this.count = cartProducts.getCount();
        }
    }
}
