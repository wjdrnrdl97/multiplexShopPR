package backend.shop.com.multiplexshop.domain.cart.entity;

import backend.shop.com.multiplexshop.domain.Products.entity.Products;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartProducts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id",nullable = false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "products_id",nullable = false)
    private Products products;

    @Column
    private Integer count;

    @Builder
    public CartProducts(Cart cart, Products products,Integer count) {
        this.cart = cart;
        this.products = products;
        this.count = count;
    }
}
