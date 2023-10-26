package backend.shop.com.multiplexshop.domain.products.service;


import backend.shop.com.multiplexshop.domain.exception.BadImageException;
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
    public ProductsResponseDTO createProductByRequest(ProductsRequestDTO request, List<Long> imageIds) {
        Products products = request.toEntity(request);
        Products savedProduct = productsRepository.save(products);
        ProductsResponseDTO productsResponseDTO = ProductsResponseDTO.of(products);

        addProductToUploadFile(savedProduct, imageIds);

        return productsResponseDTO;
    }


    private void addProductToUploadFile(Products productsEntity, List<Long> imageIds) {
        for(Long id : imageIds){
            UploadFile uploadFile = uploadFileRepository.findById(id)
                                    .orElseThrow(() -> new BadImageException("잘못된 이미지입니다."));
            uploadFile.updateProduct(productsEntity);
            uploadFileRepository.save(uploadFile);
        }
    }

    @Transactional
    public void updateProductByRequest(ProductsRequestDTO request){
        Long requestProductsId = request.getId();
        Products updateProduct = productsRepository.findById(requestProductsId)
                       .orElseThrow(() -> new IllegalArgumentException("잘못된 상품 번호입니다. : " + requestProductsId));
        updateProduct.updateByRequest(request);
        productsRepository.save(updateProduct);
    }

    @Transactional
    public void updateProductAndUploadFileByRequestAndIds(ProductsRequestDTO request,List<Long> imageIds){
        Long requestProductsId = request.getId();
        Products updateProduct = productsRepository.findById(requestProductsId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 상품 번호입니다. : " + requestProductsId));
        updateProduct.updateByRequest(request);
        Products saveProductOfUpdate = productsRepository.save(updateProduct);
        addProductToUploadFile(saveProductOfUpdate,imageIds);
    }

    @Transactional
    public void deleteProductById(Long productId) {
        deleteImageFilesByProduct(productId);

        Products deleteProduct = productsRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 상품 번호입니다. : " + productId));
        productsRepository.delete(deleteProduct);
    }

    private void deleteImageFilesByProduct(Long productId) {
        Products findProductById = productsRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 상품 번호입니다. : " + productId));
        List<UploadFile> byProducts = uploadFileRepository.findByProductsId(productId);
        uploadFileRepository.deleteAll(byProducts);
    }

    public ProductsResponseDTO findProductByRequest(Long productId){
        Products findProductById = productsRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 상품 번호입니다. : " + productId));
        return ProductsResponseDTO.of(findProductById);
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
