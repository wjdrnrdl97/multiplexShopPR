package backend.shop.com.multiplexshop.domain.products.service;

import backend.shop.com.multiplexshop.domain.IntegrationTestSupport;
import backend.shop.com.multiplexshop.domain.products.entity.Products;
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
        Products products = productService.productSave(request);

        //then
        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(1)
                .extracting(
                "productName","productPrice")
                .contains(tuple("향수",20000));

    }


}