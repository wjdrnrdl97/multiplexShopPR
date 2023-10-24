package backend.shop.com.multiplexshop.domain.products.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "upload_file_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    private Products products;

    private String productName;
    private String originalFileName;
    private String storeFileName;

    @Builder
    public UploadFile(Long id, Products products, String productName, String originalFileName, String storeFileName) {
        this.id = id;
        this.products = products;
        this.productName = productName;
        this.originalFileName = originalFileName;
        this.storeFileName = storeFileName;
    }

    public UploadFile updateProductId(Products products){
        this.products = products;
        return this;
    }

    public UploadFile(String originalFileName, String storeFileName) {
        this.originalFileName = originalFileName;
        this.storeFileName = storeFileName;
    }
}
