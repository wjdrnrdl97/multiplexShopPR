package backend.shop.com.multiplexshop.domain.products.controller;


import backend.shop.com.multiplexshop.domain.products.entity.Products;
import backend.shop.com.multiplexshop.domain.products.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static backend.shop.com.multiplexshop.domain.products.dto.ProductsDTOs.*;

@RestController
@RequiredArgsConstructor
public class ProductsAPIController {

    private final ProductsService productsService;
    @PostMapping(value = "/api/products", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductsResponseDTO> postProducts(@RequestBody ProductsRequestDTO productsRequestDTO){
        Products products = productsService.productSave(productsRequestDTO);
        ProductsResponseDTO responseDTO = ProductsResponseDTO.of(products);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }


}
