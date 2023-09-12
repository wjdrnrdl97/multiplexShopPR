package backend.shop.com.multiplexshop.domain.orders;

import backend.shop.com.multiplexshop.domain.Products.entity.Products;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import backend.shop.com.multiplexshop.domain.orders.entity.Orders;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProducts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Orders orders;


    @ManyToOne
    @JoinColumn(name = "products_id", nullable = false)
    private Products products;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private Integer orderPrice;

    @Column(nullable = false)
    private Integer orderCount;

    @Builder
    public OrderProducts(Orders orders, Products products, Member member, Integer orderPrice, Integer orderCount) {
        this.orders = orders;
        this.products = products;
        this.member = member;
        this.orderPrice = orderPrice;
        this.orderCount = orderCount;
    }
    public void changeOrders(Orders order){
        if (this.orders!=null){
            this.orders.getOrderProducts().remove(this);
        }
        this.orders = order;
    }
}
