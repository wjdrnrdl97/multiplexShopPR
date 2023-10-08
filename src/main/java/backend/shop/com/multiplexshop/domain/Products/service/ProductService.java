package backend.shop.com.multiplexshop.domain.Products.service;

import backend.shop.com.multiplexshop.domain.Products.entity.Products;
import backend.shop.com.multiplexshop.domain.Products.repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static backend.shop.com.multiplexshop.domain.Products.dto.ProductsDTOs.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductsRepository productsRepository;
    @Transactional
    public Products productSave(ProductsRequestDTO request) {
        Products products = request.toEntity(request);
        return productsRepository.save(products);
    }

    }

