package backend.shop.com.multiplexshop.domain.products.controller;

import backend.shop.com.multiplexshop.domain.ControllerTestSupport;
import backend.shop.com.multiplexshop.domain.products.dto.ProductsDTOs;
import backend.shop.com.multiplexshop.domain.products.entity.Categories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static backend.shop.com.multiplexshop.domain.products.dto.ProductsDTOs.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



class ProductsAPIControllerTest extends ControllerTestSupport {


    @Test
    @DisplayName("클라이언트의 상품 등록 요청에 HTTP 201번 상태코드로 응답에 성공한다.")
    void postProducts() throws Exception {
        //given
        String url = "/api/products";
        ProductsRequestDTO requestDTO
                = ProductsRequestDTO.builder()
                .stockQuantity(1)
                .productName("향수")
                .productPrice(10000)
                .categories(Categories.STUFF)
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
    void updateProducts() throws Exception {
        //given
        String url = "/api/products/{id}";
        ProductsRequestDTO requestDTO
                = ProductsRequestDTO.builder()
                .stockQuantity(1)
                .productName("향수")
                .productPrice(10000)
                .categories(Categories.STUFF)
                .build();
        productsService.productSaveByRequest(requestDTO);

        ProductsRequestDTO updateRequest = ProductsRequestDTO.builder()
                .productScript("수정으로인해 추가된 스크립트.")
                .productName("조말론")
                .productPrice(10000)
                .stockQuantity(2)
                .build();


        // when //then
        mockMvc.perform(
                        put(url, 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateRequest))
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }


    @Test
    @DisplayName("클라이언트의 상품 삭제 요청에 HTTP 204번 상태코드로 응답에 성공한다. ")
    void deleteProducts() throws Exception {
        //given
        String url = "/api/products/{id}";
        ProductsRequestDTO requestDTO
                = ProductsRequestDTO.builder()
                .stockQuantity(1)
                .productName("향수")
                .productPrice(10000)
                .categories(Categories.STUFF)
                .build();
        productsService.productSaveByRequest(requestDTO);

        // when //then
        mockMvc.perform(delete(url, 1L))
                .andDo(print())
                .andExpect(status().isNoContent());

    }


}