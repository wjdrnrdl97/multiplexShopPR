package backend.shop.com.multiplexshop.domain.Products.repository;

import backend.shop.com.multiplexshop.domain.Products.entity.Products;
import backend.shop.com.multiplexshop.domain.Products.entity.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UploadFileRepository extends JpaRepository<UploadFile,Long> {

    List<UploadFile> findAllByProductName(String productName);

    List<UploadFile> findByProducts(Products products);
}
