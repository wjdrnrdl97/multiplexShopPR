package backend.shop.com.multiplexshop.domain.products.repository;

import backend.shop.com.multiplexshop.domain.products.entity.Categories;
import backend.shop.com.multiplexshop.domain.products.entity.Products;
import backend.shop.com.multiplexshop.domain.products.entity.UploadFile;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ListAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class UploadFileRepositoryTest {

    @Autowired
    private UploadFileRepository uploadFileRepository;
    @Autowired
    private ProductsRepository productsRepository;

    @Test
    @DisplayName("상품을 입력받아 해당 상품의 모든 업로드 파일을 조회한다.")
    public void findAllByProducts(){
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
        UploadFile uploadFile = UploadFile.builder()
                .originalFileName("향수")
                .storeFileName("썸네일")
                .products(findProduct)
                .build();
        uploadFileRepository.save(uploadFile);
        UploadFile uploadFile2 = UploadFile.builder()
                .originalFileName("음식")
                .storeFileName("썸네일")
                .products(findProduct)
                .build();
        uploadFileRepository.save(uploadFile2);
        //when
        List<UploadFile> result = uploadFileRepository.findByProductsId(1L);
        //then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getStoreFileName()).isEqualTo("썸네일");
    }
    @Test
    @DisplayName("상품을 입력받아 해당 상품의 모든 업로드 파일을 조회한다.")
    public void findByProductsId(){
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
        UploadFile uploadFile = UploadFile.builder()
                .originalFileName("향수")
                .storeFileName("썸네일")
                .products(findProduct)
                .build();
        uploadFileRepository.save(uploadFile);
        UploadFile uploadFile2 = UploadFile.builder()
                .originalFileName("음식")
                .storeFileName("썸네일")
                .products(findProduct)
                .build();
        uploadFileRepository.save(uploadFile2);
        //when
        List<UploadFile> result = uploadFileRepository.findByProductsId(1L);
        //then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getStoreFileName()).isEqualTo("썸네일");
    }
}