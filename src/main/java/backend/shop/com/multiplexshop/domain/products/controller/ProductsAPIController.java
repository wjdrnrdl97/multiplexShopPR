package backend.shop.com.multiplexshop.domain.products.controller;

import backend.shop.com.multiplexshop.domain.common.GlobalCommonResponseCode;
import backend.shop.com.multiplexshop.domain.common.GlobalCommonResponseDTO;
import backend.shop.com.multiplexshop.domain.products.service.ProductsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

import static backend.shop.com.multiplexshop.domain.products.dto.ProductsDTOs.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductsAPIController {

    private final ProductsService productsService;

    @PostMapping(value = "/api/products")
    public ResponseEntity<GlobalCommonResponseDTO> postProductByRequest(@RequestParam("ids")List<Long> ids,
                                                                    @RequestBody @Valid ProductsRequestDTO productsRequestDTO
                                                                                        , BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            HashMap<String, String> errors = productsService.validateHandling(bindingResult);
            GlobalCommonResponseDTO<Object> errorResponse = GlobalCommonResponseDTO.builder()
                    .code(GlobalCommonResponseCode.BAD_REQUEST.getCode())
                    .message("validation error")
                    .data(errors)
                    .build();
            return ResponseEntity.badRequest().body(errorResponse);
        }
        ProductsResponseDTO responseDTO = productsService.createProductByRequest(productsRequestDTO, ids);
        GlobalCommonResponseDTO<Object> successResponse = GlobalCommonResponseDTO.builder()
                .code(GlobalCommonResponseCode.SUCCESS.getCode())
                .message("validation error")
                .data(responseDTO)
                .build();
        log.info("dto = {}", responseDTO.getProductScript());
        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }

    @PutMapping(value = "/api/products", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateProductByRequest(@RequestBody ProductsRequestDTO request){
        productsService.updateProductByRequest(request);
        return ResponseEntity.noContent().build();
    }
    @PutMapping(value = "/api/productsAndImage", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateProductsWithImagePathByRequest
                                                (@RequestParam List<Long> ids, @RequestBody ProductsRequestDTO request){
        productsService.updateProductAndUploadFileByRequestAndIds(request,ids);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/api/products/{id}")
    public ResponseEntity deleteProducts(@PathVariable("id") Long productId){
        productsService.deleteProductById(productId);
        return ResponseEntity.noContent().build();
    }

}
