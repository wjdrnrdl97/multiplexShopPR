package backend.shop.com.multiplexshop.domain.products.controller;

import backend.shop.com.multiplexshop.domain.ControllerTestSupport;
import backend.shop.com.multiplexshop.domain.products.dto.UploadFileDTOs;
import backend.shop.com.multiplexshop.domain.products.dto.UploadFileDTOs.UploadFileResponseDTO;
import backend.shop.com.multiplexshop.domain.products.entity.Categories;
import backend.shop.com.multiplexshop.domain.products.entity.Products;
import backend.shop.com.multiplexshop.domain.products.entity.UploadFile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static backend.shop.com.multiplexshop.domain.products.dto.ProductsDTOs.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



class ProductsAPIControllerTest extends ControllerTestSupport {


    @Test
    @DisplayName("클라이언트의 상품 등록 요청에 HTTP 201번 상태코드로 응답에 성공한다.")
    void postProducts() throws Exception {
        //given
        byte[] testByte = null;
        MultipartFile mockMultipartFile = new MockMultipartFile("테스트","test.png",null, testByte);
        uploadService.createUploadFileByRequest(mockMultipartFile);
        uploadService.createUploadFileByRequest(mockMultipartFile);
        String url = "/api/products?ids=1&ids=2";
        ProductsRequestDTO requestDTO
                = ProductsRequestDTO.builder()
                .stockQuantity(1)
                .productName("향수")
                .productPrice(10000)
                .categories(Categories.FOOD)
                .productScript("설명")
                .detailImagePath("test.png")
                .imagePath("test.png")
                .build();

        // when //then
        mockMvc.perform(
                post(url)
                        .content(objectMapper.writeValueAsString(requestDTO))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated());

    }

    @Test
    @DisplayName("클라이언트의 상품 수정 요청에 HTTP 204번 상태 코드로 응답에 성공한다.")
    void updateProductByRequest() throws Exception {
        //given
        String url = "/api/products";

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

        // when //then
        mockMvc.perform(
                        put(url)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }
    @Test
    @DisplayName("클라이언트의 상품 및 업로드파일 수정 요청에 HTTPS 204번 상태코드 응답에 성공한다.")
    public void updateProductsWithImagePathByRequest() throws Exception {
        //given
        byte[] testByte = null;
        MultipartFile mockMultipartFile = new MockMultipartFile("테스트","test.png",null, testByte);
        uploadService.createUploadFileByRequest(mockMultipartFile);
        UploadFileResponseDTO updateThumbnailImage = uploadService.createUploadFileByRequest(mockMultipartFile);
        UploadFileResponseDTO updateDetailImage = uploadService.createUploadFileByRequest(mockMultipartFile);

        String uri = "/api/productsAndImage?ids="+updateThumbnailImage.getId()+"&ids="+updateDetailImage.getId();

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
                .imagePath(updateThumbnailImage.getStoreFileName())
                .detailImagePath(updateDetailImage.getStoreFileName())
                .productScript("설명")
                .categories(Categories.STUFF)
                .build();
        //when //then
        mockMvc.perform(
                put(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("클라이언트의 상품 삭제 요청에 HTTP 204번 상태코드로 응답에 성공한다. ")
    void deleteProducts() throws Exception {
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

        String url = "/api/products/{id}";


        // when  //then
        mockMvc.perform(delete(url, 1L))
                .andDo(print())
                .andExpect(status().isNoContent());


    }

}