package backend.shop.com.multiplexshop.domain.products.service;

import backend.shop.com.multiplexshop.domain.IntegrationTestSupport;
import backend.shop.com.multiplexshop.domain.products.entity.Products;
import backend.shop.com.multiplexshop.domain.products.entity.UploadFile;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static backend.shop.com.multiplexshop.domain.products.dto.ProductsDTOs.*;
import static backend.shop.com.multiplexshop.domain.products.entity.Categories.*;
import static org.assertj.core.api.Assertions.*;

@Sql("classpath:test.sql")
class ProductsServiceTest extends IntegrationTestSupport {

    @BeforeEach
    public void beforeEach(){
        productsRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("상품 등록 요청에 의한 상품 등록이 완료되어야 한다.")
    void productSaveByRequest() throws Exception {
        //given
        ProductsRequestDTO request = ProductsRequestDTO.builder()
                .productName("향수")
                .productPrice(20000)
                .categories(STUFF)
                .stockQuantity(1)
                .build();

        productService.productSaveByRequest(request);

        //then
        List<Products> products = productsRepository.findAll();
        assertThat(products).hasSize(1)
                .extracting(
                "id","productName","productPrice")
                .contains(tuple(1L,"향수",20000));
    }


    @Test
    @DisplayName("클라이언트 요청에 따른 정보로 상품 정보를 업데이트를 진행한다.")
    void productUpdateByRequestAndId() throws Exception{
        //given
        ProductsRequestDTO request = ProductsRequestDTO.builder()
                .productName("향수")
                .productPrice(20000)
                .categories(STUFF)
                .stockQuantity(1)
                .build();

        productService.productSaveByRequest(request);

        ProductsRequestDTO updateRequest = ProductsRequestDTO.builder()
                .productScript("수정으로인해 추가된 스크립트.")
                .productName("조말론")
                .productPrice(10000)
                .stockQuantity(2)
                .build();
        //when
        productService.productUpdateByRequestAndId(updateRequest,1L);
        //then
        List<Products> products = productsRepository.findAll();
        Assertions.assertThat(products).extracting(
                "productPrice","stockQuantity"
        ).contains(tuple(10000,2));
    }



    @Test
    @DisplayName("상품 번호에 따른 해당 상품을 삭제한다.")
    void deleteProductById() throws Exception {
        //given
        ProductsRequestDTO request = ProductsRequestDTO.builder()
                .productName("향수")
                .productPrice(20000)
                .categories(STUFF)
                .stockQuantity(1)
                .build();

        productService.productSaveByRequest(request);
        // when
        productService.deleteProductById(1L);
        //then

        List<Products> products = productsRepository.findAll();
        assertThat(products).isEmpty();

    }

//    private ProductsResponseDTO createProductForTest(String productName, int productPrice, int quantity) {
//        ProductsRequestDTO request = ProductsRequestDTO.builder()
//                .id(1L)
//                .productName(productName)
//                .productPrice(productPrice)
//                .categories(STUFF)
//                .stockQuantity(quantity)
//                .build();
//        return productService.productSaveByRequest(request);
//    }


    @Transactional
    @Test
    @DisplayName("요청에의해 상품이 등록되고 등록된 상품의 번호가 업로드파일 DB에 저장되야한다")
    public void productSaveByRequestWithUploadFile(){
        //given
        String productName = "향수";

        UploadFile uploadFile = UploadFile.builder()
                .productName(productName)
                .build();
        UploadFile uploadFile2 = UploadFile.builder()
                .productName(productName)
                .build();
        uploadFileRepository.saveAll(List.of(uploadFile, uploadFile2));

        ProductsRequestDTO request = ProductsRequestDTO.builder()
                .productName("향수")
                .productPrice(20000)
                .categories(STUFF)
                .stockQuantity(1)
                .build();


        //when
        productService.productSaveByRequest(request);

        //then
        List<UploadFile> uploadFiles = uploadFileRepository.findAll();

        Products products = productsRepository.findById(1L).get();

        assertThat(uploadFiles.get(0).getProducts()).isEqualTo(products);
    }

}
