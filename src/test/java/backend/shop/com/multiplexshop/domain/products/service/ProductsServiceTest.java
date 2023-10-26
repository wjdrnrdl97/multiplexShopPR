package backend.shop.com.multiplexshop.domain.products.service;

import backend.shop.com.multiplexshop.domain.IntegrationTestSupport;
import backend.shop.com.multiplexshop.domain.products.dto.ProductsDTOs;
import backend.shop.com.multiplexshop.domain.products.dto.ProductsDTOs.ProductsResponseDTO;
import backend.shop.com.multiplexshop.domain.products.entity.Categories;
import backend.shop.com.multiplexshop.domain.products.entity.Products;
import backend.shop.com.multiplexshop.domain.products.entity.UploadFile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static backend.shop.com.multiplexshop.domain.products.dto.ProductsDTOs.*;
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
    @Test
    @DisplayName("요청에 따라(상품번호) 해당 상품을 조회에 성공한다.")
    public void findProductByRequest(){
        //given
        Products products = Products.builder()
                .productName("향수")
                .productPrice(10000)
                .stockQuantity(100)
                .imagePath("썸네일")
                .detailImagePath("상세이미지")
                .categories(Categories.STUFF)
                .build();
        Products findProduct = productsRepository.save(products);
        //when
        ProductsResponseDTO result = productService.findProductByRequest(1L);
        //then
        assertThat(result).isNotNull();
        assertThat(result.getProductName()).isEqualTo("향수");
    }
    @Test
    @DisplayName("요청에 따라 상품을 조회하여 상품 내용을 수정 후 저장한다.")
    public void updateProductByRequest(){
        //given
        Products products = Products.builder()
                .productName("향수")
                .productPrice(10000)
                .stockQuantity(100)
                .imagePath("썸네일")
                .detailImagePath("상세이미지")
                .categories(Categories.STUFF)
                .build();
        productsRepository.save(products);
        ProductsRequestDTO request = ProductsRequestDTO.builder()
                .id(1L)
                .productName("수정")
                .productPrice(5000)
                .imagePath("썸네일")
                .detailImagePath("상세이미지")
                .productScript("설명")
                .categories(Categories.STUFF)
                .build();
        //when
        productService.updateProductByRequest(request);
        //then
        ProductsResponseDTO result = productService.findProductByRequest(1L);
        assertThat(result).isNotNull();
        assertThat(result.getProductName()).isEqualTo("수정");
    }

    @Test
    @DisplayName("상품을 생성하고 해당 상품을 업로드파일의 상품컬럼에 값을 insert한다.")
    public void productSaveByRequest(){
        //given
        UploadFile thumbnail = UploadFile.builder()
                .storeFileName("썸네일")
                .originalFileName("원본")
                .build();
        UploadFile saveThumbnail = uploadFileRepository.save(thumbnail);
        UploadFile detail = UploadFile.builder()
                .storeFileName("상세이미지")
                .originalFileName("원본")
                .build();
        UploadFile saveDetail = uploadFileRepository.save(detail);
        ProductsRequestDTO request = ProductsRequestDTO.builder()
                .productName("향수")
                .productPrice(5000)
                .stockQuantity(100)
                .imagePath("썸네일")
                .detailImagePath("상세이미지")
                .productScript("설명")
                .categories(Categories.STUFF)
                .build();
        List<Long> imageIds = Arrays.asList(saveThumbnail.getId(), saveDetail.getId());
        //when
        ProductsResponseDTO result = productService.createProductByRequest(request, imageIds);
        UploadFile uploadFile = uploadFileRepository.findById(saveThumbnail.getId()).orElse(null);
        //then
        assertThat(result).isNotNull();
        assertThat(uploadFile.getProducts().getId()).isEqualTo(1L);
    }
    @Test
    @DisplayName("상품을 수정하고 수정된 상품을 업로드파일의 상품도 주입 및 수정한다.")
    @Transactional
    public void updateProductAndUploadFileByRequestAndIds(){
        //given
        Products products = Products.builder()
                .productName("향수")
                .productPrice(10000)
                .stockQuantity(100)
                .imagePath("썸네일")
                .detailImagePath("상세이미지")
                .categories(Categories.STUFF)
                .build();
        Products saveProduct = productsRepository.save(products);

        UploadFile thumbnail = UploadFile.builder()
                .storeFileName("썸네일")
                .originalFileName("원본")
                .products(saveProduct)
                .build();
        UploadFile saveThumbnail = uploadFileRepository.save(thumbnail);
        UploadFile detail = UploadFile.builder()
                .storeFileName("상세이미지")
                .originalFileName("원본")
                .products(saveProduct)
                .build();
        UploadFile saveDetail = uploadFileRepository.save(detail);
        UploadFile updateDetail = UploadFile.builder()
                .storeFileName("수정한 상세이미지")
                .originalFileName("원본")
                .build();
        UploadFile saveUpdateDetail = uploadFileRepository.save(updateDetail);

        ProductsRequestDTO request = ProductsRequestDTO.builder()
                .id(1L)
                .productName("수정한 향수")
                .productPrice(5000)
                .imagePath("썸네일")
                .detailImagePath("수정한 상세이미지")
                .productScript("수정한 설명")
                .categories(Categories.STUFF)
                .build();
        List<Long> imageIds = Arrays.asList(saveThumbnail.getId(), saveUpdateDetail.getId());
        //when
        productService.updateProductAndUploadFileByRequestAndIds(request,imageIds);
        //then
        Products resultProduct = productsRepository.findById(1L).orElse(null);
        UploadFile resultUploadFile = uploadFileRepository.findById(3L).orElse(null);
        assertThat(resultProduct.getDetailImagePath()).isEqualTo(request.getDetailImagePath());
        assertThat(resultUploadFile.getProducts()).isEqualTo(saveProduct);
    }
    @Test
    @DisplayName("상품번호를 입력받아 해당 상품의 업로드파일을 모두 삭제하고 그다음 해당 상품을 삭제한다. ")
    public void deleteProductById(){
        //given
        Products products = Products.builder()
                .productName("향수")
                .productPrice(10000)
                .stockQuantity(100)
                .imagePath("썸네일")
                .detailImagePath("상세이미지")
                .categories(Categories.STUFF)
                .build();
        Products saveProduct = productsRepository.save(products);
        UploadFile thumbnail = UploadFile.builder()
                .storeFileName("썸네일")
                .originalFileName("원본")
                .products(saveProduct)
                .build();
        uploadFileRepository.save(thumbnail);
        UploadFile detail = UploadFile.builder()
                .storeFileName("상세이미지")
                .originalFileName("원본")
                .products(saveProduct)
                .build();
        uploadFileRepository.save(detail);
        UploadFile updateDetail = UploadFile.builder()
                .storeFileName("전 상세이미지")
                .originalFileName("원본")
                .products(saveProduct)
                .build();
        uploadFileRepository.save(updateDetail);
        //when
        productService.deleteProductById(1L);
        //then
        List<UploadFile> uploadFileResult = uploadFileRepository.findAll();
        List<Products> productResult = productsRepository.findAll();
        assertThat(uploadFileResult).isEmpty();
        assertThat(productResult).isEmpty();
    }
}