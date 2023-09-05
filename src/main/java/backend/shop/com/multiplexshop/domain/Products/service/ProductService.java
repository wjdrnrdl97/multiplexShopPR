package backend.shop.com.multiplexshop.domain.Products.service;

import backend.shop.com.multiplexshop.domain.Products.dto.ProductsDTOs;
import backend.shop.com.multiplexshop.domain.Products.entity.Products;

import static backend.shop.com.multiplexshop.domain.Products.dto.ProductsDTOs.*;

public interface ProductService {

    public Products productSave(ProductsRequestDTO productsRequestDTO);
}
