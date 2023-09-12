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
     *  주문상품과 주문과의 양방향 관계 메서드
     * @param products
     */
    public void addOrdersProducts(OrderProducts products){
        orderProducts.add(products);
        products.changeOrders(this);
    }

    /**
     *  배송과 주문과의 양방향 관계 메서드
     * @param delivery
     */
    public void addDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.changeOrder(this);
    }

    /**
     *  주문을 생성하는 메소드
     * @param member
     * @param delivery
     * @param products
     * @return new Orders
     */
    public Orders createOrders(Member member,Delivery delivery, OrderProducts products){
            Orders newOrder = new Orders();
            newOrder.member = member;
            newOrder.delivery = delivery;
            for (OrderProducts product: newOrder.orderProducts){
               newOrder.addOrdersProducts(products);
            }
            newOrder.orderStatus = OrderStatus.ORDER;
            return newOrder;
    }
}
