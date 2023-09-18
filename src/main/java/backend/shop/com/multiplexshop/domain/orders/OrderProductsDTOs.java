package backend.shop.com.multiplexshop.domain.orders;

import backend.shop.com.multiplexshop.domain.Products.entity.Products;
import backend.shop.com.multiplexshop.domain.orders.entity.Orders;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class OrderProductsDTOs {

    @Getter
    @NoArgsConstructor
    public static class OrderProductsResponseDTO{
        private Orders orders;
        private Products products;

        @Builder
        public OrderProductsResponseDTO(Orders orders, Products products) {
            this.orders = orders;
            this.products = products;
        }
        @Builder
        public static OrderProductsResponseDTO of(OrderProducts orderProducts){
            return OrderProductsResponseDTO.builder()
                    .orders(orderProducts.getOrders())
                    .products(orderProducts.getProducts())
                    .build();
        }
    }






    }

