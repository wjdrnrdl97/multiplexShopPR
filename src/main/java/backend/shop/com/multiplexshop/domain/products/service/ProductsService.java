package backend.shop.com.multiplexshop.domain.products.service;


import backend.shop.com.multiplexshop.domain.products.entity.Products;
import backend.shop.com.multiplexshop.domain.products.repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static backend.shop.com.multiplexshop.domain.products.dto.ProductsDTOs.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ProductsService {

    private final ProductsRepository productsRepository;

    @Transactional
    public ProductsResponseDTO productSaveByRequest(ProductsRequestDTO request) {
        Products products = request.toEntity(request);
        return ProductsResponseDTO.of(productsRepository.save(products));
    }

    @Transactional
    public void productUpdateByRequestAndId(ProductsRequestDTO requestDTO, Long productId) {
        Products updateProduct = findProductById(productId);
        log.debug("product id = {}",updateProduct.getId());
        updateProduct.updateByRequest(requestDTO);
        productsRepository.save(updateProduct);
    }

    @Transactional
    public void deleteProductById(Long productId) {
        Products deleteProduct = findProductById(productId);
        productsRepository.delete(deleteProduct);
    }

    private Products findProductById(Long productId) {
        return productsRepository.findById(productId)
                .orElseThrow(()-> new IllegalArgumentException("잘못된 상품 번호입니다. : "+productId));
    }
}
