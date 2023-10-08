package backend.shop.com.multiplexshop.domain.Products.service;


import backend.shop.com.multiplexshop.domain.Products.entity.Products;
import backend.shop.com.multiplexshop.domain.Products.repository.ProductsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static backend.shop.com.multiplexshop.domain.Products.dto.ProductsDTOs.*;
import static backend.shop.com.multiplexshop.domain.Products.entity.Categories.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class ProductsServiceTest {


    @Autowired
    private ProductService productService;

    @Autowired
    private ProductsRepository productsRepository;


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
        assertThat(products).isNotNull().extracting(
                        "productName", "productPrice", "categories", "stockQuantity")
                .contains("향수", 20000, STUFF, 1);

    }
}
