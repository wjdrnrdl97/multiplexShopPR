package backend.shop.com.multiplexshop.domain.orders.entity;

import backend.shop.com.multiplexshop.domain.Products.entity.Products;
import backend.shop.com.multiplexshop.domain.common.BaseEntity;
import backend.shop.com.multiplexshop.domain.delivery.entity.Delivery;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.orders.OrderProducts;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.ast.Or;

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

    @Enumerated(EnumType.STRING)
    @Column(length = 10, columnDefinition = "varchar(10) default 'ORDER'")
    private OrderStatus orderStatus = OrderStatus.ORDER;

    @Column(nullable = false)
    private Integer orderPrice;

    @Builder
    public Orders(Member member, OrderStatus orderStatus, Integer orderPrice) {
        this.member = member;
        this.orderStatus = orderStatus;
        this.orderPrice = orderPrice;
    }

    /**
     *  주문의 상태를 취소로 변경하는 로직
     */
    public void changeOrderStatus(){
        this.orderStatus = OrderStatus.CANCEL;
    }
    /**
     * 회원과 상품의 번호리스트를 받아 주문을 생성하는 로직
     * @param member
     * @param productsList
     * @return
     */
    public static Orders createOrder(Member member,List<Products> productsList){
        return Orders.builder().
                member(member)
                .orderPrice(setOrderPrice(productsList))
                .orderStatus(OrderStatus.ORDER)
                .build();
    }

    /**
     * 상품의 가격과 주문개수를 받아 총 주문의 총액을 계산하는 로직
     * @param productsList
     * @return
     */
    private static Integer setOrderPrice(List<Products> productsList) {
        Integer sum = 0;
        for (Products product : productsList){
            sum += product.getProductPrice() * product.getOrderQuantity();
        }
        return sum;
    }
}

