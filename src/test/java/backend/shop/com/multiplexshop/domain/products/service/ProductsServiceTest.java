package backend.shop.com.multiplexshop.domain.products.service;

import backend.shop.com.multiplexshop.domain.IntegrationTestSupport;
import backend.shop.com.multiplexshop.domain.products.dto.ProductsDTOs.ProductsResponseDTO;
import backend.shop.com.multiplexshop.domain.products.entity.Categories;
import backend.shop.com.multiplexshop.domain.products.entity.Products;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;

import static org.assertj.core.api.Assertions.*;


class ProductsServiceTest extends IntegrationTestSupport {
    @Test
    @DisplayName("키워드를 포함한 모든 카테고리의 상품목록을 조회하고 이를 Pagination 해야한다.(1페이지당 6개요소 처리 / 마지막 페이지 조회는 4개의 요소 처리에 성공해야한다.)")
    public void findAllByProductNameContaining(){
        //given
        String keyword = "테스트";
        for (int i = 0; i < 5; i++) {
            String testPerfumeName = keyword + i;
            String testFoodName = keyword + i;

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
        //when
        Page<ProductsResponseDTO> result = productService.findAllByProductNameContaining(keyword, 2);
        //then
        assertThat(result.getTotalPages()).isEqualTo(2);
        assertThat(result.getTotalElements()).isEqualTo(10);
        assertThat(result.getNumberOfElements()).isEqualTo(4);
    }
    @Test
    @DisplayName("키워드를 포함한 상품목록을 카테고리별로 조회하고 이를 Pagination 해야한다.(1페이지당 6개요소 처리)")
    public void findAllByCategoriesAndProductNameContaining(){
        //given
        String keyword = "테스트";
        for (int i = 0; i<=5; i++){
            String perfumeName = keyword + i;
            String foodName = keyword + i;
            Products stuff = Products.builder()
                    .productName(perfumeName)
                    .productPrice(10000)
                    .stockQuantity(100)
                    .categories(Categories.STUFF)
                    .build();
            productsRepository.save(stuff);
            Products food = Products.builder()
                    .productName(foodName)
                    .productPrice(10000)
                    .stockQuantity(100)
                    .categories(Categories.FOOD)
                    .build();
            productsRepository.save(food);
        }
        //when
        Page<ProductsResponseDTO> result = productService.findAllByCategoriesAndProductNameContaining(Categories.FOOD, keyword, 0);
        //then
        assertThat(result.getTotalElements()).isEqualTo(6);
        assertThat(result.getTotalPages()).isEqualTo(1);
    }
    @Test
    @DisplayName("모든 상품목록을 조회하여 이를 페이징 처리한다.")
    public void findAllOfPagination(){
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
        //when
        Page<ProductsResponseDTO> result = productService.findAllOfPagination(1);
        //then
        assertThat(result.getTotalPages()).isEqualTo(1);
        assertThat(result.getNumberOfElements()).isEqualTo(10);
    }
}