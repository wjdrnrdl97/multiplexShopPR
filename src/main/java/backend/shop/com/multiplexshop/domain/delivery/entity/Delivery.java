package backend.shop.com.multiplexshop.domain.delivery.entity;


import backend.shop.com.multiplexshop.domain.orders.entity.Orders;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery",fetch = FetchType.LAZY)
    private Orders order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    public Delivery(Long id, Orders order, Address address, DeliveryStatus deliveryStatus) {
        this.id = id;
        this.order = order;
        this.address = address;
        this.deliveryStatus = deliveryStatus;
    }
}
