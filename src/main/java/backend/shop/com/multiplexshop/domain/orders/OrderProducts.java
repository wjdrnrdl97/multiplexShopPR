package backend.shop.com.multiplexshop.domain.orders;

import backend.shop.com.multiplexshop.domain.Products.entity.Products;
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
    @JoinColumn(name = "order_id",nullable = false)
    private Orders orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "products_id", nullable = false)
    private Products products;

    @Builder
    public OrderProducts(Orders orders, Products products) {
        this.orders = orders;
        this.products = products;
    }
    public static OrderProducts createOrderProducts(Orders orders, Products products){
        return OrderProducts.builder()
                .orders(orders)
                .products(products)
                .build();
    }
}
