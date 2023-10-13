package backend.shop.com.multiplexshop.domain.products.repository;


import backend.shop.com.multiplexshop.domain.products.entity.Products;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import static backend.shop.com.multiplexshop.domain.products.entity.Categories.STUFF;



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
//        Page<Products> allByCategories = productsRepository.findAllByCategories(STUFF,new PageRequest(1,6, Sort.by("id").descending()));
        //then
//        assertThat(allByCategories).hasSize(1)
//                .extracting("productName")
//                .contains("향수");

    }
}