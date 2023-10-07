package backend.shop.com.multiplexshop.domain.products.repository;

import backend.shop.com.multiplexshop.domain.products.dto.ProductsDTOs;
import backend.shop.com.multiplexshop.domain.products.entity.Categories;
import backend.shop.com.multiplexshop.domain.products.entity.Products;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static backend.shop.com.multiplexshop.domain.products.entity.Categories.STUFF;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
class ProductsRepositoryTest {

    @Autowired
    private ProductsRepository productsRepository;


    @Test
    @DisplayName("상품의 카테고리별 상품 목록을 조회할 수 있어야한다.")
    void findAllByCategories() throws Exception {
        //given

        Products request = Products.builder()
                .productName("향수")
                .productPrice(20000)
                .categories(STUFF)
                .stockQuantity(1)
                .build();
        productsRepository.save(request);
        // when
        List<Products> productsByCategories = productsRepository.findAllByCategories(STUFF);
        //then
        assertThat(productsByCategories).hasSize(1)
                .extracting("productName")
                .contains("향수");

    }
}