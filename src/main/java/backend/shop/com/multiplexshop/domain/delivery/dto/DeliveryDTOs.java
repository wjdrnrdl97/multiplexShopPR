package backend.shop.com.multiplexshop.domain.delivery.dto;

import backend.shop.com.multiplexshop.domain.delivery.entity.Address;
import backend.shop.com.multiplexshop.domain.delivery.entity.Delivery;
import backend.shop.com.multiplexshop.domain.delivery.entity.DeliveryStatus;
import backend.shop.com.multiplexshop.domain.orders.entity.Orders;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class DeliveryDTOs {
    @Getter
    @NoArgsConstructor
    public static class DeliveryResponseDTO{

        private Orders order;
        private Address address;
        private DeliveryStatus deliveryStatus;

        @Builder
        public DeliveryResponseDTO(Orders order, Address address, DeliveryStatus deliveryStatus) {
            this.order = order;
            this.address = address;
            this.deliveryStatus = deliveryStatus;
        }
        public static DeliveryResponseDTO of(Delivery delivery){
            return DeliveryResponseDTO.builder()
                    .order(delivery.getOrder())
                    .address(delivery.getAddress())
                    .deliveryStatus(delivery.getDeliveryStatus())
                    .build();
        }
    }
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class DeliveryRequestDTO{
        private Long orderID;
        private String shippingAddress;
        private Integer zipcode;

        @Builder
        public DeliveryRequestDTO(Long orderID, String shippingAddress, Integer zipcode) {
            this.orderID = orderID;
            this.shippingAddress = shippingAddress;
            this.zipcode = zipcode;
        }
    }


}
