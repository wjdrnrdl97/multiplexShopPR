package backend.shop.com.multiplexshop.domain.products.repository;


import backend.shop.com.multiplexshop.domain.products.entity.Categories;
import backend.shop.com.multiplexshop.domain.products.entity.Products;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.objenesis.ObjenesisStd;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static backend.shop.com.multiplexshop.domain.products.entity.Categories.STUFF;
import static org.assertj.core.api.Assertions.*;


@DataJpaTest
class ProductsRepositoryTest {

    @Autowired
    private ProductsRepository productsRepository;

    @Test
    @DisplayName("검색어가 포함된 모든 카테고리의 상품목록을 조회한다.")
    public void findAllByProductNameContaining(){
        //given
        for (int i = 0; i < 3; i++) {
            String testPerfumeName = "테스트" + i;
            String testFoodName = "테스트" + i;

            String perfumeName = "향수" + i;
            String foodName = "음식" + i;

            Products testStuff = Products.builder()
                    .productName(testPerfumeName)
                    .productPrice(10000)
                    .stockQuantity(100)
                    .categories(Categories.STUFF)
                    .build();
            productsRepository.save(testStuff);
            Products stuff = Products.builder()
                    .productName(perfumeName)
                    .productPrice(10000)
                    .stockQuantity(100)
                    .categories(Categories.STUFF)
                    .build();
            productsRepository.save(stuff);

            Products testFood = Products.builder()
                    .productName(testFoodName)
                    .productPrice(10000)
                    .stockQuantity(100)
                    .categories(Categories.FOOD)
                    .build();
            productsRepository.save(testFood);
            Products food = Products.builder()
                    .productName(foodName)
                    .productPrice(10000)
                    .stockQuantity(100)
                    .categories(Categories.FOOD)
                    .build();
            productsRepository.save(food);
        }
        PageRequest search = PageRequest.of(0, 6, Sort.by(Sort.Direction.DESC, "modDate"));
        //when
        Page<Products> result = productsRepository.findAllByProductNameContaining("테스트", search);
        //then
        assertThat(result.getNumberOfElements()).isEqualTo(6);
    }
    @Test
    @DisplayName("검색어가 포함된 상품 목록을 카테고리별로 조회한다.")
    void findAllByCategoriesAndProductNameContaining() throws Exception {
        //given
        for (int i = 0; i < 6; i++) {
            Products stuff = Products.builder()
                    .productName("향수" + i)
                    .productPrice(10000)
                    .stockQuantity(100)
                    .categories(Categories.STUFF)
                    .build();
            productsRepository.save(stuff);
            Products food = Products.builder()
                    .productName("음식" + i)
                    .productPrice(10000)
                    .stockQuantity(100)
                    .categories(Categories.FOOD)
                    .build();
            productsRepository.save(food);
        }
        PageRequest search = PageRequest.of(0, 6, Sort.by(Sort.Direction.DESC, "modDate"));
        //when
        Page<Products> result = productsRepository.findAllByCategoriesAndProductNameContaining(STUFF, "향수", search);
        //then

        assertThat(result.getNumberOfElements()).isEqualTo(6);
        assertThat(result.getTotalPages()).isEqualTo(1);
    }
    @Test
    @DisplayName("모든 상품목록을 조회하여 페이징처리에 성공한다.")
    public void findAll(){
        //given
        for (int i = 0; i < 10; i++) {
            Products stuff = Products.builder()
                    .productName("향수" + i)
                    .productPrice(10000)
                    .stockQuantity(100)
                    .categories(Categories.STUFF)
                    .build();
            productsRepository.save(stuff);
        }
        PageRequest pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "id"));
        //when
        Page<Products> result = productsRepository.findAll(pageable);
        //then
        assertThat(result.getNumberOfElements()).isEqualTo(5);
        assertThat(result.getTotalPages()).isEqualTo(2);
    }
}