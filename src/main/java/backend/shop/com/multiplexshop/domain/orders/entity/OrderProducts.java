package backend.shop.com.multiplexshop.domain.orders.entity;

import backend.shop.com.multiplexshop.domain.products.entity.Products;
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

    @Column
    private Integer count;

    @Column
    private Integer orderPrice;

    @Builder
    public OrderProducts(Orders orders, Products products, Integer count, Integer orderPrice) {
        this.orders = orders;
        this.products = products;
        this.count = count;
        this.orderPrice = orderPrice;
    }

    public static OrderProducts createOrderProducts(Orders orders, Products products,Integer count){
        return OrderProducts.builder()
                .orders(orders)
                .products(products)
                .count(count)
                .orderPrice(products.getProductPrice() * count)
                .build();
    }
}
