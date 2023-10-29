package backend.shop.com.multiplexshop.domain.products.repository;

import backend.shop.com.multiplexshop.domain.products.entity.Categories;
import backend.shop.com.multiplexshop.domain.products.entity.Products;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductsRepository extends JpaRepository<Products, Long> {

    Page<Products> findAllByCategories(Categories categories, PageRequest pageable);

    Page<Products> findAllByProductNameContaining(String productName, Pageable pageable);

    Page<Products> findAllByCategoriesAndProductNameContaining
                                    (Categories categories, String productName, Pageable pageable);
    @Override
    Page<Products> findAll(Pageable pageable);

    List<Products> findAllByOrderByIdDesc();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Products p where p.id = :id")
    Optional<Products> findAndPessimisticLockById(Long id);

}
