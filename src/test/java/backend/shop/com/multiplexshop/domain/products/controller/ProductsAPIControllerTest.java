package backend.shop.com.multiplexshop.domain.products.controller;

import backend.shop.com.multiplexshop.domain.ControllerTestSupport;
import backend.shop.com.multiplexshop.domain.products.entity.Categories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static backend.shop.com.multiplexshop.domain.products.dto.ProductsDTOs.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



class ProductsAPIControllerTest extends ControllerTestSupport {


    @Test
    @DisplayName("클라이언트의 상품 등록 요청에 201번 상태코드로 응답에 성공한다.")
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

        // when//then
        mockMvc.perform(
                post(url)
                        .content(objectMapper.writeValueAsString(requestDTO))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated());

    }


}