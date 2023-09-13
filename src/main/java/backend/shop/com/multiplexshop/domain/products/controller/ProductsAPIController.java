package backend.shop.com.multiplexshop.domain.products.controller;


import backend.shop.com.multiplexshop.domain.products.entity.Products;
import backend.shop.com.multiplexshop.domain.products.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static backend.shop.com.multiplexshop.domain.products.dto.ProductsDTOs.*;

@RestController
@RequiredArgsConstructor
public class ProductsAPIController {

    private final ProductsService productsService;
    @PostMapping(value = "/api/products", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductsResponseDTO> postProducts(@RequestBody ProductsRequestDTO productsRequestDTO){
        ProductsResponseDTO responseDTO = productsService.productSave(productsRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/api/products/{id}")
    public ResponseEntity updateProducts(@RequestBody ProductsRequestDTO requestDTO){
        productsService.productUpdate(requestDTO);
        return ResponseEntity.noContent().build();
    }

}
