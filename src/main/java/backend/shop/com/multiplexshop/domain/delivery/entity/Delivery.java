package backend.shop.com.multiplexshop.domain.delivery.entity;
import backend.shop.com.multiplexshop.domain.delivery.dto.DeliveryDTOs;
import backend.shop.com.multiplexshop.domain.orders.entity.Orders;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static backend.shop.com.multiplexshop.domain.delivery.dto.DeliveryDTOs.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="delivery_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")

    private Orders order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(10) default 'READY'")
    private DeliveryStatus deliveryStatus = DeliveryStatus.READY;

    @Builder
    public Delivery(Orders order, Address address, DeliveryStatus deliveryStatus) {
        this.order = order;
        this.address = address;
        this.deliveryStatus = deliveryStatus;
    }

    public static Delivery createDelivery(Orders order){
        return Delivery.builder()
                .order(order)
                .address(Address.createAddress())
                .deliveryStatus(DeliveryStatus.READY)
                .build();
    }
}
