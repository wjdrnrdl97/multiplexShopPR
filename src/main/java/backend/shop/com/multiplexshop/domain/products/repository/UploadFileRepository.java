package backend.shop.com.multiplexshop.domain.products.repository;

import backend.shop.com.multiplexshop.domain.products.entity.Products;
import backend.shop.com.multiplexshop.domain.products.entity.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UploadFileRepository extends JpaRepository<UploadFile,Long> {

    List<UploadFile> findAllByProducts(Products products);

    List<UploadFile> findByProductsId(Long productId);
}
