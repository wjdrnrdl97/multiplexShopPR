package backend.shop.com.multiplexshop.domain.products.service;


import backend.shop.com.multiplexshop.domain.products.entity.Categories;
import backend.shop.com.multiplexshop.domain.products.entity.Products;
import backend.shop.com.multiplexshop.domain.products.entity.UploadFile;
import backend.shop.com.multiplexshop.domain.products.repository.ProductsRepository;
import backend.shop.com.multiplexshop.domain.products.repository.UploadFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static backend.shop.com.multiplexshop.domain.products.dto.ProductsDTOs.*;

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
        PageRequest pageable = PageRequest.of(pageNum, 6, Sort.by("modDate").descending());

        Page<Products> productsByCategories =
                productsRepository.findAllByCategories(categories, pageable);

        return productsByCategories.map(ProductsResponseDTO::of);
    }
    public Page<ProductsResponseDTO>findAllByProductNameContaining(String keyword, int page){
        int pageNum = (page == 0) ? 0 : page - 1;
        PageRequest pageable = PageRequest.of(pageNum, 6, Sort.by(Sort.Direction.DESC, "modDate"));

        Page<Products> productsByNameContaining = productsRepository.findAllByProductNameContaining(keyword, pageable);

        return productsByNameContaining.map(ProductsResponseDTO::of);
    }

    public Page<ProductsResponseDTO>findAllByCategoriesAndProductNameContaining
                                                                    (Categories categories, String keyword, int page){
        int pageNum = (page == 0) ? 0 : page - 1;
        PageRequest pageable = PageRequest.of(pageNum, 6, Sort.by(Sort.Direction.DESC, "modDate"));
        Page<Products> byCategoriesAndProductNameContaining
                        = productsRepository.findAllByCategoriesAndProductNameContaining(categories, keyword, pageable);
        return byCategoriesAndProductNameContaining.map(ProductsResponseDTO::of);
    }

    public Page<ProductsResponseDTO>findAllOfPagination(int page){
        int pageNum = (page == 0) ? 0 : page - 1;
        PageRequest pageable = PageRequest.of(pageNum, 10, Sort.by(Sort.Direction.DESC, "id"));
        Page<Products> allProductOfPagination = productsRepository.findAll(pageable);
        return allProductOfPagination.map(ProductsResponseDTO::of);
    }
}
