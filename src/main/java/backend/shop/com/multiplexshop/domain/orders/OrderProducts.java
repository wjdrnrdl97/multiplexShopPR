package backend.shop.com.multiplexshop.domain.orders;

import backend.shop.com.multiplexshop.domain.Products.entity.Products;
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
    @JoinColumn(name = "order_id")
    @Column(nullable = false)
    private Long orderId;

    @ManyToOne
    @Column(nullable = false)
    @JoinColumn(name = "product_id")
    private Long productId;

    @ManyToOne
    @Column(nullable = false)
    @JoinColumn(name = "member_id")
    private Long memberId;

    @Column(nullable = false)
    private Integer orderPrice;

    @Column(nullable = false)
    private Integer order_count;

    @Builder
    public OrderProducts(Long orderId, Long productId, Long memberId, Integer orderPrice, Integer order_count) {
        this.orderId = orderId;
        this.productId = productId;
        this.memberId = memberId;
        this.orderPrice = orderPrice;
        this.order_count = order_count;
    }


}
