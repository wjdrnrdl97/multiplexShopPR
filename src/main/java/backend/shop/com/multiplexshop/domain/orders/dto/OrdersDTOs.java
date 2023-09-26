package backend.shop.com.multiplexshop.domain.orders.dto;

import backend.shop.com.multiplexshop.domain.Products.entity.Products;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.orders.OrderProducts;
import backend.shop.com.multiplexshop.domain.orders.entity.OrderStatus;
import backend.shop.com.multiplexshop.domain.orders.entity.Orders;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrdersDTOs {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class OrderRequestDTO {
        private Long memberId;
        private List<Long> productId;

        @Builder
        public OrderRequestDTO(Long memberId, List<Long> productId) {
            this.memberId = memberId;
            this.productId = productId;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class OrderResponseDTO{
        private Long id;
        private Member member;
        private OrderStatus orderStatus;
        private Integer orderPrice;

        @Builder
        public OrderResponseDTO(Long id, Member member, OrderStatus orderStatus,
                                Integer orderPrice) {
            this.id = id;
            this.member = member;
            this.orderStatus = orderStatus;
            this.orderPrice = orderPrice;
        }

        public static OrderResponseDTO of(Orders orders){
            return OrderResponseDTO.builder()
                    .id(orders.getId())
                    .member(orders.getMember())
                    .orderPrice(orders.getOrderPrice())
                    .orderStatus(orders.getOrderStatus())
                    .build();
        }
    }


}
