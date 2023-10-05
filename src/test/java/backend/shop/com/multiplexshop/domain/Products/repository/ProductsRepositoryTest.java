package backend.shop.com.multiplexshop.domain.Products.repository;

import backend.shop.com.multiplexshop.domain.Products.entity.Categories;
import backend.shop.com.multiplexshop.domain.Products.entity.Products;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductsRepositoryTest {

    @Autowired
    ProductsRepository productsRepository;

    @Test
    @DisplayName("")
    @Transactional
    public void test2(){
        //given
        Products createProduct = Products.builder()
                .productName("향수")
                .productPrice(10000)
                .stockQuantity(50)
                .categories(Categories.STUFF)
                .build();
        productsRepository.save(createProduct);
        //when
        Products test1 = productsRepository.findAndLockById(1L).orElse(null);

        //then
    }
}