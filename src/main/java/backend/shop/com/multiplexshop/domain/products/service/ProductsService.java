package backend.shop.com.multiplexshop.domain.Products.service;


import backend.shop.com.multiplexshop.domain.Products.dto.ProductsDTOs;
import backend.shop.com.multiplexshop.domain.Products.entity.Categories;
import backend.shop.com.multiplexshop.domain.Products.entity.Products;
import backend.shop.com.multiplexshop.domain.Products.entity.UploadFile;
import backend.shop.com.multiplexshop.domain.Products.repository.ProductsRepository;
import backend.shop.com.multiplexshop.domain.Products.repository.UploadFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static backend.shop.com.multiplexshop.domain.Products.dto.ProductsDTOs.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ProductsService {

    private final ProductsRepository productsRepository;
    private final UploadFileRepository uploadFileRepository;

    @Transactional
    public ProductsResponseDTO productSaveByRequest(ProductsRequestDTO request) {
        Products products = request.toEntity(request);
        Products savedProduct = productsRepository.save(products);
        ProductsResponseDTO productsResponseDTO = ProductsResponseDTO.of(products);

        addProductNumberToUploadfile(savedProduct);

        return productsResponseDTO;
    }


    private void addProductNumberToUploadfile(Products productsEntity) {
        List<UploadFile> uploadFilesByProductName
                = uploadFileRepository.findAllByProductName(productsEntity.getProductName());

        productsEntity.addImagePath(uploadFilesByProductName.get(0));

        List<UploadFile> uploadFileAddedProduct = uploadFilesByProductName.stream()
                .map(uploadFile -> uploadFile.updateProductId(productsEntity))
                .toList();

        uploadFileRepository.saveAll(uploadFileAddedProduct);
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
        deleteImageFilesByProduct(productId);

        Products deleteProduct = findProductById(productId);
        productsRepository.delete(deleteProduct);
    }

    private void deleteImageFilesByProduct(Long productId) {
        Products productById = findProductById(productId);
        List<UploadFile> byProducts = uploadFileRepository.findByProducts(productById);
        uploadFileRepository.deleteAll(byProducts);
    }

    public Products findProductById(Long productId) {
        return productsRepository.findById(productId)
                .orElseThrow(()-> new IllegalArgumentException("잘못된 상품 번호입니다. : "+productId));
    }

    public List<UploadFile> findUploadFile(Long productId){
        Products products = findProductById(productId);
        return uploadFileRepository.findByProducts(products);
    }


    public Page<ProductsResponseDTO> findAllProductsByCategories(Categories categories, int page) {
        int pageNum = (page == 0)? 0 : page - 1;
        PageRequest pageAble = PageRequest.of(pageNum, 6, Sort.by("id").descending());

        Page<Products> productsByCategories =
                productsRepository.findAllByCategories(categories, pageAble);

        return productsByCategories.map(ProductsResponseDTO::of);
    }
}
