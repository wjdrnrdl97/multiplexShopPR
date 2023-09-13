package backend.shop.com.multiplexshop.domain.products.service;


import backend.shop.com.multiplexshop.domain.products.dto.ProductsDTOs;
import backend.shop.com.multiplexshop.domain.products.entity.Products;
import backend.shop.com.multiplexshop.domain.products.repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static backend.shop.com.multiplexshop.domain.products.dto.ProductsDTOs.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductsService {

    private final ProductsRepository productsRepository;

    @Transactional
    public ProductsResponseDTO productSave(ProductsRequestDTO request) {
        Products products = request.toEntity(request);
        Products savedProduct = productsRepository.save(products);

        return ProductsResponseDTO.of(savedProduct);
    }

    @Transactional
    public void productUpdate(ProductsRequestDTO requestDTO) {
        Products products = productsRepository.findById(requestDTO.getId())
                .orElseThrow(()-> new IllegalArgumentException("잘못된 상품 번호입니다."));
        products.updateByRequest(requestDTO);
        productsRepository.save(products);
    }
}
