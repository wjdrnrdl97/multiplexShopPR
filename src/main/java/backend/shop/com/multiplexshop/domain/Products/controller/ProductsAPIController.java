package backend.shop.com.multiplexshop.domain.Products.controller;


import backend.shop.com.multiplexshop.domain.Products.dto.ProductsDTOs;
import backend.shop.com.multiplexshop.domain.Products.entity.Products;
import backend.shop.com.multiplexshop.domain.Products.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static backend.shop.com.multiplexshop.domain.Products.dto.ProductsDTOs.*;

@RestController
@RequiredArgsConstructor
public class ProductsAPIController {

    private final ProductService productService;
    @PostMapping(value = "/api/products", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductsResponseDTO> postProducts(@RequestBody ProductsRequestDTO productsRequestDTO){
        Products products = productService.productSave(productsRequestDTO);
        ProductsResponseDTO responseDTO = ProductsResponseDTO.of(products);

        return ResponseEntity.ok().body(responseDTO);
    }


}
