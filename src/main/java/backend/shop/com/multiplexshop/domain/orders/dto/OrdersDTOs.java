package backend.shop.com.multiplexshop.domain.orders.dto;

import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.orders.entity.OrderStatus;
import backend.shop.com.multiplexshop.domain.orders.entity.Orders;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static backend.shop.com.multiplexshop.domain.orders.dto.OrderProductsDTOs.*;

public class OrdersDTOs {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class OrderRequestDTO {
        private Long memberId;
        private List<OrderProductsRequestDTO> productWithCount;

        @Builder
        public OrderRequestDTO(Long memberId, List<OrderProductsRequestDTO> productWithCount) {
            this.memberId = memberId;
            this.productWithCount = productWithCount;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class OrderResponseDTO{
        private Long id;
        private Member member;
        private OrderStatus orderStatus;

        @Builder
        public OrderResponseDTO(Long id, Member member, OrderStatus orderStatus) {
            this.id = id;
            this.member = member;
            this.orderStatus = orderStatus;
        }

        public static OrderResponseDTO of(Orders orders){
            return OrderResponseDTO.builder()
                    .id(orders.getId())
                    .member(orders.getMember())
                    .orderStatus(orders.getOrderStatus())
                    .build();
        }
    }


}
