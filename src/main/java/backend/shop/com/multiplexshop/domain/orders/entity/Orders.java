package backend.shop.com.multiplexshop.domain.orders.entity;

import backend.shop.com.multiplexshop.domain.common.BaseEntity;
import backend.shop.com.multiplexshop.domain.delivery.entity.Delivery;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.orders.OrderProducts;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orders extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    Member member;

    @OneToMany(mappedBy = "orders",cascade = CascadeType.ALL)
    private List<OrderProducts> orderProducts = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;


    @Enumerated(EnumType.STRING)
    @Column(length = 10,columnDefinition = "varchar(10) default 'ORDER'")
    private OrderStatus orderStatus = OrderStatus.ORDER;


    /**
     * 새로운 주문을 생성하는 로직
     * @param member (주문하는 회원)
     * @param orderProducts (주문상품)
     * @return
     */
    @Builder
    public static Orders createOrders(Member member, List<OrderProducts> orderProducts){
    return Orders.builder()
            .member(member)
            .orderProducts(orderProducts)
            .build();
    }
}
