package backend.shop.com.multiplexshop.domain.orders.entity;

import backend.shop.com.multiplexshop.domain.common.BaseEntity;
import backend.shop.com.multiplexshop.domain.member.entity.Member;

import jakarta.persistence.*;
import lombok.*;


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

    @Enumerated(EnumType.STRING)
    @Column(length = 10, columnDefinition = "varchar(10) default 'ORDER'")
    private OrderStatus orderStatus = OrderStatus.ORDER;

    @Builder
    public Orders(Member member, OrderStatus orderStatus) {
        this.member = member;
        this.orderStatus = orderStatus;
    }

    /**
     *  주문의 상태를 ORDER에서 CANCEL로 변경하는 로직
     */
    public void changeOrderStatus(){
        this.orderStatus = OrderStatus.CANCEL;
    }

    /**
     * 회원과 상품의 번호리스트를 받아 주문을 생성하는 로직
     * @param member
     * @return
     */
    public static Orders createOrder(Member member){
        return Orders.builder().
                member(member)
                .orderStatus(OrderStatus.ORDER)
                .build();
    }
}

