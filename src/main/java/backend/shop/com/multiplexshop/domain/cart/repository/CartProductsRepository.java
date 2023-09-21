package backend.shop.com.multiplexshop.domain.cart.repository;

import backend.shop.com.multiplexshop.domain.Products.entity.Products;
import backend.shop.com.multiplexshop.domain.cart.entity.Cart;
import backend.shop.com.multiplexshop.domain.cart.entity.CartProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartProductsRepository extends JpaRepository<CartProducts,Long> {

    List<CartProducts> findAllByCart(Cart cart);
}
