package backend.shop.com.multiplexshop.domain.cart.repository;

import backend.shop.com.multiplexshop.domain.Products.entity.Products;
import backend.shop.com.multiplexshop.domain.cart.entity.Cart;
import backend.shop.com.multiplexshop.domain.cart.entity.CartProducts;
import backend.shop.com.multiplexshop.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartProductsRepository extends JpaRepository<CartProducts,Long> {


    @Query("SELECT cp FROM CartProducts cp JOIN FETCH cp.products JOIN FETCH cp.cart WHERE cp.cart = :cart")
    List<CartProducts> findAllByCart(@Param("cart") Cart cart);

    @Query("SELECT cp FROM CartProducts cp JOIN FETCH cp.cart JOIN FETCH cp.products WHERE cp.id = :id")
    Optional<CartProducts> findWithCartAndProductById(@Param("id") Long id);

    void deleteAllByCart(Cart cart);

    void deleteByCartMemberAndProducts(Member member, Products products);


}
