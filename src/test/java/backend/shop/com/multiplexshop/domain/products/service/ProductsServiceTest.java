package backend.shop.com.multiplexshop.domain.products.service;

import backend.shop.com.multiplexshop.domain.IntegrationTestSupport;
import backend.shop.com.multiplexshop.domain.products.entity.Products;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static backend.shop.com.multiplexshop.domain.products.dto.ProductsDTOs.*;
import static backend.shop.com.multiplexshop.domain.products.entity.Categories.*;
import static org.assertj.core.api.Assertions.*;

class ProductsServiceTest extends IntegrationTestSupport {



    @Test
    @DisplayName("상품 등록 요청에 의한 상품 등록이 완료되어야 한다.")
    void productSave() throws Exception {
        //given
        ProductsRequestDTO request = ProductsRequestDTO.builder()
                .productName("향수")
                .productPrice(20000)
                .categories(STUFF)
                .stockQuantity(1)
                .build();

        // when
        ProductsResponseDTO responseDTO = productService.productSave(request);

        //then
        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(1)
                .extracting(
                "productName","productPrice")
                .contains(tuple("향수",20000));

    }


    @Test
    @DisplayName("요청에 따른 정보로 업데이트를 진행한다.")
    void productUpdate() throws Exception{
        //given
        ProductsRequestDTO request = ProductsRequestDTO.builder()
                .id(1L)
                .productName("향수")
                .productPrice(20000)
                .categories(STUFF)
                .stockQuantity(1)
                .build();
        productService.productSave(request);

        ProductsRequestDTO updateRequest = ProductsRequestDTO.builder()
                .id(1L)
                .productScript("수정으로인해 추가된 스크립트.")
                .productName("조말론")
                .productPrice(10000)
                .stockQuantity(2)
                .build();
        //when
        productService.productUpdate(updateRequest);
        //then
        List<Products> products = productsRepository.findAll();
        Assertions.assertThat(products).extracting(
                "productPrice","stockQuantity"
        ).contains(tuple(10000,2));
    }

}