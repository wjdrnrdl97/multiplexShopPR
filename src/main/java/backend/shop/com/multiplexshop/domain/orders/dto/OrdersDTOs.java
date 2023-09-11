package backend.shop.com.multiplexshop.domain.orders.dto;

import backend.shop.com.multiplexshop.domain.orders.OrderProducts;
import backend.shop.com.multiplexshop.domain.orders.entity.Orders;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrdersDTOs {
    /**
     * 주문의 기록 정보를 응답하는 DTO
     */
    @Getter
    @NoArgsConstructor
    public static class OrderResponseDTO{
        private Long id;
        private String orderStatus;
        private LocalDateTime regDate;
        private LocalDateTime modDate;
        private List<OrderProducts> orderProducts = new ArrayList<>();
        private String DeliveryStatus;

        @Builder
        public OrderResponseDTO(Orders orders) {
            this.id = orders.getId();
            this.orderStatus = orders.getOrderStatus().label();
            this.regDate = orders.getRegDate();
            this.modDate = orders.getModDate();
            this.orderProducts = orders.getOrderProducts().stream().toList();
            this.DeliveryStatus = orders.getDelivery().getDeliveryStatus().label();
        }
    }


}
