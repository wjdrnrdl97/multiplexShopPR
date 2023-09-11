package backend.shop.com.multiplexshop.domain.orders;

import backend.shop.com.multiplexshop.domain.Products.entity.Products;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class OrderProductsDTOs {
    /**
     * 주문하고자 하는 상품이름과 주문 총금액, 주문수량을 응답하는 DTO (주문상품의 상세조회)
     */
    @Getter
    @NoArgsConstructor
    public static class OrderProductsResponseDTO {
        private String productName;
        private Integer orderPrice;
        private Integer orderCount;

        @Builder
        public OrderProductsResponseDTO(OrderProducts orderProducts) {
            this.productName = orderProducts.getProducts().getProductName();
            this.orderPrice = orderProducts.getOrderPrice();
            this.orderCount = orderProducts.getOrderCount();
        }
    }

    /**
     * 주문하고자 하는 상품번호와 주문 수량, 회원번호를 요청하는 DTO
     */
        @Getter
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        public static class OrderProductsRequestDTO {

            private Long productId;
            private Integer orderCount;
            private Long memberId;

            @Builder
            public OrderProductsRequestDTO(Long productId, Integer orderCount, Long memberId) {
                this.productId = productId;
                this.orderCount = orderCount;
                this.memberId = memberId;
            }
        }
    }

