package backend.shop.com.multiplexshop.domain.orders;

import backend.shop.com.multiplexshop.domain.products.entity.Products;
import backend.shop.com.multiplexshop.domain.orders.entity.Orders;
import lombok.*;

import java.util.List;

public class OrderProductsDTOs {

    @Getter
    @NoArgsConstructor
    @ToString
    public static class OrderProductsResponseDTO{
        private Long id;
        private Orders orders;
        private Products products;
        private Integer count;
        private Integer orderPrice;

        @Builder
        public OrderProductsResponseDTO(Long id, Orders orders, Products products, Integer count, Integer orderPrice) {
            this.id = id;
            this.orders = orders;
            this.products = products;
            this.count = count;
            this.orderPrice = orderPrice;
        }

        @Builder
        public static OrderProductsResponseDTO of(OrderProducts orderProducts){
            return OrderProductsResponseDTO.builder()
                    .id(orderProducts.getId())
                    .orders(orderProducts.getOrders())
                    .products(orderProducts.getProducts())
                    .count(orderProducts.getCount())
                    .orderPrice(orderProducts.getOrderPrice())
                    .build();
        }
    }
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class OrderProductsRequestDTO {
        private Long productId;
        private Integer count;

        @Builder
        public OrderProductsRequestDTO(Long productId, Integer count) {
            this.productId = productId;
            this.count = count;
        }
    }

    }

